package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.CreditCardService
import br.com.zup.shoppingcart.infra.ManagerResponseServlet
import br.com.zup.shoppingcart.infra.ReadPayload
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "CreditCartController", value = ["/creditCart/*"])
class CreditCartController : HttpServlet() {

    private val readPayload = ReadPayload()
    private val manager = ManagerResponseServlet()
    private val service = CreditCardService(ConnectionFactory(), DAOFactory())

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        if (request.pathInfo != null) {
            val param = request.pathInfo.replace("/", "")
            try {
                manager.succcessObject(response, service.getCreditCardById(param))
            } catch (e: Exception) {
                manager.badRequest(response, e)
            }
        } else {
            try {
                manager.succcessObject(response, service.getCreditCards())
            } catch (e: Exception) {
                manager.badRequest(response, e)
            }
        }
    }


}