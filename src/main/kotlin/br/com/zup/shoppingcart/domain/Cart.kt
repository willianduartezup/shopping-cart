package br.com.zup.shoppingcart.domain

import java.sql.Date
import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class Cart(

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:NotNull(message = "Items field is null!")
    var items: ArrayList<String>,

    @field:NotEmpty(message = "User ID is empty!")
    var user_id: String,

    @field:PositiveOrZero(message = "Total price is negative!")
    var total_price: Int,

    var update_at: Date? = Date(java.util.Date().time),

    var status: Status? = Status.ACTIVE

    )