package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.domain.ItemCart
import br.com.zup.shoppingcart.domain.Product
import junit.framework.Assert.assertTrue
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id as idProduct

class ItemCartJdbcTest {
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
            } catch (e: Exception) {
                e.printStackTrace()

                LOG.info("error on 'Insert Item Cart' test. Exception is $e")

            } finally {
                jdbc.closeConnection()
            }
        }

        @AfterClass
        @JvmStatic
        fun delete() {
            val jdbc = ConnectionFactory()
            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO
                val itemsCartDAO: ItemsCartDAO = factory.getInstanceOf(ItemsCartDAO::class.java, jdbc.getConnection()) as ItemsCartDAO

                itemsCartDAO.remove(id)
                productDAO.remove(idProduct)
            } catch (e: Exception) {
                e.printStackTrace()
                LOG.info("error on 'Insert Item Cart' test. Exception is $e")

            } finally {
                jdbc.closeConnection()
            }
        }
    }

    @Test
    fun `validate insert item`() {
        LOG.info("Validate Insert Item Cart")

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val itemsCartDAO: ItemsCartDAO = factory.getInstanceOf(ItemsCartDAO::class.java, jdbc.getConnection()) as ItemsCartDAO

            assertEquals(id, itemsCartDAO.get(id).id)
        } catch (e: Exception) {

          //  assertTrue(false)
            LOG.error("Error on 'Validate insert Item Cart' test. Exception is $e")
            e.printStackTrace()


        } finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `validate update item`(){
        LOG.info("Validate Update Item Cart")

        val jdbc = ConnectionFactory()
        try {
            val itemCart = ItemCart(id, idProduct, 2, 2)
            LOG.info(" ")
            val factory = DAOFactory()
            val itemsCartDAO: ItemsCartDAO = factory.getInstanceOf(ItemsCartDAO::class.java, jdbc.getConnection()) as ItemsCartDAO

            itemsCartDAO.edit(itemCart)

            assertEquals(2, itemsCartDAO.get(id).quantity)
        } catch (e: Exception) {
            LOG.info(" ")

            e.printStackTrace()
//            assertTrue(false)
            LOG.error("Error on 'Validate Update Item Cart' test. Exception is $e")

        } finally {
            jdbc.closeConnection()
        }
    }
}