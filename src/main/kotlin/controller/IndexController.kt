package main.kotlin.controller


import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.log4j.Logger;


@WebServlet(name = "Index", value = ["/index"])
class IndexController: HttpServlet() {

    var LOG = Logger.getLogger(IndexController::class.java)

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        LOG.info("configured loggers")
        resp.writer.write("Hello!! Servlet application is run!")
    }
}