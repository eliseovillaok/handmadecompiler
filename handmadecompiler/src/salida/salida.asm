.386
option casemap :none
include \masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib

printf PROTO C : VARARG

.data
E_OF_SUMA db "ERROR: Overflow en sumas de datos de punto flotante", 10, 0
buffer db 64 dup(0)
@1E308 real8 1.0E308               ; Un número muy grande para provocar overflow
infinito real8 1.0E308             ; Usa un valor límite de gran tamaño

.data?
resultado sdword ?
impresionFloat dq ?

.code
ERROR_OVERFLOW_SUMA:
    invoke printf, addr E_OF_SUMA
    invoke ExitProcess, 1

START:
    ; Carga los números muy grandes en la FPU
    FLD @1E308
    FLD @1E308

    ; Realiza la suma
    FADD

    ; Compara el resultado con infinito
    FLD ST(0)              ; Duplica el valor en ST(0) para compararlo
    FLD infinito           ; Carga el valor de infinito en la FPU
    FCOMPP                  ; Compara ST(0) y ST(1) y vacía ambos
    FSTSW AX                ; Almacena el FPU Status Word en AX
    SAHF                    ; Mapea los bits de AX en los flags de la CPU
    JZ ERROR_OVERFLOW_SUMA  ; Salta al error si el resultado es infinito

    ; Si no hay overflow, almacena el resultado
    FSTP impresionFloat     ; Guarda el resultado en memoria para impresión
    invoke printf, cfm$("%.5Lf\n"), impresionFloat

    ; Finaliza el programa
    invoke ExitProcess, 0
end START
