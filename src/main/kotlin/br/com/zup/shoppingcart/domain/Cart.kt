package br.com.zup.shoppingcart.domain

import java.util.Date
import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class Cart(

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:NotNull(message = "Itens is null!")
    var items: ArrayList<String>,

    @field:NotEmpty(message = "User ID is empty!")
    var user_id: String,

    @field:PositiveOrZero(message = "Total price is negative!")
    var total_price: Int,

    var update_at: Date? = Date(),

    var status: Status? = Status.ACTIVE

    )