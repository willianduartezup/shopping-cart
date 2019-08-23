package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.CreditCard
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.Connection

class CreditCardJdbcDAO(val connection: Connection) : CreditCardDAO {

    override fun get(id: String): CreditCard {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(e: CreditCard): CreditCard {

        val pstm = connection.prepareStatement("INSERT INTO creditcard (id, user_id, number, card_name) VALUES(?, ?, ?,?)")
        pstm.setString(1, e.id)
        pstm.setString(2, e.userId)
        pstm.setString(3, e.number)
        pstm.setString(4, e.cardName)

        pstm.execute()
        pstm.close()

        return e
    }


    override fun edit(e: CreditCard): CreditCard {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun ArrayList<String>.toJson() = jacksonObjectMapper().writeValueAsString(this)

    override fun listCardsByUser(): ArrayList<CreditCard> {
        val listCards = ArrayList<CreditCard>()
        try {

            val pstm = connection.prepareStatement("SELECT * FROM creditcard WHERE user_id = ?")
            pstm.setString(1, e.id)
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