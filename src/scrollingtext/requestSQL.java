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
	MySQL timestampMsql;
	
	boolean sql=true;
	
	int currentMaxId = 0;
	PApplet parent;
	
	Time lastTime;

	public requestSQL(PApplet parent){
		lastTime = new Time(0);
		this.parent = parent;
	    String user     = "root";
	    String pass     = "autoIndex2026";
	    String database = "kommentare";
	    msql = new MySQL(parent, "localhost", database, user, pass );
	    msql.connect();
	}

	public void getCommentsSetup(){
		msql.query("SELECT * FROM kommentar");
		while(msql.next()){
			//System.out.println(msql.getInt(1) + msql.getString(2) + msql.getTime(3));
			Time creation = msql.getTime(3);
			int id = msql.getInt(1);
			String content = msql.getString(2);
			if(creation.after(lastTime)) {
					lastTime = creation;	
				}
			kommentar newcomment = new kommentar(id,content,creation);
			scrollDraw.kommentare.add(newcomment);
			}

		}
	
	
	public void updateComments() {
		System.out.println(lastTime);
		msql.query("SELECT * FROM kommentar WHERE time > '" + lastTime +"'");
		while(msql.next()){
			Time creation = msql.getTime(3);
			System.out.println("create: " + creation);
			int id = msql.getInt(1);
			String content = msql.getString(2);
			if(creation.after(lastTime)) {
					lastTime = creation;	
				}
			kommentar newcomment = new kommentar(id,content,creation);
			scrollDraw.kommentare.add(newcomment);
			scrollDraw.mainUI.createArrays();
			}
		System.out.println(scrollDraw.kommentare.size());
		}
	
}
	

