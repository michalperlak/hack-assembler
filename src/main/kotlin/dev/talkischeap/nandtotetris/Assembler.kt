package dev.talkischeap.nandtotetris

import dev.talkischeap.nandtotetris.code.InstructionCoder
import dev.talkischeap.nandtotetris.parser.Parser
import dev.talkischeap.nandtotetris.symbol.SymbolTable

class Assembler(
    private val parser: Parser,
    private val coder: InstructionCoder,
    private val symbolTable: SymbolTable
) {

    fun assemble(symbolicCode: List<String>): List<String> = TODO()
}