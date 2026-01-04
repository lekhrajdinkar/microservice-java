## Stream-response
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





---
