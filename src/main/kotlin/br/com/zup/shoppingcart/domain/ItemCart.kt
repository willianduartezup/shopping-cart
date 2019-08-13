package br.com.zup.shoppingcart.domain

import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero

data class ItemCart(

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:NotEmpty(message = "Product ID is empty!")
    var product_id: String,

    @field:PositiveOrZero(message = "Product's price unit is zero or negative!")
    var price_unit_product: Int,

    @field:PositiveOrZero(message = "Quantity is zero or negative!")
    var quantity: Int


) {
}