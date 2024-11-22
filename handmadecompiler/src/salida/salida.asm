
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