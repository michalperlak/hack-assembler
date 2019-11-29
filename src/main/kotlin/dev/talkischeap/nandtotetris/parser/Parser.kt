package dev.talkischeap.nandtotetris.parser

import dev.talkischeap.nandtotetris.*
import dev.talkischeap.nandtotetris.symbol.SymbolTable

class Parser {
    fun parse(symbolicCode: List<String>, symbolTable: SymbolTable): List<Instruction> {
        return symbolicCode
            .filter { it.isAInstruction() || it.isCInstruction() }
            .map { buildInstruction(it, symbolTable) }
    }

    private fun buildInstruction(code: String, symbolTable: SymbolTable): Instruction =
        when {
            code.isAInstruction() -> AInstruction(symbolTable.resolve(code.drop(1)))
            code.isCInstruction() -> buildCInstruction(code)
            else -> throw IllegalStateException("Unrecognized instruction: $code")
        }

    private fun buildCInstruction(code: String): Instruction {
        val instruction = getFullCInstruction(code)
        val parts = instruction
            .split('=')
            .flatMap { it.split(';') }
            .map { it.trim() }

        return CInstruction(
            dest = DEST_TABLE[parts[0]] ?: throw IllegalStateException("Unknown dest: ${parts[0]}"),
            comp = COMP_TABLE[parts[1]] ?: throw IllegalStateException("Unknown comp: ${parts[1]}"),
            jump = JUMP_TABLE[parts[2]] ?: throw IllegalStateException("Unknown jump: ${parts[1]}")
        )
    }

    private fun getFullCInstruction(code: String): String =
        if (!code.contains('=')) {
            " =$code"
        } else if (!code.contains(';')) {
            "$code; "
        } else {
            code
        }

    companion object {
        private val COMP_TABLE = mapOf(
            "0" to "0101010",
            "1" to "0111111",
            "-1" to "0111010",
            "D" to "0001100",
            "A" to "0110000",
            "M" to "1110000",
            "!D" to "0001101",
            "!A" to "0110001",
            "!M" to "1110001",
            "-D" to "0001111",
            "-A" to "0110011",
            "-M" to "1110011",
            "D+1" to "0011111",
            "A+1" to "0110111",
            "M+1" to "1110111",
            "D-1" to "0001110",
            "A-1" to "0110010",
            "M-1" to "1110010",
            "D+A" to "0000010",
            "D+M" to "1000010",
            "D-A" to "0010011",
            "D-M" to "1010011",
            "A-D" to "0000111",
            "M-D" to "1000111",
            "D&A" to "0000000",
            "D&M" to "1000000",
            "D|A" to "0010101",
            "D|M" to "1010101"
        )

        private val DEST_TABLE = mapOf(
            "" to "000",
            "M" to "001",
            "D" to "010",
            "MD" to "011",
            "A" to "100",
            "AM" to "101",
            "AD" to "110",
            "AMD" to "111"
        )

        private val JUMP_TABLE = mapOf(
            "" to "000",
            "JGT" to "001",
            "JEQ" to "010",
            "JGE" to "011",
            "JLT" to "100",
            "JNE" to "101",
            "JLE" to "110",
            "JMP" to "111"
        )
    }
}