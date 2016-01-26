# staircase
Lightweight kotlin based http server

Example of using:

<code>
val port = 9000
val syncServer = startSyncServer(port)
syncServer.registerListener(ServerListener("/hello", RequestType.GET, { request ->
    HttpResponse(request.httpVersion, ResponseStatus.OK, "Hello from staircase!")
}))
</code>

Request
<code> curl localhost:9000/hello </code>

Response
<code>Hello from staircase!</code>
