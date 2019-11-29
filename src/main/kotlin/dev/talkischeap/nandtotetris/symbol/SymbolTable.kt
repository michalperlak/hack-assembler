package dev.talkischeap.nandtotetris.symbol

import dev.talkischeap.nandtotetris.isAInstruction
import dev.talkischeap.nandtotetris.isCInstruction

typealias Symbol = String

class SymbolTable private constructor(
    private val symbols: Map<String, Int>
) {
    fun resolve(symbol: Symbol): Int = symbols[symbol] ?: -1

    companion object {
        fun prepare(code: List<String>): SymbolTable = SymbolTable(readSymbols(code))

        private fun readSymbols(code: List<String>): Map<String, Int> {
            val labelDeclarations = readLabelDeclarations(code)
            val symbols = PREDEFINED_SYMBOLS + labelDeclarations
            return symbols + readVariableDeclarations(code, symbols)
        }

        private fun readLabelDeclarations(code: List<String>): Map<String, Int> {
            val symbols = mutableMapOf<String, Int>()
            var currentIndex = 0
            for (instruction in code) {
                if (instruction.isAInstruction() || instruction.isCInstruction()) {
                    currentIndex++
                }
                if (isLabelDeclaration(instruction)) {
                    symbols[instruction.drop(1)] = currentIndex + 1
                }
            }
            return symbols
        }

        private fun readVariableDeclarations(code: List<String>, symbols: Map<String, Int>): Map<String, Int> {
            val variables = mutableMapOf<String, Int>()
            var currentVariableAddress = VARIABLE_START_ADDRESS
            code
                .filter { isNotDeclaredVariableAccess(it, symbols) }
                .map { it.drop(1) }
                .forEach {
                    if (!variables.containsKey(it)) {
                        variables[it] = currentVariableAddress++
                    }
                }
            return variables
        }

        private fun isLabelDeclaration(instruction: String): Boolean = instruction.startsWith('(')

        private fun isNotDeclaredVariableAccess(instruction: String, symbols: Map<String, Int>): Boolean {
            if (!instruction.isAInstruction()) {
                return false
            }
            val symbol = instruction.drop(1)
            return !symbols.containsKey(symbol)
        }

        private val PREDEFINED_SYMBOLS = mapOf(
            "SCREEN" to 16384,
            "KBD" to 24576,
            "SP" to 0,
            "LCL" to 1,
            "ARG" to 2,
            "THIS" to 3,
            "THAT" to 4
        ) + (0..15).map { "R$it" to it }.toMap()

        private const val VARIABLE_START_ADDRESS = 16
    }
}