package domain

import infra.exception.ValidationException

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val unity: String = "piece",
    val quantity: Int
) {
    fun validateFields() {
        val listErrors = mutableMapOf<String, String>()

        if (id.trim() == "") {
            listErrors["id"] = "id field is required"
        }
        if(name.trim() == ""){
            listErrors["name"] = "name field is required"
        }
        if(quantity == 0){
            listErrors["quantity"] = "quantity field cannot be zero"
            println(listErrors.toList())
        }

        if (listErrors.isNotEmpty()){
            throw ValidationException(listErrors)
        }
    }
}



