package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.ProductDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import main.kotlin.infra.ReadPayload
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ProductService {

    private val jdbc = ConnectionFactory()
    private val mapper = jacksonObjectMapper()
    private val reader = ReadPayload()
    private val factory = DAOFactory()
    private val productDAO: ProductDAO =
        factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO


    fun add(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            val product = reader.mapper<Product>(request.inputStream)

            productDAO.add(product)
            response.setStatus(201, "CREATED")

        } catch (e: Exception) {
            response.sendError(400, "ERROR")
        }
    }

    fun getProductById(req: HttpServletRequest, resp: HttpServletResponse) {

        if (req.pathInfo != null) {
            try {
                val param = req.pathInfo.replace("/", "")

                var product = productDAO.get(param)

                val jsonString = mapper.writeValueAsString(product)

                resp.writer.write(jsonString)
                resp.setStatus(200, "SUCCESS")

            } catch (e: Exception) {
                resp.sendError(400, "User not found!")
            }
        } else resp.sendError(400, "Error, param not found!")
    }

    fun getListProducts(req: HttpServletRequest, resp: HttpServletResponse){
        try{
            var listProduct = productDAO.listProduct()

            val jsonString = mapper.writeValueAsString(listProduct)

            resp.writer.write(jsonString)
            resp.setStatus(200, "OK")
        } catch(e: Exception){
            resp.sendError(400,"List Products not found!")
        }
    }

    fun edit(req: HttpServletRequest, resp: HttpServletResponse){

        try {
            val product = reader.mapper<Product>(req.inputStream)

            productDAO.edit(product)
            resp.setStatus(200, "SUCCESS")

        } catch(e: Exception){
            resp.sendError(400, "Error edit product!")
        }

    }



}