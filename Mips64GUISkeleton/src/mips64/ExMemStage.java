package mips64;

public class ExMemStage {

    PipelineSimulator simulator;
    boolean halted;
    boolean shouldWriteback = false;
    int instPC;
    int opcode;
    int aluIntData;
    int regBData;
    char destReg;
    boolean memRead;
    boolean memWrite;
    boolean branchTaken;

    public ExMemStage(PipelineSimulator sim) {
        simulator = sim;
    }

    public void update() {
		idEx = simulator.getIdExStage();
		halted = idEx.getHalted();
		shouldWriteback = idEx.getShouldWriteback();
		instPC = idEx.getInstPC();
		opcode = idEx.getOpcode();
		regBData = idEx.getRegBData();
		destReg = idEx.getDestReg();
		memRead = idEx.getMemRead();
		memWrite = idEx.getMemWrite();
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
				aluIntData = regAData + regBData;
				break;
			}
			case (Instruction.INST_SUB) : {
				aluIntData = regAData - regBData;
				break;
			}
			case (Instruction.INST_MUL) : {
				aluIntData = regAData * regBData;
				break;
			}
			case (Instruction.INST_DIV) : {
				aluIntData = regAData / regBData;
				break;
			}
			case (Instruction.INST_AND) : {
				aluIntData = regAData & regBData;
				break;
			}
			case (Instruction.INST_OR) : {
				aluIntData = regAData | regBData;
				break;
			}
			case (Instruction.INST_XOR) : {
				aluIntData = regAData ^ regBData;
				break;
			}
			case (Instruction.INST_SLL) : {
				aluIntData = regAData << regBData;
				break;
			}
			case (Instruction.INST_SRL) : {
				aluIntData = regAData >> regBData;
				break;
			}
			case (Instruction.INST_SRA) : {
				aluIntData = (int)(((long)regAData) >> regBData);
				break;
			}
			case (Instruction.INST_JR) :
			case (Instruction.INST_JALR) : {
				aluIntData = instPC + regBData;
				break;
			}
			default : {
				aluIntData = instPC + idEx.getImmediate();
			}
		}
		branchTaken = opcode == Instruction.INST_JR || opcode == Instruction.INST_JALR
				   || opcode == Instruction.INST_J || opcode == Instruction.INST_JAL
				   || opcode == Instruction.INST_BEQ && regAData == regBData
				   || opcode == Instruction.INST_BNE && regAData != regBData
				   || opcode == Instruction.INST_BLTZ && regAData < 0
				   || opcode == Instruction.INST_BLEZ && regAData <= 0
				   || opcode == Instruction.INST_BGTZ && regAData > 0
				   || opcode == Instruction.INST_BGEZ && regAData >= 0;
		idEx.update();
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

    char getDestReg() {
        return destReg;
    }

    boolean getWbSource() {
        return isLoad;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
