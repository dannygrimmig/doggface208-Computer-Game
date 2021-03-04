public class GameLauncher{ 
    /**
    * Only CHANGE constant values as appropriate
    *
    * THERE SHOULD NOT B ANYTHING SUBSTANTIAL TO **ADD** TO THIS CLASS
    * 
    * The Scrolling Game is described the Project Handout. 
    *  
    * @author Elodie Fourquet 
    * @date October, 2020
    */
    
    private static final int BASE = 0;
    private static final int CREATIVE = 1;
    
    private static final int RUNNING = CREATIVE;
    //DANNY REMEMBER! change running to CREATIVE for creative game, BASE for base game...
  
    
    
    public static void main(String[] args) {
        GameCore game = null;
        
        switch (RUNNING) {
        case BASE:
            // Construct sized version of the base game you've written
            game = new AScrollingGame(8, 16);
            // Test me too (and more)
            //game = new AScrollingGame();
           
            System.out.println("From GameLauncher main: Running the BaseGame version");
            break;
        case CREATIVE:
            // Construct sized version of the creative game you've written
            game = new CreativeGame(9, 16);
            System.out.println("From GameLauncher main: Running the CreativeGame version ");
            break;
        default:
            System.out.println("Not sure which version you meant...");
            
        }
        
        // Launch the instantiated game version
        // Make sure to trace the run method in GameCore
        if (game != null)
            game.run();
            
        else 
            System.out.println("Check that a Game is instantiated in GameLauncher");
        
    }
    
}
