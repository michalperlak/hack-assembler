package dev.talkischeap.nandtotetris

import dev.talkischeap.nandtotetris.parser.Parser
import dev.talkischeap.nandtotetris.symbol.SymbolTable

class Assembler(
    private val parser: Parser
) {
    fun assemble(symbolicCode: List<String>): List<String> {
        val code = removeComments(symbolicCode)
        val symbolTable = SymbolTable.prepare(code)
        val instructions = parser.parse(code, symbolTable)
        return instructions.map { it.translate() }
    }

    private fun removeComments(symbolicCode: List<String>): List<String> = symbolicCode
        .map {
            it
                .replaceAfter("//", "")
                .removeSuffix("//")
                .trim()
        }.filter { it.isNotBlank() }
}