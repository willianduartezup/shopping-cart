package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.application.UserService
import com.meterware.servletunit.ServletRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.io.FileInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse
import com.mockrunner.servlet.BasicServletTestCaseAdapter
import org.mockito.Mockito.mock


@RunWith(JUnit4::class)
class UserControllerTest : BasicServletTestCaseAdapter() {
    private var request: HttpServletRequest = mock(HttpServletRequestWrapper::class.java)
    private lateinit var requestAdd: HttpServletRequest
    private lateinit var response: HttpServletResponse
    private lateinit var service: UserService

    @Before
    fun setup() {
        LOG.info("UserControllerTest.setup")
        createServlet(UserController::class.java)

        val input = FileInputStream("./src/test/resources/userTest.json")
        val inputStream: Input  =

        service = UserService()
        // request = mock(HttpServletRequestWrapper::class.java)
        response = mock(HttpServletResponse::class.java)
        requestAdd.inputStream
    }

    @Test
    fun `should get user by id`() {
        val sr = ServletRunner()
        sr.registerServlet("UserService", UserService::class.java.name)
        val sc = sr.newClient()
        request.setAttribute("id", id)
        /*try {
            val ic = sc.newInvocation(request)
            val userServlet = ic.servlet as UserController
            val user = userServlet.(ic.request)
            assertTrue(times.contains("Flamengo"))
            assertTrue(times.contains("Vasco"))
            assertEquals(times.size, 2)
        } catch (e: Exception) {
            fail("Error testing TabelaCampeonatoServlet Exception is $e")
            e.printStackTrace()
        }*/

    }

    @Test
    fun `should add user`() {
        LOG.info("should add user")
        val input = FileInputStream("./src/test/resources/userTest.json")
        // request.inputStream =

    }
}

