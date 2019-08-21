package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.repository.CartDAO
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ItemsCartDAO
import br.com.zup.shoppingcart.repository.ProductDAO
import br.com.zup.shoppingcart.repository.UserDAO
import org.json.JSONArray
import org.json.JSONObject

class SellerOrderService(
    private val jdbc: ConnectionFactory,
    private val factory: DAOFactory
) {

    fun getByUserId(idUser: String): JSONObject{

        val connection = jdbc.getConnection()

        try{

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


            for (items in listItems){

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
            jsonCart.put("items",jsonArray)

            jsonOne.put("id", "2000")
            jsonOne.put("number", "1")
            jsonOne.put("cart", jsonCart)

            return jsonOne

        } catch (e: Exception){
            throw e
        } finally {
            jdbc.closeConnection()
        }


    }



}