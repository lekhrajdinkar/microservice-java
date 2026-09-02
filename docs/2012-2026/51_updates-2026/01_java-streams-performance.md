# Java Performance
## references
- https://www.youtube.com/watch?v=qe5zyOElzsU | Loop vs Stream vs Parallel Stream
- https://www.youtube.com/embed/qe5zyOElzsU?si=vlveqXjZWdn_OG67
- https://www.perplexity.ai/search/https-www-youtube-com-watch-v-jjXK3MGMRIu5x0hTNJr66A

---
## Loop vs Stream vs Parallel Stream
- use For loop and Stream for  small Dataset
- use Parallel Stream for large Dataset
    - much slower for small tasks 
      - due to thread creation/coordination overhead
- remember **warmup** the JVM
- parallel stream is uses **cores of CPU**
    - using a fork/join pool, 
      - making it fast for large CPU-heavy operations.


