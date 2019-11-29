package dev.talkischeap.nandtotetris

sealed class Instruction {
    abstract fun translate(): String
}

data class AInstruction(
    private val address: Int
) : Instruction() {
    override fun translate(): String = "0${address.toString(radix = 2)}"
}

data class CInstruction(
    private val dest: String,
    private val comp: String,
    private val jump: String
) : Instruction() {
    override fun translate(): String = "111$comp$dest$jump"
}

fun String.isAInstruction(): Boolean = this.startsWith('@')

fun String.isCInstruction(): Boolean = this.contains('=')