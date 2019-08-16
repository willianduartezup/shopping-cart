package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.UserService
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.infra.ManagerResponseServlet
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
    private val manager = ManagerResponseServlet()

    private val service = UserService()

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {

       try {
            val user: User = readPayload.mapper<User>(request.inputStream)
            service.add(user)

           //response.setStatus(HttpServletResponse.SC_CREATED, "User successful created!")
           manager.created(response, "User created success")
        } catch (e: Exception) {
            //response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString())
           manager.badRequest(response, e)
        }
    }

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {

        var jsonUser = ""

        if (request.pathInfo != null) {
            val param = request.pathInfo.replace("/", "")
            try {
                /*jsonUser = service.getUserById(param)
                response.setStatus(HttpServletResponse.SC_OK, jsonUser)*/
                manager.succcessObject(response, service.getUserById(param))
            } catch (e: Exception) {
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found!")
                manager.badRequest(response, e)
            }
        } else {
            //response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error, param not found!")
            //manager.badRequest(response, Exception("Error, param not found!"))
            try {
                manager.succcessObject(response, service.getListUser())
            }catch (e: Exception){
                manager.badRequest(response, e)
            }
        }
    }

    override fun doPut(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            val user = readPayload.mapper<User>(request.inputStream)
            this.service.edit(user)
            //response.setStatus(HttpServletResponse.SC_OK, "User successful updated!")
            manager.succcess(response, "User updated success")
        } catch (e: Exception) {
            //response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString())
            manager.badRequest(response, e)
        }
    }

    override fun doDelete(request: HttpServletRequest, response: HttpServletResponse) {

        if (request.pathInfo != null) {
            val param = request.pathInfo.replace("/", "")
            try {
                this.service.remove(param)
                //response.setStatus(HttpServletResponse.SC_NO_CONTENT, "User successful removed!")
                manager.succcess(response, "User successful removed!")
            } catch (e: Exception) {
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString())
                manager.badRequest(response, e)
            }
        }
    }
}
