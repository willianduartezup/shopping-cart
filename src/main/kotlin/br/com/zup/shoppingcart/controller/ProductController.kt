package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.ProductService
import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.infra.ReadPayload
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "ProductController", value = ["/product/*"])
class ProductController : HttpServlet() {

    private val productService = ProductService()
    private val readPayload = ReadPayload()
    private val mapper = jacksonObjectMapper()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            if (req.pathInfo != null) {
                val id = req.pathInfo.replace("/", "")

                val product = productService.getProductById(id)

                val jsonString = mapper.writeValueAsString(product)

                resp.writer.write(jsonString)
                resp.setStatus(200, "SUCCESS")
            } else {
                val jsonString = mapper.writeValueAsString(this.productService.getListProducts())

                resp.writer.write(jsonString)
                resp.setStatus(200, "OK")
            }
        }catch (ex: Exception){
            resp.sendError(400, ex.message)
        }
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        try {
            val product = readPayload.mapper<Product>(req.inputStream)

            productService.add(product)

            resp.setStatus(201, "CREATED")
        }catch (ex: Exception){
            resp.sendError(400, ex.message)
        }
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {

        try {
            val product = readPayload.mapper<Product>(req.inputStream)

            productService.edit(product)

            resp.setStatus(200, "UPDATED")
        }catch (ex: Exception){
            resp.sendError(400, ex.message)
        }
    }
}