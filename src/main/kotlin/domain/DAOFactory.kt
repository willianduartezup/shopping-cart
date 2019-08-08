package domain

class DAOFactory {

    fun getInstanceOf(c: Class<*>): Any? {

        return if (c == ProductDAO::class.java) {
            ProductJdbcDAO()
        } else {
            null
        }
    }
}