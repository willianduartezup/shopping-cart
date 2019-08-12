package br.com.zup.shoppingcart.repository

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ConnectionFactory {

    private val url = "jdbc:postgresql://localhost:5432/shopping-cart-db"
    private val user = "postgres"
    private val password = "postgres"
    private lateinit var connection: Connection

    @Throws(Exception::class)
    fun getConnection(): Connection {
        Class.forName("org.postgresql.Driver")
        connection = DriverManager.getConnection(url, user, password)
        return connection
    }

    fun closeConnection(){
        try {
            connection.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}