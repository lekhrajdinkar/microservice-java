# Temporal - microservice workflows

## References
- https://temporal.io/
- https://www.perplexity.ai/search/microservice-with-temporal-htt-jEr_tT_wSFSFxsMm8uCDpQ
- https://github.com/Java-Techie-jt/spring-temporal
- https://www.youtube.com/embed/zVfOa7z-Gdo?si=n0AgZB9KHULQdd2v

## Overview
- open-source **workflow orchestration engine**
- design, run, and observe resilient, **stateful** microservice workflows in Java/Spring Boot.
    - `workflow`, `activities` (business task), `worker`
    - they are implemented as regular Java classes (SB beans)
    - defined `QUEUE` to pull task task for worker.
- manage distributed workflows including **built-in** - retries, rollbacks, long-running
- Temporal persists **state** in pg
    - Workflow history,
    - events
    - metadata
- transaction compensation (the **Saga pattern**). 👈🏻

