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
Begin Assembly
-- Stack will be at Org5000 - R30 is SP
LW R2, 4008(R0)
NOP
NOP
LW R3, 4012(R0)
NOP
NOP
LW R4, 4016(R0)
NOP
NOP
LW R5, 4020(R0)
NOP
NOP
ADD R1, R2, R3
NOP
NOP
ADD R1, R1, R4
NOP
NOP
ADD R1, R1, R5
NOP
NOP
ADDI R10, R1, 10
NOP
NOP
SW R10, 4004(R0)
NOP
NOP
HALT
NOP
NOP
NOP
NOP
NOP
End Assembly
-- begin main data
Begin Data 4000 44
10
0
23
71
33
5
93
82
34
13
111
23
End Data
-- stack
Begin Data 5000 100
End Data
