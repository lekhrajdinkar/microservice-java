# StockApp
## Run time details
- **Database** : No DB 
- **ApiDoc** : http://localhost:8084/stockApp/swagger-ui/index.html#/
- **Security**: not secured

---
## POC
### Streaming-transfer
- https://chatgpt.com/c/68cafc59-3db0-8330-9d79-8e9daf258693
- NDJSON (Newline Delimited JSON)

- **Case 1**: Send 1000 records all at once | [StockBulkController.java](StockBulkController.java)
```
Single JSON array response ✅
    { "records": [ {...}, {...}, ... 1000 items ... ] }

Paginated response ✅
    Return in chunks (limit + offset, cursor-based pagination).

Compressed response ✅
    Apply gzip / brotli at HTTP layer to reduce payload size.

File-based response + download API ✅
    Generate a CSV/JSON file, return a pre-signed URL (S3 style).
```

- **Case 2**: Stream records one by one | [StockStreamController.java](StockStreamController.java)
```
Chunked Transfer Encoding (HTTP/1.1) ✅
    Keep connection open, send record-by-record as chunks of JSON.
    Example: {"record": {...}}\n{"record": {...}}\n

Server-Sent Events (SSE) ✅
    Stream JSON objects as event: message lines.

webflux (Spring WebFlux) 
    Reactive streams, backpressure support.
---

WebSockets
Bi-directional streaming, send records continuously.

gRPC Streaming
More efficient binary protocol; client consumes records as they arrive.

Multipart responses
Send each JSON as a separate MIME part.
```

---
### Temporal
- todo

---
### Spring Modulith
- todo

