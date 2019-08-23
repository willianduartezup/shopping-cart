package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.ProductService
import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.infra.ManagerResponseServlet
import br.com.zup.shoppingcart.infra.ReadPayload
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "ProductController", value = ["/product/*"])
class ProductController : HttpServlet() {

    private val productService = ProductService(ConnectionFactory(), DAOFactory())
    private val readPayload = ReadPayload()
    private val manager = ManagerResponseServlet()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            if (req.pathInfo != null) {
                val id = req.pathInfo.replace("/", "")
                val product = productService.getProductById(id)
                manager.succcessObject(resp, product)
            } else {
                manager.succcessObject(resp, this.productService.getListProducts())
            }
        }catch (ex: Exception){
            manager.badRequest(resp, ex)
        }
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val product = readPayload.mapper<Product>(req.inputStream)
            productService.add(product)

            manager.created(resp, "Product created")
        }catch (ex: Exception){
            manager.badRequest(resp, ex)
        }
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val product = readPayload.mapper<Product>(req.inputStream)
            productService.edit(product)
          
            manager.succcess(resp, "Product updated")
        }catch (ex: Exception){
            manager.badRequest(resp, ex)
        }
    }
}