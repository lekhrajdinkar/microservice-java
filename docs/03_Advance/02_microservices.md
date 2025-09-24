# microservices
POC - 
## 🔶Stream-response
<iframe width="560" height="315" src="https://www.youtube.com/embed/1Tv1Sb_-TPg?si=-0HIB2Ax4llDpnTZ&amp;start=35" 
title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; 
picture-in-picture; microservice-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

- [https://www.perplexity.ai/search/https-www-youtube-com-watch-v-WvLYGYO3SNyzF9ilMNyPtQ?0=d](https://www.perplexity.ai/search/https-www-youtube-com-watch-v-WvLYGYO3SNyzF9ilMNyPtQ?0=d)
### 🔸StreamingResponseBody
- enables the server to send response data **in chunks**
- significantly **reduces**  (on the server)
    - perceived wait times
    - memory consumption 
- as each record can be **serialized** and written to the output stream **one at a time**
- Not suitable for very high concurrency (more than about `10,000` clients)


## 🔶temporal
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

- hands-on: `pending`


---
## 🔶Spring modulith
<iframe width="560" height="315" src="https://www.youtube.com/embed/RYtIndaGdNg?si=rcQAoeLEbhS7PIcQ" 
title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; 
gyroscope; picture-in-picture; microservice-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

<iframe width="560" height="315" src="https://www.youtube.com/embed/5SwaWowtU30?si=k1p0gjgCcVVYPog2" 
title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; 
encrypted-media; gyroscope; picture-in-picture; microservice-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

- [https://www.perplexity.ai/search/spring-modulith-part-1-https-w-N8l_R84tQc.Qb2IjGhmkTQ](https://www.perplexity.ai/search/spring-modulith-part-1-https-w-N8l_R84tQc.Qb2IjGhmkTQ)
- hands-on: `pending`