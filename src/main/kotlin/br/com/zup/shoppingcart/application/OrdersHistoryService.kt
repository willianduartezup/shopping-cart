package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.SalesOrder
import br.com.zup.shoppingcart.repository.CartDAO
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.SalesOrderDAO
import br.com.zup.shoppingcart.repository.UserDAO
import com.fasterxml.jackson.databind.annotation.JsonAppend
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONString
import java.util.ArrayList

class OrdersHistoryService (
    private val factory: DAOFactory
) {

    fun getOrdersUser(idUser: String): JSONArray{

        val jdbc = ConnectionFactory()
        val connection = jdbc.getConnection()

        try{

            val salesOrderDAO: SalesOrderDAO = factory.getInstanceOf(SalesOrderDAO::class.java, connection) as SalesOrderDAO
            val cartDao: CartDAO = factory.getInstanceOf(CartDAO::class.java, connection) as CartDAO
            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO


            var order: ArrayList<SalesOrder>

            order = if (idUser != "") {
                salesOrderDAO.getListOrdersUser(idUser)
            } else {
                salesOrderDAO.listOrders()
            }

            val jsonArray = JSONArray()


            for ((i, selOrder) in order.withIndex()){

                val cart = cartDao.get(selOrder.cart_id)

                val user = userDAO.get(cart.user_id)

                val json = JSONObject()
                json.put("id",selOrder.id)
                json.put("number",selOrder.number)
                json.put("date", selOrder.date_generation)
                json.put("total",cart.total_price)
                json.put("user",user.name)

               jsonArray.put(json)
            }

            return jsonArray


        }catch (e: Exception) {
            throw e
        } finally {
            jdbc.closeConnection(connection)
        }

    }





}