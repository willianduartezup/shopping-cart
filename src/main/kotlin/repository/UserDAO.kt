package repository

import main.kotlin.domain.User

interface UserDAO: CrudDAO<User, String> {}