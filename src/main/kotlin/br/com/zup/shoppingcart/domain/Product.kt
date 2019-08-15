package br.com.zup.shoppingcart.domain

import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive


data class Product(

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:NotEmpty(message = "Name is Empty!")
    var name: String,

    @field:Positive(message = "Price invalid!")
    var price: Int,

    var unit: String = "piece",

    var quantity: Int

)

