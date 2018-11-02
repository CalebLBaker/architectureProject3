package mips64;

public class IfIdStage {
  PipelineSimulator simulator;
  Instruction inst = Instruction.getInstructionFromOper(Instruction.INST_NOP << 26);
  int instPC = 0;
  int opcode = Instruction.INST_NOP;

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

  public void update() {
    int pc = simulator.getPCStage().getPC();
    inst = simulator.getMemory().getInstAtAddr(pc);
    opcode = inst.getOpcode();
    ExMemStage exMem = simulator.getExMemStage();
    instPC = exMem.getBranchTaken() ? exMem.getAluIntData() : pc + 4;
  }
}
