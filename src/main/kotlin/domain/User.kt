package main.kotlin.domain

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.NotEmpty



class User(
    var id: String,

    @field:NotEmpty(message = "Name is blank!")
    var name: String,

    @field:NotNull
    var price: Int?,

    @field:NotBlank(message = "Unit is blank!")
    var unit: String,

    @field:NotNull
    var quantity: Int
) {
}