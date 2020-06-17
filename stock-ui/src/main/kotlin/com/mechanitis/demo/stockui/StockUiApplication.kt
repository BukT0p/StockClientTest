package com.mechanitis.demo.stockui

import javafx.application.Application
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class StockUiApplication

fun main(args: Array<String>) {
    Application.launch(ChartApplication::class.java, *args)
}
