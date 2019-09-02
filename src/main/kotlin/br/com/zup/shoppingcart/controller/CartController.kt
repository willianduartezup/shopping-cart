package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.CartService
import br.com.zup.shoppingcart.domain.ItemCart
import br.com.zup.shoppingcart.infra.ManagerResponseServlet
import br.com.zup.shoppingcart.infra.ReadPayload
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "CartController", value = ["/cart/*"])
class CartController : HttpServlet() {

    private val reader = ReadPayload()
    private val manager = ManagerResponseServlet()
    private val service = CartService(DAOFactory())

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val userId = req.pathInfo.replace("/", "")
            val itemCart = reader.mapper<ItemCart>(req.inputStream)
            this.service.add(userId, itemCart)

        } catch (e: Exception) {
            manager.badRequest(resp, e)
        }
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.pathInfo != "") {
            try {
                val idUser = req.pathInfo.replace("/", "")
                manager.succcessObject(resp, this.service.get(idUser))
            } catch (e: Exception) {
                manager.badRequest(resp, e)
            }
        } else {
            manager.badRequest(resp, Exception("Cart not found!"))
        }
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {

        try {
            val itemCart = reader.mapper<ItemCart>(req.inputStream)
            this.service.edit(itemCart)
            manager.succcess(resp, "Item updated")

        } catch (e: Exception) {
            manager.badRequest(resp, e)
        }
    }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val idItemCart = req.pathInfo.replace("/", "")
            this.service.remove(idItemCart)

            manager.succcess(resp, "Item deleted")

        } catch (e: Exception) {
            manager.badRequest(resp, e)
        }
    }
}