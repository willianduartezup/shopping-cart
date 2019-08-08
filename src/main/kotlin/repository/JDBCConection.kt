package main.kotlin.repository

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class JDBCConection {

    private val url = "jdbc:postgresql://localhost:5432/shopping-cart-db"
    private val user = "postgres"
    private val password = "postgres"
    private lateinit var connection: Connection

    fun getConnection(): Any {
        try {
            connection = DriverManager.getConnection(url, user, password)
            return connection
        }catch (e :Exception){
            return e.printStackTrace()
        }
    }

    fun closeConnection(){
        try {
            connection.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}