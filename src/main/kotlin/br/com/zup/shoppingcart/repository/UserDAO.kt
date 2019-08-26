package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.User

interface UserDAO: CrudDAO<User, String> {

    @Throws(Exception::class)
    fun  removeUserFromTable(id: String)

    @Throws(Exception::class)
    fun getRemovedUserById(id: String): User

    @Throws(Exception::class)
    fun listUsers(): ArrayList<User>

    @Throws(Exception::class)
    fun emailIsexist(user: User)

}