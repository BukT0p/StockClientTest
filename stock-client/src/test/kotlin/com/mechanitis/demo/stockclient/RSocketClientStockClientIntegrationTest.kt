package com.mechanitis.demo.stockclient

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import reactor.test.StepVerifier

@SpringBootTest
class RSocketClientStockClientIntegrationTest {
    @Autowired
    private lateinit var builder: RSocketRequester.Builder

    @Test
    public fun shouldReceiveStockPricesFromTheService() {
        //given
        val stockClient = RSocketClientStockClient(createRSocketClient()!!)
        val symbol = "USD"
        //when
        val pricesObservable = stockClient.getPricesFor(symbol).take(5)

        //then
        Assertions.assertNotNull(pricesObservable)
        Assertions.assertEquals(5, pricesObservable.count().block())
        StepVerifier.create(pricesObservable.take(2))
                .expectNextMatches { it.symbol == symbol }
                .expectNextMatches { it.symbol == symbol }
                .verifyComplete()
    }

    private fun createRSocketClient(): RSocketRequester? =
            builder.connectTcp("localhost", 7000).block()
}