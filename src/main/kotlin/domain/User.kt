package main.kotlin.domain


import java.util.UUID
import javax.validation.constraints.NotEmpty


data class User(


    var id: UUID? = UUID.randomUUID(),

    @field:NotEmpty(message = "Name is blank!")
    var name: String,

    @field:NotEmpty(message = "Email is blank!")
    var email: String,

    @field:NotEmpty(message = "Password is blank!")
    var password: String


) {
}