package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.ItemCart


interface ItemsCartDAO: CrudDAO<ItemCart, String>  {

    @Throws(Exception::class)
    fun listItemCart(idCart: String): ArrayList<ItemCart>

}