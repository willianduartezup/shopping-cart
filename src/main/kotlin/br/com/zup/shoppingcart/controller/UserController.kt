package br.com.zup.shoppingcart.controller

import br.com.zup.shoppingcart.application.UserApplication
import br.com.zup.shoppingcart.application.UserService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "Index", value = ["/user/*"])
class UserController : HttpServlet() {

    private val service = UserService()

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.add(req, resp)
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.getUserById(req, resp)
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.edit(req, resp)
    }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        this.service.remove(req, resp)
    }

    /*private lateinit var userDAO: UserDAO
    private val factory = DAOFactory()
    private val jdbc = ConnectionFactory()
    private val mapper = jacksonObjectMapper()
    private val readPayload = ReadPayload()

    fun add(req: HttpServletRequest, resp: HttpServletResponse){
        try {
            val userDAO: UserDAO =
                factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO
            val user: User = readPayload.mapper<User>(req.inputStream)
            userDAO.add(user)
            return resp.setStatus(201, "CREATED")
        } catch (e: Exception) {
            return resp.sendError(400, "ERROR")
        }
    }

    fun getUserById(req: HttpServletRequest, resp: HttpServletResponse){
        if (req.pathInfo != null) {
            val param = req.pathInfo.replace("/", "")
            try {
                val userDAO: UserDAO =
                    factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO
                val user: User = userDAO.get(param)
                val jsonString = mapper.writeValueAsString(user)

                resp.writer.write(jsonString)
            } catch (e: Exception) {
                resp.sendError(400, "User not found!")
            }
        } else resp.sendError(400, "Error, param not found!")
    }

    fun edit(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val jdbc = ConnectionFactory()
            val userDAO: UserDAO =
                factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO
            val readPayload = ReadPayload()
            val user: User = readPayload.mapper<User>(req.inputStream)
            userDAO.edit(user)
            return resp.setStatus(200, "SUCCESS")
        } catch (e: Exception) {
            return resp.sendError(400, "ERROR")
        }
    }
    fun remove(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.pathInfo != null) {
            val param = req.pathInfo.replace("/", "")
            try {
                val userDAO: UserDAO =
                    factory.getInstanceOf(UserDAO::class.java, jdbc.getConnection()) as UserDAO
                val user = userDAO.get(param)
                return resp.setStatus(204, "SUCCESS")
            } catch (e: Exception) {
                return resp.sendError(400, "ERROR")
            }
        }
    }*/

}