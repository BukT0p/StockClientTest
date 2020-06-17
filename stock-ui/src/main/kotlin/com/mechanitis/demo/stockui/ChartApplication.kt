package com.mechanitis.demo.stockui

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationEvent
import org.springframework.context.ConfigurableApplicationContext

class ChartApplication : Application() {

    private var applicationContext: ConfigurableApplicationContext? = null

    override fun init() {
        super.init()
        applicationContext = SpringApplicationBuilder(StockUiApplication::class.java).run()
    }

    override fun start(primaryStage: Stage) {
        applicationContext?.publishEvent(StageReadyEvent(primaryStage))
    }

    override fun stop() {
        super.stop()
        applicationContext?.close()
        Platform.exit()
    }
}

class StageReadyEvent(val stage: Stage) : ApplicationEvent(stage)
