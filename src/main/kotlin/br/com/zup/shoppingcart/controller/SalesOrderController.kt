package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.SalesOrderService
import br.com.zup.shoppingcart.infra.ManagerResponseServlet
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SalesOrderController", value = ["/sales/*"])
class SalesOrderController: HttpServlet() {

    private val service = SalesOrderService(ConnectionFactory(), DAOFactory())
    private val manager = ManagerResponseServlet()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {

        if (req.pathInfo != ""){

        val param = req.pathInfo.replace("/","")

        resp.writer.write(service.getByOrderId(param).toString())

        } else {
            resp.sendError(400,"User id not found")
        }

    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        try {

            val userId = req.pathInfo.replace("/", "")
            val orderId = this.service.addOrder(userId)

            manager.created(resp, "Item created success -> orderId is: $orderId" )

        } catch (e: Exception) {

            manager.badRequest(resp, e)
        }

    }

}