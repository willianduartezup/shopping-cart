package br.com.zup.shoppingcart.application

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import br.com.zup.shoppingcart.domain.User
import main.kotlin.infra.ReadPayload
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.UserDAO
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserService {

    private val factory = DAOFactory()
    private val jdbc = ConnectionFactory()
    private val mapper = jacksonObjectMapper()
    private val readPayload = ReadPayload()
    private val userDAO: UserDAO =
        factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO

    fun add(request: HttpServletRequest, response: HttpServletResponse){
        try {
            val user: User = readPayload.mapper<User>(request.inputStream)

            userDAO.add(user)

            response.setStatus(201, "CREATED")

        } catch (e: Exception) {
            response.sendError(400, "ERROR")
        }
    }

    fun getUserById(req: HttpServletRequest, resp: HttpServletResponse){
        if (req.pathInfo != null) {
            val param = req.pathInfo.replace("/", "")
            try {

                val user = userDAO.get(param)
                val jsonString = mapper.writeValueAsString(user)

                resp.writer.write(jsonString)

            } catch (e: Exception) {
                resp.sendError(400, "User not found!")
            }
        } else resp.sendError(400, "Error, param not found!")
    }

    fun edit(req: HttpServletRequest, resp: HttpServletResponse) {
        try {

            val user = readPayload.mapper<User>(req.inputStream)
            userDAO.edit(user)

            resp.setStatus(200, "SUCCESS")
        } catch (e: Exception) {
            resp.sendError(400, "ERROR")
        }
    }

    fun remove(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.pathInfo != null) {
            val param = req.pathInfo.replace("/", "")
            try {

                userDAO.remove(param)

                resp.setStatus(204, "SUCCESS")

            } catch (e: Exception) {
                resp.sendError(400, "ERROR")
            }
        }
    }

}