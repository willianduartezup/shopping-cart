package domain

import infra.exception.ValidationException
import main.kotlin.repository.ConnectionFactory
import org.junit.BeforeClass
import org.junit.Test
import repository.DAOFactory
import repository.ProductDAO
import java.util.*
import kotlin.test.assertEquals


class ProductTest {

    companion object {
        val id = UUID.randomUUID().toString()

        @BeforeClass
        @JvmStatic
        fun insertProduct() {
            println("insert......")
            val product = Product(id, "Apple", 2, "piece", 1)

            val jdbc = ConnectionFactory()
            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

                productDAO.add(product)
            }catch (ex: Exception){
                ex.printStackTrace()
            }finally {
                jdbc.closeConnection()
            }
        }
    }

    @Test
    fun `product field validation`() {
        val product = Product("", "", 0, "piece", 0)
        var ok = 0

        try {
            product.validateFields()
        } catch (ex: ValidationException) {
            if (ex.listErrors.containsKey("id") && ex.listErrors.containsKey("name") && ex.listErrors.containsKey("quantity")) {
                ok = 1
            }
        }
        assertEquals(1, ok)
    }

    @Test
    fun `validate insert product`() {

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            assertEquals(id, productDAO.get(id).id)
        }catch (ex: java.lang.Exception){
            ex.printStackTrace()
            assertEquals(id, "")
        }finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `update product`() {

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            val product = Product(id, "Orange", 2, "piece", 1)

            productDAO.edit(product)

            assertEquals(product.name, productDAO.get(id).name)
        }catch (ex: java.lang.Exception){
            ex.printStackTrace()
            assertEquals("", "error")
        }finally {
            jdbc.closeConnection()
        }
    }
}