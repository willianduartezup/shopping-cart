package domain

<<<<<<< HEAD
import repository.ConnectionFactory
=======
>>>>>>> 1510ab67b3d7593e393a3fe486a52beab5020890
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import repository.ConnectionFactory
import repository.DAOFactory
import repository.ProductDAO
import java.util.UUID
import javax.validation.ConstraintViolation
import javax.validation.Validation
import kotlin.test.assertEquals


class ProductTest {

    private var validator = Validation.buildDefaultValidatorFactory().validator

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

    @Test
    fun productNameIsEmpty() {
        val product = Product(id, "", 2, "piece", 1)

        val violations = validator.validate(product)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("name"))
    }

    @Test
    fun productPriceIsPositivo() {

        val product = Product(id, "Test", 0, "piece", 1)

        val violations = validator.validate(product)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("price"))
    }

    private fun getNameFirstProperty(violations: Set<ConstraintViolation<Product>>): String {
        return violations.iterator().next().propertyPath.iterator().next().name
    }

}