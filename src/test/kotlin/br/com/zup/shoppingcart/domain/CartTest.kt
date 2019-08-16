package br.com.zup.shoppingcart.domain

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation

class CartTest {

    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `cart user id not empty`() {

        LOG.info("cart user id not empty test")

        val items = ArrayList<String>()
        items.add("id1")
        items.add("id2")

        val cart = Cart(null,items,"",10)

        val violations = validator.validate(cart)

        MatcherAssert.assertThat(violations, Matchers.hasSize(1))
        MatcherAssert.assertThat(getNameFirstProperty(violations), Matchers.`is`("user_id"))
    }

    @Test
    fun `cart total price is negative`(){

        LOG.info("cart total price is negative")

        val items = ArrayList<String>()
        items.add("id1")
        items.add("id2")

        val cart = Cart(null,items,"abc123",-1)

        val violations = validator.validate(cart)

        MatcherAssert.assertThat(violations, Matchers.hasSize(1))
        MatcherAssert.assertThat(getNameFirstProperty(violations), Matchers.`is`("total_price"))
    }


    private fun getNameFirstProperty(violations: Set<ConstraintViolation<Cart>>): String {
        return violations.iterator().next().propertyPath.iterator().next().name
    }

}