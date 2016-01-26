package by.westside.staircase.core.server

/**
 * Created by d.pranchuk on 1/26/16.
 */
fun startSyncServer(port: Int) : SyncServer {
    val syncServer = SyncServer(port)
    Thread(syncServer).start()
    return syncServer
}
