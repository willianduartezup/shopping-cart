package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.CreditCard
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.Connection

class CreditCardJdbcDAO(val connection: Connection) : CreditCardDAO {

    override fun get(id: String): CreditCard {

        val stm = connection.prepareStatement("SELECT * FROM users WHERE id = ? and deleted = false")
        stm.setString(1, id)

        val rs = stm.executeQuery()

        if (!rs.next()) {
            throw Exception("User not found")
        }

        val credCart = CreditCard(
            rs.getString("id"),
            rs.getString("number"),
            rs.getInt("expirationDate"),
            rs.getString("cardName"),
            rs.getString("cvv")
        )

        stm.close()

        return credCart

    }

    override fun add(e: CreditCard): CreditCard {
        val psmt =
            connection.prepareStatement("INSERT INTO creditCard(id, number, expirationDate, cardName) VALUES(?,?,?,?)")
        psmt.setString(1, e.number)
        psmt.setInt(2, e.expirationDate)
        psmt.setString(3, e.cardName)
        psmt.setString(4, e.id)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun edit(e: CreditCard): CreditCard {
        val psmt =
            connection.prepareStatement("UPDATE creditCard SET number = ?, expirationDate = ?, cardName = ? WHERE id like ?")
        psmt.setString(1, e.number)
        psmt.setInt(2, e.expirationDate)
        psmt.setString(3, e.cardName)
        psmt.setString(4, e.id)

        psmt.execute()
        psmt.close()

        return e
    }

    override fun remove(id: String) {
        val psmt = connection.prepareStatement("DELETE FROM creditCard WHERE id like ?")
        psmt.setString(1, id)

        psmt.execute()
        psmt.close()
    }

    fun ArrayList<String>.toJson() = jacksonObjectMapper().writeValueAsString(this)

    override fun listCards(): ArrayList<CreditCard> {
        val listCards = ArrayList<CreditCard>()
        try {
            val stm = connection.createStatement()
            val rs = stm.executeQuery("Select * from creditCard")

            while (rs.next()) {
                val card = CreditCard(
                    rs.getString("id"),
                    rs.getString("number"),
                    rs.getInt("expirationDate"),
                    rs.getString("cardName"),
                    rs.getString("cvv")
                )
                listCards.add(card)
            }
            stm.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return listCards
        }
    }
}