.data
;Creates Array 10 Sized
  array_size = 10
  numbers: .skip array_size*4
  newline: .asciz "\n\r"
  fillthearray: .asciz "Please enter a number to place in the array.\n\r"
  findthenumber: .asciz "Please enter a number to find.\n\r"

.text
;load array into register 2
  ldr  r2, =numbers

;load into register 4 the local variable of the array Sized
  ldr r4, =array_size
  mov r5, #0
  bl  .get_number

;sort the array
  bl  .sort



  swi 0x11


.sort:
    stmfd sp!,{lr}
    ldr  r2, =numbers
    ldr  r4, =array_size
    mov  r5, #2
    cmp  r5, r4
    blt .checkSorted


.checkSorted:
    ldr  r6, [r2]
    ldr  r7, [r2, #4]
    cmp  r6, r7
    bgt  .swap


    ldmfd sp!, {lr}
    mov  pc, lr
.swap:
    str  r6, [r2, #4]
    str  r7, [r2], #4
    mov pc, lr







;if the array_size is bigger than the amount of items in the array add number
; get_number Method
.get_number:
    cmp  r5, r4
    blt  .addNumber
    bgt  .searchNumber
    mov pc, lr

.addNumber:
    mov  r1, #1
    ldr  r1, =fillthearray
    swi  0x69
    mov  r0, #0
    swi  0x6c
    strlt r0, [r2], #4
    mov r0, #1
    add  r5, r0, r5
    b    .get_number

  .searchNumber:
    ldr  r1, =findthenumber
    swi 0x69
    mov r0, #0
    swi 0x6c
.end
