package scrollingtext;

import java.util.ArrayList;
import java.util.List;

import processing.core.*;
import processing.opengl.PShader;

public class scrollDraw extends PApplet{

	float wordsize;
	String [] words;
	int maximumtextlength = 3;
	
	static StringBuilder message;
	
	PFont mainFont;
	PShader shift;
	PGraphics textArea;
	int textWidth = 1950;
	
	int charCount;
	char[] outText;
	String outString;
	
	float timer;
	int lastEnter = 0;
	
	public static requestSQL database;
	static List<kommentar> kommentare;

	public static userInterface mainUI;
	
	public void settings() {
		 fullScreen(P2D,2);
		 //smooth(8);
	}
	
	public void setup(){
		 textArea = createGraphics(1950,80,P2D);
		 mainFont = createFont("Monospaced.plain",128);
		 shift = loadShader("./resources/shader/shiftfrag.glsl","./resources/shader/shiftvert.glsl");
		
		 kommentare = new ArrayList<kommentar>();
		 database = new requestSQL(this);

		 mainUI = new userInterface();
		 
		 database.getCommentsSetup();
		 
		 //textSize = 30
		 charCount = textWidth / 30;
		 //System.out.println(charCount);
		 outText = new char[charCount];
		 message = new StringBuilder("");
		 initChar();
	}
	
	public void draw(){
		  clear();
		  colorMode(RGB, 255);
		  background(0,255,0);
		  
		  textArea.beginDraw();
		  textArea.clear();
		  textArea.background(255);
		  textArea.noStroke();
		  //textArea.fill(255,255,0,255);
		  //textArea.rect(0,0,1,textArea.height);
		  textArea.fill(0);
		  textArea.textFont(mainFont);
		  textArea.textSize(50);
		  //System.out.println(textArea.textWidth("A")); = 30.
		  // 64 in / 1 out
		  textArea.textAlign(LEFT, TOP);
		  textArea.text(outString,0,0);
		  textArea.endDraw();
		  //translate(100,100);
		  pushMatrix();
		  translate(0,1000);
		  timer = (float)(frameCount*0.002) % 1;
		  shift.set("time",(float)timer);
		  shader(shift);
		  image(textArea,0,0);
		  resetShader();
		  popMatrix();
		  
		  fill(0,255,0);
		  noStroke();
		  rect(0,1000,30,80);
		  rect(1890,1000,30,80);
		  rebuildChar();
		  
		  //textSize(13);
		  //text("fps: " + frameRate,1600,900);
		  
		  
		  if(frameCount % 60 == 0) {
			  database.updateComments();
		  }
		  
		}

		void initChar(){
		 for(int i = 0; i < charCount; i++){
		  outText[i] = 8; 
		 }
		 outString = String.valueOf(outText);
		  
		}

		void rebuildChar(){
		  int start = (int)(timer * (charCount));
		  int startend = (start + charCount)%charCount; 
		  if(startend != lastEnter){
			// INFO  
		    // 1920 are in (=64) - 30 are out ( = 1)
		    char newChar = ' ';
		    if(message.length() > 0){
		      newChar = message.charAt(0);
		      message.deleteCharAt(0);
		    }
		    outText[startend] = newChar;
		    lastEnter = startend;
		    outString = String.valueOf(outText);
		  }
		}

		public void keyReleased(){
			if (key == 'i'){
    		 	mainUI.contentPane.setVisible(true);
    		 	mainUI.guiframe.setVisible(true);
    	      } 
		}
	
	
		public static void updateMessage() {
			for(kommentar mI : kommentare) {
				if(mI.getStatus() == Status.SHOW) {
					message.append(" + ");
					message.append(mI.getContent());
					message.append(" + ");
					//System.out.println(message.toString());
					Status newStatus = mI.setStatus(Status.SHOWED);
					mainUI.createArrays();
				}
			}
		}
		
	
	
}
