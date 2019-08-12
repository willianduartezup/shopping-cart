package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.domain.Product
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import main.kotlin.infra.ReadPayload

import repository.ProductDAO
import java.io.IOException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Product", value = ["/product/*"])
class ProductController : HttpServlet() {

    private val jdbc = ConnectionFactory()


    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val mapper = jacksonObjectMapper()
        try {

            val factory = DAOFactory()
            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO

            if (req.pathInfo == null) {

                var listProduct = productDAO.listProduct()

                val jsonString = mapper.writeValueAsString(listProduct)

                resp.writer.write(jsonString)

                return resp.setStatus(200, "Success!")
            } else {

                var product = productDAO.get(req.pathInfo.replace("/", ""))

                val jsonString = mapper.writeValueAsString(product)

                resp.writer.write(jsonString)
            }


        } catch (e: Exception) {

            resp.sendError(400, "Error list products!")

        }

    }


    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        try {

            val factory = DAOFactory()
            val productDAO: ProductDAO =
                factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO


            val reader = ReadPayload()
            val product = reader.mapper<Product>(req.inputStream)

            productDAO.add(product)

        } catch (e: Exception) {
            resp.sendError(400, "Error")
            resp.writer.write(e.message)
        }
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.pathInfo != null) {
            try {
                val factory = DAOFactory()
                val productDAO: ProductDAO =
                    factory.getInstanceOf(ProductDAO::class.java, jdbc.getConnection()) as ProductDAO


                val reader = ReadPayload()
                val product = reader.mapper<Product>(req.inputStream)

                productDAO.edit(product)

            } catch (e: Exception) {
                resp.setStatus(400, "Error")
            } catch (oi: IOException) {
            }

        } else {
            resp.sendError(400, "Id ")
        }

    }

}