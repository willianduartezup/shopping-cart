package br.com.zup.shoppingcart.infra.exception


import javax.validation.Validation

object FieldValidator {
    fun validate(domain: Any) {
        val validator = Validation.buildDefaultValidatorFactory().validator
        val violations = validator.validate(domain)
        var message = ""
        if (violations.size > 0) {
            for (item in violations) {
                message += "${item.message} "
            }
            throw Exception(message)
        }

    }
}
