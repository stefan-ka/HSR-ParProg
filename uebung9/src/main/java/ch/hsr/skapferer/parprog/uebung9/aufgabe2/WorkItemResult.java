package ch.hsr.skapferer.parprog.uebung9.aufgabe2;

public class WorkItemResult {
	private final long value;
	private final String description;

	public WorkItemResult(String description, long value) {
		this.description = description;
		this.value = value;
	}

	public long getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
}
