package br.com.zup.shoppingcart.repository


import br.com.zup.shoppingcart.domain.ItemCart
import java.sql.Connection

class ItemsCartJdbcDAO(private val connection: Connection) : ItemsCartDAO {
    override fun listItemCart(idCart: String): ArrayList<ItemCart> {

        val listItemCart = ArrayList<ItemCart>()

       try {
           val jdbc = ConnectionFactory()
           val factory = DAOFactory()

           val cartDao: CartDAO =
               factory.getInstanceOf(CartDAO::class.java, jdbc.getConnection()) as CartDAO



           val listIdItem = cartDao.get(idCart).items

           for (idItem in listIdItem) {

               val itemCart = get(idItem)

               if (itemCart.deleted == false) {
                   listItemCart.add(itemCart)
               }
           }

           return listItemCart

        } catch (e: Exception){

           return listItemCart
       }

    }


    override fun get(id: String): ItemCart {
        val stm = connection.prepareStatement("SELECT * FROM itemcart WHERE id = ?")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("Item Cart not found")
        }

        val itemCart = ItemCart(
            rs.getString("id"),
            rs.getString("product_id"),
            rs.getInt("price_unit_product"),
            rs.getInt("quantity"),
            rs.getBoolean("deleted")
        )

        stm.close()

        return itemCart

    }

    override fun add(e: ItemCart): ItemCart {

        val psmt =
            connection.prepareStatement("INSERT INTO itemcart(id, product_id, price_unit_product, quantity, deleted) VALUES(?,?,?,?,?)")
        psmt.setString(1, e.id)
        psmt.setString(2, e.product_id)
        psmt.setInt(3, e.price_unit_product)
        psmt.setInt(4, e.quantity)
        psmt.setBoolean(5, false)

        psmt.execute()
        psmt.close()

        return e

    }

    override fun edit(e: ItemCart): ItemCart {

        val psmt =
            connection.prepareStatement("UPDATE itemcart SET product_id = ?, price_unit_product = ?, quantity = ? WHERE id = ?")
        psmt.setString(1, e.product_id)
        psmt.setInt(2, e.price_unit_product)
        psmt.setInt(3, e.quantity)
        psmt.setString(4, e.id)

        psmt.execute()
        psmt.close()

        return e

    }

    override fun remove(id: String) {

        val psmt = connection.prepareStatement("DELETE FROM itemcart WHERE id = ?")
        psmt.setString(1, id)

        psmt.execute()
        psmt.close()

    }
    override fun delete(id: String) {
        val psmt = connection.prepareStatement("UPDATE itemcart SET deleted = ? WHERE id like ?")
        psmt.setBoolean(1, true )
        psmt.setString(2, id)

        psmt.execute()
        psmt.close()
    }

}