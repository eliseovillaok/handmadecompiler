
.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\masm32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib

.data
ERROR_OVERFLOW_SUMA db "ERROR: Overflow en sumas de datos de punto flotante", 0
ERROR_RESULTADO_NEGATIVO db "ERROR: Resultados negativos en restas de enteros sin signo", 0
ERROR_INVOCACION db "ERROR: Recursión en invocaciones de funciones", 0
ERROR_OVERFLOW_MUL db "ERROR: Overflow en multiplicación de enteros sin signo", 0
buffer db 10 dup(0)
@25 sdword 2.5
@37 sdword 3.7


.data?
_x_program dw ?
_y_program sdword ?
aux0 dw ?
aux1 dw ?
aux2 dw ?
aux3 dw ?
aux4 dw ?
aux5 dw ?
aux6 dw ?
aux7 dw ?
aux8 dw ?
aux9 dw ?
aux10 dw ?
aux11 dw ?
aux12 dw ?
aux13 dw ?
aux14 dw ?
aux15 dw ?
aux16 dw ?
aux17 dw ?
aux18 dw ?
aux19 dw ?
aux20 dw ?
aux21 dw ?
aux22 dw ?
aux23 dw ?
aux24 dw ?
aux25 dw ?
aux26 dw ?
aux27 dw ?
aux28 dw ?
aux29 dw ?
aux30 dw ?
aux31 dw ?
aux32 sdword ?
aux33 sdword ?
aux34 sdword ?
aux35 sdword ?
aux36 sdword ?
aux37 sdword ?
aux38 sdword ?
aux39 sdword ?
aux40 sdword ?
aux41 sdword ?
aux42 sdword ?
aux43 sdword ?
aux44 sdword ?
aux45 sdword ?
aux46 sdword ?
aux47 sdword ?
aux48 sdword ?
aux49 sdword ?
aux50 sdword ?
aux51 sdword ?
aux52 sdword ?
aux53 sdword ?
aux54 sdword ?
aux55 sdword ?
aux56 sdword ?
aux57 sdword ?
aux58 sdword ?
aux59 sdword ?
aux60 sdword ?
aux61 sdword ?
aux62 sdword ?
aux63 sdword ?

.code

START:
MOV AX, 3
MOV DX, 0
MOV aux0, 8
DIV aux0
MOV aux1, AX

MOV AX, aux1
MOV _x_program, AX

MOV AX, _x_program
MOVZX EAX, AX
MOV aux32, EAX
invoke dwtoa, aux32, addr buffer
invoke StdOut, addr buffer

FLD @25
FDIV @37
FSTP aux33

FLD aux33
FSTP _y_program

FLD _y_program
FIST aux34
invoke dwtoa, aux34, addr buffer
invoke StdOut, addr buffer


invoke ExitProcess, 0
end START