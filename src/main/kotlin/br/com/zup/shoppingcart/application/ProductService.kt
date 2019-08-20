package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.infra.exception.FieldValidator
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ProductDAO

class ProductService(
    private val jdbc: ConnectionFactory,
    private val factory: DAOFactory
) {

    fun add(product: Product) {
        FieldValidator.validate(product)
        val connection = jdbc.getConnection()
        try {
            val productDAO: ProductDAO = factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            productDAO.add(product)

            connection.commit()

        } catch (ex: Exception) {

            connection.rollback()
            throw ex

        } finally {
            jdbc.closeConnection()
        }
    }

    fun getProductById(id: String): Product {

        val connection = jdbc.getConnection()

        try {
            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            return productDAO.get(id)

        } catch (ex: Exception) {
            throw ex

        } finally {
            jdbc.closeConnection()
        }
    }

    fun getListProducts(): ArrayList<Product> {

        val connection = jdbc.getConnection()

        try {
            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            return productDAO.listProduct()

        } catch (ex: Exception) {
            throw ex

        } finally {
            jdbc.closeConnection()
        }
    }

    fun edit(product: Product) {
        FieldValidator.validate(product)
        val connection = jdbc.getConnection()

        try {
            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, connection) as ProductDAO

            productDAO.edit(product)

            connection.commit()

        } catch (ex: Exception) {

            connection.rollback()
            throw ex

        } finally {
            jdbc.closeConnection()
        }
    }

}