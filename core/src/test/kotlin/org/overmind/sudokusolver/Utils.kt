package org.overmind.sudokusolver

fun Any.filepath(path: String): String {
    return javaClass.getResource("/$path").file
}