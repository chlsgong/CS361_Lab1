import java.util.ArrayList;
import java.io.*;

public class CovertChannel {
	static FileOutputStream outStream;
	static BufferedWriter writer;
	
	String createH = "create hal obj\n";
	String createL = "create lyle obj\n";
	String destroy = "destroy lyle obj\n";
	String read = "read lyle obj\n";
	String write = "write lyle obj 1\n";
	String run = "run lyle\n";
	
	Instruction createHInstruction = new Instruction("obj", "hal", createH, InstructionType.CREATE);
	Instruction createLInstruction = new Instruction("obj", "lyle", createL, InstructionType.CREATE);
	Instruction writeInstruction = new Instruction("obj", "lyle", write, 1);
	Instruction readInstruction = new Instruction("obj", "lyle", read, InstructionType.READ);
	Instruction destroyInstruction = new Instruction("obj", "lyle", destroy, InstructionType.DESTROY);
	Instruction runInstruction = new Instruction("lyle", run);
	
	ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	
	void generateInstructions(int bit, boolean vFlag) {
		if(bit == 0) {
			// CREATE HAL OBJ
			
			instructions.add(createHInstruction);
		}
		
		// CREATE LYLE OBJ
		// WRITE LYLE OBJ 1
		// READ LYLE OBJ
		// DESTROY LYLE OBJ
		// RUN LYLE
		
		instructions.add(createLInstruction);
		instructions.add(writeInstruction);
		instructions.add(readInstruction);
		instructions.add(destroyInstruction);
		instructions.add(runInstruction);
		
		if(vFlag) {
			try {
				for(Instruction ins: instructions) {
				    writer.write(ins.command);
				}
			}
			catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}
	
	void executeInstructions(ReferenceMonitor rm) {
		for(Instruction instruction: instructions) {
			rm.executeInstruction(instruction);
		}
		instructions.clear();
	}
	
	public static void main(String[] args) {
		// Make system environment
		CovertChannel cc = new CovertChannel();
		SecureSystem ss = new SecureSystem();
		ReferenceMonitor rm = new ReferenceMonitor();
		
		// Create subjects
		Subject lyle = new Subject("lyle", 0);
		Subject hal = new Subject("hal", 0);
		SecureSystem.subjects.add(lyle);
		SecureSystem.subjects.add(hal);
		rm.addSubject(lyle, ss.LOW);
		rm.addSubject(hal, ss.HIGH);
		
		try {
			String fileName;
			boolean vFlag;
			
			String firstArg = args[0];
			if(firstArg.equalsIgnoreCase("v")) {
				fileName = args[1];	
				vFlag = true;
			}
			else {
				fileName = firstArg;	
				vFlag = false;
			}
			FileInputStream inStream = new FileInputStream(fileName);
			outStream = new FileOutputStream(fileName + ".out");
			writer = new BufferedWriter(new FileWriter("log"));
			
			int inputByte = inStream.read();
			while(inputByte > -1) {
				for(int i = 0; i < 8; i++) {
					int bit = (inputByte >> i) & 1;
					cc.generateInstructions(bit, vFlag);
					cc.executeInstructions(rm);
				}
				inputByte = inStream.read();
			}
			inStream.close();
			outStream.close();
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Invalid arguments.");
		}
	}

}
