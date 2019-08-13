package br.com.zup.shoppingcart

import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import org.junit.Before
import org.mockito.MockitoAnnotations
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class ServletTestConfig(
    var request: HttpServletRequest,
    var response: HttpServletResponse
) {


    companion object {
        var LOG: Logger = LogManager.getLogger(ServletTestConfig::class.java)
        val id = UUID.randomUUID().toString()
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

    }
}

