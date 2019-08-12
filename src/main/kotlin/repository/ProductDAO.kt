package repository

import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.repository.CrudDAO

interface ProductDAO: CrudDAO<Product, String> {

    @Throws(Exception::class)
    fun  listProduct(): ArrayList<Product>

}