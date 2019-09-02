package br.com.zup.shoppingcart.service

import br.com.zup.shoppingcart.ServletTestConfig
import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.application.CartService
import br.com.zup.shoppingcart.application.ProductService
import br.com.zup.shoppingcart.application.UserService
import br.com.zup.shoppingcart.domain.ItemCart
import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CartServiceTest {

    companion object {

        private val factory = DAOFactory()

        private val array = ArrayList<String>()

        private val user: User = User(name = "User Test", email = "test@user.com", password = "PRIVATE", orders = array)

        private val apple = Product(name = "Apple", price = 210, unit = "kg", quantity = 6)
        private val orange = Product(name = "Orange", price = 150, unit = "kg", quantity = 6)
        private val strawberry = Product(name = "Strawberry", price = 550, unit = "tr", quantity = 20)

        private val appleCart = ItemCart(product_id = apple.id!!, price_unit_product = apple.price, quantity = 3)
        private val orangeCart = ItemCart(product_id = orange.id!!, price_unit_product = orange.price, quantity = 3)
        private val strawberryCart = ItemCart(product_id = strawberry.id!!, price_unit_product = strawberry.price, quantity = 5)

        private val userService = UserService(factory)
        private val productService = ProductService(factory)
        private val cartService = CartService(factory)

        @BeforeClass
        @JvmStatic
        fun setup() {

            //BUG ON CREATE CART

            LOG.info("Setup to CartServiceTest")

            try {
                this.userService.add(user)
                this.productService.add(apple)
                this.productService.add(orange)
                this.productService.add(strawberry)

            } catch (e: Exception) {
                LOG.error("Failed into prepare requirements for tests (product or user). Exception is $e")
                Assert.assertTrue(false)
                e.printStackTrace()

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

            }
        }

    }

    @Test
    fun `A | should successfully add cart`() {

        //this.add(userId, itemCart)
        LOG.info("A | should successfully add cart")
        appleCart.quantity = 3

        try {
            cartService.add(user.id!!, appleCart)

            val userCart = userService.getUserById(user.id!!)
            assertTrue(userCart.id == user.id && userCart.cart_id != "")

        } catch (e: Exception) {
            LOG.error("Failed into add cart. Exception is $e")
            Assert.assertTrue(false)
            e.printStackTrace()

        } catch (io: IOException) {
            LOG.error("Failed into add cart. Exception is $io")
            Assert.assertTrue(false)
            io.printStackTrace()

        }
    }

    @Test
    fun `B | should successfully validates creation cart for user`() {
        // IS NECESSARY REVIEW OF CREATE CART_ID INTO USER
        LOG.info("B | should successfully validates creation cart for user")

        val userCart = userService.getUserById(user.id!!)
        assertTrue(userCart.cart_id != "")
    }

    @Test
    fun `D | should failed into insert because not sufficient stock`() {

        LOG.info("D | should failed into insert because not sufficient stock")
        try {

            val appleCart2 = ItemCart(product_id = apple.id!!, price_unit_product = apple.price, quantity = 8)

            cartService.add(user.id!!, appleCart2)
            LOG.error("Error on validates inserting item")
            assertTrue(false)
        } catch (e: Exception) {
            LOG.info("Successfully on return error because not sufficient stock. Exception is $e")
        }

    }

    @Test
    fun `C |  should successfully to edit itemCart`() {

        LOG.info("C | should successfully to edit itemCart")

        //val appleCart = ItemCart(product_id = apple.id!!, price_unit_product = apple.price, quantity = 3)
        //val orangeCart = ItemCart(product_id = orange.id!!, price_unit_product = orange.price, quantity = 2)
        appleCart.quantity = 2
        try {
            cartService.edit(appleCart)
            LOG.info("successfully on edit itemCart")

        } catch (e: Exception) {
            LOG.error("Failed into edit itemCart Exception is $e")
            assertTrue(false)
        }

    }

    @Test
    fun `E | should failed into edit itemCart because cart not have this item`() {

        LOG.info("E | should failed into edit itemCart because cart not have this item")

        orangeCart.quantity = 1

        try {
            cartService.edit(orangeCart)
            LOG.info("successfully on edit itemCart")
            assertTrue(false)

        } catch (e: Exception) {
            LOG.info(" Success on failure returns into edit itemCart. Exception is $e")
        }

    }

    @Test
    fun `F | should successfully add more items `() {

        LOG.info("F | should successfully add more items ")

        try {
           // cartService.edit(orangeCart)
            cartService.add(user.id!!, strawberryCart)


        } catch (e: Exception) {
            LOG.error("Failed. Exception is $e")
            assertTrue(false)
        }

    }

    @Test
    fun `I | should result in successfully remove item of cart do`() {

        LOG.info("I | should result in successfully remove item of cart do")

        try {

            cartService.remove(appleCart.id!!)

        } catch (e: Exception) {
            LOG.error("Failed. Exception is $e")
            assertTrue(false)
        }
    }

    @Test
    fun `K | should result in failed on removes item of cart because not have it`() {

        LOG.info("K | should result in failed on removes item of cart because not have it")

        try {

            val cartUser = userService.getUserById(user.id!!).cart_id!!

            cartService.remove(orangeCart.id!!)
            assertTrue(false)

        } catch (e: Exception) {
            LOG.info("Failed. Exception is $e")
            assertEquals(e.message, "Item Cart not found")
        }
    }

    @Test
    fun `H | should successfully create new cart because the current  cart  it canceled`(){
        LOG.info("H | should successfully create new cart because the current  cart  it canceled")
        try {
        } catch (e: Exception) {
            LOG.error("Failed. Exception is $e")
            assertTrue(false)
        }
    }

    @Test
    fun `G | should successfully get list of the cart `() {
        LOG.info("G | should successfully get list of the cart")

        try {

            val list = cartService.get(user.id!!)
            assertTrue(!list.isEmpty())
        } catch (e: Exception) {
            LOG.error("Failed. Exception is $e")
            assertTrue(false)
        }
    }

    @Test
    fun `J | should failure in get list of the cart because user not have cart `() {
        LOG.info("J | should failure in get list of the cart because user not have cart")

        val userNotHave: User = User(name = "user not car", email = "not_car@user.com", password = "PRIVATE", orders = array)

        try {

            userService.add(userNotHave)
            val list = cartService.get(userNotHave.id!!)

        } catch (e: Exception) {
            LOG.info("Failure in get list of the cart because user not have valid cart. Exception is $e")
        }
    }

    @Test
    fun calculatePrice() {
        Assert.assertEquals(9,cartService.calculatePriceItem(3,3))
    }

}