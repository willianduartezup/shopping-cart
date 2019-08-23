package br.com.zup.shoppingcart.domain

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import javax.validation.Validation
import kotlin.test.assertEquals

class SalesOrderTest {

    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `sales order cartId not empty`() {

        LOG.info("Sales-order cartId not empty test")

        val sales = SalesOrder(
            cart_id = id,
            number = 1,
            card_id = "123123123"
        )

        val violations = validator.validate(sales)

        MatcherAssert.assertThat(violations, Matchers.empty())
    }

    @Test
    fun `sales order card id is empty`(){

        LOG.info("Sales-order cartId not empty test")

        val sales = SalesOrder(
            cart_id = id,
            number = 1,
            card_id = ""
        )

        val violations = validator.validate(sales)

        var message = ""

        for (item in violations){
            message = item.message
        }

        assertEquals(message, "Card ID is empty!")

        val property = violations.iterator().next().propertyPath.iterator().next().name

        assertEquals(property, "card_id")

    }
    
}