# staircase
Lightweight kotlin based http server

Example of using:

```kotlin
val port = 9000
val syncServer = startSyncServer(port)
syncServer.registerListener(ServerListener("/hello", RequestType.GET, { request ->
    HttpResponse(request.httpVersion, ResponseStatus.OK, "Hello from staircase!")
}))
```
Request <br/>
``` bash
curl localhost:9000/hello
```
Response
<br/>
```Hello from staircase!```
