package com.mechanitis.demo.stockclient

import reactor.core.publisher.Flux

interface IStockClient {
    fun getPricesFor(symbol: String): Flux<StockPrice>

    fun testUrlEncode(link: String): Flux<String>
}