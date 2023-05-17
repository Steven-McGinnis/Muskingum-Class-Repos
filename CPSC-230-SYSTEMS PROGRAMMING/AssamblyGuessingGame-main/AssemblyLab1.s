.data
  introtext: .asciz "Please Guess The Number.\n\r"
  guesshigh: .asciz "Your guess was too high.\n\r"
  guesslow:  .asciz "Your guess was too low.\n\r"
  guesscorrect: .asciz "You guessed correctly.\n\r"
  incrimenttext: .asciz "It took you \r"
  incrimenttext2: .asciz " guesses.\n\r"
.text
;Places the incriment counter.
  mov  r8, #1
  mov  r9, #0

;Gets ClockCount to use for hidden number.
;Then Moves ClockCount to register 8 to be out of the way.
  swi 0x6d
  mov  r7, r0

;Trims ClockCount to get 0 - 256.
  and  r7, r7, #255

.Loopstart:
;Creates Text for Goal of Program.
  ldr  r1, =introtext
  mov r0, #1
  swi 0x69

;Gets Number from user to Compare to Target.
  mov r0, #0
  swi 0x6c

;Compares the number from the user to the random number from ClockCount.
  add  r9, r8, r9
  cmp  r7, r0
  blt  .Greaterthan
  bgt  .Lessthan
  beq  .Loopend

;Returns to the start if the numbers dont match.
  b .Loopstart

;Ends Program.
  swi 0x11

;If the number was less than the Target.
.Greaterthan:
  mov  r0, #0
  ldr  r1, =guesshigh
  swi 0x69
  b .Loopstart

;If the number was greater than the Target.
.Lessthan:
  mov  r0, #0
  ldr  r1, =guesslow
  swi 0x69
  b .Loopstart

;Ends Loop Printing Out the Necessary Strings
.Loopend:

  mov r0, #1
  ldr r1, =guesscorrect
  swi 0x69
  ldr r1, =incrimenttext
  swi 0x69

  mov r0, #1
  mov r1, r9
  swi 0x6b

  mov r0, #1
  ldr r1, =incrimenttext2
  swi 0x69
  swi 0x11

.end
