package br.com.zup.shoppingcart.domain

import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive


data class Product(

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:NotEmpty(message = "Name is Empty!")
    var name: String,

    @field:Positive(message = "Price invalid!")
    @field:Max(9000000, message = "This price is too high!")
    var price: Int,

    var unit: String = "piece",

    @field:Positive(message = "Quantity must be positive")
    @field:Max(9000000, message = "That's too much.")
    var quantity: Int

)

