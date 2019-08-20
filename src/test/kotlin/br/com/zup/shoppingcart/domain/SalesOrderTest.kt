package br.com.zup.shoppingcart.domain

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import javax.validation.Validation

class SalesOrderTest {

    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `sales order cartId not empty`() {

        LOG.info("Sales-order cartId not empty test")

        val sales = SalesOrder(
            cart_id = id,
            number = 1
        )

        val violations = validator.validate(sales)

        MatcherAssert.assertThat(violations, Matchers.empty())
    }
    
}