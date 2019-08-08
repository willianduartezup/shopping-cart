package repository

import domain.Product

class ProductJdbcDAO: ProductDAO {
    override fun get(id: String): Product {
        println("jdbc product")
        return Product("342jhj", "Apple", 2, "piece", 1)
    }

    override fun add(e: Product): Product {
        println("jdbc product")
        return e
    }

    override fun edit(e: Product): Product {
        println("jdbc product")
        return e
    }
}