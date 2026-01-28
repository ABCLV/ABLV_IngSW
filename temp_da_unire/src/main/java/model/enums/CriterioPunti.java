package model.enums;

public enum CriterioPunti {
	NUM_CATTURE, PESO, BIG_ONE;

	@Override
	public String toString() {
		String s = name().toLowerCase().replace("_", " ");
		if (s.isEmpty())
			return s;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
}
