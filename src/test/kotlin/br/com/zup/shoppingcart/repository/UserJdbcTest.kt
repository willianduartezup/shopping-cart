package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.domain.User
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals

class UserJdbcTest {

    companion object {

        private val array = ArrayList<String>()

        private val user = User(id, "Tester", "teste@teste.com", "piece!", orders = array)

        @BeforeClass
        @JvmStatic
        fun insertUser() {
            LOG.info("Insert User Test")

            val jdbc = ConnectionFactory()
            val connection = jdbc.getConnection()
            try {
                val factory = DAOFactory()
                val userDao: UserDAO =
                    factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

                userDao.add(user)
                connection.commit()
            } catch (ex: Exception) {

                connection.rollback()
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
            val connection = jdbc.getConnection()
            try {
                val factory = DAOFactory()
                val userDao: UserDAO =
                    factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

                userDao.removeUserFromTable(id)
                connection.commit()

            } catch (ex: Exception) {
                connection.rollback()
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
        } finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `update user`() {

        LOG.info("Update User")

        val jdbc = ConnectionFactory()
        val connection = jdbc.getConnection()
        try {
            val factory = DAOFactory()
            val userDao: UserDAO =
                factory.getInstanceOf(UserDAO::class.java, connection) as UserDAO

            val user = User(id, "Test Modify", "teste@teste.com", "12345678", orders = array)

            userDao.edit(user)
            connection.commit()

        } catch (ex: java.lang.Exception) {
            connection.rollback()
            ex.printStackTrace()

        } finally {
            jdbc.closeConnection()
        }
    }

}