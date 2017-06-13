
enum InstructionType {
	READ,
	WRITE,
	BAD;
}

class Instruction {
	String[] words;
	String object;
	String subject;
	int value;
	InstructionType type;
	
	public Instruction() {
		type = InstructionType.BAD;
	}
	
	public Instruction(String o, String s, String[] w) {
		object = o;
		subject = s;
		words = w;
		type = InstructionType.READ;
	}

	public Instruction(String o, String s, String[] w, int v) {
		object = o;
		subject = s;
		words = w;
		value = v;
		type = InstructionType.WRITE;
	}
}

//class ReadInstruction extends Instruction {
//	public ReadInstruction(String o, String s, String[] w) {
//		words = w;
//		object = o;
//		subject = s;
//		isBadInstruction = false;
//	}
//}
//
//class WriteInstruction extends Instruction {
//	int value;
//	
//	public WriteInstruction(String o, String s, String[] w, int v) {
//		words = w;
//		object = o;
//		subject = s;
//		value = v;
//		isBadInstruction = false;
//	}
//}
//
//class BadInstruction extends Instruction {
//	public BadInstruction() {
//		isBadInstruction = true;
//	}
//}
