package br.com.zup.shoppingcart.repository

import org.postgresql.ds.PGConnectionPoolDataSource
import java.sql.Connection

class ConnectionFactory {

    //private val url = "jdbc:postgresql://localhost:5432/shopping-cart-db"
    //private val user = "postgres"
    //private val password = "postgres"
    //private lateinit var connection: Connection
    private val pool = PGConnectionPoolDataSource()

    init {
        pool.serverName = "localhost"
        pool.portNumber = 5432
        pool.databaseName = "shopping-cart-db"
        pool.user = "postgres"
        pool.password = "postgres"
        pool.loginTimeout = 20
        pool.socketTimeout = 20
    }


    @Throws(Exception::class)
    fun getConnection(): Connection {
        Class.forName("org.postgresql.Driver")
        /*val pool = PGConnectionPoolDataSource()

        pool.serverName = "localhost"
        pool.portNumber = 5432
        pool.databaseName = "shopping-cart-db"
        pool.user = "postgres"
        pool.password = "postgres"
        pool.loginTimeout = 20
        pool.socketTimeout = 20*/
        // pool.connection.autoCommit = false

        val connection = pool.connection

        connection.autoCommit = false

        return connection
    }

    fun closeConnection(connection: Connection){
        try {
            connection.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}