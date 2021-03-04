import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;



import java.io.File;          //These imports bellow are all needed for the music
import java.io.IOException; 
import java.util.Scanner; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

public class CreativeGame extends GameCore {
    
    //---------------- Class Variables and Constants -----------------//
    
//ARROW KEYS
    protected static final int KEY_MOVE_DOWN = KeyEvent.VK_DOWN;
    protected static final int KEY_MOVE_UP = KeyEvent.VK_UP;
    protected static final int KEY_MOVE_RIGHT = KeyEvent.VK_RIGHT;
    protected static final int KEY_MOVE_LEFT = KeyEvent.VK_LEFT;


    
//PLAYERS STARTING ROW
    private static final int DEFAULT_PLAYER_ROW = 4;

//INTRO SPLASH SCREEN  
    private static final String INTRO_SCREEN = "Cranberrysurvival.gif";
  
//PLAYER IMAGE (THE CRANBERRY JUICE GUY
    protected static String PLAYER_IMG = "dogface.gif";    // specify user image file
    

//AVOID IMAGES
	//LEFT FACING CARS
    protected static String AVOID_IMG1 = "left_car1.gif"; 
    protected static String AVOID_IMG2 = "left_car2.gif"; 
    protected static String AVOID_IMG3 = "left_car3.gif"; 
    
    //RIGHT FACING CARS
    protected static String AVOID_IMG4 = "right_car1.gif"; 
    protected static String AVOID_IMG5 = "right_car2.gif"; 
    protected static String AVOID_IMG6 = "right_car3.gif"; 
//GET IMAGE
	//CRANBERRY JUICE 
    protected static String GET_IMG = "cranberryjuice.gif"; 
    
    
//CLICK INSTANCE VARIABLE
    protected Location clickCoord;
    
//PLAYER LOCATION
    protected Location playerCoord;

//SCORE
    protected int score;
//HITS
    protected int hits;
//GOAL SCORE
    protected int endscore = 20;
//GAME ENDING SCORE
    protected int killscore = 5;
//IS THE GAME OVER? To start... no the game is not over so begins as false
    protected boolean gameOver = false;
    
  
  //---------------- CONSTRUCTORS -----------------//
  
    public CreativeGame(int grid_h, int grid_w){
        this(grid_h, grid_w, DEFAULT_TIMER_DELAY);
    }
    
    public CreativeGame(int hdim, int wdim, int init_delay_ms) {
        super(hdim, wdim, init_delay_ms);        
    }
    
    
//BEGINNING OF GAME TASK
// - DISPLAY STILL SCREEN UNTIL ENTER KEY
// - RESET GAME PARAMS FOR GAME START
    protected void startGame(){
        updateTitle("Welcome to my game...Collect 20 Cranberry to win, get hit by 5 cars to lose!");  
        displayStillScreen(INTRO_SCREEN);  
        resetGamePlayParam();
    }
     
     protected void resetGamePlayParam() {
        score = 0; 
        hits = 0;
        updateTitle("Cranberry Survival --- SCORE " + score + " ;hits " + hits);
        
        
        // store and initialize user position
        playerCoord = new Location(DEFAULT_PLAYER_ROW, 0);
        setGridImage(playerCoord, PLAYER_IMG);
        
        // Try the lines below
        displayGameBackground("background.gif");
        
//CALLS THE PLAYSOUND FUNCTION, PLAYING DREAMS by FLEETWOODMAC
        playSound();  
        setGridColor(playerCoord, null);
    }
    
    //Call methods that check for user input
    //   key press and mouse click
    protected int performGameUpdates() {
        clickCoord = handleMouseClick();
        if (clickCoord != null)
            System.out.println("Mouse clicked at : " + clickCoord);
        
        return handleKeyPress();
    }
    
//PLAYS SONG FILE in the form of WAV, not mp3
    public void playSound() {
        	try {
        		System.out.println("Music time");
        		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Dreams_FleatwoodMac.wav").getAbsoluteFile());
        		Clip clip = AudioSystem.getClip();
        		clip.open(audioInputStream);
        		clip.start();
        	} catch(Exception ex) {
        		System.out.println("Error with playing sound.");
        		ex.printStackTrace();
        	}
        }
    
    
   
    
    /****************** Methods to Implement Part I ******************/

// WHAT I WANT THE CODE TO DO AT EACH 'TIC'
	//I WANT IT TO SCROLL, POPULATE, AND CHECK IF THE GAME HAS ENDED
    protected void performRenderUpdates(){
    	scroll();
    	//CHECK IF GAME IS OVER, IF WON--> POPULATE THE BOARD WITH PLAYER IMAGE, IF LOST --> ALL CARS!
    	if (score>=endscore){
    		populateWinner();
    	}
    	else if(hits>= killscore){
    		populateLoser();
    	}
    	else{
    		//SINCE GAME DIDNT END --> POPULATE NORMALLY!
    		populate(); 
        }
        endGame();
    }
    
    
//FUNCTION PUTS IMAGES ONTO THE EDGES OF THE BOARDS
    protected void populate() {
    	//THIS CHUNK POPULATES THE RIGHT EDGE, ON THE UPPER HALF OF THE BOARD
    	Random rand = new Random();
    	for (int i = 0; i < super.getTotalGridRows()/2; i++){
    		Location new_loc = new Location(i, super.getTotalGridCols() - 1);
    		int n = rand.nextInt(12);
    	
    		if(n==0) {
    			super.setGridImage(new_loc, this.AVOID_IMG1);
      	  	}
      	  	else if(n==1) {
      	  		super.setGridImage(new_loc, this.AVOID_IMG2);
      	  	}
      	  	else if(n==2){
      	  		super.setGridImage(new_loc, this.AVOID_IMG3);
      	  	}
      	  	else if(n==3) {
      	  		super.setGridImage(new_loc, this.GET_IMG);
      	  	}
      	  	else{
      	  		super.setGridImage(new_loc, null);
      	  	}
      	}
//MIDDLE LANE OF BOARD IS PURPOSLY EXCLUDED AS A SAFE ZONE!
     	
//THIS CHUNK POPULATES THE BOTTOM HALF ON THE LEFT SIDE, TO REPRESENT CARS COMING FROM THE OTHER DIRECTION      	
      	for (int i = super.getTotalGridRows()/2 + 1; i < super.getTotalGridRows(); i++){
    		Location new_loc = new Location(i, 0);
    		int n = rand.nextInt(12);
    	
    		if(n==0) {
    			super.setGridImage(new_loc, this.AVOID_IMG4);
      	  	}
      	  	else if(n==1) {
      	  		super.setGridImage(new_loc, this.AVOID_IMG5);
      	  	}
      	  	else if(n==2){
      	  		super.setGridImage(new_loc, this.AVOID_IMG6);
      	  	}
      	  	else if(n==3) {
      	  		super.setGridImage(new_loc, this.GET_IMG);
      	  	}
      	  	else{
      	  		super.setGridImage(new_loc, null);
      	  	}
      	}
   	 }
   	 
    
//SCROLL THE POPULATED CHARACTERS ON THE GRID!
    protected void scroll() {
    	//THIS CHUNK SCROLLS THE TOP HALF FROM RIGHT TO LEFT
    	for (int i = 0; i < super.getTotalGridRows()/2; i++){
    		for (int j = 1; j < super.getTotalGridCols(); j++){
    			Location old_loc = new Location(i, j);
    			Location new_loc = new Location(i, j - 1);
    			if(!(old_loc.equals(playerCoord) /*|| new_loc.equals(playerCoord)*/)){
    				super.moveGridImage(old_loc, new_loc);	
    			}
    		}
    	}
    	//THIS CHUNK SCROLLS THE BOTTOM HALF FROM LEFT TO RIGHT
    	for (int i = super.getTotalGridRows()/2 + 1; i < super.getTotalGridRows(); i++){
    		for (int j = super.getTotalGridCols()-2; j >= 0; j--){
    			Location old_loc = new Location(i, j);
    			Location new_loc = new Location(i, j +1);
    			if(!(old_loc.equals(playerCoord) /*|| new_loc.equals(playerCoord)*/)){
    				super.moveGridImage(old_loc, new_loc);
    			}
    		}
    	}
    	//BEFORE SETTING GRID IMAGE,CHECK HANDLE COLLISON FIRST, SO TEMPORARILY THE PLAYER LOCATION WILL BE EITHER, GET, AVOID, OR NULL
    	handleCollision();
    	setGridImage(playerCoord, PLAYER_IMG);
    }
    
//CHECK FOR COLLISION BETWEEN PLAYER AND EITHER AVOID OR GET COMPONENTS
    protected void handleCollision() {
    	if (super.getGridImage(playerCoord) != null){ //MAKE SURE ITS NOT A NULL SPACE ,or else nullpointer exception.
    		if (super.getGridImage(playerCoord).equals(GET_IMG)){
    			score += 1;
    		}
    		else if (super.getGridImage(playerCoord).equals(AVOID_IMG1)){
    			System.out.println("CAR");
    			hits += 1;
    		}
    		else if (super.getGridImage(playerCoord).equals(AVOID_IMG2)){
    			hits += 1;
    		}
    		else if (super.getGridImage(playerCoord).equals(AVOID_IMG3)){
    			hits += 1;
    		}
    		else if (super.getGridImage(playerCoord).equals(AVOID_IMG4)){
    			hits += 1;
    		}
    		else if (super.getGridImage(playerCoord).equals(AVOID_IMG5)){
    			hits += 1;
    		}
    		else if (super.getGridImage(playerCoord).equals(AVOID_IMG6)){
    			hits += 1;
    		}
        }
       updateTitle("Cranberry Survival---Juice's Collected "+ score + "; Hits " + hits);
       endGame();
    }

    /****************** Methods to Complete Part I ******************/
    
//HANDLES ALL THE KEY PRESSES DURING THE GAME
    protected int handleKeyPress() {
        int key = super.handleKeyPress(); // delegate to parent window level keys
        
        if (key != GameGrid.NO_KEY) {
            System.out.println("A key has been pressed ");
        }
        
        if (key == KeyEvent.VK_S) {
            System.out.println("could save the screen: add the call");
            takeScreenShot("DANNYGRIMMIG_CREATIVEGAME_SCREENSHOT.png");
        }
        
        if (key == KEY_MOVE_UP){
        	// if not in the top row...
        	if (playerCoord.getRow() != 0){
        		Location old_loc = new Location(playerCoord.getRow(), playerCoord.getCol());
        		Location new_loc = new Location(playerCoord.getRow()-1, playerCoord.getCol());
//CHANGE PLAYER LOCATION TO NEW LOC, THEN CHECK FOR COLLISON. THIS CHECKS FOR PLAYER ACTIVILY GOING INTO OBJECT      
        		playerCoord = new_loc;
        		handleCollision();
        		super.moveGridImage(old_loc, new_loc);
        	}
        }
         if (key == KEY_MOVE_DOWN){
        	if (playerCoord.getRow() != super.getTotalGridRows()-1){
        		Location old_loc = new Location(playerCoord.getRow(), playerCoord.getCol());
        		Location new_loc = new Location(playerCoord.getRow()+1, playerCoord.getCol());
//CHANGE PLAYER LOCATION TO NEW LOC, THEN CHECK FOR COLLISON. THIS CHECKS FOR PLAYER ACTIVILY GOING INTO OBJECT       		
        		playerCoord = new_loc;
        		handleCollision();
        		super.moveGridImage(old_loc, new_loc);
        	}
        }
         if (key == KEY_MOVE_LEFT){
        	if (playerCoord.getCol() != 0){
        		Location old_loc = new Location(playerCoord.getRow(), playerCoord.getCol());
        		Location new_loc = new Location(playerCoord.getRow(), playerCoord.getCol()-1);
 //CHANGE PLAYER LOCATION TO NEW LOC, THEN CHECK FOR COLLISON. THIS CHECKS FOR PLAYER ACTIVILY GOING INTO OBJECT             		
        		playerCoord = new_loc;
        		handleCollision();
        		super.moveGridImage(old_loc, new_loc);
        	}
        }
         if (key == KEY_MOVE_RIGHT){
        	if (playerCoord.getCol() != super.getTotalGridCols()-1){
        		Location old_loc = new Location(playerCoord.getRow(), playerCoord.getCol());
        		Location new_loc = new Location(playerCoord.getRow(), playerCoord.getCol()+1);
//CHANGE PLAYER LOCATION TO NEW LOC, THEN CHECK FOR COLLISON. THIS CHECKS FOR PLAYER ACTIVILY GOING INTO OBJECT              		
        		playerCoord = new_loc;
        		handleCollision();
        		super.moveGridImage(old_loc, new_loc);
        	}
        }
        return key;
    }

//POPULATE THE GAME BOARD WITH ALL PHOTOS OF THE PLAYER!
    protected void populateWinner() {
    	for (int i = 0; i < super.getTotalGridRows()/2; i++){
    		Location new_loc = new Location(i, super.getTotalGridCols() - 1);
    		super.setGridImage(new_loc,PLAYER_IMG);
      	}
      	for (int i = super.getTotalGridRows()/2 + 1; i < super.getTotalGridRows(); i++){
    		Location new_loc = new Location(i, 0);
    		super.setGridImage(new_loc,PLAYER_IMG);
    	}
    }
//POPULATE THE GAME BOARD WITH ALL PHOTOS CARS (AVOID IMAGES)
     protected void populateLoser() {
    	for (int i = 0; i < super.getTotalGridRows()/2; i++){
    		Location new_loc = new Location(i, super.getTotalGridCols() - 1);
    		super.setGridImage(new_loc,AVOID_IMG1);
      	}
      	for (int i = super.getTotalGridRows()/2 + 1; i < super.getTotalGridRows(); i++){
    		Location new_loc = new Location(i, 0);
    		super.setGridImage(new_loc,AVOID_IMG4);
    	}
    }
    
//CHECK TO SEE IF GAME IS OVER, if so, UPDATE TITLE ACCORDINGLY
    protected void endGame(){
    	if (score>= endscore){
    		updateTitle("WINNER WINNER CHICKEN DINNER; press q to quit or stay and enjoy the tunes!");
    	}
    	else if (hits>= killscore){
    		updateTitle("You got run over 5 times!!!Thanks for Playing! q to exit");
    	}
    }
    
    
//RETURNS FALSE, I WANT USER TO ACTUALLY PRESS Q INSTEAD OF GAME CLOSING SO THAT THE SONG CAN CONTINUE PLAYING
    protected boolean isGameOver() {
        return gameOver;
    }
}
