package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.Cart
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.Connection
import java.sql.Date

fun ArrayList<String>.toJson() = jacksonObjectMapper().writeValueAsString(this)


class CartJdbcDAO(private val connection: Connection) : CartDAO {

    private val mapper = jacksonObjectMapper()

    override fun get(id: String): Cart {

        val stm = connection.prepareStatement("SELECT * FROM cart WHERE id like ?")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("Cart not found")
        }

        val cart = Cart(
            rs.getString("id"),
            getArrayList(rs.getString("items")),
            rs.getString("user_id"),
            rs.getInt("total_price"),
            rs.getDate("update_at")
        )

        stm.close()

        return cart
    }

    private fun getArrayList(listCart: String): ArrayList<String> {


        val actualObj = mapper.readTree(listCart)

        val listReturn = ArrayList<String>()

        for (i in actualObj) {

            println(i)

            listReturn.add(i.toString())
        }

        return listReturn

    }

    override fun getCartActive(): Cart {

        val stm = connection.prepareStatement("SELECT * FROM cart WHERE canceledoractive like true")

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("Cart not found")
        }

        return get(rs.getString("id"))

    }


    override fun add(e: Cart): Cart {

        val psmt =
            connection.prepareStatement("INSERT INTO cart(id, items, user_id, total_price, update_at, canceledoractive) VALUES(?,?::json,?,?,?,?)")

        println(e.id)


        psmt.setString(1, e.id)
        psmt.setString(2, e.items.toJson())
        psmt.setString(3, e.user_id)
        psmt.setString(4, e.total_price.toString())
        psmt.setDate(5, e.update_at as Date)
        psmt.setBoolean(6, true)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun edit(e: Cart): Cart {
        val psmt =
            connection.prepareStatement("UPDATE cart SET items = ?, user_id = ?, total_price = ?, update_at = ?, canceledoractive = ? WHERE id = ?")

        val array = arrayOfNulls<String>(e.items.size)


        psmt.setString(1, e.id)
        psmt.setString(2, e.items.toJson())
        psmt.setString(3, e.user_id)
        psmt.setInt(4, e.total_price)
        psmt.setDate(5, e.update_at as Date?)
        psmt.setBoolean(6, true)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun remove(id: String) {
        val psmt = connection.prepareStatement("UPDATE cart SET canceledoractive = ? WHERE id like ?")
        psmt.setBoolean(1, false)
        psmt.setString(2, id)

        psmt.execute()
        psmt.close()
    }
}