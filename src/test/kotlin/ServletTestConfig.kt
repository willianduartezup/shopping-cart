import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import org.junit.Before
import java.util.UUID


class ServletTestConfig {

    companion object {
        var LOG: Logger = LogManager.getLogger(ServletTestConfig::class.java)
        val id = UUID.randomUUID().toString()
    }

    @Before
    fun setup() {

    }

}