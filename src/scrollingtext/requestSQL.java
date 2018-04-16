package scrollingtext;

	
import static java.lang.Math.toIntExact;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.bezier.data.sql.MySQL;
import processing.core.PApplet;


public class requestSQL {

	MySQL msql;
	MySQL statusMsql;
	
	boolean sql=true;
	
	int currentMaxId = 0;
	PApplet parent;


	public requestSQL(PApplet parent){
		this.parent = parent;
	    String user     = "root";
	    String pass     = "autoIndex2026";
	    String database = "kommentare";
	    msql = new MySQL(parent, "localhost", database, user, pass );
	    msql.connect();
	    
	    statusMsql= new MySQL(parent, "localhost", database, user, pass );
	    statusMsql.connect();
	    
	}

	public void getCommentsSetup(){
		msql.query("SELECT * FROM kommentar");
		while(msql.next()){
			int id = msql.getInt(1);
			String content = msql.getString(2);
			Time creation = msql.getTime(4);
			String statusS = msql.getString(3);
			Status status = Status.NEW;
			if(statusS.equals("NEW"))  status = Status.NEW;  
		    else if (statusS.equals("LOAD"))   status = Status.LOAD;
		    else if (statusS.equals("SHOW"))   status = Status.SHOWED;
		    else if (statusS.equals("DELETE"))   status = Status.DELETE;

			kommentar newcomment = new kommentar(id,content,creation,status);
			scrollDraw.kommentare.add(newcomment);
			}

		}
	
	public void updateComments() {
		msql.query("SELECT * FROM kommentar WHERE status = 'NEW'");
		boolean update = false;
		while(msql.next()){
			int id = msql.getInt(1);
			String content = msql.getString(2);
			Time creation = msql.getTime(4);
			String statusS = msql.getString(3);
			
			Status status = Status.NEW;
			if(statusS.equals("NEW"))  status = Status.NEW;  
		    else if (statusS.equals("LOAD"))   status = Status.LOAD;
		    else if (statusS.equals("SHOW"))   status = Status.SHOW;
		    else if (statusS.equals("DELETE"))   status = Status.DELETE;
			
			kommentar newcomment = new kommentar(id,content,creation,status);
			scrollDraw.kommentare.add(newcomment);
			update = true;
			
			}
		if(update == true) {
			scrollDraw.mainUI.createArrays();
			}
		}
}
	

