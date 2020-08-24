## Example code

This repo contains example code that combines the `sente` realtime communication library 
with `Reagent` and `re-frame`. 

The code is based on [the examples section](https://github.com/ptaoussanis/sente/#example-projects) from the `sente` repository.

Note that the code in this repo, both the client and server, is intentionally simplified and include fewer features than the official example at the `sente` repository.

### Client
#### ClojureScript, Reagent, re-frame and sente

Start the app

``` shell
cd sente-example-app
npx shadow-cljs watch frontend
```

### Server
#### Clojure, Compojure, ring, http-kit and sente

Start the server

``` shell
cd sente-example-server
clj -m example.backend.main
```

### References
[sente](https://github.com/ptaoussanis/sente/)
