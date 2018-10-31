package mips64;

public class ExMemStage {

    PipelineSimulator simulator;
    boolean halted;
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
    boolean isLoad;

    public ExMemStage(PipelineSimulator sim) {
        simulator = sim;
    }

    public void update() {
    }

    boolean getHalted() {
        return halted;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean getshouldWriteback() {
        return shouldWriteback;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getinstPC() {
        return instPC;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getAluIntData() {
        return aluIntData;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getDestReg() {
        return destReg;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean getWbSource() {
        return isLoad;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
