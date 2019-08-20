package br.com.zup.shoppingcart.application


import br.com.zup.shoppingcart.domain.Cart
import br.com.zup.shoppingcart.domain.ItemCart
import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.infra.exception.FieldValidator
import br.com.zup.shoppingcart.repository.CartDAO
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ItemsCartDAO
import br.com.zup.shoppingcart.repository.ProductDAO
import br.com.zup.shoppingcart.repository.UserDAO
import java.sql.Connection
import java.util.ArrayList


class CartService(
    private val jdbc: ConnectionFactory,
    private val factory: DAOFactory
) {

    fun getQuantityOld(idItem: String, connection: Connection): Int{
        val itemCartDao: ItemsCartDAO =
                factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO
        return try {
            val itemCartOld = itemCartDao.get(idItem)
            itemCartOld.quantity
        }catch (ex: Exception){
            0
        }
    }

    fun add(userId: String, itemCart: ItemCart) {
        FieldValidator.validate(itemCart)

        var idCart = ""
        var idItemCartAdd = ""

        val connection = jdbc.getConnection()

        val userCart: User
        try {
            val quantityOld = getQuantityOld(itemCart.id!!, connection)

            val userDAO: UserDAO =
                factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            userCart = userDAO.get(userId)

            validateQuantity(itemCart.quantity)
            validateInventoryProduct(itemCart.product_id, itemCart.quantity, connection)

            if (userCart.cart_id != "") {

                idCart = userCart.cart_id.toString()
            }

            if (idCart == "") {

                val itemCartDao: ItemsCartDAO =
                        factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO

                itemCartDao.add(itemCart)

            } else {
                idItemCartAdd = groupItemsCart(idCart, itemCart, connection)
            }

            var idItem = itemCart.id.toString()


            var totalPrice = calculatePriceItem(itemCart.price_unit_product, itemCart.quantity)

            if (idCart == "") {
                addCart(idItem, userCart, userId, totalPrice, connection)

            } else if (idCart != "") {

                val totalPriceInCart = recalculateTotalPrice(idCart, connection)

                totalPrice += totalPriceInCart

                if (idItemCartAdd != ""){ idItem = idItemCartAdd }


                editCart(idCart, idItem, totalPrice, connection)
            }

            updateQuantityProduct(itemCart.product_id, itemCart.quantity, quantityOld, "-", connection)

            connection.commit()

        } catch (e: Exception) {

            connection.rollback()
            throw e

        } finally {
            jdbc.closeConnection()
        }
    }


    private fun groupItemsCart(idCart: String, itemCart: ItemCart, connection: Connection): String {

        var idItemCart = ""

        try {

            val product = itemCart.product_id

            val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, jdbc.getConnection()) as CartDAO
            val listItems = cartDao.get(idCart).items

            val itemCartDao: ItemsCartDAO =
                factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO


            for (idItems in listItems) {

                if (product == itemCartDao.get(idItems).product_id && itemCartDao.get(idItems).deleted == false) {

                    idItemCart = itemCartDao.get(idItems).id.toString()

                }
            }
            if (idItemCart != "") {

                val itemCartUpdB = itemCartDao.get(idItemCart)

                val quantity = itemCart.quantity + itemCartUpdB.quantity

                val itemCartUpdA =
                    ItemCart(idItemCart, itemCartUpdB.product_id, itemCartUpdB.price_unit_product, quantity)

                itemCartDao.edit(itemCartUpdA)

            } else {
                itemCartDao.add(itemCart)
            }

            connection.commit()


        } catch (e: Exception) {

            connection.rollback()
            throw e

        }

        return idItemCart

    }

    fun remove(idItemCart: String) {

        val connection = jdbc.getConnection()

        try {
            val itemCartDao: ItemsCartDAO =
                factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO
            val quantityOld = getQuantityOld(idItemCart, connection)

            val itemCart = itemCartDao.get(idItemCart)

            updateQuantityProduct(itemCart.product_id, itemCart.quantity, quantityOld, "+", connection)

            itemCartDao.delete(idItemCart)

            connection.commit()

        } catch (e: Exception) {

            connection.rollback()
            throw e

        } finally {

            jdbc.closeConnection()

        }

    }

    fun edit(itemCart: ItemCart) {

        val connection = jdbc.getConnection()

        val operator = getOperatorStock(itemCart.id.toString(), itemCart.quantity, connection)

        try {
            val itemCartDao: ItemsCartDAO =
                    factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO
            val quantityOld = getQuantityOld(itemCart.id!!, connection)

            validateQuantity(itemCart.quantity)
            validateInventoryProduct(itemCart.product_id, itemCart.quantity, connection)

            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO


            if (operator != "") {
                val product = productDAO.get(itemCart.product_id)

                updateQuantityProduct(product.id.toString(), itemCart.quantity, quantityOld, operator, connection)
            }

            itemCartDao.edit(itemCart)
            connection.commit()

        } catch (e: Exception) {

            connection.rollback()
            throw e

        } finally {

            jdbc.closeConnection()

        }
    }

    fun get(idUser: String): ArrayList<ItemCart> {

        val connection = jdbc.getConnection()

        try {

            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO
            val idCart = userDAO.get(idUser).cart_id!!

            val itemCartDao: ItemsCartDAO =
                factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO
            return itemCartDao.listItemCart(idCart)

        } catch (e: Exception) {

            throw e

        } finally {

            jdbc.closeConnection()

        }
    }


    private fun getOperatorStock(idItemCart: String, quantity: Int, connection: Connection): String {

        try {
            val itemCartDao: ItemsCartDAO =
                factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO

            val quantityBefore = itemCartDao.get(idItemCart).quantity

            return when {
                quantityBefore > quantity -> "+"
                quantityBefore < quantity -> "-"
                else -> "a"
            }
        } catch (e: Exception) {

            throw e

        }
    }

    private fun updateQuantityProduct(idProduct: String, quantity: Int, quantityOld: Int, operator: String, connection: Connection) {

        try {
            val quantityReal = when {
                quantity == quantityOld -> quantity
                quantityOld > quantity -> quantityOld - quantity
                else -> quantity - quantityOld
            }

            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            val product = productDAO.get(idProduct)

            var newQuantity = 0

            if (operator == "-") {
                newQuantity = product.quantity - quantityReal
            } else if (operator == "+") {
                newQuantity = product.quantity + quantityReal
            }

            val productUpdate = Product(idProduct, product.name, product.price, product.unit, newQuantity)

            productDAO.edit(productUpdate)
            connection.commit()

        } catch (e: Exception) {
            connection.rollback()
            throw e

        }
    }

    private fun validateQuantity(quantity: Int) {
        if (quantity <= 0) {
            throw Exception("Quantity is zero!")
        }
    }

    private fun validateInventoryProduct(idProduct: String, quantity: Int, connection: Connection) {

        try {
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            val quantityProduct = productDAO.get(idProduct).quantity

            if (quantityProduct - quantity < 0) {
                throw Exception("Product has no quantity in stock")
            }
        } catch (e: Exception) {

            throw e

        }
    }

    fun calculatePriceItem(priceUnit: Int, quantity: Int): Int {
        return priceUnit * quantity
    }

    private fun addCart(idItem: String, userCart: User, userId: String, totalPrice: Int, connection: Connection) {

        try {
            val listItem = ArrayList<String>()

            listItem.add(idItem)

            val cart = Cart(items = listItem, user_id = userId, total_price = totalPrice)

            val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, connection) as CartDAO
            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            val userUpdate = User(userId, userCart.name, userCart.email, userCart.password, userCart.deleted, cart.id)

            cartDao.add(cart)

            userDAO.edit(userUpdate)

            connection.commit()


        } catch (e: Exception) {
            connection.rollback()
            throw e

        }
    }

    private fun editCart(idCart: String, idItem: String, totalPrice: Int, connection: Connection) {

        try {

            val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, connection) as CartDAO

            val cart = cartDao.get(idCart)

            val items = cart.items

            items.add(idItem)

            val cartUpdate = Cart(idCart, items, cart.user_id, totalPrice)

            cartDao.edit(cartUpdate)

            connection.commit()

        } catch (e: Exception) {

            connection.rollback()

            throw e

        }
    }

    private fun recalculateTotalPrice(idCart: String, connection: Connection): Int {

        try {
            val itemCartDao: ItemsCartDAO =
                factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO

            val listItemCart = itemCartDao.listItemCart(idCart)

            var totalPrice = 0

            for (itemCart in listItemCart) {

                val unitPrice = itemCart.price_unit_product
                val quantity = itemCart.quantity
                val itemPrice = (unitPrice * quantity)

                totalPrice += itemPrice
            }
            return totalPrice

        } catch (e: Exception) {

            connection.rollback()
            throw e

        }

    }

}