package mips64;

public class ExMemStage {

    PipelineSimulator simulator;
    boolean halted = false;
    boolean shouldWriteback = false;
    int instPC = 0;
    int opcode = Instruction.INST_NOP;
    int aluIntData = 0;
    int storeIntData = 0;
    int destReg = 1;
    boolean memRead = false;
    boolean memWrite = false;
    boolean branchTaken = false;
    boolean squashed = false;

    public ExMemStage(PipelineSimulator sim) {
        simulator = sim;
    }

    public void update() {
    	IdExStage idEx = simulator.getIdExStage();
        IfIdStage ifId = simulator.getIfIdStage();
    	halted = idEx.getHalted();
    	shouldWriteback = idEx.getShouldWriteback();
    	instPC = idEx.getInstPC();
    	opcode = idEx.getOpcode();
    	storeIntData = idEx.getRegBData();
    	destReg = idEx.getDestReg();
    	memRead = idEx.getMemRead();
    	memWrite = idEx.getMemWrite();
        squashed = idEx.getSquashed();
    	int regAData = idEx.getRegAData();
    	switch (opcode) {
            case (Instruction.INST_ADDI) : {
                aluIntData = regAData + idEx.getImmediate();
    		break;
            }
            case (Instruction.INST_ANDI) : {
                aluIntData = regAData & idEx.getImmediate();
    		break;
            }
            case (Instruction.INST_ORI) : {
    		aluIntData = regAData | idEx.getImmediate();
    		break;
            }
            case (Instruction.INST_XORI) : {
    		aluIntData = regAData ^ idEx.getImmediate();
    		break;
            }
            case (Instruction.INST_ADD) : {
    		aluIntData = regAData + storeIntData;
    		break;
            }
            case (Instruction.INST_SUB) : {
    		aluIntData = regAData - storeIntData;
    		break;
            }
            case (Instruction.INST_MUL) : {
        	aluIntData = regAData * storeIntData;
    		break;
            }
            case (Instruction.INST_DIV) : {
    		aluIntData = regAData / storeIntData;
    		break;
            }
            case (Instruction.INST_AND) : {
    		aluIntData = regAData & storeIntData;
    		break;
            }
            case (Instruction.INST_OR) : {
    		aluIntData = regAData | storeIntData;
    		break;
            }
            case (Instruction.INST_XOR) : {
    		aluIntData = regAData ^ storeIntData;
    		break;
            }
            case (Instruction.INST_SLL) : {
    		aluIntData = regAData << storeIntData;
    		break;
            }
            case (Instruction.INST_SRL) : {
        	aluIntData = regAData >> storeIntData;
    		break;
            }
            case (Instruction.INST_SRA) : {
    		aluIntData = (int)(((long)regAData) >> storeIntData);
    		break;
            }
            case (Instruction.INST_JR) :
            case (Instruction.INST_JALR) : {
    		aluIntData = instPC + storeIntData;
    		break;
            }
            default : {
    		aluIntData = instPC + idEx.getImmediate();
            }
    	}
    	branchTaken = opcode == Instruction.INST_JR || opcode == Instruction.INST_JALR
    		   || opcode == Instruction.INST_J || opcode == Instruction.INST_JAL
    		   || opcode == Instruction.INST_BEQ && regAData == storeIntData
    		   || opcode == Instruction.INST_BNE && regAData != storeIntData
    		   || opcode == Instruction.INST_BLTZ && regAData < 0
    		   || opcode == Instruction.INST_BLEZ && regAData <= 0
    		   || opcode == Instruction.INST_BGTZ && regAData > 0
    		   || opcode == Instruction.INST_BGEZ && regAData >= 0;
        
        if (branchTaken) {
            idEx.setSquashed(true);
            ifId.setSquashed(true);
            squashed = true;
        }
    }

    boolean getBranchTaken() {
        return branchTaken;
    }
    
    boolean getHalted() {
        return halted;
    }

    boolean getShouldWriteback() {
        return shouldWriteback;
    }

    int getInstPC() {
        return instPC;
    }

    int getAluIntData() {
        return aluIntData;
    }

    int getDestReg() {
        return destReg;
    }

    int getOpcode() {
        return opcode;
    }
    
    boolean getMemRead() {
        return memRead;
    }
    
    boolean getMemWrite() {
        return memWrite;
    }
    
    int getStoreIntData() {
        return storeIntData;
    }
    
    boolean getSquashed () {
        return squashed;
    }
    
}
