package repository

import java.sql.Connection

class DAOFactory {

    fun getInstanceOf(c: Class<*>, connection: Connection): Any? {

        return if (c == ProductDAO::class.java) {
            ProductJdbcDAO(connection)
        } else {
            null
        }
    }
}