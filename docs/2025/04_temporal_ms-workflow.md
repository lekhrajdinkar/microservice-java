# Temporal - microservice workflows

## Overview
<iframe width="560" height="315" src="https://www.youtube.com/embed/zVfOa7z-Gdo?si=n0AgZB9KHULQdd2v"
title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write;
encrypted-media; gyroscope; picture-in-picture; microservice-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

- **Reference**:
    - [https://temporal.io/](https://temporal.io/)
    - 🤖: [https://www.perplexity.ai](https://www.perplexity.ai/search/microservice-with-temporal-htt-jEr_tT_wSFSFxsMm8uCDpQ)
    - git: [https://github.com/Java-Techie-jt/spring-temporal](https://github.com/Java-Techie-jt/spring-temporal)
        - small code and quicly check.
- **insight**
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

## hands-on 
- status: `pending`