package scrollingtext;

import processing.core.PApplet;
import scrollingtext.scrollDraw;


public class scrollApp {

	public static scrollDraw scroll;
	public static userInterface mainUI;
	
	public scrollApp() {
		
	}
	
	public static void main(String[] args0) {
		scrollApp mainSketch = new scrollApp();
		mainUI = new userInterface();
		mainSketch.start();
		
	}
	
	public void start() {
		String[] processingArgs = {"scrollMain"};
		scroll = new scrollDraw();
		PApplet.runSketch(processingArgs, scroll);
	}
	
}
