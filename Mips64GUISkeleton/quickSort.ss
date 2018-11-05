      -- Main Function
      -- int main();
      -- R4:	function call first parameter
      -- R5:	function call second paramter
      -- R30:	stack pointer
      --
      --
      -- Set stack pointer
      -- Stack begins at 4000, but there is already an array on the stack
0:  ADDI R30, R0, 4040
      --
      -- quickSort(arr, 10)
4:  ADDI R4, R30, -40
8:  ADDI R5, R0, 10
12:  JAL quickSort
      --
      -- Finish
16:  HALT
      --
      --
      --
      -- Quick Sort Subroutine
      -- void quickSort(long *arr, size_t len);
      -- R2:	Scratch Register
      -- R4:	arr
      -- R5:	len
      -- R8:	pivot_index
      -- R9:	pivot
      -- R10:	i
      -- R11:	j
      -- R12: Scratch Register / left_len
      -- R2, R4, R5, R8, R9, R10, R11, and R12 are caller saved 
      --
LABEL quickSort
20:  ADDI R13, R0, 4
      --
      -- Allocate stack space and put return address on stack
24:  ADDI R30, R30, 24
28:  SW R31, -24(R30)
      --
      -- if (len < 2) return
32:  ADDI R2, R5, -2
36:  BLTZ R2, return
      --
      -- Set pivot, pivot_index, i, and j
40:  ADDI R8, R5, -1
44:  MUL R8, R8, R13
48:  ADD R8, R8, R4
52:  LW R9, 0(R8)
56:  ADD R10, R4, R0
60:  ADD R11, R4, R0
      --
      -- Partition
LABEL partition
64:  LW R2, 0(R11)
68:  SUB R12, R9, R2
72:  BLEZ R12, postIf
76:  LW R12, 0(R10)
80:  SW R2, 0(R10)
84:  SW R12, 0(R11)
88:  ADDI R10, R10, 4
LABEL postIf
92:  ADDI R11, R11, 4
96:  BNE R8, R11, partition
      --
      -- SWAP(i,pivot_index)
100:  LW R2, 0(R8)
104:  LW R12, 0(R10)
108:  SW R2, 0(R10)
112:  SW R12, 0(R8)
      --
      -- Save arr and left_len to stack
116:  SUB R12, R10, R4
120:  DIV R12, R12, R13
124:  SW R4, -16(R30)
128:  SW R12, -8(R30)
      --
      -- quickSort(i + 1, len - left_len - 1)
132:  ADDI R4, R10, 4
136:  SUB R5, R5, R12
140:  ADDI R5, R5, -1
144:  JAL quickSort
      --
      -- quickSort(arr, left_len)
148:  LW R4, -16(R30)
152:  LW R5, -8(R30)
156:  JAL quickSort
      --
LABEL return
160:  LW R31, -24(R30)
164:  ADDI R30, R30, -24
168:  JR R31
      --
