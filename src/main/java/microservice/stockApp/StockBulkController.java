package microservice.stockApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

@RestController
@RequestMapping("/stocksApp/bulk-transfer")
public class StockBulkController
{
    @GetMapping("/get-all-stocks")
    public List<Stock> getAllStocks()
    {
        List<Stock> stocks = new java.util.ArrayList<>(1000);
        for (long i = 1; i <= 1000; i++) {
            Stock stock = new Stock();
            stock.setId(i);
            stock.setName("Stock_" + i);
            stock.setSymbol("SYM_" + i);
            stock.setValue(String.valueOf(100 + i));
            stock.setSector("Sector_" + (i % 10));
            stock.setIndustry("Industry_" + (i % 20));
            stocks.add(stock);
        }
        return stocks;
    }
}
