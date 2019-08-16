package br.com.zup.shoppingcart.infra

import br.com.zup.shoppingcart.infra.response.ServletResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.lang.Exception
import javax.servlet.http.HttpServletResponse

class ManagerResponseServlet {

    private val mapper = jacksonObjectMapper()

    fun created(resp: HttpServletResponse, message: String){
        val jsonString = mapper.writeValueAsString(ServletResponse(message))

        resp.writer.write(jsonString)
        resp.setStatus(201, "CREATED")
    }

    fun succcess(resp: HttpServletResponse, message: String){
        val jsonString = mapper.writeValueAsString(ServletResponse(message))

        resp.writer.write(jsonString)
        resp.setStatus(200, "SUCCESS")
    }

    fun succcessObject(resp: HttpServletResponse, obj: Any){
        val jsonString = mapper.writeValueAsString(obj)

        resp.writer.write(jsonString)
        resp.setStatus(200, "SUCCESS")
    }

    fun badRequest(resp: HttpServletResponse, exception: Exception){
        val jsonString = mapper.writeValueAsString(ServletResponse(exception.message))

        resp.writer.write(jsonString)
        resp.setStatus(400, "BAD REQUEST")
    }
}