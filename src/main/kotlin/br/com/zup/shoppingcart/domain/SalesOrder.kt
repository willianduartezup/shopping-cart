package br.com.zup.shoppingcart.domain

import java.util.Date
import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.NotEmpty

class SalesOrder(

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:NotEmpty(message = "Cart ID is empty!")
    var cart_id: String,

    var number: Int?,

    var date_generation: Date? = Date(java.util.Date().time)

)