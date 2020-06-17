package com.mechanitis.demo.stockclient

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.time.Duration

class WebClientStockClientIntegrationTest {
    private val webClient: WebClient = WebClient.create()

    @Test
    public fun shouldReceiveStockPricesFromTheService() {
        //given
        val stockClient = WebClientStockClient(webClient)
        val symbol = "USD"
        //when
        val pricesObservable = stockClient.getPricesFor(symbol).take(5)

        //then
        Assertions.assertNotNull(pricesObservable)
        Assertions.assertEquals(5, pricesObservable.count().block())
    }

    @Test
    fun shouldReceiveRedirect() {
        val client = WebClientStockClient(webClient)

        val observable: Flux<String> = client.testUrlEncode("http://localhost/hello?id=y3e4hiu1hbjksabd712g2i")
        val result: String? = observable.take(1).blockFirst(Duration.ofSeconds(1))
        Assertions.assertNotNull(result)
    }
}