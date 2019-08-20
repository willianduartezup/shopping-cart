package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.User

interface UserDAO: CrudDAO<User, String> {

    @Throws(Exception::class)
    fun  removeUserFromTable(id: String)

    @Throws(Exception::class)
    fun getRemovedUserById(id: String): User

    fun listUsers(): ArrayList<User>

}