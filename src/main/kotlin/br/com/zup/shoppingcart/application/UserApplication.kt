package br.com.zup.shoppingcart.application

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface UserApplication {

    fun add(request: HttpServletRequest, response: HttpServletResponse)

    fun getUserById(req: HttpServletRequest, resp: HttpServletResponse)

    fun edit(req: HttpServletRequest, resp: HttpServletResponse)

    fun remove(req: HttpServletRequest, resp: HttpServletResponse)

}