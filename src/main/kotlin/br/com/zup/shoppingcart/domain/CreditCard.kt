package br.com.zup.shoppingcart.domain

import java.util.Date
import java.util.UUID
import javax.persistence.Id

data class CreditCard (
    @Id
    var id: String = UUID.randomUUID().toString(),

    var number: String,

    var expirationDate: Date,

    var cardName : String

)

