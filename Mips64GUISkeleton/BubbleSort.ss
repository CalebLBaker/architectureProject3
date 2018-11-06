      -- Main Function
0:  ADDI R1, R0, 4000
4:  ADDI R2, R0, 40
8:  JAL bubbleSort
12:  HALT
      --
      --
      --
      -- Bubble Sort Subroutine
      --r1: addr of beginning of array
      --r2: size of array
      --r3: current address of end of the array (i) 
      --r4: current beginning of the array (j)
      --r5: next value in array after j
      --r6: temp value
      --r7: value from r4
      --r8: value from r5
      --r0: hard coded as 0
      --
LABEL bubbleSort
      --location @ length of array 
16:  ADD R3, R1, R2
20:  ADDI R3, R3, -4
      --Load j value
      --
LABEL OutLoop
      -- Set next and j
24:  ADD R5, R1, R0
28:  ADD R4, R1, R0
      --
      --Start of inner loop
LABEL InLoop
      --end for loop if R4 == R3
      --R5++
32:  ADDI R5, R5, 4
      --
      -- Check if A[j] > A[next]
36:  LW R7, 0(R4)
40:  LW R8, 0(R5)
44:  SUB R6, R7, R8
48:  BLEZ R6, postSwap
      --
      --Swap function
52:  SW R8, 0(R4)
56:  SW R7, 0(R5)
      --
LABEL postSwap
60:  ADD R4, R0, R5
      --Jump to inner loop
64:  BNE R4, R3, InLoop
68:  ADDI R3, R3, -4
72:  BNE R3, R1, OutLoop
      --
      --
LABEL END
76:  JR R31
      --
