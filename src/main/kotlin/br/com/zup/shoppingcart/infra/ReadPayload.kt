package br.com.zup.shoppingcart.infra

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.servlet.ServletInputStream

class ReadPayload {


    inline fun <reified T> mapper(content: InputStream): T {
        val payload = getPayload(content)

        return jacksonObjectMapper().readValue(payload, jacksonTypeRef<T>())
    }

    inline fun <reified T> mapper(content: ServletInputStream): T {
        val payload = getPayload(content)

        return jacksonObjectMapper().readValue(payload, jacksonTypeRef<T>())
    }

    fun getPayload(input: InputStream): String {
        val br = BufferedReader(InputStreamReader(input))
        var payload = ""
        if (br != null) {
            for (line in br.lines()) {
                payload += line
            }
        }

        return payload

    }

    fun getPayload(input: ServletInputStream): String {
        val br = BufferedReader(InputStreamReader(input))
        var payload = ""
        if (br != null) {
            for (line in br.lines()) {
                payload += line
            }
        }

        return payload
    }

    fun editCard(card: String): String{

        var charArray = card.toCharArray()
        var cardEdt = ""
        var finalNumber = ""

        for (i in 0 until card.length) {

            if (i < 12) {
                if (i == 4 || i == 8 || i == 12) {
                    cardEdt += "-"
                }
                cardEdt += "X"
            } else {

                finalNumber += charArray[i]
            }

        }
        return "$cardEdt-$finalNumber"

    }

}