package mips64;

public class MemWbStage {

    public static final int NO_FORWARDING = -1;
    
    PipelineSimulator simulator;
    boolean halted = false;
    boolean shouldWriteback = false; //doesWB
    int instPC = 0;
    int opcode = Instruction.INST_NOP;
    int aluIntData = 0; //aluResult
    int loadIntData = 0;//mem result
    int destReg = 1; //DestinationReg
    boolean isLoad = false; //ALU or memory (1 = memory, 0 = load)
    boolean squashed = false;
    boolean jal;
    
    
    // Forwarding stuff
    int forwardReg = NO_FORWARDING;
    int forwardData = 0;
    
    static final int RETURN_ADDRESS_REG = 31;

    public MemWbStage(PipelineSimulator sim) {
        simulator = sim;
    }

    public int getDestReg() {
        return destReg;
    }

    public int getForwardReg() {
        return forwardReg;
    }
    
    public int getForwardData() {
        return forwardData;
    }
    
    public boolean isHalted() {
        return halted;
    }
    
    public boolean getShouldWriteback() {
        return shouldWriteback;
    }

    public void update() {
        
        if (shouldWriteback && !squashed && destReg != 0) {
            forwardReg = destReg;
            if (isLoad) {
                //memory operation
                simulator.getIdExStage().setRegister(destReg, loadIntData);
                forwardData = loadIntData;
            }
            else {
                //ALU operation
                simulator.getIdExStage().setRegister(destReg, aluIntData);
                forwardData = aluIntData;
            }
        }
        else if (jal && !squashed) {
            forwardReg = RETURN_ADDRESS_REG;
            forwardData = instPC;
            simulator.getIdExStage().setRegister(RETURN_ADDRESS_REG, instPC);
        }
        else {
            forwardReg = NO_FORWARDING;
        }
        
        ExMemStage ExMem = simulator.getExMemStage();
        squashed = ExMem.getSquashed() || ExMem.getInterlocked();
        if (!squashed) {
            halted = ExMem.getHalted();
            shouldWriteback = ExMem.getShouldWriteback();
            instPC = ExMem.getInstPC();
            opcode = ExMem.getOpcode();
            aluIntData = ExMem.getAluIntData();
            destReg = ExMem.getDestReg();
            isLoad = ExMem.getMemRead();
            jal = ExMem.getJal();
            if (isLoad) {
                loadIntData=simulator.getMemory().getIntDataAtAddr(aluIntData);
            }
            else if (ExMem.getMemWrite()) {
                simulator.getMemory().setIntDataAtAddr(aluIntData, 
                                                       ExMem.getStoreIntData());
            }
        }
    }
}
