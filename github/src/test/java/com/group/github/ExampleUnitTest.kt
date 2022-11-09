package com.group.github

import com.group.base.ktor.ServerFactory
import com.group.base.modular.github.server.ktor.routes
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import java.util.logging.Logger

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val logger: Logger = Logger.getLogger("KtorServerTest")
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun startTestServer() = testApplication { //withTestApplication Deprecated
        runBlocking {
            val server = ServerFactory.getServer(8080)
            server.application.apply {
//                moduleValidateInputGithubAccount()
//                routeTransfer()
//                routeFork()
                routes()
            }
            logger.info("Starting server...")
            server.start(wait = true)
            assertEquals("HttpStatusCode.OK", "response.status")
        }
    }
}