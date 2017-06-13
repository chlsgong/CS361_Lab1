import java.util.*;

public class ReferenceMonitor {
	HashMap<String, SecurityLevel> subjectLevels = new HashMap<String, SecurityLevel>();
	HashMap<String, SecurityLevel> objectLevels = new HashMap<String, SecurityLevel>();
	
	void addSubject(Subject s, SecurityLevel level) {
		String subjectName = s.name;
		subjectLevels.put(subjectName, level);
	}
	
	void addObject(Object o, SecurityLevel level) {
		String objectName = o.name;
		objectLevels.put(objectName, level);
	}
	
	void executeInstruction(Instruction instruction) {
		if(instruction.type == InstructionType.BAD) {
			return;
		}
		
		// Conditions:
		// High s reads Low o  - pass		High s writes Low o  - fail
		// High s reads High o - pass		High s writes High o - pass
		// Low s reads Low o   - pass		Low s writes Low o 	 - pass
		// Low s reads High o  - fail		Low s writes High o  - pass 
		
		String subjectName = instruction.subject;
		String objectName = instruction.object;
				
		SecurityLevel subjectLevel = subjectLevels.get(subjectName);
		SecurityLevel objectLevel = objectLevels.get(objectName);
		
		switch(instruction.type) {
			case READ:
				Subject subject = SecureSystem.retrieveSubject(subjectName);
		
				if(canRead(subjectLevel, objectLevel)) {
					// Execute read
					subject.temp = SecureSystem.om.getObjectValue(objectName);
				}
				else {
					subject.temp = 0;
				}
				break;
			case WRITE:
				if(canWrite(subjectLevel, objectLevel)) {
					// Execute write
					int writeValue = instruction.value;
					SecureSystem.om.setObjectValue(objectName, writeValue);
				}
				break;
			default:
				break;
		}
	}
	
	private boolean canRead(SecurityLevel subjectLevel, SecurityLevel objectLevel) {
		if(!subjectLevel.dominates(objectLevel)) {
			return false;
		}
		return true;
	}
	
	private boolean canWrite(SecurityLevel subjectLevel, SecurityLevel objectLevel) {
		if(!subjectLevel.equals(objectLevel) && subjectLevel.dominates(objectLevel)) {
			return false;
		}
		return true;
	}
}
