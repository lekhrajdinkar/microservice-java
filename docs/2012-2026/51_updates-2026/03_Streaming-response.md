# Stream-response
## References
- https://www.youtube.com/embed/1Tv1Sb_-TPg?si=-0HIB2Ax4llDpnTZ&amp;start=35
- https://www.perplexity.ai/search/https-www-youtube-com-watch-v-WvLYGYO3SNyzF9ilMNyPtQ?0=d

## StreamingResponseBody
- enables the server to send response data **in chunks**
- significantly **reduces**  (on the server)
    - perceived wait times
    - memory consumption 
- as each record can be **serialized** and written to the output stream **one at a time**
- Not suitable for very high concurrency (more than about `10,000` clients)
