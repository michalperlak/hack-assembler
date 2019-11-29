package dev.talkischeap.nandtotetris

import dev.talkischeap.nandtotetris.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import kotlin.streams.toList

internal class AssemblerTest {

    @ParameterizedTest(name = "{0}.asm")
    @ArgumentsSource(TestDataProvider::class)
    fun testAssemble(programName: String, symbolicCode: List<String>, expectedMachineCode: List<String>) {
        val assembler = Assembler(Parser())
        val machineCode = assembler.assemble(symbolicCode)
        assertEquals(expectedMachineCode, machineCode)
    }

    class TestDataProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
            val programs = listOf("Add", "Max", "Pong", "Rect")
            return programs
                .stream()
                .map { Arguments.of(it, getInput(it), getOutput(it)) }
        }

        private fun getInput(programName: String): List<String> = readFile("$INPUT_PATH/$programName.asm")

        private fun getOutput(programName: String): List<String> = readFile("$EXPECTED_PATH/$programName.hack")

        private fun readFile(path: String): List<String> = AssemblerTest::class.java
            .getResourceAsStream(path)
            .bufferedReader()
            .lines()
            .filter { !it.isBlank() }
            .toList()

        companion object {
            const val INPUT_PATH = "/asm"
            const val EXPECTED_PATH = "/hack"
        }
    }
}