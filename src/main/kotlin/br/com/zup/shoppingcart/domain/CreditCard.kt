package br.com.zup.shoppingcart.domain

import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class CreditCard (

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:[NotEmpty(message = "Number cannot be empty!") NotBlank(message = "Number cannot be blank!")]
    var userId: String,

    @field:[NotEmpty(message = "Number cannot be empty!") NotBlank(message = "Number cannot be blank!")]
    var number: String,

    var expirationDate: Int,

    @field:[NotEmpty(message = "CVV cannot be empty!") NotBlank(message = "CVV cannot be blank!")]
    var cardName : String,

    var cvv: String?
)
