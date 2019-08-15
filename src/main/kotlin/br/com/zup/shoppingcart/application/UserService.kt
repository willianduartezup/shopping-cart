package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.UserDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class UserService {

    companion object {
        private val factory = DAOFactory()
        private val jdbc = ConnectionFactory()
        private val mapper = jacksonObjectMapper()
        private val userDAO: UserDAO =
            factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO

    }

    fun add(user: User) = userDAO.add(user)

    fun getUserById(param: String): String {

        val user = userDAO.get(param)
        return mapper.writeValueAsString(user)

    }

    fun getRemovedUserById(param: String): String {

        val user = userDAO.getRemovedUserById(param)
        return mapper.writeValueAsString(user)

    }

    fun edit(user: User) = userDAO.edit(user)

    fun getListUser(): String {

        val listUsers = userDAO.listUsers()

        return mapper.writeValueAsString(listUsers)

    }

    fun remove(param: String) = userDAO.remove(param)

}