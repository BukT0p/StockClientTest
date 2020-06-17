package com.mechanitis.demo.stockclient

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.rsocket.RSocketRequester
import reactor.core.publisher.Flux
import java.io.IOException
import java.time.Duration

class RSocketClientStockClient(private val client: RSocketRequester) : IStockClient {
    private val log: Logger = LoggerFactory.getLogger(RSocketClientStockClient::class.java)

    override fun getPricesFor(symbol: String): Flux<StockPrice> = client
            .route("stockPrices")
            .data(symbol)
            .retrieveFlux(StockPrice::class.java)
            .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(20))
            .doOnError(IOException::class.java) { log.error(it.message) }

    override fun testUrlEncode(url: String): Flux<String> {
        TODO("Not yet implemented")
    }
}
