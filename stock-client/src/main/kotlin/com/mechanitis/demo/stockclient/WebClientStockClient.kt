package com.mechanitis.demo.stockclient

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.io.IOException
import java.time.Duration

class WebClientStockClient(private val webClient: WebClient) : IStockClient {
    private val log: Logger = LoggerFactory.getLogger(WebClientStockClient::class.java)

    override fun getPricesFor(symbol: String): Flux<StockPrice> =
            webClient.get()
                    .uri("http://localhost:8080/stocks/{symbol}", symbol)
                    .retrieve()
                    .bodyToFlux(StockPrice::class.java)
                    .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(20))
                    .doOnError(IOException::class.java) { log.error(it.message) }

    override fun testUrlEncode(link: String): Flux<String> =
            webClient.get()
                    .uri("http://localhost:8080/echo") { it.replaceQuery("redirect=$link").build() }
                    .retrieve()
                    .bodyToFlux(String::class.java)
                    .doOnError(IOException::class.java) { log.error(it.message) }
}