package br.com.zup.shoppingcart.application


import br.com.zup.shoppingcart.domain.Cart
import br.com.zup.shoppingcart.domain.ItemCart
import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.infra.ReadPayload
import br.com.zup.shoppingcart.repository.CartDAO
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ItemsCartDAO
import br.com.zup.shoppingcart.repository.ProductDAO
import br.com.zup.shoppingcart.repository.UserDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.ArrayList


class CartService(
    private val jdbc: ConnectionFactory,
    private val factory: DAOFactory
) {

    fun add(userId: String, itemCart: ItemCart) {

        var idCart = ""

        val connection = jdbc.getConnection()

        val userDAO: UserDAO =
            factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

        val userCart: User
        try {

            userCart = userDAO.get(userId)
            connection.commit()

            validateQuantity(itemCart.quantity)
            validateInventoryProduct(itemCart.product_id, itemCart.quantity)

            if (userCart.cart_id != "") {

                idCart = userCart.cart_id.toString()
            }

            if (idCart == "") {

                val itemCartDao: ItemsCartDAO =
                    factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO

                itemCartDao.add(itemCart)

            } else {
                groupItemsCart(idCart, itemCart)
            }

            val idItem = itemCart.id.toString()


            var totalPrice = calculatePriceItem(itemCart.price_unit_product, itemCart.quantity)

            if (idCart == "") {
                addCart(idItem, userCart, userId, totalPrice)

            } else if (idCart != "") {

                val totalPriceInCart = recalculateTotalPrice(idCart)

                totalPrice += totalPriceInCart

                editCart(idCart, idItem, totalPrice)
            }

            updateQuantityProduct(itemCart.product_id, itemCart.quantity, "-")

            connection.commit()

        } catch (e: Exception) {

            connection.rollback()
            throw e

        } finally {
            jdbc.closeConnection()
        }
    }


    private fun groupItemsCart(idCart: String, itemCart: ItemCart) {

        val connection = jdbc.getConnection()

        try {

            val product = itemCart.product_id

            val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, jdbc.getConnection()) as CartDAO
            val listItems = cartDao.get(idCart).items

            var idItemCart = ""

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

        } finally {

            jdbc.closeConnection()

        }


    }

    fun remove(idItemCart: String) {

        val connection = jdbc.getConnection()

        try {
            val itemCartDao: ItemsCartDAO =
                factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO

            val itemCart = itemCartDao.get(idItemCart)

            updateQuantityProduct(itemCart.product_id, itemCart.quantity, "+")

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

        val operator = getOperatorStock(itemCart.id.toString(), itemCart.quantity)

        val connection = jdbc.getConnection()

        try {

            validateQuantity(itemCart.quantity)
            validateInventoryProduct(itemCart.product_id, itemCart.quantity)

            val itemCartDao: ItemsCartDAO =
                factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO

            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO


            if (operator != "") {
                val product = productDAO.get(itemCart.product_id)

                updateQuantityProduct(product.id.toString(), itemCart.quantity, operator)
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


    private fun getOperatorStock(idItemCart: String, quantity: Int): String {

        val connection = jdbc.getConnection()

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

        } finally {

            jdbc.closeConnection()

        }

    }

    private fun updateQuantityProduct(idProduct: String, quantity: Int, operator: String) {

        val connection = jdbc.getConnection()

        val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO
        try {
            val product = productDAO.get(idProduct)

            var newQuantity = 0

            if (operator == "-") {
                newQuantity = product.quantity - quantity
            } else if (operator == "+") {
                newQuantity = product.quantity + quantity
            }

            val productUpdate = Product(idProduct, product.name, product.price, product.unit, newQuantity)

            productDAO.edit(productUpdate)

        } catch (e: Exception) {

            throw e

        } finally {

            jdbc.closeConnection()

        }

    }

    private fun validateQuantity(quantity: Int) {
        if (quantity <= 0) {
            throw Exception("Quantity is zero!")
        }
    }

    private fun validateInventoryProduct(idProduct: String, quantity: Int) {

        val connection = jdbc.getConnection()

        val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

        try {

            val quantityProduct = productDAO.get(idProduct).quantity

            if (quantityProduct - quantity < 0) {
                throw Exception("Product has no quantity in stock")
            }
        } catch (e: Exception) {

            connection.rollback()
            throw e

        } finally {

            jdbc.closeConnection()

        }
    }

    fun calculatePriceItem(priceUnit: Int, quantity: Int): Int {
        return priceUnit * quantity
    }

    private fun addCart(idItem: String, userCart: User, userId: String, totalPrice: Int) {

        val connection = jdbc.getConnection()

        try {
            val listItem = ArrayList<String>()

            listItem.add(idItem)

            val cart = Cart(items = listItem, user_id = userId, total_price = totalPrice)

            val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, connection) as CartDAO
            cartDao.add(cart)

            val userUpdate = User(userId, userCart.name, userCart.email, userCart.password, userCart.deleted, cart.id)

            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO
            userDAO.edit(userUpdate)


        } catch (e: Exception) {

            connection.rollback()
            throw e

        } finally {

            jdbc.closeConnection()

        }
    }

    private fun editCart(idCart: String, idItem: String, totalPrice: Int) {

        val connection = jdbc.getConnection()

        val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, connection) as CartDAO

        try {

            val cart = cartDao.get(idCart)

            val items = cart.items

            items.add(idItem)

            val cartUpdate = Cart(idCart, items, cart.user_id, totalPrice)

            cartDao.edit(cartUpdate)
        } catch (e: Exception) {

            connection.rollback()
            throw e

        } finally {

            jdbc.closeConnection()

        }


    }

    private fun recalculateTotalPrice(idCart: String): Int {

        val connection = jdbc.getConnection()

        val itemCartDao: ItemsCartDAO =
            factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO

        try {
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

        } finally {

            jdbc.closeConnection()

        }

    }

}