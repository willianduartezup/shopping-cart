package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.domain.Product
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals

class ProductJdbc {
    companion object {
        @BeforeClass
        @JvmStatic
        fun insertProduct() {

            LOG.info("Insert Product Test")
            val product = Product(id, "Apple", 2, "piece", 1)

            val jdbc = ConnectionFactory()
            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO =
                    factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

                productDAO.add(product)
            } catch (ex: Exception) {
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

            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO =
                    factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

                productDAO.remove(id)

            } catch (ex: Exception) {
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

            assertEquals(id, productDAO.get(id).id.toString())
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            assertEquals(id, "")
        } finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `update product`() {

        LOG.info("Update Product Test")

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            val product = Product(id, "Orange", 2, "piece", 1)

            productDAO.edit(product)

            assertEquals(product.name, productDAO.get(id).name)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            assertEquals("", "error")
        } finally {
            jdbc.closeConnection()
        }
    }
}