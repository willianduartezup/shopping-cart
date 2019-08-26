package br.com.zup.shoppingcart.infra

import br.com.zup.shoppingcart.infra.response.ServletResponse
import br.com.zup.shoppingcart.infra.response.ServletResponseId
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.lang.Exception
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class ManagerResponseServlet {

    private val mapper = jacksonObjectMapper()

    fun createdId(resp: HttpServletResponse, id: String){
        val jsonString = mapper.writeValueAsString(ServletResponseId(id))

        resp.writer.write(jsonString)
        resp.contentType = "application/json; charset=utf-8";
        resp.status = 201
    }

    fun created(resp: HttpServletResponse, message: String){
        val jsonString = mapper.writeValueAsString(ServletResponse(message))

        resp.writer.write(jsonString)
        resp.contentType = "application/json; charset=utf-8";
        resp.status = 201
    }

    fun succcess(resp: HttpServletResponse, message: String){
        val jsonString = mapper.writeValueAsString(ServletResponse(message))

        resp.writer.write(jsonString)
        resp.contentType = "application/json; charset=utf-8";
        resp.status = 200
    }

    fun succcessObject(resp: HttpServletResponse, obj: Any){
        val jsonString = mapper.writeValueAsString(obj)

        resp.writer.write(jsonString)
        resp.contentType = "application/json; charset=utf-8";
        resp.status = 200
    }

    fun badRequest(resp: HttpServletResponse, exception: Exception){
        
        val jsonString = mapper.writeValueAsString(ServletResponse(exception.message))
        resp.writer.write(jsonString)
        resp.contentType = "application/json; charset=utf-8";
        resp.status = 400
    }
}