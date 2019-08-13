package br.com.zup.shoppingcart.infra.exception

import java.lang.Exception

class ValidationException(val listErrors: Map<String, String>): Exception() {


}