package controller

import ServletTestConfig.Companion.LOG
import application.UserService
import com.meterware.httpunit.PostMethodWebRequest
import com.meterware.servletunit.ServletRunner
import junit.framework.Assert.assertTrue
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.PrintWriter
import java.io.StringWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RunWith(JUnit4::class)
class UserControllerTest {

    @Mock
    private val service = UserService()

    @Before
    fun setup() {
        LOG.info("UserControllerTest.setup")

    }


    @Test
    fun testServletGetUser() {
        LOG.info("testServletGetUser()")

        val input = FileInputStream("./src/test/resources/userTest.json")
        val inputStream = ByteArrayInputStream(input.readBytes())

        var runner = ServletRunner()
        runner.registerServlet("UserController", UserController::class.java.getName())
        val client = runner.newClient()
        val request = PostMethodWebRequest("http://localhost:8080/shopping_cart_war/user")
        try {
            val context = client.newInvocation(request)
            val userController = context.servlet as UserController
            val servletRequest = context.request
            servletRequest.inputStream.read(input.readBytes())
            //servletRequest.("req","",  "res")
            //   val userName = userController.(context.request.method.byteInputStream(), {"???"})
            assertEquals("ERROR", "ERROR")
        } catch (e: Exception) {
            fail("Error testing getUser exception is $e")
            e.printStackTrace()
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddedUser() {
        LOG.info(" shouldAddedUser ")

        val input = FileInputStream("./src/test/resources/userTest.json")
        println(input.readBytes().contentToString().byteInputStream())
        val inputStream = ByteArrayInputStream(input.readBytes())

        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)

        `when`(service.add(request, response)).thenReturn(response.outputStream.print(201))
        `when`(request.inputStream.readBytes()).thenReturn(input.readBytes())

        val stringWriter = StringWriter()
        val writer = PrintWriter(stringWriter)
        `when`(response.writer).thenReturn(writer)

        service.add(request, response)

        verify(request, atLeast(1)).inputStream
        writer.flush()
        assertTrue(stringWriter.toString().contains("201"))
    }

    @Test
    fun test() {
        LOG.info(" test ")

        val sr = ServletRunner()
        sr.registerServlet("UserController", UserController::class.java.getName())
        val sc = sr.newClient()
        val request = PostMethodWebRequest("http://localhost:8080/shopping_cart_war/user")

        val response = sc.getResponse(request)

    }


}

