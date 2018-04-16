package scrollingtext;

import java.sql.Time;

public class kommentar {

	private int id;
	private Time timestamp;
	private String content;
	private Status status;
	
	public kommentar(int id, String content, Time timestamp,Status status) {
		this.id = id;
		this.timestamp = timestamp;
		this.content = content;
		this.status = status;
		if(this.status == Status.NEW) {
			scrollDraw.database.statusMsql.query("UPDATE kommentar SET status = 'LOAD' WHERE ID = " + this.id);
			this.status = Status.LOAD;
		}
		
	}

	public Status getStatus() {
		return status;
	}

	public Status setStatus(Status statusIn) {
		String newstatus = "";
		if(statusIn.equals(Status.NEW))  newstatus = "NEW";  
	    else if (statusIn.equals(Status.LOAD))   newstatus = "LOAD";
	    else if (statusIn.equals(Status.SHOW) || statusIn.equals(Status.SHOWED) )   newstatus = "SHOW";
	    else if (statusIn.equals(Status.DELETE))   newstatus = "DELETE";
		scrollDraw.database.statusMsql.query("UPDATE kommentar SET status = '" + newstatus + "' WHERE ID = " + this.id);
		this.status = statusIn;
		return this.status;
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
