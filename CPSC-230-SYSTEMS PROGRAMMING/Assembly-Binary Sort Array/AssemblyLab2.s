.data
;Creates Array 10 Sized
  array_size = 10
  numbers: .skip array_size*4
  fillthearray: .asciz "Please enter a number to place in the array.\n\r"
  findthenumber: .asciz "Please enter a number to find.\n\r"
  numberFound:  .asciz "The Number was found at \r"
  notnumberFound: .asciz "The Number was not found in the array. \n\r"
.text
;load array into register 2
  ldr  r2, =numbers

;load into register 4 the local variable of the array Sized
  ldr r4, =array_size
  mov r5, #0
  bl  .get_number

;sort the array
  bl  .bsort

  bl  .binary_search

  swi 0x11


.binary_search:
  stmfd sp!, {lr}
  mov  r5, #2
  mov  r4, #1
  bl  .get_number
  ldr  r0, =numbers                       ;Array Location
  mov  r2, #1                             ;Start Index
  ldr  r3, =array_size
  ldr  r4, =array_size                   ;End Index
  add  r3, r3, #1

;do math to get the elements from the array
.binary_search_next:                      ;r1 is number to search for
  add  r5, r3, r2
  mov  r5, r5, asr #1                     ;Middle Index
  sub  r6, r5, #1
  ldr  r7, [r0, r6, lsl #2]

;if the start spot is larger than the array size
.binary_search_checkSize:
  cmp  r3, r2
  bgt .binary_search_failed

;compare the target vs the array element and branch to necessary adjustments
.binary_search_compare:
  cmp  r1, r7
  beq  .binary_equal
  bgt  .binary_over
  blt  .binary_under

;if the number equals the target
.binary_equal:
  mov r0, #0
  ldr  r1, =numberFound
  swi 0x69
  mov r0, #1
  mov r1, r5
  swi 0x6b
  b .binary_search_done

;if the number is greater
.binary_over:
  mov r2, r5
  b .binary_search_next

;if the number was smaller
.binary_under:
  mov  r3, r5
  b .binary_search_next

.binary_search_failed:
  mov  r0, #0
  ldr  r1, =notnumberFound
  b  .binary_search_done

.binary_search_done:
  ldmfd sp!, {pc}














;Bubble Sort Algorithm
.bsort:
  ldr  r0, =numbers
  ldr  r1, =array_size
  stmfd sp!,{lr}

.bsort_next:
  mov     r2, #0
  mov     r6, #0

.bsort_loop:
  add     r3, r2, #1
  cmp     r3, r1
  bge     .bsort_check
  ldr     r4, [r0, r2, lsl #2]
  ldr     r5, [r0, r3, lsl #2]
  cmp     r4, r5
  strgt   r5, [r0, r2, lsl #2]
  strgt   r4, [r0, r3, lsl #2]
  addgt   r6, r6, #1
  mov     r2, r3
  b       .bsort_loop

.bsort_check:
  CMP     r6, #0
  subgt   r1, r1, #1
  bgt     .bsort_next

;return
.bsort_done:
  ldmfd sp!, {pc}


;if the array_size is bigger than the amount of items in the array add number
; get_number Method
.get_number:
    cmp  r5, r4
    blt  .add_Number
    bgt  .search_Number
    beq  .get_number_done

;return
.get_number_done:
    mov pc, lr

.add_Number:
    ldr  r1, =fillthearray
    swi  0x69
    mov  r0, #0
    swi  0x6c
    strlt r0, [r2], #4
    mov r0, #1
    add  r5, r0, r5
    b    .get_number

  .search_Number:
    mov  r0, #0
    ldr  r1, =findthenumber
    swi 0x69
    mov r0, #0
    swi 0x6c
    mov r1,  r0
    mov  r5, #1
    b   .get_number
.end
