package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.domain.ItemCart
import br.com.zup.shoppingcart.domain.Product
import junit.framework.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id as idProduct

class ItemCartJdbc {
    companion object {
        @BeforeClass
        @JvmStatic
        fun insert() {
            LOG.info("Insert Item Cart")
            val product = Product(idProduct, "Apple", 2, "piece", 1)
            val itemCart = ItemCart(id, idProduct, product.price, 1)

            val jdbc = ConnectionFactory()
            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO
                val itemsCartDAO: ItemsCartDAO = factory.getInstanceOf(ItemsCartDAO::class.java, jdbc.getConnection()) as ItemsCartDAO

                productDAO.add(product)
                itemsCartDAO.add(itemCart)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }

        @BeforeClass
        @JvmStatic
        fun delete() {
            val jdbc = ConnectionFactory()
            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO
                val itemsCartDAO: ItemsCartDAO = factory.getInstanceOf(ItemsCartDAO::class.java, jdbc.getConnection()) as ItemsCartDAO

                itemsCartDAO.remove(id)
                productDAO.remove(idProduct)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }
    }

    @Test
    fun `validate insert user`() {
        LOG.info("Validate Insert Item Cart")

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val itemsCartDAO: ItemsCartDAO = factory.getInstanceOf(ItemsCartDAO::class.java, jdbc.getConnection()) as ItemsCartDAO

            assertEquals(id, itemsCartDAO.get(id).id)
        } catch (ex: Exception) {
            ex.printStackTrace()
            assertTrue(false)
        } finally {
            jdbc.closeConnection()
        }
    }
}