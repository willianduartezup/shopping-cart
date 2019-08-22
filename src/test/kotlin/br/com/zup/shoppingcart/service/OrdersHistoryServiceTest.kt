package br.com.zup.shoppingcart.service

import br.com.zup.shoppingcart.ServletTestConfig
import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.application.SalesOrderService
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import com.fasterxml.jackson.module.kotlin.convertValue
import org.junit.AfterClass
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertTrue

class OrdersHistoryServiceTest {

    companion object {

        private val factory = DAOFactory()
        private val jdbc = ConnectionFactory()

        private val array = ArrayList<String>()

        private val userA: User =
            User(name = "User Test", email = "test@user.com", password = "PRIVATE", orders = array)


        private val salesOrderService = SalesOrderService(ConnectionFactory(), DAOFactory())

        private lateinit var orderId: String

        /*@BeforeClass
        @JvmStatic
        fun setup() {

            try {

            } catch (e: Exception) {
                LOG.error("Failed into prepare requirements for tests. Exception is $e")
                Assert.assertTrue(false)
                e.printStackTrace()

            } finally {
                jdbc.closeConnection()
            }

        }

        @AfterClass
        @JvmStatic
        fun tearDown() {

            try {

            } catch (e: Exception) {
                LOG.error("Failure to remove test requirements")
                e.printStackTrace()

            } finally {
                jdbc.closeConnection()
            }
        }*/

    }


    @Test
    fun `A | should successful get full list of orders`() {

        LOG.info("should successful get user list")

        try {

          //  val jsonList = service.
         //   val users: List<User> = ServletTestConfig.mapper.convertValue(jsonList)
/*

            Assert.assertTrue(users.isNotEmpty())
            LOG.info("successful user return")
*/

        } catch (e: Exception) {

            LOG.error("Error testing Exception is $e")
            Assert.assertTrue(false)
        }
    }

    @Test
    fun `B | should successful get list of orders per users`() {

        LOG.info("should successful get user list")

     /*   try {

       //     val jsonList = service.getListUser()
            val users: List<User> = ServletTestConfig.mapper.convertValue(jsonList)

            Assert.assertTrue(users.isNotEmpty())
            LOG.info("successful user return")

        } catch (e: Exception) {

            LOG.error("Error testing Exception is $e")
            Assert.assertTrue(false)
        }*/
    }

}