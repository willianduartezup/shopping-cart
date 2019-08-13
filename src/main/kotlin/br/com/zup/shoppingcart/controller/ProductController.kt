package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.ProductService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "Product", value = ["/product/*"])
class ProductController : HttpServlet() {

    private val productService = ProductService()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.pathInfo != null) {
            this.productService.getProductById(req, resp)
        } else {
            this.productService.getListProducts(req, resp)
        }
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        this.productService.add(req, resp)

    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {

        this.productService.edit(req, resp)

    }
}