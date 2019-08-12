package br.com.zup.shoppingcart.domain


import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ProductDAO
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import java.util.UUID
import javax.validation.ConstraintViolation
import javax.validation.Validation
import kotlin.test.assertEquals


class ProductTest {

    private var validator = Validation.buildDefaultValidatorFactory().validator

    val id = UUID.randomUUID().toString()

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