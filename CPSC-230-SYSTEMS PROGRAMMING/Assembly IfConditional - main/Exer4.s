@if ((x > y && y > z) || (y < z && y < x))

.data
  istruestring: .asciz "The if Statement is True\n\r"
  x: .word 3
  y: .word 4
  z: .word 5

.text
  ldr  r1, =x
  ldr  r2, =y
  ldr  r3, =z

  cmpgt  r1, r2
  cmpgt  r2, r3
  bgt .istrue

  cmplt  r2, r3
  cmplt  r1, r2
  blt .istrue

  swi 0x11

.istrue:
  ldr  r0, =istruestring
  swi 0x02
  swi 0x11
