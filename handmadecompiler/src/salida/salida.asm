
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
@340000000000000000000000000000000000000 sdword 34000000000000000000000000000000000000.0


.data?
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
E_RES_NEG:
invoke printf, addr ERROR_RESULTADO_NEGATIVO
invoke ExitProcess, 1

E_OF_SUMA:
invoke printf, addr ERROR_OVERFLOW_SUMA
invoke ExitProcess, 1

E_RECURSION:
invoke printf, addr ERROR_INVOCACION
invoke ExitProcess, 1


START:
FLD @340000000000000000000000000000000000000
FSTP _x_program

FLD @340000000000000000000000000000000000000
FSTP _y_program

FLD _x_program
FADD _y_program
FCOM sdword ptr limiteFloat
FSTSW AX
SAHF
JA E_OF_SUMA
FSTP aux32

FLD aux32
FSTP _x_program

FLD _x_program
FST impresionFloat
invoke printf, cfm$("%.5Lf\n"), impresionFloat

invoke ExitProcess, 0
end START