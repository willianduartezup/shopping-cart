package br.com.zup.shoppingcart.repository

import java.sql.Connection

class DAOFactory {
    fun getInstanceOf(c: Class<*>, connection: Connection): Any? {
        return when(c){
            ProductDAO::class.java ->  ProductJdbcDAO(connection)
            UserDAO::class.java -> UserJdbcDAO(connection)
            CartDAO::class.java -> CartJdbcDAO(connection)
            ItemsCartDAO::class.java -> ItemsCartJdbcDAO(connection)
            SalesOrderDAO::class.java -> SalesOrderJdbcDAO(connection)
            CreditCardDAO::class.java -> CreditCardJdbcDAO(connection)
            else ->  null
        }
    }
}