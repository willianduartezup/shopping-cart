package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.SalesOrder

interface SalesOrderDAO: CrudDAO<SalesOrder, String> {

    @Throws(Exception::class)
    fun getListOrdersUser(id: String): ArrayList<SalesOrder>

    @Throws(Exception::class)
    fun listOrders(): ArrayList<SalesOrder>

}