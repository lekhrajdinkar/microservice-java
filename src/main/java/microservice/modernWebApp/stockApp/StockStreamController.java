package microservice.modernWebApp.stockApp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Tag(name = "Stock Streaming APIs", description = "APIs for bulk, streaming, and live price updates of stocks.")
@RestController
@RequestMapping("/stocksApp/streaming-transfer")
public class StockStreamController
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

    /*
    ??NLDJSON (Newline Delimited JSON) format:
    - Each line is a valid JSON object, Records are separated by \n (newline), not wrapped in a big JSON array
    - Streaming-friendly → client can parse as soon as a line arrives

    {"id": 1, "name": "Alice"}
    {"id": 2, "name": "Bob"}
    {"id": 3, "name": "Charlie"}
    // notice : no commas or array brackets


    Purpose: Low-level streaming of the entire response body via an OutputStream.

    Controller returns StreamingResponseBody.
    You write "raw bytes" to the stream directly. ????
    More control, but you manage flushing and formatting.

    Use case: Streaming large files (CSV, ZIP, video, logs).
     */
    @Operation(
            summary = "Stream 1000 stocks using StreamingResponseBody",
            description = "Streams 1000 dummy stock records as a JSON array using StreamingResponseBody. " +
                    "Data is written directly to the response output stream for efficient transfer.")
    @GetMapping("/get-all-stocks/StreamingResponseBody")
    public StreamingResponseBody streamAllStocksStreamingResponse(
            //HttpServletResponse response
            // some reason not injecting. error:
            // java.lang.IllegalStateException: No primary or single unique constructor found for interface javax.servlet.http.HttpServletResponse - put on hold ????
    ) {
        //response.setContentType("application/json"); // "text/event-stream" ????
        //response.setHeader("X-Content-Stream", "true"); 👈🏻
        return outputStream -> {
            //outputStream.write("[".getBytes());
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            for (long i = 1; i <= 1000; i++) {
                Stock stock = createDummyStock(i);
                String json = mapper.writeValueAsString(stock); //json string
                //if (i > 1) outputStream.write(",".getBytes());
                outputStream.write((json + "\n").getBytes());
                outputStream.flush(); //flush after every write, to send data in chunks
            }
            //outputStream.write("]".getBytes());
            outputStream.flush();
        };
    }


    /*
    Purpose: General async streaming of data fragments (text, JSON, binary).

    Controller returns ResponseBodyEmitter.
    You can call emitter.send(obj) multiple times → each is written to response as it’s available.
    Client must know how to parse (often NDJSON or custom delimiter).

    Use case: Long-running tasks that emit multiple responses (e.g., pushing intermediate results in JSON).
     */
    @Operation(
            summary = "Stream 1000 stocks using ResponseBodyEmitter",
            description = "Streams 1000 dummy stock records as a JSON array using ResponseBodyEmitter. " +
                    "Each record is sent individually for efficient memory usage.")
    @GetMapping(
            value="/get-all-stocks/ResponseBodyEmitter",
            produces = "application/x-ndjson"  //👈🏻
    )
    public ResponseBodyEmitter streamAllStocks() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        new Thread(() -> {
            try {
                //emitter.send("[");
                for (long i = 1; i <= 1000; i++) {
                    Stock stock = createDummyStock(i);
                    String json = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(stock);
                    //if (i > 1) emitter.send(",");
                    emitter.send(json, MediaType.APPLICATION_JSON );
                    emitter.send("\n"); // newline separator or custom delimiter
                    Thread.sleep(10);   // simulate delay
                }
                //emitter.send("]");
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }


    /*
        // Purpose: Specialization of ResponseBodyEmitter for Server-Sent Events (SSE).

        Automatically sets Content-Type: text/event-stream.
        (produces = "text/event-stream") 👈🏻👈

        Real-time event updates to browsers (stock prices, notifications, logs).
     */
    @Operation(
            summary = "Live price update for a stock (SSE)",
            description = "Streams live price updates for a given stock every second using Server-Sent Events (SSE). " +
                    "Sends 100 updates simulating price changes.")
    @GetMapping("/live-price-update/{stockId}")
    public SseEmitter livePriceUpdate(@PathVariable Long stockId) {
        SseEmitter emitter = new SseEmitter();
        new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) { // send 100 updates, or use while(true) for infinite
                    Stock stock = createDummyStock(stockId);
                    // Simulate price change
                    double price = 100 + stockId + Math.random() * 10 + i;
                    stock.setValue(String.valueOf(price));
                    emitter.send(stock);
                    Thread.sleep(1000); // 1 second interval
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }


}
