import java.util.ArrayList;

public class ObjectManager {
	ArrayList<Object> objects = new ArrayList<Object>();
	
	void addObject(Object o) {
		objects.add(o);
	}
	
	void removeObject(String objectName) {
		Object o = retrieveObject(objectName);
		objects.remove(o);
	}
	
	boolean containsObject(String objectName) {
		for(Object object: objects) {
			if(object.name.equalsIgnoreCase(objectName)) {
				return true;
			}
		}
		return false;
	}
	
	int getObjectValue(String objectName) {
		Object o = retrieveObject(objectName);
		return o.value;
	}
	
	void setObjectValue(String objectName, int value) {
		Object o = retrieveObject(objectName);
		o.value = value;
	}
	
	String state() {
		String state = "";
		int i = 0;
		for(Object object: objects) {
			state += ("   " + object.name + ": " + object.value);
			if(i != (objects.size() - 1)) {
				state += "\n";
			}
			i++;
		}
		return state;
	}
	
	private Object retrieveObject(String objectName) {
		Object object;
		for(int i = 0; i < objects.size(); i++) {
			object = objects.get(i);
			if(object.name.equalsIgnoreCase(objectName)) {
				return object;
			}
		}
		
		return null;
	}
}