package mips64;

public class MemWbStage {

    PipelineSimulator simulator;
    boolean halted;
    boolean shouldWriteback = false; //doesWB
    int instPC;
    int opcode;
    int aluIntData; //aluResult
    int loadIntData;//mem result
    char DestReg; //DestinationReg
    int wbSource; //ALU or memory
    

    public MemWbStage(PipelineSimulator sim) {
        simulator = sim;
    }

    public boolean isHalted() {
        return halted;
    }

    public void update() {
    }
}
