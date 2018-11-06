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
      --
      -- Allocate stack space and put return address on stack
20:  ADDI R30, R30, 24
24:  SW R31, -24(R30)
      --
      -- if (len < 2) return
28:  ADDI R2, R5, -2
32:  BLTZ R2, return
      --
      -- Set pivot, pivot_index, i, and j
36:  ADDI R8, R5, -1
40:  SLL R8, R8, 2
44:  ADD R8, R8, R4
48:  LW R9, 0(R8)
52:  ADD R10, R4, R0
56:  ADD R11, R4, R0
      --
      -- Partition
LABEL partition
60:  LW R2, 0(R11)
64:  SUB R12, R9, R2
68:  BLEZ R12, postIf
72:  LW R12, 0(R10)
76:  SW R2, 0(R10)
80:  SW R12, 0(R11)
84:  ADDI R10, R10, 4
LABEL postIf
88:  ADDI R11, R11, 4
92:  BNE R8, R11, partition
      --
      -- SWAP(i,pivot_index)
96:  LW R2, 0(R8)
100:  LW R12, 0(R10)
104:  SW R2, 0(R10)
108:  SW R12, 0(R8)
      --
      -- Save arr and left_len to stack
112:  SUB R12, R10, R4
116:  SRL R12, R12, 2
120:  SW R4, -16(R30)
124:  SW R12, -8(R30)
      --
      -- quickSort(i + 1, len - left_len - 1)
128:  ADDI R4, R10, 4
132:  SUB R5, R5, R12
136:  ADDI R5, R5, -1
140:  JAL quickSort
      --
      -- quickSort(arr, left_len)
144:  LW R4, -16(R30)
148:  LW R5, -8(R30)
152:  JAL quickSort
      --
LABEL return
156:  LW R31, -24(R30)
160:  ADDI R30, R30, -24
164:  JR R31
      --
