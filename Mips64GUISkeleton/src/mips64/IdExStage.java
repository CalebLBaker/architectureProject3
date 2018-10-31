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

    public void setRegister(char regNum, int value) {
        registers[regNum] = value;
    }
    
    public int getRegister(char regNum) {
        return registers[regNum];
    }
    
    int getIntRegister(int regNum) {
        // todo - add supporting code
        return 0;
    }

    public void update() {
    }
}
