package microservice.modernWebApp.stockApp;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

@RestController
@RequestMapping("/stocksApp/bulk-transfer")
public class StockBulkController
{
    private Stock createDummyStock(long i) {
        Stock stock = new Stock();
        stock.setId(i);
        stock.setName("Stock_" + i);
        stock.setSymbol("SYM_" + i);
        stock.setValue(String.valueOf(100 + i));
        stock.setSector("Sector_" + (i % 10));
        stock.setIndustry("Industry_" + (i % 20));
        return stock;
    }


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


    @Operation(
            summary = "Paginated stocks response",
            description = "Returns stocks in chunks using limit and offset for pagination."
    )
    @GetMapping("/get-all-stocks/paginated")
    public ResponseEntity<List<Stock>> getPaginatedStocks(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "100") int limit)
    {
        int start = Math.max(0, offset);
        int end = Math.min(start + limit, 10000);
        java.util.List<Stock> stocks = new java.util.ArrayList<>();
        for (int i = start + 1; i <= end; i++) {
            stocks.add(createDummyStock(i));
        }
        return ResponseEntity.ok(stocks);
    }


    @Operation(
            summary = "Compressed stocks response",
            description = "Returns all stocks with GZIP compression if enabled at HTTP layer."
    )
    @GetMapping("/get-all-stocks/compressed")
    public ResponseEntity<?> getCompressedStocks() {
        java.util.List<Stock> stocks = new java.util.ArrayList<>(10000);
        for (int i = 1; i <= 10000; i++) {
            stocks.add(createDummyStock(i));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_ENCODING, "gzip"); // Spring Boot will compress if enabled 👈🏻
        return ResponseEntity.ok().headers(headers).body(stocks);
    }

    @Operation(
            summary = "File-based stocks response",
            description = "Generates a CSV file with stocks and returns a download URL (simulated pre-signed S3 URL)."
    )
    @GetMapping("/get-all-stocks/file")
    public ResponseEntity<Resource> getStocksFile() throws Exception {
        File file = new File("stocks.csv");
        try (FileWriter writer = new FileWriter(file))
        {
            writer.write("id,name,symbol,value,sector,industry\n");
            for (int i = 1; i <= 10000; i++) {
                Stock s = createDummyStock(i);
                writer.write(String.format("%d,%s,%s,%s,%s,%s\n",
                        s.getId(), s.getName(), s.getSymbol(), s.getValue(), s.getSector(), s.getIndustry()));
            }
        }
        String downloadUrl = "/stocksApp/bulk-transfer/download/stocks.csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv")
                .header("X-Download-Url", downloadUrl)
                .body(new FileSystemResource(file));
    }

    @Operation(
            summary = "Download stocks CSV file",
            description = "Downloads the generated stocks.csv file."
    )
    @GetMapping("/download/stocks.csv")
    public ResponseEntity<Resource> downloadStocksFile() {
        File file = new File("stocks.csv");
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv")
                .body(new FileSystemResource(file));
    }
}
