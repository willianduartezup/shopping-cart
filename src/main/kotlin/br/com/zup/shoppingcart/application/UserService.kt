package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.UserDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.lang.Exception
import javax.validation.Validation

class UserService {

    companion object {
        private val factory = DAOFactory()
        private val jdbc = ConnectionFactory()
        private val mapper = jacksonObjectMapper()
        private val userDAO: UserDAO =
            factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO
    }

    fun getUserById(param: String): User {

        /*val user = userDAO.get(param)
        return mapper.writeValueAsString(user)*/
        return userDAO.get(param)

    }

    fun getListUser(): ArrayList<User> {

        /*val listUsers = userDAO.listUsers()
        return mapper.writeValueAsString(listUsers)*/
        return userDAO.listUsers()

    }

    fun getRemovedUserById(param: String): String {

        val user = userDAO.get(param)
        return mapper.writeValueAsString(user)
//           obj = objectMapper.readValue(json, List.class);
    }

    fun add(user: User) {
        val validator = Validation.buildDefaultValidatorFactory().validator
        val violations = validator.validate(user)
        var message = ""
        if(violations.size > 0){
                for(item in violations){
                    message += "${item.message} "
                }
                throw Exception (message)
        }
        userDAO.add(user)
    }

    fun edit(user: User) = userDAO.edit(user)

    fun remove(param: String) = userDAO.remove(param)

}