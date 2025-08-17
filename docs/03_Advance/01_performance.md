# Java Performance
## 🔶Collection/s
### 🔸Loop vs Stream vs Parallel Stream

<iframe width="560" height="315" src="https://www.youtube.com/embed/qe5zyOElzsU?si=vlveqXjZWdn_OG67" 
title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; 
gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>


  - 🤖 [https://www.perplexity.ai/](https://www.perplexity.ai/search/https-www-youtube-com-watch-v-jjXK3MGMRIu5x0hTNJr66A)
  - use For loop and Stream for  small Dataset
  - use Parallel Stream for large Dataset
    - much slower for small tasks 
    - due to thread creation/coordination overhead
  - remember **warmup** the JVM
  - parallel stream is uses **cores of CPU**
    - using a fork/join pool, 
    - making it fast for large CPU-heavy operations.
   
--- 
## 🔶Threads
### 🔸Virtual threads

<iframe width="560" height="315" src="https://www.youtube.com/embed/DZIC_Jrrg4U?si=qhyJZvL7HwmYJkn4" 
title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; 
picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>