package mips64;

public class IdExStage {

    PipelineSimulator simulator;
    boolean shouldWriteback = false;
    int instPC;
    int opcode;
    int regAData;
    int regBData;
    int immediate;
    int[] registers;
    int destReg;
    boolean useImmediate;
    boolean isControl;
    boolean memRead;
    boolean memWrite;
    boolean wbSource;
    boolean halted;

    public IdExStage(PipelineSimulator sim) {
        simulator = sim;
        registers = new int[32];
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

    public boolean getWbSource() {
    	return wbSource;
    }

    public boolean getHalted() {
    	return halted;
    }

    int getIntRegister(int regNum) {
        // todo - add supporting code
        return registers[regNum];
    }
    
    void setRegister(int regNum, int data) {
    	registers[regNum] = data;
    }

    public void update() {
    	IfIdStage ifId = simulator.getIfIdStage();
        ifId.update();
    	instPC = ifId.getInstPC();
    	opcode = ifId.getOpcode();
    	Instruction inst = ifId.getInst();
    	if (inst instanceof ITypeInst) {
            shouldWriteback = opcode == Instruction.INST_LW || opcode == Instruction.INST_ADDI
    			   || opcode == Instruction.INST_ADDI || opcode == Instruction.INST_ANDI
    			   || opcode == Instruction.INST_ORI || opcode == Instruction.INST_XORI;
            ITypeInst iType = (ITypeInst)inst;
            regAData = registers[iType.getRS()];
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
            isControl = true;
            memRead = false;
            memWrite = false;
            halted = opcode == Instruction.INST_HALT;;
    	}
    	else {
            shouldWriteback = true;
            RTypeInst rType = (RTypeInst)inst;
            regAData = registers[rType.getRS()];
            regBData = registers[rType.getRT()];
            destReg = rType.getRD();
            useImmediate = false;
            isControl = false;
            memRead = false;
            memWrite = false;
            halted = false;
    	}
    }
}
