package br.com.zup.shoppingcart.application

import br.com.zup.shoppingcart.domain.CreditCard
import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.CreditCardDAO
import br.com.zup.shoppingcart.repository.DAOFactory

class CreditCardService(private val jdbc: ConnectionFactory, private val factory: DAOFactory) {
    //return credit cart id
    fun getCreditCardById(param: String): CreditCard {

        val connection = jdbc.getConnection()
        try {
            val creditCardDao = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO
            return creditCardDao.get(param)
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }
    fun getCreditCards(): ArrayList<CreditCard> {

        val connection = jdbc.getConnection()
        try {
            val creditCardDao = factory.getInstanceOf(CreditCardDAO::class.java, connection) as CreditCardDAO
            return creditCardDao.listCards()
        } catch (ex: Exception) {
            throw ex
        } finally {
            jdbc.closeConnection()
        }
    }
}