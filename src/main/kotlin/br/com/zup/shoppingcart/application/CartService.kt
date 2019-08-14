package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.Cart
import br.com.zup.shoppingcart.domain.ItemCart
import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.repository.CartDAO
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ItemsCartDAO
import br.com.zup.shoppingcart.repository.ProductDAO
import br.com.zup.shoppingcart.repository.UserDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import br.com.zup.shoppingcart.infra.ReadPayload
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

    fun add(req: HttpServletRequest, resp: HttpServletResponse) {

        try {

            val userId = req.pathInfo.replace("/", "")
            val itemCart = reader.mapper<ItemCart>(req.inputStream)
            var idCart = ""

            val userCart = userDAO.get(userId)

            validateQuantity(itemCart.quantity)

            if (userCart.cart_id != "") {

                idCart = userCart.cart_id.toString()

                validateInventoryProduct(idCart, itemCart.quantity)
            }

            itemCartDao.add(itemCart)

            updateQuantityProduct(itemCart.product_id, itemCart.quantity, "-")

            val idItem = itemCart.id.toString()

            var totalPrice = calculatePriceItem(itemCart.price_unit_product, itemCart.quantity)

            if (idCart == "") {
                addCart(idItem, userCart, userId, totalPrice)

            } else if (idCart != "") {

                val totalPriceInCart = recalculateTotalPrice(idCart)

                totalPrice += totalPriceInCart

                editCart(idCart, idItem, totalPrice)
            }

            resp.setStatus(201, "CREATED")


        } catch (e: Exception) {
            resp.sendError(400, e.message)
        }

    }

    fun remove(req: HttpServletRequest, resp: HttpServletResponse) {

        try {
            val idItemCart = req.pathInfo.replace("/", "")

            val itemCart = itemCartDao.get(idItemCart)

            updateQuantityProduct(itemCart.product_id, itemCart.quantity, "+")

            itemCartDao.remove(idItemCart)

        } catch (e: Exception) {
            resp.sendError(400, e.message)
        }
    }

    fun edit(req: HttpServletRequest, resp: HttpServletResponse) {

        try {
            val itemCart = reader.mapper<ItemCart>(req.inputStream)

            val operator = getOperatorStock(itemCart.id.toString(), itemCart.quantity)

            if (operator != "") {
                val product = productDAO.get(itemCart.product_id)

                updateQuantityProduct(product.id.toString(), itemCart.quantity, operator)
            }

            itemCartDao.edit(itemCart)

        } catch (e: Exception) {
            resp.sendError(400, e.message)
        }
    }

    fun get(req: HttpServletRequest, resp: HttpServletResponse) {

        if (req.pathInfo != "") {

            val idUser = req.pathInfo.replace("/", "")

            val idCart = userDAO.get(idUser).cart_id.toString()

            val listItems = itemCartDao.listItemCart(idCart)

            val jsonString = mapper.writeValueAsString(listItems)

            resp.writer.write(jsonString)
        } else {
            resp.sendError(400, "User not found!")
        }
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

        if (quantityProduct - quantity <= 0) {
            throw Exception("Product has no quantity in stock")
        }
    }

    private fun calculatePriceItem(priceUnit: Int, quantity: Int): Int {
        return priceUnit * quantity
    }

    private fun addCart(idItem: String, userCart: User, userId: String, totalPrice: Int) {
        val listItem = ArrayList<String>()

        listItem.add(idItem)

        val cart = Cart(null, listItem, userId, totalPrice)

        cartDao.add(cart)

        val userUpdate = User(userId, userCart.name, userCart.email, userCart.password, userCart.deleted, cart.id)

        userDAO.edit(userUpdate)
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


}