package com.mechanitis.demo.stockui

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.util.Callback
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class StageInitializer(
        @Value("\${spring.application.ui.title}") private val applicationTitle: String,
        val applicationContext: ApplicationContext
) : ApplicationListener<StageReadyEvent> {

    @Value("classpath:/chart.fxml")
    private lateinit var chartResource: Resource

    override fun onApplicationEvent(event: StageReadyEvent) {
        val fxmlLoader = FXMLLoader(chartResource.url)
        fxmlLoader.controllerFactory = Callback { applicationContext.getBean(it) }
        val parent: Parent = fxmlLoader.load()
        event.stage.apply {
            scene = Scene(parent, 800.0, 600.0)
            title = applicationTitle
            show()
        }
    }
}