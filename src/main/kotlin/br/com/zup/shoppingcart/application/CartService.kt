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
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CartService {

    private val jdbc = ConnectionFactory()
    private val mapper = jacksonObjectMapper()
    private val reader = ReadPayload()

    private val factory = DAOFactory()
    private val userDAO: UserDAO =
        factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO
    private val cartDao: CartDAO =
        factory.getInstanceOf(CartDAO::class.java, jdbc.getConnection()) as CartDAO
    private val itemCartDao: ItemsCartDAO =
        factory.getInstanceOf(ItemsCartDAO::class.java, jdbc.getConnection()) as ItemsCartDAO

    private val productDAO: ProductDAO =
        factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

    fun add(userId: String, itemCart: ItemCart) {

        var idCart = ""

        val userCart = userDAO.get(userId)

        validateQuantity(itemCart.quantity)
        validateInventoryProduct(itemCart.product_id, itemCart.quantity)

        if (userCart.cart_id != "") {

            idCart = userCart.cart_id.toString()
        }

        if (idCart == "") {
            itemCartDao.add(itemCart)
        } else {
            agroupItemsCart(idCart, itemCart)
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

    }

    private fun agroupItemsCart(idCart: String, itemCart: ItemCart) {

        try {

            val product = itemCart.product_id

            val ListItems = cartDao.get(idCart).items

            var idItemCart = ""

            for (idItems in ListItems) {

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


        } catch (e: Exception) {
            throw Exception(e.message)
        }

    }


    fun remove(idItemCart: String) {

        val itemCart = itemCartDao.get(idItemCart)

        updateQuantityProduct(itemCart.product_id, itemCart.quantity, "+")

        itemCartDao.delete(idItemCart)

    }

    fun edit(itemCart: ItemCart) {

        val operator = getOperatorStock(itemCart.id.toString(), itemCart.quantity)

        if (operator != "") {
            val product = productDAO.get(itemCart.product_id)

            updateQuantityProduct(product.id.toString(), itemCart.quantity, operator)
        }

        itemCartDao.edit(itemCart)

    }

    fun get(idUser: String): String {

        val idCart = userDAO.get(idUser).cart_id.toString()

        val listItems = itemCartDao.listItemCart(idCart)

        return mapper.writeValueAsString(listItems)

    }


    private fun getOperatorStock(idItemCart: String, quantity: Int): String {

        val quantityBefore = itemCartDao.get(idItemCart).quantity

        return when {
            quantityBefore > quantity -> "+"
            quantityBefore < quantity -> "-"
            else -> "a"
        }

    }

    private fun updateQuantityProduct(idProduct: String, quantity: Int, operator: String) {

        val product = productDAO.get(idProduct)

        var newQuantity = 0

        if (operator == "-") {
            newQuantity = product.quantity - quantity
        } else if (operator == "+") {
            newQuantity = product.quantity + quantity
        }

        val productUpdate = Product(idProduct, product.name, product.price, product.unit, newQuantity)

        productDAO.edit(productUpdate)
    }

    private fun validateQuantity(quantity: Int) {
        if (quantity <= 0) {
            throw Exception("Quantity is zero!")
        }
    }

    private fun validateInventoryProduct(idProduct: String, quantity: Int) {

        val quantityProduct = productDAO.get(idProduct).quantity

        if (quantityProduct - quantity < 0) {
            throw Exception("Product has no quantity in stock")
        }
    }

    fun calculatePriceItem(priceUnit: Int, quantity: Int): Int {
        return priceUnit * quantity
    }

    private fun addCart(idItem: String, userCart: User, userId: String, totalPrice: Int) {
        try {
            val listItem = ArrayList<String>()

            listItem.add(idItem)

            val cart = Cart(items = listItem, user_id = userId, total_price = totalPrice)

            cartDao.add(cart)

            val userUpdate = User(userId, userCart.name, userCart.email, userCart.password, userCart.deleted, cart.id)

            userDAO.edit(userUpdate)

        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    private fun editCart(idCart: String, idItem: String, totalPrice: Int) {
        val cart = cartDao.get(idCart)

        val items = cart.items

        items.add(idItem)

        val cartUpdate = Cart(idCart, items, cart.user_id, totalPrice)

        cartDao.edit(cartUpdate)
    }

    private fun recalculateTotalPrice(idCart: String): Int {
        val listItemCart = itemCartDao.listItemCart(idCart)

        var totalPrice = 0

        for (itemCart in listItemCart) {

            val unitPrice = itemCart.price_unit_product
            val quantity = itemCart.quantity
            val itemPrice = (unitPrice * quantity)

            totalPrice += itemPrice
        }
        return totalPrice
    }
//aa

}