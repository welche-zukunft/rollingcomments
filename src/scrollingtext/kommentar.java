package scrollingtext;

import java.sql.Time;

public class kommentar {

	private int id;
	private Time timestamp;
	private String content;
	private Status status;
	
	public kommentar(int id, String content, Time timestamp) {
		this.id = id;
		this.timestamp = timestamp;
		this.content = content;
		status = status.NEW;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Time timestamp) {
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
