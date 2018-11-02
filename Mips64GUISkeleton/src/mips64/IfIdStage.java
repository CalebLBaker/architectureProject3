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
    inst = simulator.getMemory().getInstAtAddr(pc.getPC());
    opcode = inst.getOpcode();
    pc.update();
    instPC = pc.getPC();
  }
}
