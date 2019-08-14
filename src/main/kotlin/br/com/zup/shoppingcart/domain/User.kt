package br.com.zup.shoppingcart.domain

import java.util.UUID
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class User(

    @Id
    var id: String? = UUID.randomUUID().toString(),

    @field:NotEmpty(message = "Name is blank!")
    var name: String,

    @field:Email(message = "Email is not valid!")
    @field:NotEmpty(message = "Email is blank!")
    var email: String,

    @field:Size(min = 6, message = "Password is not valid!")
    @field:NotEmpty(message = "Password is blank!")
    var password: String,

    var deleted: Boolean? = false,

    var cart_id: String? = ""
)
