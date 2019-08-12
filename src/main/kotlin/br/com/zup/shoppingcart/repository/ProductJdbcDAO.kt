package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.Product
import java.sql.Connection

class ProductJdbcDAO(val connection: Connection) : ProductDAO {

    override fun get(id: String): Product {
        println("jdbc product get")
        val stm = connection.prepareStatement("SELECT * FROM product WHERE id = ?")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("Product not found")
        }

        val product = Product(
            rs.getString("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("unit"),
            rs.getInt("quantity")
        )

        stm.close()

        return product
    }

    override fun add(e: Product): Product {
        val psmt = connection.prepareStatement("INSERT INTO product(id, name, price, unit, quantity) VALUES(?,?,?,?,?)")
        psmt.setString(1, e.id)
        psmt.setString(2, e.name)
        psmt.setInt(3, e.price)
        psmt.setString(4, e.unity)
        psmt.setInt(5, e.quantity)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun edit(e: Product): Product {
        val psmt =
            connection.prepareStatement("UPDATE product SET name = ?, price = ?, unit = ?, quantity = ? WHERE id = ?")
        psmt.setString(1, e.name)
        psmt.setInt(2, e.price)
        psmt.setString(3, e.unity)
        psmt.setInt(4, e.quantity)
        psmt.setString(5, e.id)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun remove(id: String) {

        val psmt = connection.prepareStatement("DELETE FROM product WHERE id = ?")
        psmt.setString(1, id)

        psmt.execute()
        psmt.close()
    }

}