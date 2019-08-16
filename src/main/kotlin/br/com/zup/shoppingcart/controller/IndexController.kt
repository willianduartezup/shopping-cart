package br.com.zup.shoppingcart.controller

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "Index", value = ["/index"])
class IndexController: HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {

        resp.writer.write("Hello!! Servlet application is run!")
    }
}
