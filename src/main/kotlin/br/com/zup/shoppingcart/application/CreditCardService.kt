package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.CreditCard
import br.com.zup.shoppingcart.infra.ReadPayload
import br.com.zup.shoppingcart.infra.exception.FieldValidator
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.CreditCardDAO
import br.com.zup.shoppingcart.repository.DAOFactory
import java.sql.Connection

class CreditCardService(private val jdbc: ConnectionFactory, private val factory: DAOFactory) {

    private val readPayload = ReadPayload()

    fun getCreditCardById(param: String): CreditCard {
        val connection = jdbc.getConnection()
        try {
            val creditCardDAO = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO
            return creditCardDAO.get(param)
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection(connection)
        }
    }
    fun getCreditCards(idUser: String): ArrayList<CreditCard> {
        val connection = jdbc.getConnection()
        try {
            val creditCardDAO: CreditCardDAO = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO

            val array = ArrayList<CreditCard>()

            val list = creditCardDAO.listCardsByUser(idUser)

            for (cred in list){

                val credEdt = readPayload.editCard(cred.number)

                val newCred = CreditCard(cred.id,cred.userId,credEdt,cred.expirationDate,cred.cardName, null)

                array.add(newCred)

            }

            return array

        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection(connection)
        }
    }

    fun insertCard(card: CreditCard){

        val connection = jdbc.getConnection()
        try {

            FieldValidator.validate(card)
            val creditCardDAO: CreditCardDAO = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO

            creditCardDAO.add(card)
            connection.commit()
        } catch (ex: Exception) {
            connection.rollback()
            throw ex
        } finally {
            jdbc.closeConnection(connection)
        }
    }

    fun removeCard(cardId: String){
        val connection: Connection = jdbc.getConnection()
        try{
            val creditCardDAO: CreditCardDAO = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO
            creditCardDAO.remove(cardId)
            connection.commit()
        } catch (ex: Exception) {
            connection.rollback()
            throw ex
        } finally {
            jdbc.closeConnection(connection)
        }
    }
}