      --A program to find the sum of a list of numbers
      -- The program uses a subroutine to add 2 numbers, as a demo
      -- It also sets up a stack frame, although not needed for this program
      -- 4000 = # of nums to sum
      -- 4004  = location for sum to be put
      -- 4008 = beginning of array of nums
      -- 
      -- R20, R21 - parameter passing regs
      -- R30 = SP
      -- R31 = Ret Addr Reg
      -- R3 = size of array, in bytes
      -- R4 = Address of beginning of array (4008)
      -- R5 = first address past array, for loop termination
      -- R6 = current address being worked on (loop i variable)
      -- R7 = sum
      -- R8 = current array data value
      -- 
      -- Stack will be at Org5000 - R30 is SP
0:  LW R2, 4008(R0)
4:  NOP
8:  NOP
12:  LW R3, 4012(R0)
16:  NOP
20:  NOP
24:  LW R4, 4016(R0)
28:  NOP
32:  NOP
36:  LW R5, 4020(R0)
40:  NOP
44:  NOP
48:  ADD R1, R2, R3
52:  NOP
56:  NOP
60:  ADD R1, R1, R4
64:  NOP
68:  NOP
72:  ADD R1, R1, R5
76:  NOP
80:  NOP
84:  ADDI R10, R1, 10
88:  NOP
92:  NOP
96:  SW R10, 4004(R0)
100:  NOP
104:  NOP
108:  HALT
112:  NOP
116:  NOP
120:  NOP
124:  NOP
128:  NOP
