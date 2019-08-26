package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.OrdersHistoryService
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "OrdersHistory", value = ["/history/*"])
class OrdersHistoryController : HttpServlet() {

    private val service = OrdersHistoryService(ConnectionFactory(), DAOFactory())

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        var param = ""
        if (req.pathInfo != null) {
            param = req.pathInfo.replace("/", "")
        }
        resp.contentType = "application/json; charset=utf-8";
        resp.writer.write(service.getOrdersUser(param).toString())
    }
}