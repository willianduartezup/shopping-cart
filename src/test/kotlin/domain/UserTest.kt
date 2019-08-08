package test.kotlin.domain

import org.hamcrest.MatcherAssert.assertThat
import main.kotlin.domain.User
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation


class UserTest() {

    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun userNameIsEmpty() {

        val user = User("123", "", "teste@teste.com", "123456")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("name"))
    }

    @Test
    fun userEmailIsEmpty() {

        val user = User("123", "teste", "", "123456")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("email"))

    }

    @Test
    fun userPasswordIsEmpty(){

        val user = User("123", "teste", "teste@test.com", "")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("password"))

    }


    private fun getNameFirstProperty(violations: Set<ConstraintViolation<User>>): String {
        return violations.iterator().next().propertyPath.iterator().next().name
    }

}