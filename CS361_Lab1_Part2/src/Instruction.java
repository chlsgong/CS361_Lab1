
enum InstructionType {
	READ,
	WRITE,
	CREATE,
	DESTROY,
	RUN,
	BAD;
}

class Instruction {
	String[] words;
	String object;
	String subject;
	String command;
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
	
	public Instruction(String o, String s, String c, InstructionType t) {
		object = o;
		subject = s;
		command = c;
		type = t;
	}
	
	public Instruction(String o, String s, String c, int v) {
		object = o;
		subject = s;
		command = c;
		value = v;
		type = InstructionType.WRITE;
	}
	
	public Instruction(String s, String c) {
		subject = s;
		command = c;
		type = InstructionType.RUN;
	}
}
