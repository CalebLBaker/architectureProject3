package mips64;

public class IfIdStage {
    PipelineSimulator simulator;
    Instruction inst = Instruction.getInstructionFromOper(Instruction.INST_NOP << 26);
    int instPC = 0;
    int opcode = Instruction.INST_NOP;
    boolean squashed = false;

    public IfIdStage(PipelineSimulator sim) {
        simulator = sim;
        inst.setOpcode(Instruction.INST_NOP);
    }
  
    public Instruction getInst() {
        return inst;
    }
  
    public int getInstPC() {
        return instPC;
    }
  
    public int getOpcode() {
        return opcode;
    }
  
    boolean getSquashed () {
        return squashed;
    }
  
    void setSquashed (boolean x) {
        squashed = x;
    }

    public void update() {
        if (!simulator.getExMemStage().getInterlocked()) {
            int pc = simulator.getPCStage().getPC();
            inst = simulator.getMemory().getInstAtAddr(pc);
            opcode = inst.getOpcode();
            ExMemStage exMem = simulator.getExMemStage();
            instPC = exMem.getBranchTaken() ? exMem.getAluIntData() : pc + 4;
            squashed = false;
        }
    }
}
