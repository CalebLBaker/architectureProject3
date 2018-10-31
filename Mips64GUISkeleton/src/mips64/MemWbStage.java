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
    boolean isLoad; //ALU or memory (1 = memory, 0 = load)
    

    public MemWbStage(PipelineSimulator sim) {
        simulator = sim;
    }

    public boolean isHalted() {
        return halted;
    }

    public void update() {
        ExMemStage ExMem = simulator.getExMemStage();
        ExMem.update();
        
        halted = ExMem.getHalted();
        shouldWriteback = ExMem.getshouldWriteback();
        instPC = ExMem.getinstPC();
        aluIntData = ExMem.getAluIntData();
        DestReg = ExMem.getDestReg();
        isLoad = ExMem.getWbSource();
        
        if (halted) {
            //halt the program
        }
        
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
    }
}
