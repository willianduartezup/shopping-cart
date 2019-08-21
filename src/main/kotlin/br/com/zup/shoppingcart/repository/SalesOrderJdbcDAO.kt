package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.SalesOrder
import java.sql.Connection
import java.sql.Date

class SalesOrderJdbcDAO(val connection: Connection): SalesOrderDAO {
    override fun get(id: String): SalesOrder {
        val stm = connection.prepareStatement("SELECT * FROM salesorder WHERE id = ?")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("Sales order not found")
        }

        val salesOrder = SalesOrder(
            rs.getString("id"),
            rs.getString("cart_id"),
            rs.getInt("number"),
            rs.getDate("date_generation")
        )

        rs.close()
        stm.close()

        return salesOrder
    }

    override fun add(e: SalesOrder): SalesOrder {
        val psmt =
            connection.prepareStatement("INSERT INTO salesorder(id, cart_id, number, date_generation) VALUES(?,?,?,?)")
        psmt.setString(1, e.id)
        psmt.setString(2, e.cart_id)
        psmt.setInt(3, e.number as Int)
        psmt.setDate(4, e.date_generation as Date)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun edit(e: SalesOrder): SalesOrder {
        val psmt =
            connection.prepareStatement("UPDATE salesorder SET cart_id = ?, number = ?, date_generation = ?  WHERE id like ?")
        psmt.setString(1, e.cart_id)
        psmt.setInt(2, e.number as Int)
        psmt.setDate(3, e.date_generation as Date)
        psmt.setString(4, e.id)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun remove(id: String) {
        val psmt = connection.prepareStatement("DELETE FROM salesorder WHERE id like ?")
        psmt.setString(1, id)

        psmt.execute()
        psmt.close()
    }
}