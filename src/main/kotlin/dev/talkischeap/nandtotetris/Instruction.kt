package dev.talkischeap.nandtotetris

sealed class Instruction {
    abstract fun translate(): String
}

data class AInstruction(
    private val address: Int
) : Instruction() {
    override fun translate(): String = "0${address.toBinary(15)}"

    private fun Int.toBinary(bits: Int): String {
        val binary = this.toString(radix = 2)
        val missingLength = bits - binary.length
        return if (missingLength > 0) {
            "0".repeat(missingLength) + binary
        } else {
            binary
        }
    }
}

data class CInstruction(
    private val dest: String,
    private val comp: String,
    private val jump: String
) : Instruction() {
    override fun translate(): String = "111$comp$dest$jump"
}

fun String.isAInstruction(): Boolean = this.startsWith('@')

fun String.isCInstruction(): Boolean = (this.contains('=') || this.contains(';')) && !this.startsWith("//")