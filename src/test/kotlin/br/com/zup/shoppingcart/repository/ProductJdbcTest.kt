package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.domain.Product
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals

class ProductJdbcTest {
    companion object {
        private val product = Product(id, "Apple", 2, "piece", 1)

        @BeforeClass
        @JvmStatic
        fun insertProduct() {

            LOG.info("Insert Product Test")

            val jdbc = ConnectionFactory()
            val connection = jdbc.getConnection()
            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO =
                    factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

                productDAO.add(product)
                connection.commit()
            } catch (ex: Exception) {
                connection.rollback()
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }

        @AfterClass
        @JvmStatic
        fun deletProduct() {
            LOG.info("Delete Product Test")

            val jdbc = ConnectionFactory()
            val connection = jdbc.getConnection()
            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO =
                    factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

                productDAO.remove(product.id!!)
                connection.commit()
            } catch (ex: Exception) {
                connection.rollback()
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }
    }

    @Test
    fun `validate insert product`() {

        LOG.info("Validate Insert Product Test")

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            assertEquals(product.id, productDAO.get(product.id!!).id)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            assertEquals(product.id, "")
        } finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `update product`() {

        LOG.info("Update Product Test")

        val jdbc = ConnectionFactory()
        val connection = jdbc.getConnection()
        try {
            val factory = DAOFactory()
            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            val productUpdated = Product(product.id, "Orange", 2, "piece", 1)

            productDAO.edit(productUpdated)
            connection.commit()
            assertEquals(productUpdated.name, productDAO.get(product.id!!).name)
        } catch (ex: java.lang.Exception) {
            connection.rollback()
            ex.printStackTrace()
            assertEquals("", "error")
        } finally {
            jdbc.closeConnection()
        }
    }
}