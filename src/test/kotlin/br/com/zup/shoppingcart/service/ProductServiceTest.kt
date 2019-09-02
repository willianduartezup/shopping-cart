package br.com.zup.shoppingcart.service

import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.application.ProductService
import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ProductDAO
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals

class ProductServiceTest {
    companion object {
        private val product = Product(id, "Apple", 2, "piece", 1)
        private val service = ProductService(DAOFactory())

        @BeforeClass
        @JvmStatic
        fun insertProduct() {

            LOG.info("Insert Product Test")

            service.add(product)
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

                productDAO.remove(id)

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection(connection)
            }
        }
    }

    @Test
    fun `validate insert product`() {

        LOG.info("Validate Insert Product Test")

        assertEquals(product.id, service.getProductById(product.id!!).id)
    }

    @Test
    fun `update product`() {

        LOG.info("Update Product Test")
        product.name = "Orange"

        service.edit(product)

        assertEquals(product.name, service.getProductById(product.id!!).name)
    }
}