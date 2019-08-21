package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.SalesOrderService
import br.com.zup.shoppingcart.infra.ManagerResponseServlet
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SellerIOrder", value = ["/sales/*"])
class SalesOrderController: HttpServlet() {

    private val service = SalesOrderService(ConnectionFactory(), DAOFactory())
    private val manager = ManagerResponseServlet()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {

        if (req.pathInfo != ""){

        val param = req.pathInfo.replace("/","")

        resp.writer.write(service.getByUserId(param).toString())

        } else {
            resp.sendError(400,"User id not found")
        }

    }


}