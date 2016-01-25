package by.westside.staircase.core.util

import java.util.*

/**
 * Created by d.pranchuk on 1/25/16.
 */
fun executionTime(block : () -> Unit ) : Long {
    val startDate = Date().time
    block()
    return Date().time - startDate
}
