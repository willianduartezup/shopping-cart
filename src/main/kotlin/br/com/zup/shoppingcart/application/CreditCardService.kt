package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.CreditCard
import br.com.zup.shoppingcart.infra.exception.FieldValidator
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.CreditCardDAO
import br.com.zup.shoppingcart.repository.DAOFactory

class CreditCardService(private val jdbc: ConnectionFactory, private val factory: DAOFactory) {
    //return credit cart id
    fun getCreditCardById(param: String): CreditCard {

        val connection = jdbc.getConnection()
        try {
            val creditCardDAO = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO
            return creditCardDAO.get(param)
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }
    fun getCreditCards(): ArrayList<CreditCard> {

        val connection = jdbc.getConnection()
        try {
            val creditCardDAO: CreditCardDAO = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO
            return creditCardDAO.listCardsByUser()
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }

    fun insertCard(card: CreditCard){

        FieldValidator.validate(card)
        val connection = jdbc.getConnection()
        try {
            val creditCardDAO: CreditCardDAO = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO

            creditCardDAO.add(card)
            connection.commit()
        } catch (ex: Exception) {
            connection.rollback()
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }
}