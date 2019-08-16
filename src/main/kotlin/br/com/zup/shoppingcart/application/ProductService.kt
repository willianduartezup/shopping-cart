package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.infra.ReadPayload
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ProductDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class ProductService {

    private val jdbc = ConnectionFactory()
    private val factory = DAOFactory()
    private val productDAO: ProductDAO =
            factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

    fun add(product: Product) {
        productDAO.add(product)
    }

    fun getProductById(id: String): Product {
        return productDAO.get(id)
    }

    fun getListProducts(): ArrayList<Product> {
        return productDAO.listProduct()
    }

    fun edit(product: Product) {
        productDAO.edit(product)
    }

}