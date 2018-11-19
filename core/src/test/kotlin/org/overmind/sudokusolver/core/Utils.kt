package org.overmind.sudokusolver.core

fun Any.filepath(path: String): String {
    return javaClass.getResource("/$path").file
}