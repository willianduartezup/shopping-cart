package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.Cart
import br.com.zup.shoppingcart.domain.Status
import java.sql.Connection
import java.sql.Date

class CartJdbcDAO(val connection: Connection) : CartDAO {
    override fun get(id: String): Cart {

        val stm = connection.prepareStatement("SELECT * FROM cart WHERE id like ? AND status like Status.ACTIVE")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("User not found")
        }

        val cart = Cart(
            rs.getString("id"),
            rs.getArray("items") as ArrayList<String>,
            rs.getString("user_id"),
            rs.getInt("total_price"),
            rs.getDate("update_at"),
            rs.getObject("status") as Status
        )

        stm.close()

        return cart
    }

    override fun getCartActive(): Cart{

        val stm = connection.prepareStatement("SELECT * FROM cart WHERE status like Status.ACTIVE")

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("Cart not found")
        }

        return get(rs.getString("id"))

    }


    override fun add(e: Cart): Cart {
        val psmt = connection.prepareStatement("INSERT INTO cart(id, itens, user_id:, total_price, update_at, status) VALUES(?,?,?,?,?,?)")
        psmt.setString(1, e.id)
        psmt.setObject(2, e.items)
        psmt.setString(3, e.user_id)
        psmt.setInt(4, e.total_price)
        psmt.setDate(5, e.update_at as Date?)
        psmt.setObject(6, e.status)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun edit(e: Cart): Cart {
        val psmt =
            connection.prepareStatement("UPDATE cart SET itens = ?, user_id = ?, total_price = ?, update_at = ?, status = ? WHERE id = ?")
        psmt.setString(1, e.id)
        psmt.setObject(2, e.items)
        psmt.setString(3, e.user_id)
        psmt.setInt(4, e.total_price)
        psmt.setDate(5, e.update_at as Date?)
        psmt.setObject(6, e.status)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun remove(id: String) {
        val psmt = connection.prepareStatement("UPDATE cart SET status = ? WHERE id like ?")
        psmt.setObject(1, Status.CANCELLED )
        psmt.setString(2, id)

        psmt.execute()
        psmt.close()
    }
}