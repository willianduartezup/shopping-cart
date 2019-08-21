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
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.InputStream

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserServiceTest {

    companion object {

        private val array = ArrayList<String>()

        val user: User = User(name = "User Test", email = "test@user.com", password = "PRIVATE", cart_id = "", orders = array)
        private val service = UserService(ConnectionFactory(), DAOFactory())


        /*@AfterClass
        @JvmStatic
        fun deleteUser() {

            try {

                val param = user.id!!
                service.removeFromTable(param)
                LOG.info("Delete user Test")

            } catch (e: Exception) {

                Assert.assertTrue(false)
                e.printStackTrace()
            }
        }*/
    }

    @Test
    fun `C | should get user by id`() {

        LOG.info("should get user by id")

        try {

            val param = user.id!!
            val userGet = service.getUserById(param)
            LOG.info(userGet)
            Assert.assertEquals("differences between", userGet, user)
            LOG.info("successful user return")

        } catch (e: Exception) {

            LOG.error("should get user by id\nError testing Exception is $e")
            assertTrue(false)
        }
    }

    @Test
    fun `A | should add user`() {

        LOG.info("Insert user for tests")

        service.add(user)

        LOG.info("should validates add user")

        try {
            val confirmUser = service.getUserById(user.id!!)
            LOG.info(confirmUser.id)
            assertTrue(user.id == confirmUser.id)

        } catch (e: Exception) {

            LOG.error("Error in 'should add user' test. Exception is $e")
            assertTrue(false)

        }
    }

    @Test
    fun `B | should successful get user list`() {

        LOG.info("should successful get user list")

        try {

            val jsonList = service.getListUser()
            val users: List<User> = mapper.convertValue(jsonList)

            assertTrue(users.isNotEmpty())
            LOG.info("successful user return")

        } catch (e: Exception) {

            LOG.error("Error testing Exception is $e")
            assertTrue(false)
        }
    }

    @Test
    fun `D | should successful on removes user`() {

        LOG.info("should successful on removes user")

        try {

            val param: String = user.id!!

            service.remove(param)
            val deletedTre = service.getRemovedUserById(param).deleted!!
            assertTrue(deletedTre)
            LOG.info("User removed successful")


        } catch (e: Exception) {

            LOG.error("Error in test of removes user. Exception is $e")
            assertTrue(false)
        }

    }
}