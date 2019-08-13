package br.com.zup.shoppingcart.domain

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation

class ItemCartDAOTest {

    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `itens cart product id not empty`() {

        LOG.info("itens cart product id not empty test")

        val itensCart = ItemCart(null, "", 10, 5)

        val violations = validator.validate(itensCart)

        MatcherAssert.assertThat(violations, Matchers.hasSize(1))
        MatcherAssert.assertThat(getNameFirstProperty(violations), Matchers.`is`("product_id"))
    }

    @Test
    fun `itens cart price negative`() {
        LOG.info("itens cart price negative")

        val itensCart = ItemCart(null, "123", -1, 5)

        val violations = validator.validate(itensCart)

        MatcherAssert.assertThat(violations, Matchers.hasSize(1))
        MatcherAssert.assertThat(getNameFirstProperty(violations), Matchers.`is`("price_unit_product"))

    }

    @Test
    fun `itens cart quantity negative`() {
        LOG.info("itens cart quantity negative")

        val itensCart = ItemCart(null, "123", 10, -1)

        val violations = validator.validate(itensCart)

        MatcherAssert.assertThat(violations, Matchers.hasSize(1))
        MatcherAssert.assertThat(getNameFirstProperty(violations), Matchers.`is`("quantity"))

    }


    private fun getNameFirstProperty(violations: Set<ConstraintViolation<ItemCart>>): String {
        return violations.iterator().next().propertyPath.iterator().next().name
    }


}