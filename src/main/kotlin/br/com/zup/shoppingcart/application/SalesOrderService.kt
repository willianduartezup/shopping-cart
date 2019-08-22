package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.SalesOrder
import br.com.zup.shoppingcart.repository.CartDAO
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ItemsCartDAO
import br.com.zup.shoppingcart.repository.ProductDAO
import br.com.zup.shoppingcart.repository.SalesOrderDAO
import br.com.zup.shoppingcart.repository.SalesOrderJdbcDAO
import br.com.zup.shoppingcart.repository.UserDAO
import org.json.JSONArray
import org.json.JSONObject

class SalesOrderService(
    private val jdbc: ConnectionFactory,
    private val factory: DAOFactory
) {

    private val cartService = CartService(ConnectionFactory(), DAOFactory())
    private val userService = UserService(ConnectionFactory(), DAOFactory())


    fun addOrder(userId: String): String {

        val user = userService.getUserById(userId)
        val cart = cartService.get(userId)
        val order: SalesOrder
        if (user.cart_id != "" && !cart.isEmpty()) {
            order = SalesOrder(cart_id = user.cart_id!!)
        } else throw Exception("Invalid cart")

        val connection = jdbc.getConnection()
        try {
            val salesOrderJdbcDAO: SalesOrderDAO =
                factory.getInstanceOf(SalesOrderDAO::class.java, connection) as SalesOrderDAO

            salesOrderJdbcDAO.add(order)

            connection.commit()

            // FieldValidator.validate(product)

            return order.id!!

        } catch (ex: Exception) {

            connection.rollback()
            throw ex

        } finally {
            jdbc.closeConnection()
        }
    }

    /*fun getByUserId(idUser: String): JSONObject {

        val connection = jdbc.getConnection()

        try {

            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO
            val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, connection) as CartDAO
            val itemCartDao: ItemsCartDAO = factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            val user = userDAO.get(idUser)

            val idCart = user.cart_id.toString()

            val cart = cartDao.get(idCart)

            val listItems = itemCartDao.listItemCart(idCart)

            val jsonOne = JSONObject()
            val jsonCart = JSONObject()
            val jsonItems = JSONObject()
            val jsonArray = JSONArray()

            for (items in listItems) {

                val product = productDAO.get(items.product_id)

                jsonItems.put("id", items.id)
                jsonItems.put("name", product.name)
                jsonItems.put("unit_price", items.price_unit_product)
                jsonItems.put("unit", product.unit)
                jsonItems.put("quantity", items.quantity)

                jsonArray.put(jsonItems)
            }

            jsonCart.put("id", cart.id)
            jsonCart.put("total_price", cart.total_price)
            jsonCart.put("items", jsonArray)

            jsonOne.put("id", "2000")
            jsonOne.put("number", "1")
            jsonOne.put("cart", jsonCart)

            return jsonOne

        } catch (e: Exception) {
            throw e
        } finally {
            jdbc.closeConnection()
        }

    }*/

    fun getByOrderId(idOrder: String): JSONObject {

        val connection = jdbc.getConnection()

        try {

            val salesOrderDAO: SalesOrderDAO =
                factory.getInstanceOf(SalesOrderDAO::class.java, connection) as SalesOrderDAO
            val order = salesOrderDAO.get(idOrder)

            val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, connection) as CartDAO
            val cart = cartDao.get(order.cart_id)

            val itemCartDao: ItemsCartDAO = factory.getInstanceOf(ItemsCartDAO::class.java, connection) as ItemsCartDAO
            val listItems = itemCartDao.listItemCart(order.cart_id)

            val jsonUser = JSONObject()
            val jsonOne = JSONObject()
            val jsonCart = JSONObject()
            val jsonItems = JSONObject()
            val jsonArray = JSONArray()

            val user = userService.getUserById(cart.user_id)

            jsonUser.put("id", user.id)
            jsonUser.put("name", user.name)

            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            for (items in listItems) {

                val product = productDAO.get(items.product_id)

                jsonItems.put("id", items.id)
                jsonItems.put("name", product.name)
                jsonItems.put("unit_price", items.price_unit_product)
                jsonItems.put("unit", product.unit)
                jsonItems.put("quantity", items.quantity)

                jsonArray.put(jsonItems)
            }

            jsonCart.put("id", cart.id)
            jsonCart.put("total_price", cart.total_price)
            jsonCart.put("items", jsonArray)

            jsonOne.put("id", order.id)
            jsonOne.put("number", order.number)
            jsonOne.put("cart", jsonCart)
            jsonOne.put("user", jsonUser)

            return jsonOne

        } catch (e: Exception) {
            throw e
        } finally {
            jdbc.closeConnection()
        }

    }

}