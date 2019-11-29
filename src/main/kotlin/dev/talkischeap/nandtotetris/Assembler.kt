package dev.talkischeap.nandtotetris

import dev.talkischeap.nandtotetris.parser.Parser
import dev.talkischeap.nandtotetris.symbol.SymbolTable

class Assembler(
    private val parser: Parser
) {
    fun assemble(symbolicCode: List<String>): List<String> {
        val symbolTable = SymbolTable.prepare(symbolicCode)
        val instructions = parser.parse(symbolicCode, symbolTable)
        return instructions.map { it.translate() }
    }
}