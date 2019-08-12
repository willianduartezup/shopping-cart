package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.User

interface UserDAO: CrudDAO<User, String> {}