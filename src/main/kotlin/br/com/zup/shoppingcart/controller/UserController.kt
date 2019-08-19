package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.UserService
import br.com.zup.shoppingcart.domain.User
import br.com.zup.shoppingcart.infra.ManagerResponseServlet
import br.com.zup.shoppingcart.infra.ReadPayload
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "User", value = ["/user/*"])
class UserController : HttpServlet() {

    private val readPayload = ReadPayload()
    private val manager = ManagerResponseServlet()
    private val service = UserService(ConnectionFactory(), DAOFactory())

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {

       try {
            val user: User = readPayload.mapper(request.inputStream)
            service.add(user)
            manager.created(response, "User created success")
        } catch (e: Exception) {
            manager.badRequest(response, e)
        }
    }

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {

        if (request.pathInfo != null) {
            val param = request.pathInfo.replace("/", "")
            try {
                manager.succcessObject(response, service.getUserById(param))
            } catch (e: Exception) {
                manager.badRequest(response, e)
            }
        } else {
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
            manager.succcess(response, "User updated success")
        } catch (e: Exception) {
            manager.badRequest(response, e )
        }
    }

    override fun doDelete(request: HttpServletRequest, response: HttpServletResponse) {

        if (request.pathInfo != null) {
            val param = request.pathInfo.replace("/", "")
            try {
                this.service.remove(param)
                manager.succcess(response, "User successful removed")
            } catch (e: Exception) {
                manager.badRequest(response, e)
            }
        }
    }
}
