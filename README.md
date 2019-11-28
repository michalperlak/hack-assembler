# Hack Assembler
Assembler for the hack computer built during NAND to Tetris course.


## Hack language specification

### A-instruction

*Symbolic syntax*: `@value`

Where value is either:
  - a non-negative decimal constant
  - a symbol referring to such a constant

Examples: `@21` `@foo`

*Binary syntax*: `0valueInBinary`

Example: `0000000000010101`

### C-instruction

*Symbolic syntax*: `dest = comp ; jump`

*Binary syntax*: `1 1 1 a c1 c2 c3 c4 c5 c6 d1 d2 d3 j1 j2 j3`

| comp (a = 0)  | comp (a = 1)    | c1 c2 c3 c4 c5 c6|
| ------------- | -------------   | -------------    |
| `0`           |                 | `1 0 1 0 1 0`|
| `1`           |                 | `1 1 1 1 1 1`|
| `-1`          |                 | `1 1 1 0 1 0`|
| `D`           |                 | `0 0 1 1 0 0`|
| `A`           |  `M`            | `1 1 0 0 0 0`|
| `!D`          |                 | `0 0 1 1 0 1`|
| `!A`          |  `!M`           | `1 1 0 0 0 1`|
| `-D`          |                 | `0 0 1 1 1 1`|
| `-A`          |  `-M`           | `1 1 0 0 1 1`|
| `D+1`         |                 | `0 1 1 1 1 1`|
| `A+1`         |  `M+1`          | `1 1 0 1 1 1`|
| `D-1`         |                 | `0 0 1 1 1 0`|
| `A-1`         |  `M-1`          | `1 1 0 0 1 0`|
| `D+A`         |  `D+M`          | `0 0 0 0 1 0`|
| `D-A`         |  `D-M`          | `0 1 0 0 1 1`|
| `A-D`         |  `M-D`          | `0 0 0 1 1 1`|
| `D&A`         |  `D&M`          | `0 0 0 0 0 0`|
| `D\|A`         |  `D\|M`          | `0 1 0 1 0 1`|


| dest          | d1 d2 d3        | the value is stored in: |
| ------------- | -------------   | ----------------------- |
| `null`        | `0 0 0`         | the value is not stored |
| `M`           | `0 0 1`         | `RAM[A]` |
| `D`           | `0 1 0`         | `D` register |
| `MD`          | `0 1 1`         | `RAM[A]` and `D` register |
| `A`           | `1 0 0`         | `A` register |
| `AM`          | `1 0 1`         | `A` register and `RAM[A]` |
| `AD`          | `1 1 0`         | `A` register and `D` register |
| `AMD`         | `1 1 1`         | `A` register, `RAM[A]` and `D` register |


| jump          | j1 j2 j3        | effect: |
| ------------- | -------------   | ----------------------- |
| `null`        | `0 0 0`         | no jump |
| `JGT`         | `0 0 1`         | `if out > 0 jump` |
| `JEQ`         | `0 1 0`         | `if out == 0 jump` |
| `JGE`         | `0 1 1`         | `if out >= 0 jump` |
| `JLT`         | `1 0 0`         | `if out < 0 jump` |
| `JNE`         | `1 0 1`         | `if out != 0 jump` |
| `JLE`         | `1 1 0`         | `if out <= 0 jump` |
| `JMP`         | `1 1 1`         | unconditional jump |

### Symbols

#### Pre-defined symbols

| Symbol        | Value           |
| ------------- | -------------   |
| `R0`          | `0`             |
| `R1`          | `1`             |
| `R2`          | `2`             |
| `R3`          | `3`             |
| `R4`          | `4`             |
| `R5`          | `5`             |
| `R6`          | `6`             |
| `R7`          | `7`             |
| `R8`          | `8`             |
| `R9`          | `9`             |
| `R10`         | `10`            |
| `R11`         | `11`            |
| `R12`         | `12`            |
| `R13`         | `13`            |
| `R14`         | `14`            |
| `R15`         | `15`            |
| `SCREEN`      | `16384`         |
| `KBD`         | `24576`         |
| `SP`          | `0`             |
| `LCL`         | `1`             |
| `ARG`         | `2`             |
| `THIS`        | `3`             |
| `THAT`        | `4`             |

#### Label declaration

`(label)`

#### Variable declaration

`@variableName`
