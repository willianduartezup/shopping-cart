package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ProductDAO

class ProductService(private val jdbc: ConnectionFactory,
        private val factory: DAOFactory) {

    fun add(product: Product) {

        try {
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            productDAO.add(product)
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

    fun getProductById(id: String): Product {

        try {
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            return productDAO.get(id)
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

    fun getListProducts(): ArrayList<Product> {

        try {
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            return productDAO.listProduct()
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

    fun edit(product: Product) {

        try {
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            productDAO.edit(product)
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

}