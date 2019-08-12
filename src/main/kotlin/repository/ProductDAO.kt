package repository

import domain.Product

interface ProductDAO: CrudDAO<Product, String> {

    @Throws(Exception::class)
    fun  listProduct(): ArrayList<Product>

}