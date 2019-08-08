package main.kotlin.controller

import main.kotlin.domain.User
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Validation


@WebServlet(name = "Index", value = ["/user"])
class UserController: HttpServlet() {

    private var validator = Validation.buildDefaultValidatorFactory().validator

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.writer.write("Users!")
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {

        var user = User("123","",0,"",0)

        val violations = validator.validate(user)

        for (violation in violations) {
            var error = violation.message

            println(error)
        }

    }

}