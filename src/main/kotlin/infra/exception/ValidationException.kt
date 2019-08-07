package infra.exception

import java.lang.Exception

class ValidationException(val listErrors: Map<String, String>): Exception() {


}