
.386
option casemap :none
include \masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib

dll_dllcrt0 PROTO C
 printf PROTO C : VARARG
.data
ERROR_OVERFLOW_SUMA db "ERROR: Overflow en sumas de datos de punto flotante", 10, 0
ERROR_RESULTADO_NEGATIVO db "ERROR: Resultados negativos en restas de enteros sin signo", 10, 0
ERROR_INVOCACION db "ERROR: Recursion en una funcion", 10, 0
buffer db 10 dup(0)
limiteFloat sdword 3400000000000000000000000000000000000.0
TAG_ db "TAG ", 10, 0
S_VAR_SINGLE_ db "S_VAR SINGLE ", 10, 0
impresion_struct db "impresion struct", 10, 0
Hola_mundo db "Hola mundo", 10, 0
ITERACION db "ITERACION", 10, 0


.data?
_mistruct_program dd 2 dup(?)
_x_program_f1 dw ?
_x1_program_f1 dw ?
_a_mistruct_program dw ?
_inicio@_program dd ? 
_var_1_program dw ?
_var_2_program sdword ?
_b_mistruct_program sdword ?
_tags_program dw ?
_v_var_program dw ?
_w_var_program dw ?
_s_var_program sdword ?
_u_var_program dw ?
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
RetUint dw ?
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
RetSingle sdword ?

impresionFloat dq ? 

.code
E_RES_NEG:
invoke printf, addr ERROR_RESULTADO_NEGATIVO
invoke ExitProcess, 1

E_OF_SUMA:
invoke printf, addr ERROR_OVERFLOW_SUMA
invoke ExitProcess, 1

E_RECURSION:
invoke printf, addr ERROR_INVOCACION
invoke ExitProcess, 1


f1_program proc _y_program_f1:WORD

MOV AX, _y_program_f1
ADD AX, 1
MOV aux0, AX

MOV AX, aux0
MOV _x1_program_f1, AX

MOV AX, _x1_program_f1
CMP AX, 2
JBE etiqueta0

MOV AX, _x1_program_f1
ADD AX, 2
MOV aux1, AX

mov ax, aux1
mov RetUint, ax

JMP etiqueta1
etiqueta0:

MOV AX, _x_program_f1
MOV DX, 0
MOV aux2, 2
DIV aux2
MOV aux3, AX

mov ax, aux3
mov RetUint, ax

etiqueta1:

ret
f1_program endp

START:
MOV AX, 2
MOV _a_mistruct_program, AX

invoke printf, ADDR impresion_struct

invoke printf, cfm$("%u\n"), _a_mistruct_program

invoke f1_program, _var_1_program

MOV AX, 0
MOV _var_1_program, AX

MOV AX, _var_1_program
ADD AX, 3
MOV aux4, AX

MOV AX, aux4
MOV _var_1_program, AX

invoke printf, ADDR Hola_mundo

invoke printf, cfm$("%u\n"), _var_1_program

etiqueta3:

invoke printf, ADDR ITERACION

MOV AX, _var_1_program
SUB AX,1
JC E_RES_NEG
MOV aux5, AX

MOV AX, aux5
MOV _var_1_program, AX

MOV AX, _var_1_program
CMP AX, 0
JNE etiqueta3

MOV AX, 2
ADD AX, 3
MOV aux6, AX

MOV AX, 3
MOV aux7, 4
MUL aux7
MOV aux8, AX

MOV AX, 4
MOV DX, 0
MOV aux9, 2
DIV aux9
MOV aux10, AX

MOV AX, aux6
MOV _u_var_program, AX

MOV AX, aux8
MOV _v_var_program, AX

MOV AX, aux10
MOV _w_var_program, AX

MOVZX EAX, _u_var_program
MOV aux32, EAX
FILD aux32
FSTP aux32

FLD aux32
FSTP _s_var_program

invoke printf, ADDR S_VAR_SINGLE_

FLD _s_var_program
FST impresionFloat
invoke printf, cfm$("%.5Lf\n"), impresionFloat

MOV AX, 0
MOV _tags_program, AX

inicio@_program:

MOV AX, _tags_program
CMP AX, 5
JNB etiqueta4

MOV AX, _tags_program
ADD AX, 1
MOV aux12, AX

MOV AX, aux12
MOV _tags_program, AX

JMP inicio@_program
etiqueta4:

invoke printf, ADDR TAG_

invoke printf, cfm$("%u\n"), _tags_program

invoke ExitProcess, 0
end START