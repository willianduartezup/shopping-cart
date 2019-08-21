package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.SalesOrder
import br.com.zup.shoppingcart.repository.CartDAO
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.SalesOrderDAO
import br.com.zup.shoppingcart.repository.UserDAO
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class OrdersHistoryService (
    private val jdbc: ConnectionFactory,
    private val factory: DAOFactory
) {

    fun getOrdersUser(idUser: String): JSONArray{

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

            val json = JSONObject()
            val jsonArray = JSONArray()

            for (selOrder in order){

                val cart = cartDao.get(selOrder.cart_id)

                val user = userDAO.get(cart.user_id)

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
            jdbc.closeConnection()
        }

    }



}