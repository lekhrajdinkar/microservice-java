# StockApp
## Overview
- Streaming JSON response
- spring Modulith ...
- Spring temporal ...

## Run time details
- **Database** : No DB 
- **ApiDoc** : http://localhost:8084/stockApp/swagger-ui/index.html#/
- **Security**: not secured

---
## POC
[02_microservices.md](../../../../../docs/03_Advance/02_microservices.md) >> [ point 1,2 and 3 ]

### 1 Streaming-transfer
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
    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv") 🔸
    produces = "octet-stream" 🔸
```

- **Case 2**: Stream records one by one | [StockStreamController.java](StockStreamController.java)
```
Chunked Transfer Encoding (HTTP/1.1) ✅
    Keep connection open, send record-by-record as chunks of JSON.
    Example: {"record": {...}}\n{"record": {...}}\n

Server-Sent Events (SSE) ✅
    Stream JSON objects as event: message lines.
    produces = "text/event-stream" 🔸

webflux (Spring WebFlux)
    preferred approach <<<< 
    better scalability, non-blocking I/O, for large data sets.
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
### 2 Temporal
- todo

---
### 3 Spring Modulith
- todo

