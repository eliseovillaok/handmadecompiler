
.386
option casemap :none
include \masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib

dll_dllcrt0 PROTO C
 printf PROTO C : VARARG
.data
E_OF_SUMA db "ERROR: Overflow en sumas de datos de punto flotante", 10, 0
E_RES_NEG db "ERROR: Resultados negativos en restas de enteros sin signo", 10, 0
E_RECURSION db "ERROR: Recursión en invocaciones de funciones", 10, 0
buffer db 10 dup(0)


.data?
_z_program sdword ?
_f1_program sdword ?
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
f2_program proc _y_program_f2:WORD
MOV AX, _y_program_f2
ADD AX, 4
MOV aux0, AX

MOV AX, aux0
MOV _y_program_f2, AX

mov ax, _y_program_f2
mov RetUint, ax

ret
f2_program endp
f1_program proc _x_program_f1:WORD
invoke f2_program_f1, 1

MOV AX, RetUint
MOV _x_program_f1, AX

MOVZX EAX, _x_program_f1
MOV aux32, EAX
FILD aux32
FSTP aux32

mov eax, aux32
mov RetSingle, eax

ret
f1_program endp


ERROR_RESULTADO_NEGATIVO:
invoke printf, addr E_RES_NEG
invoke ExitProcess, 1

ERROR_OVERFLOW_SUMA:
invoke printf, addr E_OF_SUMA
invoke ExitProcess, 1

ERROR_INVOCACION:
invoke printf, addr E_RECURSION
invoke ExitProcess, 1


START:


invoke f1_program, 0

FLD RetSingle
FSTP _z_program

FLD _z_program
FST impresionFloat
invoke printf, cfm$("%.5Lf\n"), impresionFloat

invoke ExitProcess, 0
end START