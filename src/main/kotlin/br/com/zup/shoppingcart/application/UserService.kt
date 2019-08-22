package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.infra.exception.FieldValidator
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.UserDAO
import javax.validation.Validation

class UserService(
    private val jdbc: ConnectionFactory,
    private val factory: DAOFactory
) {
    /*companion object {
        private val factory = DAOFactory()
        private val jdbc = ConnectionFactory()
        private val userDAO: UserDAO =
            factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO
    }*/

    fun getUserById(param: String): User {

        val connection = jdbc.getConnection()
        try {
            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            return userDAO.get(param)
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

    fun getListUser(): ArrayList<User> {

        val connection = jdbc.getConnection()
        try {
            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            return userDAO.listUsers()
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

    @Throws(Exception::class)
    fun getRemovedUserById(param: String): User {
        val connection = jdbc.getConnection()
        try {
            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            val user = userDAO.getRemovedUserById(param)
            return user

        } catch (ex: Exception) {

            connection.rollback()
            throw ex

        } finally {
            jdbc.closeConnection()
        }
    }

    fun add(user: User) {
        FieldValidator.validate(user)
        val connection = jdbc.getConnection()
        try {
            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            userDAO.add(user)
            connection.commit()
        } catch (ex: Exception) {
            connection.rollback()
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

    fun edit(user: User) {
        FieldValidator.validate(user)
        val connection = jdbc.getConnection()
        try {
            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            userDAO.edit(user)
            connection.commit()
        } catch (ex: Exception) {
            connection.rollback()
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

    fun remove(param: String) {

        val connection = jdbc.getConnection()
        try {
            val userDAO: UserDAO = factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            userDAO.remove(param)
            connection.commit()
        } catch (ex: Exception) {
            connection.rollback()
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }
}