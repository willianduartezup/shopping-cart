package br.com.zup.shoppingcart.infra

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.servlet.ServletInputStream

class ReadPayload {

    fun getPayload(input: ServletInputStream): String {
        val br = BufferedReader(InputStreamReader(input))
        var res = ""
        if (br != null) {
            for (line in br.lines()) {
                res += line
            }
        }

        return res
    }

    inline fun <reified T> mapper(content: ServletInputStream): T {
        val payload = getPayload(content)

        return jacksonObjectMapper().readValue(payload, jacksonTypeRef<T>())
    }


}