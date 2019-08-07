package domain

import infra.exception.ValidationException
import org.junit.Test
import kotlin.test.assertEquals
import javax.validation.Validation


class ProductTest {
    @Test
    fun `product field validation`(){
        val product = Product("", "", 0, "piece", 0)
        var ok = 0

        try {
            product.validateFields()
        }catch (ex: ValidationException){
            if(ex.listErrors.containsKey("id") && ex.listErrors.containsKey("name") && ex.listErrors.containsKey("quantity")){
                ok = 1
            }
        }
        assertEquals(1,ok)
    }
}