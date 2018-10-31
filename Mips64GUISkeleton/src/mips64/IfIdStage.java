package mips64;

public class IfIdStage {
  PipelineSimulator simulator;
  Instruction inst;
  int instPC;
  int opcode;

  public IfIdStage(PipelineSimulator sim) {
    simulator = sim;

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
    ProgramCounter pc = simulator.getPCStage();
    ExMemStage exMem = simulator.getExMemStage();
    inst = simulator.getMemory().getInstAtAddr(pc.getPC());
    instPC = exMem.getBranchTaken() ? exMem.getAluIntData() : pc.getPC() + 4;
    opcode = inst.getOpcode();
  }
}
