package domain

import infra.exception.ValidationException
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

            val factory = DAOFactory()
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java) as ProductDAO

            productDAO.add(product)
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
        val factory = DAOFactory()
        val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java) as ProductDAO

        assertEquals(id, productDAO.get(id).id)
    }

    @Test
    fun `update product`() {
        val factory = DAOFactory()
        val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java) as ProductDAO

        val product = Product(id, "Orange", 2, "piece", 1)

        productDAO.edit(product)

        assertEquals(product.name, productDAO.get(id).name)
    }
}