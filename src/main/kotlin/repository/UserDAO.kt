package repository

import domain.User

interface UserDAO: CrudDAO<User, String> {}