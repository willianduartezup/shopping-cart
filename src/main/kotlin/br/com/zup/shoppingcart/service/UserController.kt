package br.com.zup.shoppingcart.service

import br.com.zup.shoppingcart.application.UserService
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.infra.ReadPayload
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "UserController", value = ["/user/*"])
class UserController : HttpServlet() {

    private val mapper = jacksonObjectMapper()
    private val readPayload = ReadPayload()

    private val service = UserService()

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {

        try {
            val user: User = readPayload.mapper<User>(request.inputStream)
            service.add(user)

        } catch (e: Exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString())
        } finally {
            response.setStatus(HttpServletResponse.SC_CREATED, "User successful created!")
        }
    }

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {

        var jsonUser = ""

        if (request.pathInfo != null) {
            val param = request.pathInfo.replace("/", "")
            try {
                jsonUser = service.getUserById(param)
            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found!")
            } finally {
                response.setStatus(HttpServletResponse.SC_OK, jsonUser)
            }
        } else response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error, param not found!")
    }

    override fun doPut(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            val user = readPayload.mapper<User>(request.inputStream)
            this.service.edit(user)
        } catch (e: Exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString())
        } finally {
            response.setStatus(HttpServletResponse.SC_OK, "User successful updated!")
        }
    }

    override fun doDelete(request: HttpServletRequest, response: HttpServletResponse) {

        if (request.pathInfo != null) {
            val param = request.pathInfo.replace("/", "")
            try {
                this.service.remove(param)
            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString())
            } finally {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT, "User successful removed!")
            }
        }
    }
}
