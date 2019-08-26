package br.com.zup.shoppingcart.service

import br.com.zup.shoppingcart.ServletTestConfig
import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.mapper
import br.com.zup.shoppingcart.application.CartService
import br.com.zup.shoppingcart.application.OrdersHistoryService
import br.com.zup.shoppingcart.application.ProductService
import br.com.zup.shoppingcart.application.SalesOrderService
import br.com.zup.shoppingcart.application.UserService
import br.com.zup.shoppingcart.domain.ItemCart
import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.service.UserServiceTest.Companion.user
import com.fasterxml.jackson.module.kotlin.convertValue
import org.junit.AfterClass
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SalesOrderServiceTest {

    companion object {

        private val factory = DAOFactory()
        private val jdbc = ConnectionFactory()

        private val array = ArrayList<String>()

        private val userA: User = User(name = "User Test", email = "test@user.com", password = "PRIVATE", orders = array )
        private val userB: User = User(name = "User Test", email = "test@user.com", password = "PRIVATE", orders = array)
        private val userC: User = User(name = "User Test", email = "test@user.com", password = "PRIVATE", orders = array)
        private val userD: User = User(name = "User Test", email = "test@user.com", password = "PRIVATE", orders = array)

        private val apple = Product(name = "Apple", price = 210, unit = "kg", quantity = 6)
        private val orange = Product(name = "Orange", price = 150, unit = "kg", quantity = 6)
        private val strawberry = Product(name = "Strawberry", price = 550, unit = "tr", quantity = 20)

        private val appleCart = ItemCart(product_id = apple.id!!, price_unit_product = apple.price, quantity = 3)
        private val orangeCart = ItemCart(product_id = orange.id!!, price_unit_product = orange.price, quantity = 3)

        private val strawberryCart =
            ItemCart(product_id = strawberry.id!!, price_unit_product = strawberry.price, quantity = 5)

        private val userService = UserService(jdbc, factory)
        private val productService = ProductService(jdbc, factory)
        private val cartService = CartService(jdbc, factory)
        private val salesOrderService = SalesOrderService(ConnectionFactory(), DAOFactory())
        private val ordersHistoryService = OrdersHistoryService(ConnectionFactory(), DAOFactory())

        private lateinit var orderId: String

        @BeforeClass
        @JvmStatic
        fun setup() {

            ServletTestConfig.LOG.info("Setup to CartServiceTest")

            try {
                this.userService.add(userA)
                this.userService.add(userB)
                this.userService.add(userC)

                this.productService.add(strawberry)

                this.cartService.add(userA.id!!, strawberryCart)

            } catch (e: Exception) {
                ServletTestConfig.LOG.error("Failed into prepare requirements for tests. Exception is $e")
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
                //this.userService.removeFromTable(user.id!!)
                //this.productService.removeFromTable(product.id!!)
            } catch (e: Exception) {
                LOG.error("Failure to remove test requirements")
                e.printStackTrace()

            } finally {
                jdbc.closeConnection()
            }
        }

    }


    @Test
    fun `A | should successfully creates sales-order`() {

        LOG.info("A | should successfully creates sales-order")

        try {
            orderId = salesOrderService.addOrder(userA.id!!, "123")

            val userCart = userService.getUserById(userA.id!!)
            assertTrue(userCart.id == userA.id && userCart.cart_id == "")
            LOG.info("SUCCESS")

        } catch (e: Exception) {
            LOG.error("Failed into creates sales-order. Exception is $e")

            Assert.assertTrue(false)
            e.printStackTrace()

        } finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `B | should result error on create sale-order because cart is Empty`() {
        LOG.info("B | should result error on create sale-order because cart is Empty")

        try {
            val orderId = salesOrderService.addOrder(userB.id!!, "123")
            assertTrue(false)

        } catch (e: Exception) {
            assertEquals(e.message, "Invalid cart")
            LOG.info("Failed successfully into creates sales-order. Exception is $e")

        } finally {
            jdbc.closeConnection()
        }

    }

    @Test
    fun `C | should result error on create sale-order because cart is invalid`() {
        LOG.info("C | should result error on create sale-order because cart is invalid`")
        try {
            val orderId = salesOrderService.addOrder(userC.id!!, "123")
            assertTrue(false)

        } catch (e: Exception) {
            assertEquals(e.message, "Invalid cart")
            LOG.info("Failed successfully into creates sales-order. Exception is $e")

        } finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `D | should result error on create sale-order because user not found`() {
        LOG.info("D | should result error on create sale-order because user not found")
        try {
            val orderId = salesOrderService.addOrder(userD.id!!, "123")
            assertTrue(false)

        } catch (e: Exception) {
            assertEquals(e.message, "User not found")
            LOG.info("Failed successfully into creates sales-order. Exception is $e")

        } finally {
            jdbc.closeConnection()
        }
    }

    @Test
    fun `E | should successfully get order`() {
        LOG.info("E | should successfully get order")

        try {
            val order = salesOrderService.getByOrderId(orderId)
            LOG.info("order is: $order")

        } catch (e: Exception) {
            LOG.error("Failed into get order. Exception is $e")
            assertTrue(false)

        } finally {
            jdbc.closeConnection()
        }

    }

    @Test
    fun `F | should failed because user never bought`() {
        LOG.info("F | should failed because user never bought")

        try {
            val order = salesOrderService.getByOrderId(user.id!!)
            LOG.error("order is: $order")
            assertTrue(false)

        } catch (e: Exception) {
            assertEquals(e.message, "Sales order not found")
            LOG.info("Failed into get order. Order not found. Exception is $e")

        } finally {
            jdbc.closeConnection()
        }

    }

    @Test
    fun `G | should successfully returns to all orders list`() {

        LOG.info("should successful get user list")

        try {

            val jsonList = ordersHistoryService.getOrdersUser("")

            LOG.info(" success! returns all orders list\n$jsonList")
            assertTrue(!jsonList.isEmpty)

        } catch (e: Exception) {

            LOG.error("Error. Exception is $e")
            Assert.assertTrue(false)
        }
    }

    @Test
    fun `H | should successful get list of orders per users`() {

        LOG.info("should successful get user list")

        try {
            val jsonList = ordersHistoryService.getOrdersUser(userA.id!!)
            LOG.info(" success! returns orders list of user\n$jsonList")

        } catch (e: Exception) {

            LOG.error("Error. Exception is $e")
            Assert.assertTrue(false)
        }
    }

}

