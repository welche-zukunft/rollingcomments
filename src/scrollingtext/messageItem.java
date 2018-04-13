package scrollingtext;



public class messageItem {
	
	private int id;
	private String message;
	private itemStatus status;
	
	public messageItem(int id, String message, itemStatus status ) {
		this.id = id;
		this.message = message;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public itemStatus getStatus() {
		return status;
	}

	public void setStatus(itemStatus status) {
		this.status = status;
	}
	
	
}
