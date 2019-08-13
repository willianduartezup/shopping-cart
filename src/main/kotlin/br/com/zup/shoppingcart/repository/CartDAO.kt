package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.Cart

interface CartDAO: CrudDAO<Cart, String> {

    @Throws(Exception::class)
    fun  getCartActive(): Cart

}