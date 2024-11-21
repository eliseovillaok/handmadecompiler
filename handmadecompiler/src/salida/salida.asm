
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
@10 sdword 1.0
@100000000000000000000000000000000000005 sdword 10000000000000000000000000000000000000.5
@50 sdword 5.0


.data?
_l_program sdword ?
_x_program sdword ?
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

impresionFloat dq ? 

.code
f1_program proc _x_program_f1:DWORD

FLD @50
FSTP _x_program_f1

mov eax, _x_program_f1
mov RetVal, eax

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
FLD @100000000000000000000000000000000000005
FSTP _x_program

FLD @100000000000000000000000000000000000005
FSTP _l_program

FLD _x_program
FADD _l_program
FLD ST(0)
FLDZ
FADD ST, ST
FXCH
FCOMPP
FSTSW AX
SAHF
JP ERROR_OVERFLOW_SUMA
FSTP aux32

FLD aux32
FSTP _x_program

FLD _x_program
FST impresionFloat
invoke printf, cfm$("%.5Lf\n"), impresionFloat


invoke f1_program, @10

FLD invoke f1_program, @10

FSTP _y_program

FLD _y_program
FST impresionFloat
invoke printf, cfm$("%.5Lf\n"), impresionFloat

invoke ExitProcess, 0
end START