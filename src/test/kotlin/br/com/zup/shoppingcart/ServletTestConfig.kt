package br.com.zup.shoppingcart

import br.com.zup.shoppingcart.infra.ReadPayload
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import org.junit.Before
import org.mockito.MockitoAnnotations
import java.util.UUID

class ServletTestConfig {


    companion object {
        var LOG: Logger = LogManager.getLogger(ServletTestConfig::class.java)
        val id = UUID.randomUUID().toString()
        val mapper = jacksonObjectMapper()
        val readPayload = ReadPayload()


    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

    }
}

