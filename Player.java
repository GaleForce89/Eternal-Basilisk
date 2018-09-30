import java.util.ArrayList;

/**
 * The player class is where the groups actions are checked
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Player {
    /**
     * ArrayList of Character objects representing the player's group
     */
	ArrayList<Character> group;
	/**
     * ArrayList of Character objects representing the enemies
     */
	ArrayList<Character> enemies = new ArrayList<Character>();
	/**
     * Group's collective gold
     */
	private int gold = 0;
	/**
     * Player object constructor. Initializes the group ArrayList.
     */
	public Player() {
		 group = new ArrayList<Character>();
	}
	/**
     * Generates a random number of enemies, between 5 and 7, with random enemy types, adds them to the enemies array.
     */
	public void generateNewEnemies() {
	    enemies.clear();
	    int rand = (int)(Math.random()*3);
	    int numEnemies = 0;
	    switch(rand) {
	    case 0: numEnemies = 5;
	        break;
	    case 1: numEnemies = 6;
	        break;
	    case 2: numEnemies = 7;
	        break;
	    }

	    //Generate a 5% chance for a basilisk to appear the formula is
        // min + rand * (max - min) + 1
        int basilisk;

        basilisk = (int)(1+ (Math.random()* (100-1)+ 1));

        if(basilisk <= 5){
            new Basilisk(enemies);
            numEnemies--;
        }

	    int randType; 
	    for(int x = 0; x < numEnemies; x++) {
	        randType = (int)(Math.random()*3);
	        switch(randType) {
	        case 0: new Orc(enemies);
	        break;
	        case 1: new Troll(enemies);
	        break;
	        case 2: new Goblin(enemies);
	        }
	    }
	}
	
	/**
     * Gets current group gold
     * @return Gold amount
     */
    public int getGold() {
        return gold;
    }
    
    /**
     * Used to change current group gold after each battle or when an item is bought. 
     * @param g Value to change current gold amount.
     */
    public void addGold(int g) {
        gold += g;
    }
    
    /**
     * Checks to see if at least one player is alive, to see if the game is over. 
     * @return True if all the player's characters are dead, False otherwise. 
     */
	public boolean gameOver() {
	    for(Character c : group)
	        if(!c.isDead())
	            return false;
	    return true;
	}
	/**
     * Checks to see if the player has won the game.
     * @return True if all enemies are dead, False otherwise. 
     */
	public boolean victory() {
	    for(Character e : enemies)
	        if(!e.isDead())
	            return false;
	    return true;
	}
    /**
     * Checks for any group member still alive after the battle and restores them to full HP. 
     */
	public void healGroup(){
	    for(Character g : group)
	        if(!g.isDead())
	            g.fullHeal();
    }

	/**
	 * Determine amount of gold to award after battle if basilisk was encountered. 
	 * @return Gold to award for victory. 
	 */
	public int award(){
		for(Character x: enemies)
			if(x.getName().equals("Basilisk"))
				return 25;
		return 10;
	}
}
