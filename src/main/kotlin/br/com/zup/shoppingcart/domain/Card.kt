package br.com.zup.shoppingcart.domain

import java.util.Date
import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class Card(

    @Id
    val id: String? = UUID.randomUUID().toString(),

    @field:[NotEmpty(message = "Number cannot be empty!") NotBlank(message = "Number cannot be blank!")]
    val number: Number,

    @field:[NotEmpty(message = "Validity cannot be empty!") NotBlank(message = "Validity cannot be blank!")]
    val validity: Date,

    @field:[NotEmpty(message = "CVV cannot be empty!") NotBlank(message = "CVV cannot be blank!")]
    val cvv: String,

    @field:[NotEmpty(message = "CVV cannot be empty!") NotBlank(message = "CVV cannot be blank!")]
    val userName: String
)