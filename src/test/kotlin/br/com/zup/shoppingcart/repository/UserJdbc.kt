package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.domain.User
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals

class UserJdbc {
    companion object {

        @BeforeClass
        @JvmStatic
        fun insertUser() {
            LOG.info("Insert User Test")
            val user = User(id, "Tester", "teste@teste.com", "piece!")

            val jdbc = ConnectionFactory()
            try {
                val factory = DAOFactory()
                val userDao: UserDAO =
                    factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO

                userDao.add(user)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }

        @AfterClass
        @JvmStatic
        fun deleteUser() {

            LOG.info("Delete User Test")

            val jdbc = ConnectionFactory()

            try {
                val factory = DAOFactory()
                val userDao: UserDAO =
                    factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO


                userDao.removeUserFromTable(id)

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }
    }


    @Test
    fun `validate insert user`() {
        LOG.info("Validate Insert User")

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val userDAO: UserDAO =
                factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO

            assertEquals(id, userDAO.get(id).id)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            assertEquals(id, "")
        } finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `update user`() {

        LOG.info("Update User")

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val userDao: UserDAO =
                factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO

            val user = User(id, "Test Modify", "teste@teste.com", "12345678")

            userDao.edit(user)

            assertEquals(user.name, userDao.get(id).name)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            assertEquals("", "error")

        } finally {
            jdbc.closeConnection()
        }
    }

}