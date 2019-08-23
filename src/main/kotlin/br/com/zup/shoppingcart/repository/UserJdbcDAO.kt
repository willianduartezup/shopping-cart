package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.User
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.Connection

class UserJdbcDAO(val connection: Connection) : UserDAO {

    fun ArrayList<String>.toJson() = jacksonObjectMapper().writeValueAsString(this)

    override fun listUsers(): ArrayList<User> {
        val listUser = ArrayList<User>()
        try {
            val stm = connection.createStatement()
            val rs = stm.executeQuery("Select * from users")

            while (rs.next()) {
                val user = User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("deleted"),
                    rs.getString("cart_id"),
                    getArrayList(rs.getString("orders"))
                )

                listUser.add(user)
            }
            stm.close()

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {

            return listUser

        }

    }

    override fun get(id: String): User {

        val stm = connection.prepareStatement("SELECT * FROM users WHERE id = ? and deleted = false")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("User not found")
        }

        val user = User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email"),
            "PRIVATE",
            rs.getBoolean("deleted"),
            rs.getString("cart_id"),
            getArrayList(rs.getString("orders"))
        )

        stm.close()

        return user

    }

    override fun add(e: User): User {

        val psmt =
            connection.prepareStatement("INSERT INTO users(id, name, email, password, deleted, cart_id, orders, cards) VALUES(?,?,?,?,?,?,?::json,?::json)")
        psmt.setString(1, e.id)
        psmt.setString(2, e.name)
        psmt.setString(3, e.email)
        psmt.setString(4, e.password)
        psmt.setBoolean(5, false)
        psmt.setString(6, e.cart_id)
        psmt.setString(7, "[]")
        psmt.setString(8, "[]")

        psmt.execute()
        psmt.close()

        return e
    }

    override fun edit(e: User): User {

        if (e.orders.isNullOrEmpty()) {
            val userB = get(e.id.toString())

            e.orders = userB.orders
        }

        val psmt =
            connection.prepareStatement("UPDATE users SET name = ?, email = ?, password = ?, deleted = false , cart_id = ?, orders = ?::json, WHERE id like ?")
        psmt.setString(1, e.name)
        psmt.setString(2, e.email)
        psmt.setString(3, e.password)
        psmt.setString(4, e.cart_id)
        psmt.setString(5, e.orders?.toJson())
        psmt.setString(6, e.id)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun remove(id: String) {
        val psmt = connection.prepareStatement("UPDATE users SET deleted = ? WHERE id = ?")
        psmt.setBoolean(1, true)
        psmt.setString(2, id)

        psmt.execute()
        psmt.close()
    }

    override fun removeUserFromTable(id: String) {

        val psmt = connection.prepareStatement("DELETE FROM users WHERE id like ?")
        psmt.setString(1, id)

        psmt.execute()
        psmt.close()
    }

    override fun getRemovedUserById(id: String): User {

        val stm = connection.prepareStatement("SELECT * FROM users WHERE id like ? and deleted = true")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("User not found")
        }

        val user = User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email"),
            "PRIVATE",
            rs.getBoolean("deleted"),
            rs.getString("cart_id"),
            getArrayList(rs.getString("orders"))

        )

        stm.close()

        return user

    }

    private fun getArrayList(listUser: String): ArrayList<String> {
        val mapper = jacksonObjectMapper()
        val listReturn = ArrayList<String>()

        if (listUser != "" && listUser != "[]") {

            val actualObj = mapper.readTree(listUser)

            for (i in actualObj) {

                listReturn.add(i.asText())
            }
        }
        return listReturn

    }

}