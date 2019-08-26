package br.com.zup.shoppingcart.domain

import org.hibernate.validator.constraints.Length
import java.util.UUID
import javax.persistence.Id
import javax.persistence.Temporal
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class CreditCard (

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:[NotEmpty(message = "Number cannot be empty!") NotBlank(message = "Number cannot be blank!")]
    var userId: String,

    @field:[NotEmpty(message = "Number cannot be empty!") NotBlank(message = "Number cannot be blank!") Length(max=16 , min = 16)]
    var number: String,

    var expirationDate: Int,

    @field:[NotEmpty(message = "CVV cannot be empty!") NotBlank(message = "CVV cannot be blank!")]
    var cardName : String,

    var cvv: String?
)
