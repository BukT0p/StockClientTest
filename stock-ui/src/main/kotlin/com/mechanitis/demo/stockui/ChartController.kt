package com.mechanitis.demo.stockui

import com.mechanitis.demo.stockclient.IStockClient
import com.mechanitis.demo.stockclient.StockPrice
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.chart.LineChart
import javafx.scene.chart.XYChart
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class ChartController(private val stockClient: IStockClient) {

    @FXML
    lateinit var chart: LineChart<String, Double>

    @FXML
    fun initialize() {
        val symbolUSD = "USD"
        val priceSubscriberUSD = PriceSubscriber(symbolUSD)
        val symbolUAH = "UAH"
        val priceSubscriberUAH = PriceSubscriber(symbolUAH)

        val data: ObservableList<XYChart.Series<String, Double>> = FXCollections.observableArrayList()
        data.add(priceSubscriberUSD.series)
        data.add(priceSubscriberUAH.series)
        chart.data = data

        stockClient.getPricesFor(symbolUSD).subscribe(priceSubscriberUSD)
        stockClient.getPricesFor(symbolUAH).subscribe(priceSubscriberUAH)
    }
}

class PriceSubscriber(symbol: String) : Consumer<StockPrice> {
    private val seriesData: ObservableList<XYChart.Data<String, Double>> = FXCollections.observableArrayList()
    val series = XYChart.Series(symbol, seriesData)

    override fun accept(stockPrice: StockPrice) {
        Platform.runLater {
            seriesData.add(XYChart.Data(stockPrice.time.second.toString(), stockPrice.price))
        }
    }

}
