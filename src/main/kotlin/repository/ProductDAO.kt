package repository

import domain.Product

interface ProductDAO: CrudDAO<Product, String> {}