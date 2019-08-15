package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.UserService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "User", value = ["/user/*"])
class UserController : HttpServlet() {

    private val service = UserService()

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.add(req, resp)
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.pathInfo != null) {
            this.service.getUserById(req, resp)
        } else {
            this.service.getListUser(req, resp)
        }
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.edit(req, resp)
    }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.remove(req, resp)
    }
}
