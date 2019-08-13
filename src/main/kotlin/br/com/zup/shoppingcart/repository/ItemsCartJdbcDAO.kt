package br.com.zup.shoppingcart.repository


import br.com.zup.shoppingcart.domain.ItemCart
import java.sql.Connection

class ItemsCartJdbcDAO(private val connection: Connection) : ItemsCartDAO {
    override fun listItemCart(idCart: String): ArrayList<ItemCart> {

        val jdbc = ConnectionFactory()
        val factory = DAOFactory()

        val cartDao: CartDAO =
            factory.getInstanceOf(CartDAO::class.java, jdbc.getConnection()) as CartDAO

        val listItemCart = ArrayList<ItemCart>()

        val listIdItem = cartDao.get(idCart).items

        for (idItem in listIdItem) {

            val itemCart = get(idItem)

            listItemCart.add(itemCart)
        }

        return listItemCart
    }


    override fun get(id: String): ItemCart {
        val stm = connection.prepareStatement("SELECT * FROM itemCart WHERE id = ?")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("Item Cart not found")
        }

        val itemCart = ItemCart(
            rs.getString("id"),
            rs.getString("product_id"),
            rs.getInt("price_unit_product"),
            rs.getInt("quantity")
        )

        stm.close()

        return itemCart

    }

    override fun add(e: ItemCart): ItemCart {

        val psmt =
            connection.prepareStatement("INSERT INTO itemCart(id, product_id, price_unit_product, quantity) VALUES(?,?,?,?)")
        psmt.setString(1, e.id)
        psmt.setString(2, e.product_id)
        psmt.setInt(3, e.price_unit_product)
        psmt.setInt(4, e.quantity)

        psmt.execute()
        psmt.close()

        return e

    }

    override fun edit(e: ItemCart): ItemCart {

        val psmt =
            connection.prepareStatement("UPDATE itemCart SET product_id = ?, price_unit_product = ?, quantity = ? WHERE id = ?")
        psmt.setString(1, e.product_id)
        psmt.setInt(2, e.price_unit_product)
        psmt.setInt(3, e.quantity)
        psmt.setString(4, e.id)

        psmt.execute()
        psmt.close()

        return e

    }

    override fun remove(id: String) {

        val psmt = connection.prepareStatement("DELETE FROM itemCart WHERE id = ?")
        psmt.setString(1, id)

        psmt.execute()
        psmt.close()

    }
}