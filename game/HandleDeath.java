package game;

public class HandleDeath {
	private HandleDeath() {
		
	}
	public static final String getDeathMessage(int i) {
		switch(i) {
		case 1:
			return "nigga";
		case 2:
			return "You hit a wall";
		default:
			return "You died";
		}
	}
}
