package br.com.zup.shoppingcart.controller



import br.com.zup.shoppingcart.application.CartService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.log4j.Logger;


@WebServlet(name = "Cart", value = ["/cart"])
class CartController: HttpServlet() {

    val cartService = CartService()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {

    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        this.cartService.add(req,resp)
    }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        this.cartService.romeve(req,resp)
    }

}
