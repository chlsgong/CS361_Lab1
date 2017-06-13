
public enum SecurityLevel {
	LOW,
	HIGH;
	
	boolean dominates(SecurityLevel level) {
		if(this.compareTo(level) < 0) {
			return false;
		}
		else {
			return true;
		}
	}
}
