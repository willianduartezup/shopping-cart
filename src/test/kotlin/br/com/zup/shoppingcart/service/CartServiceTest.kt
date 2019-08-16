package br.com.zup.shoppingcart.service

import br.com.zup.shoppingcart.ServletTestConfig
import br.com.zup.shoppingcart.application.CartService
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CartServiceTest {

    @Before
    fun setup() {

        ServletTestConfig.LOG.info("UserServiceTest.setup")

    }

    @Test
    fun calculatePrice() {
        val cartService = CartService()
        Assert.assertEquals(9,cartService.calculatePriceItem(3,3))
    }
}