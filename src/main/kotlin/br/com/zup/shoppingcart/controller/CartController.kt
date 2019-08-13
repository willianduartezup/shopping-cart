package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.CartService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "Cart", value = ["/cart/*"])
class CartController : HttpServlet() {

    private val service = CartService()

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.add(req, resp)
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.get(req, resp)
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.edit(req, resp)
    }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.remove(req, resp)
    }
}