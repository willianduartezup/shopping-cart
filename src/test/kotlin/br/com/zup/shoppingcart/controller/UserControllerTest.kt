package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.application.UserService
import com.meterware.httpunit.PostMethodWebRequest
import com.meterware.servletunit.InvocationContext
import com.meterware.servletunit.ServletRunner
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import junit.framework.TestCase.assertEquals
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
    lateinit var request: HttpServletRequest
    lateinit var response: HttpServletResponse

    @Mock
    private val service = UserService()

    @Mock
    private val controller = UserController()


    @Before
    fun setup() {
        LOG.info("UserControllerTest.setup")
        request = mock(HttpServletRequest::class.java)
        response = mock(HttpServletResponse::class.java)
    }

    @Test
    fun `should add user`() {
        LOG.info("should add user")
        val sr = ServletRunner()
        sr.registerServlet("UserController", UserController::class.java.name)
        val sc = sr.newClient()
        val request = PostMethodWebRequest("http://localhost:8080/shopping_cart_war")

        try {
            val ic: InvocationContext = sc.newInvocation(request)
            val ppickServlet: PlayerPickServlet = ic.getServlet() as PlayerPickServlet

            assertNull(
                "A session already exists",
                ic.getRequest().getSession(false)
            );
            HttpServletRequest ppickServletRequest = ic . getRequest ();

            // Call the servlets getOpenPool() method
            FootballPool openPool =
            ppickServlet.getOpenPool(ppickServletRequest);

            // Check the return football pool to make sure it is correct
            assertEquals("Kansas City", openPool.getAwayTeam(0));
            assertEquals("Green Bay", openPool.getHomeTeam(0));
            assertEquals("Houston", openPool.getAwayTeam(1));
            assertEquals("Tennessee", openPool.getHomeTeam(1));
        } catch (Exception e) {
            fail(
                "Error testing DisplayPlayerPickServlet Exception
                        is " + e);
            e.printStackTrace();
        }

    }


    @Test
    fun testServletGetUser() {
        LOG.info("testServletGetUser()")

        val input = FileInputStream("./src/test/resources/userTest.json")
        val inputStream = ByteArrayInputStream(input.readBytes())

        service.add(request, response)
        assertEquals(response.status, 201)

        `when`(request.getParameter(id)).thenReturn(s)

        val sw = StringWriter(input.read())
        val pw = PrintWriter(sw)

        `when`(response.writer).thenReturn(pw)

        val servlet = UserController()
        //servlet.doGet(request, response)
        val result = sw.buffer.toString().trim { it <= ' ' }
        assertEquals(result, StringBuffer(input.readBytes().toString()))

        var runner = ServletRunner()
        runner.registerServlet("UserController", UserController::class.java.name)
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

