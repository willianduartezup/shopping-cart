package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.SalesOrder
import java.sql.Connection
import java.sql.Date

class SalesOrderJdbcDAO(val connection: Connection): SalesOrderDAO {
    override fun listOrders(): ArrayList<SalesOrder> {
        val listOrders = ArrayList<SalesOrder>()

        val stm = connection.createStatement()
        val rs = stm.executeQuery("Select * from salesorder")


        while (rs.next()){

            val salesOrder = SalesOrder(
                rs.getString("id"),
                rs.getString("cart_id"),
                rs.getInt("number"),
                rs.getDate("date_generation"),
                rs.getString("card_id")
            )
            listOrders.add(salesOrder)
        }

        rs.close()
        stm.close()

        return listOrders

    }

    override fun getListOrdersUser(id: String): ArrayList<SalesOrder> {
        val listOrders = ArrayList<SalesOrder>()

        val query =
            "select * from salesorder ic where ic.id in(select json_array_elements_text(orders) as id from users where id = ?)"

        val stm = connection.prepareStatement(query)
        stm.setString(1, id)

        val rs = stm.executeQuery()

        while (rs.next()){

            val salesOrder = SalesOrder(
                rs.getString("id"),
                rs.getString("cart_id"),
                rs.getInt("number"),
                rs.getDate("date_generation"),
                rs.getString("card_id")
            )
            listOrders.add(salesOrder)
        }
        return listOrders

    }

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
            rs.getDate("date_generation"),
            rs.getString("card_id")
        )

        rs.close()
        stm.close()

        return salesOrder
    }

    override fun add(e: SalesOrder): SalesOrder {
        val psmt =
            connection.prepareStatement("INSERT INTO salesorder(id, cart_id, date_generation, card_id) VALUES(?,?,?,?)")
        psmt.setString(1, e.id)
        psmt.setString(2, e.cart_id)
        psmt.setDate(3, e.date_generation as Date)
        psmt.setString(4, e.cart_id)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun edit(e: SalesOrder): SalesOrder {
        val psmt =
            connection.prepareStatement("UPDATE salesorder SET cart_id = ?, number = ?, date_generation = ?, card_id = ?  WHERE id like ?")
        psmt.setString(1, e.cart_id)
        psmt.setInt(2, e.number as Int)
        psmt.setDate(3, e.date_generation as Date)
        psmt.setString(4, e.cart_id)
        psmt.setString(5, e.id)

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