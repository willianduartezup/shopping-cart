package main.kotlin.controller

import org.slf4j.LoggerFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "Index", value = ["/index"])
class IndexController: HttpServlet() {
    var LOG = LoggerFactory.getLogger(IndexController::class.java)

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        LOG.info("configured loggers")
        resp.writer.write("Hello!! Servlet application is run!")
    }
}