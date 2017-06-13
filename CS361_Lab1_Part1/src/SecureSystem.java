import java.io.*;
import java.util.*;

class Object {
	String name;
	int value;
	
	public Object(String name, int value) {
		this.name = name;
		this.value = value;
	}
}

class Subject {
	String name;
	int temp;

	public Subject(String name, int temp) {
		this.name = name;
		this.temp = temp;
	}
}

public class SecureSystem {
	SecurityLevel LOW = SecurityLevel.LOW;
	SecurityLevel HIGH = SecurityLevel.HIGH;
	
	static ArrayList<Subject> subjects = new ArrayList<Subject>();
	static ObjectManager om = new ObjectManager();

	private void printState(Instruction instruction) {
		String subjectName;
		String objectName;
		String value;
		
		switch(instruction.type) {
			case READ:
				subjectName = instruction.words[1];
				objectName = instruction.words[2];
				println(subjectName + " reads " + objectName);
				break;
			case WRITE:
				subjectName = instruction.words[1];
				objectName = instruction.words[2];
				value = instruction.words[3];
				println(subjectName + " writes " + value + " to " + objectName);
				break;
			default:
				println("Bad Instruction");
				break;
		}
		
		println("The current state is:");
		println(SecureSystem.om.state());
		
		int i = 0;
		for(Subject subject: subjects) {
			print("   " + subject.name + " read: " + subject.temp);
			if(i != subjects.size()) {
				println("");
			}
		}		
	}
	
	private void println(String s) {
		System.out.println(s);
	}
	
	private void print(String s) {
		System.out.print(s);
	}
	
	private ArrayList<Instruction> parseFile(String file) {
		ArrayList<Instruction> instructions = new ArrayList<Instruction>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
		    String line;
		    while((line = reader.readLine()) != null) {
		    	instructions.add(parseLine(line));
		    }
		    reader.close();
		}
		catch(IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		return instructions;
	}
 	
	private Instruction parseLine(String line) {
		Instruction instruction;
		String command;
		String object;
		String subject;
		int value;
		
		String[] words = line.split(" ");
		if(words.length < 3 || words.length > 4) {
			return new Instruction();
		}
		
		command = words[0];
		subject = words[1];
		object = words[2];
		if(!containsSubject(subject) || !om.containsObject(object)) {
			return new Instruction();
		}
		
		switch(command) {
			case "read":
				instruction = new Instruction(object, subject, words);
				break;
			case "write":
				if(words.length < 4) {
					instruction = new Instruction();
				}
				else {
					try {
				        value = Integer.parseInt(words[3]);
				        instruction = new Instruction(object, subject, words, value);
				    }
				    catch(NumberFormatException e) {
				    	instruction = new Instruction();
				    }
				}
				break;
			default:
				instruction = new Instruction();
				break;
		}
		
		return instruction;
	}
	
	private boolean containsSubject(String subjectName) {
		for(Subject subject: subjects) {
			if(subject.name.equalsIgnoreCase(subjectName)) {
				return true;
			}
		}
		return false;
	}
	
	static Subject retrieveSubject(String subjectName) {
		Subject subject;
		for(int i = 0; i < subjects.size(); i++) {
			subject = subjects.get(i);
			if(subject.name.equalsIgnoreCase(subjectName)) {
				return subject;
			}
		}
		
		return null;
	}

	public static void main(String[] args) {
		// Make system environment
		SecureSystem ss = new SecureSystem();
		ReferenceMonitor rm = new ReferenceMonitor();
		
		// Create subjects
		Subject lyle = new Subject("lyle", 0);
		Subject hal = new Subject("hal", 0);
		SecureSystem.subjects.add(lyle);
		SecureSystem.subjects.add(hal);
		rm.addSubject(lyle, ss.LOW);
		rm.addSubject(hal, ss.HIGH);
		
		// Create objects
		Object lobj = new Object("lobj", 0);
		Object hobj = new Object("hobj", 0);
		SecureSystem.om.addObject(lobj);
		SecureSystem.om.addObject(hobj);
		rm.addObject(lobj, ss.LOW);
		rm.addObject(hobj, ss.HIGH);
		
		try {
			String file = args[0];
			ArrayList<Instruction> instructions = ss.parseFile(file);
			int i = 0;
			for(Instruction instruction: instructions) {
				rm.executeInstruction(instruction);
				ss.printState(instruction);
				if(i != instructions.size() - 1) {
					ss.println("");
				}
				i++;
			}
		}
		catch(Exception e) {
			System.out.println("No instructions provided.");
		}
	}
	
}
