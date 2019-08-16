package br.com.zup.shoppingcart.application

import org.junit.Assert
import org.junit.Test

class CartServiceTest {

    @Test
    fun calculatePrice() {
        val cartService = CartService()
        Assert.assertEquals(9,cartService.calculatePriceItem(3,3))
    }
}