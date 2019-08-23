package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.CreditCard

interface CreditCardDAO: CrudDAO<CreditCard, String> {

    @Throws(Exception::class)
    fun listCardsByUser(userId: String): ArrayList<CreditCard>
}


