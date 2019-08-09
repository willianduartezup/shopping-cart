package test.kotlin.domain

import domain.User
import org.apache.log4j.Logger
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import repository.ConnectionFactory
import repository.DAOFactory
import repository.UserDAO
import java.util.UUID
import javax.validation.ConstraintViolation
import javax.validation.Validation
import kotlin.test.assertEquals


class UserTest() {

    var LOG = Logger.getLogger(UserTest::class.java)

    private var validator = Validation.buildDefaultValidatorFactory().validator

    companion object {
        val id = UUID.randomUUID().toString()

        @BeforeClass
        @JvmStatic
        fun insertUser() {
            println("insert.user")
            val user = User(id, "Tester", "teste@teste.com", "piece")

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

            val jdbc = ConnectionFactory()

            try {
                val factory = DAOFactory()
                val userDao: UserDAO =
                    factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO


                userDao.remove(id)

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }
    }

    @Test
    fun `validate insert user`() {
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

    @Test
    fun userNameIsEmpty() {
        LOG.info("userNameIsEmpty")

        val user = User(null, "", "teste@teste.com", "123456")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("name"))
    }

    @Test
    fun userEmailIsEmpty() {
        LOG.info("userEmailIsEmpty")

        val user = User(null, "teste", "", "123456")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("email"))

    }

    @Test
    fun userEmailNotValid() {
        LOG.info("userEmailNotValid")

        val user = User(null, "test name", "", "123456")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("email"))

    }

    @Test
    fun userPasswordIsEmpty() {
        LOG.info("userPasswordIsEmpty")

        val user = User(null, "teste", "teste@test.com", "")

        val violations = validator.validate(user)

        if (violations.size < 1) {
            assertThat(violations, hasSize(1))
        } else if (violations.size > 1) {
            assertThat(violations, hasSize(2))
        }

        assertThat(getNameFirstProperty(violations), `is`("password"))
    }

    @Test
    fun userPasswordIsNotValid() {
        LOG.info("userPasswordIsNotValid")

        val user = User(null, "teste", "teste@test.com", "12")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("password"))
    }

    private fun getNameFirstProperty(violations: Set<ConstraintViolation<User>>): String {
        LOG.info("getNameFirstProperty")

        return violations.iterator().next().propertyPath.iterator().next().name
    }

}