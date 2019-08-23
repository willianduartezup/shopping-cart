package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.CreditCard
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.Connection

class CreditCardJdbcDAO(val connection: Connection) : CreditCardDAO {

    override fun get(id: String): CreditCard {

        val stm = connection.prepareStatement("SELECT * FROM creditcard WHERE id = ?")
        stm.setString(1, id)
        val rs = stm.executeQuery()
        if (!rs.next()) {
            throw Exception("User not found")
        }
        val credCart = CreditCard(
            rs.getString("id"),
            rs.getString("userId"),
            rs.getString("number"),
            rs.getDate("expirationDate"),
            rs.getString("cardName"),
            null

        )
        stm.close()
        return credCart
    }

    override fun add(e: CreditCard): CreditCard {

        val pstm =
            connection.prepareStatement("INSERT INTO creditcard (id, user_id, number, card_name) VALUES(?, ?, ?,?)")
        pstm.setString(1, e.id)
        pstm.setString(2, e.userId)
        pstm.setString(3, e.number)
        pstm.setString(4, e.cardName)
        pstm.execute()
        pstm.close()
        return e
    }


    override fun edit(e: CreditCard): CreditCard {
        val psmt =
            connection.prepareStatement("UPDATE creditCard SET number = ?, expiration_data = ?, card_name = ? WHERE id like ?")
        psmt.setString(1, e.number)
        psmt.setDate(2, e.expirationDate)
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

    override fun listCardsByUser(userId: String): ArrayList<CreditCard> {
        val listCards = ArrayList<CreditCard>()
        try {
            val pstm = connection.prepareStatement("SELECT * FROM creditcard WHERE user_id = ?")
            pstm.setString(1, userId)
            val rs = pstm.executeQuery()
            while (rs.next()) {
                val card = CreditCard(
                    rs.getString("id"),
                    rs.getString("userId"),
                    rs.getString("number"),
                    rs.getDate("expirationDate"),
                    rs.getString("cardName"),
                    null

                )
                listCards.add(card)
            }
            pstm.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return listCards
        }
    }
}