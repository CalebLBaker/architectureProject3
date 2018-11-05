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
    boolean interlocked = false;
    boolean squashed = false;
    boolean jal = false;

    public ExMemStage(PipelineSimulator sim) {
        simulator = sim;
    }
    
    public int forward(int reg, int val) {
        if (shouldWriteback && !memRead && destReg == reg) {
            return aluIntData;
        }
        MemWbStage memWB = simulator.getMemWbStage();
        if (memWB.getForwardReg() == reg) {
            return memWB.getForwardData();
        }
        return val;
    }
    
    public boolean getInterlocked() {
        return interlocked;
    }
    
    public boolean getJal() {
        return jal;
    }
    
    public void update() {
    	IdExStage idEx = simulator.getIdExStage();
        int regA = idEx.getRegA();
        int regB = idEx.getRegB();
        if (shouldWriteback && memRead && !squashed && !interlocked && (destReg == regA || destReg == regB)) {
            interlocked = true;
        }
        else {
            interlocked = false;
            storeIntData = forward(regB, idEx.getRegBData());
            int regAData = forward(regA, idEx.getRegAData());
            jal = idEx.getJal();
            halted = idEx.getHalted();
            instPC = idEx.getInstPC();
            opcode = idEx.getOpcode();
            memWrite = idEx.getMemWrite();
            memRead = idEx.getMemRead();
            shouldWriteback = idEx.getShouldWriteback();
            destReg = idEx.getDestReg();
            squashed = idEx.getSquashed();
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
                    aluIntData = regAData << idEx.getImmediate();
                    break;
                }
                case (Instruction.INST_SRL) : {
                    aluIntData = regAData >> idEx.getImmediate();
                    break;
                }
                case (Instruction.INST_SRA) : {
                    aluIntData = (int)(((long)regAData) >> idEx.getImmediate());
                    break;
                }
                case (Instruction.INST_JR) :
                case (Instruction.INST_JALR) : {
                    aluIntData = regAData;
                    break;
                }
                case (Instruction.INST_LW) :
                case (Instruction.INST_SW) : {
                    aluIntData = regAData + idEx.getImmediate();
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
            if (branchTaken && !squashed) {
		simulator.getIfIdStage().squash();
            }
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
