package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.Product

interface ProductDAO : CrudDAO<Product, String> {

    @Throws(Exception::class)
    fun listProduct(): ArrayList<Product>

}