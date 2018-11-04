package mips64;

public class IdExStage {

    PipelineSimulator simulator;
    boolean shouldWriteback = false;
    int instPC = 0;
    int opcode = Instruction.INST_NOP;
    int regAData = 0;
    int regBData = 0;
    int regA = 0;
    int regB = 0;
    int immediate = 0;
    int[] registers = new int[32];
    int destReg = 1;
    boolean useImmediate = true;
    boolean isControl = false;
    boolean memRead = false;
    boolean memWrite = false;
    boolean halted = false;
    boolean squashed = false;

    public IdExStage(PipelineSimulator sim) {
        simulator = sim;
    }
    
    public boolean getShouldWriteback() {
        return shouldWriteback;
    }
    
    public int getInstPC() {
        return instPC;
    }
    
    public int getOpcode() {
        return opcode;
    }
    
    public int getRegA() {
        return regA;
    }
    
    public int getRegB() {
        return regB;
    }

    public int getRegAData() {
    	return regAData;
    }

    public int getRegBData() {
    	return regBData;
    }

    public int getImmediate() {
    	return immediate;
    }

    public int getDestReg() {
    	return destReg;
    }

    public boolean getUseImmediate() {
    	return useImmediate;
    }

    public boolean getIsControl() {
    	return isControl;
    }

    public boolean getMemRead() {
    	return memRead;
    }

    public boolean getMemWrite() {
    	return memWrite;
    }

    public boolean getHalted() {
    	return halted;
    }

    int getIntRegister(int regNum) {
        // todo - add supporting code
        return registers[regNum];
    }
    
    boolean getSquashed() {
        return squashed;
    }
    
    void setSquashed (boolean x) {
        squashed = x;
    }
    
    void setRegister(int regNum, int data) {
    	registers[regNum] = data;
    }

    public void update() {
    	IfIdStage ifId = simulator.getIfIdStage();
    	opcode = ifId.getOpcode();
        instPC = ifId.getInstPC();
    	Instruction inst = ifId.getInst();
        squashed = ifId.getSquashed();
        if (!squashed) {
            if (inst instanceof ITypeInst) {
                shouldWriteback = opcode == Instruction.INST_LW || opcode == Instruction.INST_ADDI
    			   || opcode == Instruction.INST_ADDI || opcode == Instruction.INST_ANDI
    			   || opcode == Instruction.INST_ORI || opcode == Instruction.INST_XORI;
				ITypeInst iType = (ITypeInst)inst;
				regA = iType.getRS();
				regAData = registers[regA];
				destReg = iType.getRT();
				regBData = registers[destReg];
				immediate = iType.getImmed();
				useImmediate = true;
				isControl = opcode != Instruction.INST_LW && opcode != Instruction.INST_SW
						 && opcode != Instruction.INST_ADDI && opcode != Instruction.INST_ANDI
						 && opcode != Instruction.INST_ORI && opcode != Instruction.INST_XORI;
				memRead = opcode == Instruction.INST_LW;
				memWrite = opcode == Instruction.INST_SW;
				halted = false;
			}
			else if (inst instanceof JTypeInst) {
				shouldWriteback = false;
				immediate = ((JTypeInst)inst).getOffset();
				useImmediate = true;
				isControl = opcode != Instruction.INST_NOP;
				memRead = false;
				memWrite = false;
				halted = opcode == Instruction.INST_HALT;;
			}
			else {
				shouldWriteback = true;
				RTypeInst rType = (RTypeInst)inst;
				regA = rType.getRS();
				regAData = registers[regA];
				regB = rType.getRT();
				regBData = registers[regB];
				destReg = rType.getRD();
				useImmediate = false;
				isControl = false;
				memRead = false;
				memWrite = false;
				halted = false;
			}
        }
    }
}
