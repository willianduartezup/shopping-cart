package br.com.zup.shoppingcart.service

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.mapper
import br.com.zup.shoppingcart.ServletTestConfig.Companion.readPayload
import br.com.zup.shoppingcart.application.UserService
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import com.fasterxml.jackson.module.kotlin.convertValue
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.InputStream

class UserServiceTest {

    companion object {

        val input = FileInputStream("./src/test/resources/userTest.json")
        val inputStream: InputStream = ByteArrayInputStream(input.readBytes())
        val user: User = readPayload.mapper<User>(inputStream)
        var service = UserService(DAOFactory(), ConnectionFactory())

    }

    @Before
    fun setup() {

        LOG.info("UserServiceTest.setup")

    }

    @Test
    fun `should get user by id`() {

        LOG.info("should get user by id")

        try {

            val param = user.id!!
            val userGet = service.getUserById(param)
            val userString = mapper.writeValueAsString(user) as String
            LOG.info(userGet)
            LOG.info(userString)
            assertEquals("differences between", userGet as String, userString as String)
            LOG.info("successful user return")

        } catch (e: Exception) {

            LOG.error("should get user by id\nError testing Exception is $e")
            e.printStackTrace()

        } finally { }

    }

    @Test
    fun `should add user`() {

        LOG.info("should add user")

        try {

            val result = service.add(user)
            assertEquals("differences between", user, result)
            LOG.info("user added successful")

        } catch (e: Exception) {

            LOG.error("Error in 'should add user' test. Exception is $e")
            e.printStackTrace()

        } finally {

        }

    }

    @Test
    fun `should successful get user list`() {

        LOG.info("should successful get user list")

        try {

            val jsonList = service.getListUser()
            val users: List<User> = mapper.convertValue(jsonList)

            assertTrue(users.isNotEmpty())
            LOG.info("successful user return")

        } catch (e: Exception) {

            LOG.error("Error testing Exception is $e")
            e.printStackTrace()

        } finally { }

    }

    @Test
    fun `should successful on removes user`() {

        LOG.info("should successful on removes user")

        try {

            val param: String = user.id!!
            service.remove(param)

            val userGet = service.getUserById(param)
            val user: User = mapper.convertValue(userGet)
            LOG.info("User removed successful")

            assertTrue(user.deleted!!)

        } catch (e: Exception) {

            LOG.info("Error in test of removes user. Exception is $e")
            e.printStackTrace()

        } finally { }

    }
}