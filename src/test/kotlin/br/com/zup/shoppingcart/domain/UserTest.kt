package br.com.zup.shoppingcart.domain

import org.apache.log4j.Logger
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation


class UserTest {

    var LOG = Logger.getLogger(UserTest::class.java)

    private var validator = Validation.buildDefaultValidatorFactory().validator

    private val array = ArrayList<String>()


    @Test
    fun userNameIsEmpty() {
        LOG.info("userNameIsEmpty")

        array.add("123")

        val user = User(null, "", "teste@teste.com", "123456", orders = array)

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("name"))
    }

    @Test
    fun userEmailIsEmpty() {
        LOG.info("userEmailIsEmpty")

        array.add("123")

        val user = User(null, "test", "", "123456", orders = array)

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("email"))

    }

    @Test
    fun userEmailNotValid() {
        LOG.info("userEmailNotValid")

        array.add("123")

        val user = User(null, "test name", "", "123456", orders = array)

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("email"))

    }

    @Test
    fun userPasswordIsEmpty() {
        LOG.info("userPasswordIsEmpty")

        array.add("123")

        val user = User(null, "test", "teste@test.com", "", orders = array)

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

        array.add("123")

        val user = User(null, "test", "teste@test.com", "12", orders = array)

        val violations = validator.validate(user)

        assertThat(violations, hasSize(1))
        assertThat(getNameFirstProperty(violations), `is`("password"))
    }

    private fun getNameFirstProperty(violations: Set<ConstraintViolation<User>>): String {
        LOG.info("getNameFirstProperty")

        return violations.iterator().next().propertyPath.iterator().next().name
    }

}