package scrollingtext;

import processing.core.*;
import processing.opengl.PShader;

public class scrollDraw extends PApplet{

	
	String [] word = {"Scheibenwischer","Welche Zukunft","Sägespähne"};
	float wordsize;
	String [] words;
	int maximumtextlength = 3;
	
	StringBuilder message;
	
	PFont mainFont;
	PShader shift;
	PGraphics textArea;
	int textWidth = 900;
	
	int charCount;
	char[] outText;
	String outString;
	
	float timer;
	int lastEnter = 0;

	public void settings() {
		 size(1200,200,P2D);
		 smooth(8);
	}
	
	public void setup(){
		
		 textArea = createGraphics(900,30,P2D);
		 mainFont = createFont("DejaVu Sans Mono",128);
		 shift = loadShader("./resources/shader/shiftfrag.glsl","./resources/shader/shiftvert.glsl");
		 
		 //textSize = 12
		 charCount = textWidth / 12;
		 outText = new char[charCount];
		 message = new StringBuilder("");
		 initChar();
	 
	}
	
	public void draw(){
		  clear();
		  background(100);
		  fill(200);
		  rect(100,0,888,height);
		  textArea.beginDraw();
		  textArea.clear();
		  textArea.background(0);
		  textArea.noStroke();
		  textArea.fill(255,2,0,255);
		  textArea.rect(0,0,1,textArea.height);
		  textArea.fill(255);
		  textArea.textFont(mainFont);
		  textArea.textSize(20);
		  textArea.textAlign(LEFT, TOP);
		  textArea.text(outString,0,0);
		  textArea.endDraw();
		  translate(100,100);
		  timer = (float)(frameCount*0.002) % 1;
		  shift.set("time",(float)timer);
		  shader(shift);
		  image(textArea,0,0);
		  //surface.setTitle("timePosition: " + timer);
		  rebuildChar();
		  updateMessage();
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
		    // 888 are in (=74) - 12 are out ( = 1)
			  
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
		 if(key == 'n'){
		  int rand = (int)random(2) + 1;
		  message.append(" + ");
		  message.append(word[rand]);
		  message.append(" + ");
		 }
		  
		}
	
	
		private void updateMessage() {
			for(messageItem mI : scrollApp.mainUI.allItems) {
				if(mI.getStatus() == itemStatus.SHOW) {
					message.append(" + ");
					message.append(mI.getMessage());
					message.append(" + ");
					mI.setStatus(itemStatus.DELETE);
					scrollApp.mainUI.createArrays();
				}
			}
		}
		
	
	
}
