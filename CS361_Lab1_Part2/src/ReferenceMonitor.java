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
		
		Subject subject;
		Object object;
		
		switch(instruction.type) {
			case READ:
				subject = SecureSystem.retrieveSubject(subjectName);
		
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
			case CREATE:
				if(canCreate(objectLevel)) {
					// Execute create
					object = new Object(objectName, 0);
					SecureSystem.om.addObject(object);
					addObject(object, subjectLevel);
				}
				break;
			case DESTROY:
				if(canWrite(subjectLevel, objectLevel)) {
					// Execute destroy
					objectLevels.remove(objectName);
					SecureSystem.om.removeObject(objectName);
				}
				break;
			case RUN:
				// Execute run
				subject = SecureSystem.retrieveSubject(subjectName);
				subject.run();
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
	
	private boolean canCreate(SecurityLevel objectLevel) {
		if(objectLevel == null) {
			return true;
		}
		return false;
	}
}
