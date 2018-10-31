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
    char destReg;
    boolean useImmediate;
    boolean isControl;
    boolean memRead;
    boolean memWrite;
    boolean wrSource;

    public IdExStage(PipelineSimulator sim) {
        simulator = sim;
        registers = new int[16];
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
    
//    int regAData;
//    int regBData;
//    int immediate;
//    int[] registers;
//    char destReg;
//    boolean useImmediate;
//    boolean isControl;
//    boolean memRead;
//    boolean memWrite;
//    boolean wrSource;

    
    int getIntRegister(int regNum) {
        // todo - add supporting code
        return registers[regNum];
    }
    
    void setRegister(int regNum, )

    public void update() {
    }
}
