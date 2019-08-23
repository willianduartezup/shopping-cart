package br.com.zup.shoppingcart.domain

import java.sql.Date
import java.util.UUID
import javax.persistence.Id

data class CreditCard (
    @Id
    var id: String = UUID.randomUUID().toString(),

    var number: String,

    var expirationDate: Int,

    var cardName : String

)

