.data
;Creates Array 10 Sized

  array_size = 10
  numbers: .skip array_size*4
  newline: .asciz "\n\r"

.text
; if array_size is not an even number;
  ldr  r0, =array_size
  and  r0, r0, #1
  cmp  r0, #1
  beq  .stopProgram


;Load the Array into memory for manipulation
  ldr  r1, =numbers

;Load the size of the array to shrink as numbers are added
  ldr  r3, =array_size

.FillArray:
;Compare the array_size with 0
  cmp r3, #0
  beq .calculate

;Get the Random Number with the StepCount.
  swi 0x6d
  and  r0, r0, #255

;Put the random number into the array and incriment position
  str r0, [r1], #4

;Decrease incriment counter and tell it to restart
  sub  r3, r3, #1
  b   .FillArray

.calculate:
;Loads all of the numbers into array for loop
  ldr  r2, =numbers
  mov  r3, #0
  ldr  r4, =array_size
  mov  r5, #1
  ldr  r9, =numbers

.resumecalculate:
;Compares the two indexes and stops if they match
  cmp  r3, r4
  beq .stopProgram

;Pull the numbers from array for addition
  ldr  r6, [r2], #4
.ArrayFix:
  cmp  r4, r5
  ldr  r7, [r9], #4
  moveq r5, #1
  ldreq r9, =numbers
  beq .finishCalculation
  add  r5, r5, #1
  b .ArrayFix

;Takes the 2 pulled numbers and adds them outputing the result
.finishCalculation:
  add  r1, r6, r7
  mov r0, #1
  swi 0x6b
  ldr r1, =newline
  swi 0x69
  add  r3, r3, #1
  sub  r4, r4, #1
  b .resumecalculate


.stopProgram:
  swi 0x11



.end
