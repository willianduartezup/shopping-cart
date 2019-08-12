package repository

import domain.Product
import main.kotlin.domain.User
import java.sql.Connection

class UserJdbcDAO(val connection: Connection) : UserDAO {
    override fun get(id: String): User {

        println("jdbc user get")
        val stm = connection.prepareStatement("SELECT * FROM users WHERE id = ?")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("User not found")
        }

        val user = User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email"),
            "PRIVATE"
        )

        stm.close()

        return user

    }

    override fun add(e: User): User {
        val psmt = connection.prepareStatement("INSERT INTO users(id, name, email, password) VALUES(?,?,?,?)")
        psmt.setString(1, e.id)
        psmt.setString(2, e.name)
        psmt.setString(3, e.email)
        psmt.setString(4, e.password)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun edit(e: User): User {
        val psmt =
            connection.prepareStatement("UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?")
        psmt.setString(1, e.name)
        psmt.setString(2, e.email)
        psmt.setString(3, e.password)
        psmt.setString(4, e.id)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun remove(id: String) {
        val psmt = connection.prepareStatement("DELETE FROM users WHERE id = ?")
        psmt.setString(1, id)

        psmt.execute()
        psmt.close()
    }
}