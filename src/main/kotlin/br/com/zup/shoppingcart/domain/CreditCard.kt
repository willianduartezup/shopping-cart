package br.com.zup.shoppingcart.domain

import java.sql.Date
import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class CreditCard (

    @Id
    val id: String? = UUID.randomUUID().toString(),

    @field:[NotEmpty(message = "Number cannot be empty!") NotBlank(message = "Number cannot be blank!")]
    val userId: String,

    @field:[NotEmpty(message = "Number cannot be empty!") NotBlank(message = "Number cannot be blank!")]
    val number: String,

    @field:[NotEmpty(message = "Validity cannot be empty!") NotBlank(message = "Validity cannot be blank!")]
    val expirationDate: Date,

    @field:[NotEmpty(message = "CVV cannot be empty!") NotBlank(message = "CVV cannot be blank!")]
    var cardName : String,

    val cvv: String?

    )

