package mips64;

public class MemWbStage {

    PipelineSimulator simulator;
    boolean halted = false;
    boolean shouldWriteback = false; //doesWB
    int instPC = 0;
    int opcode = Instruction.INST_NOP;
    int aluIntData = 0; //aluResult
    int loadIntData = 0;//mem result
    int DestReg = 1; //DestinationReg
    boolean isLoad = false; //ALU or memory (1 = memory, 0 = load)
    boolean squashed = false;
    

    public MemWbStage(PipelineSimulator sim) {
        simulator = sim;
    }

    public boolean isHalted() {
        return halted;
    }
    
    

    public void update() {
        squashed = simulator.getExMemStage().getSquashed();
        
        if (!squashed) {
        
            if (shouldWriteback) {
                if (isLoad) {
                    //memory operation
                    simulator.getIdExStage().setRegister(DestReg, loadIntData);
                }
                else {
                    //ALU operation
                    simulator.getIdExStage().setRegister(DestReg, aluIntData);
                }
            }
        
            ExMemStage ExMem = simulator.getExMemStage();
            halted = ExMem.getHalted();
            shouldWriteback = ExMem.getShouldWriteback();
            instPC = ExMem.getInstPC();
            opcode = ExMem.getOpcode();
            aluIntData = ExMem.getAluIntData();
            DestReg = ExMem.getDestReg();
            isLoad = ExMem.getMemRead();
            if (isLoad) {
                loadIntData = simulator.getMemory().getIntDataAtAddr(aluIntData);
            }
            else if (ExMem.getMemWrite()) {
                simulator.getMemory().setIntDataAtAddr(aluIntData, ExMem.getStoreIntData());
            }
        }
    }
}
