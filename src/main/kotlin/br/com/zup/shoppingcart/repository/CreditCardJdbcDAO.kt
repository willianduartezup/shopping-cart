package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.CreditCard
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.Connection

class CreditCardJdbcDAO(val connection: Connection) : CreditCardDAO {

    override fun get(id: String): CreditCard {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(e: CreditCard): CreditCard {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun edit(e: CreditCard): CreditCard {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
                    rs.getString("cardName"),
                    rs.getDate("expirationDate"),
                    rs.getString("cardName")
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