package by.westside.staircase.app

import by.westside.staircase.core.server.SyncServer

/**
 * Created by d.pranchuk on 1/20/16.
 */
fun main(args: Array<String>) {
    Thread(SyncServer(80, 8)).start()
}
