package br.com.zup.shoppingcart.domain

import br.com.zup.shoppingcart.repository.ConnectionFactory
import br.com.zup.shoppingcart.repository.DAOFactory
import br.com.zup.shoppingcart.repository.UserDAO
import org.apache.log4j.Logger
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import java.util.UUID
import javax.validation.ConstraintViolation
import javax.validation.Validation
import kotlin.test.assertEquals


class UserTest() {

    var LOG = Logger.getLogger(UserTest::class.java)

    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun userNameIsEmpty() {
        LOG.info("userNameIsEmpty")

        val user = User(null, "", "teste@teste.com", "123456")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("name"))
    }

    @Test
    fun userEmailIsEmpty() {
        LOG.info("userEmailIsEmpty")

        val user = User(null, "teste", "", "123456")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("email"))

    }

    @Test
    fun userEmailNotValid() {
        LOG.info("userEmailNotValid")

        val user = User(null, "test name", "", "123456")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("email"))

    }

    @Test
    fun userPasswordIsEmpty() {
        LOG.info("userPasswordIsEmpty")

        val user = User(null, "teste", "teste@test.com", "")

        val violations = validator.validate(user)

        if (violations.size < 1) {
            assertThat(violations, hasSize(1))
        } else if (violations.size > 1) {
            assertThat(violations, hasSize(2))
        }

        assertThat(getNameFirstProperty(violations), `is`("password"))
    }

    @Test
    fun userPasswordIsNotValid() {
        LOG.info("userPasswordIsNotValid")

        val user = User(null, "teste", "teste@test.com", "12")

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("password"))
    }

    private fun getNameFirstProperty(violations: Set<ConstraintViolation<User>>): String {
        LOG.info("getNameFirstProperty")

        return violations.iterator().next().propertyPath.iterator().next().name
    }

}