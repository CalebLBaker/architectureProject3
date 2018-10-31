package mips64;

public class ExMemStage {

    PipelineSimulator simulator;
    boolean shouldWriteback = false;
    int instPC;
    int opcode;
    int aluIntData;
    int storeIntData;
    int regB;
    char destReg;
    boolean memRead;
    boolean memWrite;
    boolean branchTaken;
    boolean wbSource;

    public ExMemStage(PipelineSimulator sim) {
        simulator = sim;
    }

    public void update() {
    }
}
