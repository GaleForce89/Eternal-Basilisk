import java.util.*;

/**
 * <h1>Eternal Basilisk</h1><br>
 *     Welcome to Eternal Basilisk, a turn based fantasy rpg that is entirely text based.<br><br>
 *         <strong>Features include:</strong><br><br>
 *             <ul>
 *                 <li>Store bought upgrades</li>
 *                 <li>Enemies that scale with the group, creating a constant challenge</li>
 *                 <li>Random boss encounters known as the basilisk (gives extra gold!)</li>
 *                 <li>Never ending game play!</li>
 *             </ul>
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Game {

    /**
     * Used to create the player object and initializes the group array list
     */
    static Player user = new Player();

    /**
     * Establishes a scanner object used to obtain user input
     */
    static Scanner userInput = new Scanner(System.in);

    /**
     * The main method of the game, this is where every function of the game is first constructed. 
     * This class also controls the flow of the game and exits if the user wishes to quit.
     *
     * @param args No commandline options available
     */
    public static void main(String[] args) {
        int teamSize = 0;
        int type = 0;
        int waveCounter = 0;
        
        System.out.println("Welcome to Eternal Basilisk!");
        //input size of group, error checking for input mismatches and invalid inputs
        teamSize = getValidResponse(1, 7, "How many characters would you like in your group? (1-7): ", "Invalid number of characters.", 1);
        //same as above for class types
        for(int a = 0; a < teamSize; a++) {
            System.out.println("\n1 - Warrior");
            System.out.println("2 - Archer");
            System.out.println("3 - Mage");
            System.out.println("4 - Cleric");
            type = getValidResponse(1, 4, "Which class would you like to add to your group? (1-4): ", "Invalid class number.", 1);
            //create characters and add them to the player object's ArrayList (done in each class's constructor)

            switch(type) {
            case 1: new Warrior(user.group);
            break;
            case 2: new Archer(user.group);
            break;
            case 3: new Mage(user.group);
            break;
            case 4: new Cleric(user.group);
            break;
            }
        } //end character creation
        
        //game start
        int turnCounter; //used to keep track of which character's turn it is (only used for allies, incremented after each turn or when skipping over a dead ally)
        int enemyTurnCounter; //used to keep track of which enemy's turn it is (only used for enemies, incremented after each turn or when skipping over a dead enemy)
        int decision; //used for input, attack heal or quit
        int targetNum; //used for input, which enemy to attack
        int gold; //used to determine gold awarded after battle (also helps with output) initially set to zero
        boolean validTarget; //used to test if the enemy target selected is valid (user input is acceptable and target is not dead)
        boolean selectionComplete; //used to control the while loop for attack/heal/quit, allows ability to go back to previous menu)
        boolean lost; //used to tell whether or not the player has lost the game
        int finalInput; //used for the final input of the game, continue or quit
        Character currentAlly; //temporary object that holds the character whose turn it is
        Character currentEnemy; //temporary object that holds the enemy whose turn it is
        Character targetAlly; //temporary object that holds the currently targeted ally for an enemy attack
        Character targetEnemy; //temporary object that holds the currently targeted enemy for allied attack
        Character toHeal; //temporary object that holds the currently selected target to heal
        while(true) { //while loop for the game (loops back when the player survives a wave of enemies) exits loop when all allies are dead or the user quits
            //game loop variable reset
            turnCounter = 0;
            enemyTurnCounter = 0;
            lost = false;
            finalInput = 0;
            //generate enemies
            user.generateNewEnemies();
            while(true) { //while loop for turns (loops back after each player/enemy turn cycle) exits loop when all enemies are dead or all allies are dead
                //turn loop variable reset
                selectionComplete = false;
                validTarget = false;
                decision = 0;
                targetNum = 0;
                //get current ally based on turnCounter (turnCounter % group size will return a number from 0 to groupSize - 1, this number loops around in that range continuously)
                currentAlly = user.group.get((turnCounter) % user.group.size());
                //player's turn
                //check if current group member is dead, skip if dead (moves to next character's turn, does not give enemy a turn when character is dead)
                if( currentAlly.isDead() ) {
                    turnCounter++;
                    continue;
                }
                //print stats for each character/enemy
                //also post-increment turnCounter, as this is the last time it is used in the turn
                printState(turnCounter++);
                //ask for input (attack, heal, or quit)                
                while(!selectionComplete) {
                    //return point for the go back options
                    decision = getValidResponse(1, 2, "Would you like to attack(1), heal(2), or quit(q)? : ", "Invalid choice.", 3);
                  //if attack is chosen
                    if(decision == 1) {
                        validTarget = false;
                        //loop while the target is invalid (not valid input or target is dead)
                        while(!validTarget) {
                            targetNum = getValidResponse(1, user.enemies.size(), "Which enemy would you like to attack? (1-" + user.enemies.size() + " or b to go back) : ", "Invalid choice.", 2);
                          //check for go back, break if chosen
                            if(targetNum == -1) 
                                break;
                            targetEnemy = user.enemies.get(targetNum - 1); //subtract 1 from targetNum for correct ArrayList index
                            //make sure the target is not dead, loop back to target selection if it is
                            if(targetEnemy.isDead()) {
                                System.out.println("That enemy is already dead.");
                                continue;
                            }
                            validTarget = true;
                            //attack output
                            System.out.println("\n\n" + currentAlly.getName() + " attacks " + targetEnemy.getName() + ".");
                            targetEnemy.takeDamage(currentAlly.getAttack());
                            //change flag for loop, selection is complete at this point
                            selectionComplete = true;
                        }
                    }
                    //if heal is chosen
                    else if(decision == 2) {
                        validTarget = false;
                        //loop while the target of the heal is invalid (invalid input or target is dead)
                        while(!validTarget) {
                            targetNum = getValidResponse(1, user.group.size(), "Which group member would you like to heal? (1" + (user.group.size()==1?"":" - "+user.group.size()) + " or b to go back) : ", "Invalid choice.", 2);
                            //check for go back, break if chosen
                            if(targetNum == -1)
                                break;
                            //set temporary Character to group member to be healed
                            toHeal = user.group.get(targetNum - 1);
                            //make sure not dead
                            if(toHeal.isDead()) {
                                System.out.println("You cannot heal dead group members.");
                                continue;
                            }
                            //make sure heal target is not already at full health
                            if(toHeal.getMaxHp() == toHeal.getHp()) {
                                System.out.println(toHeal.getName() + " is already at full health.");
                                continue;
                            }
                            validTarget = true;
                            //healing output
                            System.out.println(toHeal.getName() + " is healed for " + toHeal.getHealed() + " hp.");
                            //set flag to true to exit loop
                            selectionComplete = true;
                        }
                    }
                  //check for quit, exits program if chosen
                    else if(decision == -1) {
                        decision = getValidResponse(1, 2, "All progress will be lost and the game will exit.\nAre you sure you would like to quit?(1 to quit, 2 to keep playing) : ", "Invalid choice.", 1);
                        if(decision == 1) {
                            System.out.print("Exiting.");
                            System.exit(0);
                        }
                    }
                }
                //check to see if all enemies are dead
                if(user.victory()) {
                    break;                    
                }
                //enemies' turn
                System.out.println("\nEnemies' turn");
                //set temp Enemy to enemy whose turn it is
                //also increment enemyTurnCounter
                currentEnemy = user.enemies.get(enemyTurnCounter++ % user.enemies.size());
                //while the currently selected enemy is dead, move to the next one and increment turn counter
                while(currentEnemy.isDead())
                    currentEnemy = user.enemies.get(enemyTurnCounter++ % user.enemies.size());
                //enemy picks a valid target (should find a better way to do this)
                //randomly selects a character from the player's group, if that player is not dead, break from the loop
                //inefficient if most of the group is dead
                while(true) { 
                    targetAlly = user.group.get((int)(Math.random() * user.group.size()));
                    if(!targetAlly.isDead())
                        break;
                }
                //enemy attack output
                System.out.println(currentEnemy.getName() + " attacks " + targetAlly.getName() + ".");
                targetAlly.takeDamage(currentEnemy.getAttack());
                System.out.println("\n");
                
                //check for game over, if yes, set lost flag
                if(user.gameOver()) {
                    lost = true;
                    break;
                }
            }//end turn loop
            
            //check for lost flag, if true, print game over and exit game
            if(lost) {
                System.out.println("Your group has fallen.");
                System.out.println("GAME OVER");
                break;
            }
            //at this point, the player has won
            //print winning statement
            gold = user.award();

            System.out.printf("%n%nCongratulations!\nYou have defeated all of the enemies!" +
                "%nYour group has been healed, but your medicine is not strong enough to resurrect dead group members." +
                "%n%d Gold awarded.%n%n", gold);
            System.out.println("");
            //give the group gold for winning
            user.addGold(gold);
            //heal group members that are still alive
            user.healGroup();
            //---------------shop start----------------------
            System.out.println("Your group encounters a shop.");
            decision = getValidResponse(1, 2, "Visit the shop(1), keep adventuring(2), or quit(q) : ", "Invalid choice.", 3);
            if(decision == -1) {
                decision = getValidResponse(1, 2, "All progress will be lost and the game will exit.\nAre you sure you would like to quit?(1 to quit, 2 to keep playing) : ", "Invalid choice.", 1);
                if(decision == 1) {
                    System.out.print("Exiting.");
                    System.exit(0);
                }
            }
            else if(decision == 1) {
                selectionComplete = false;
    
                System.out.println("\nHi! Welcome to Austin's shop!\n" +
                    "Upgrading defense cost 3 gold, upgrading attack cost 2 gold, upgrading health cost 2 gold,\n" +
                    "and resurrecting a group member cost 20 gold\n" +
                    "Your group has " + user.getGold() + " gold to spend."); 
    
    
                while(!selectionComplete) {
                    //return point for the go back options
                    decision = getValidResponse(1, 4,  "\nWould you like to upgrade defense(1), attack(2), health(3), resurrect(4) or quit shopping (q)? : ", "Invalid choice.", 3);
                    //if defense is chosen
                    if(decision == 1) {
                        validTarget = false;
                        if (user.getGold() < 3) { //makes sure user has enough gold first
                            System.out.println("You do not have enough gold for this option!\n");
                            continue;
                        }
                        //loop while the target is invalid (not valid input or target is dead)
                        while(!validTarget) {
                            printGroup();
                            targetNum = getValidResponse(1, user.group.size(), "Which character's armor would you like to upgrade: (1-" + user.group.size() + " or hit b to go back) : ", "Invalid choice.", 2);
                            //check for go back, break if chosen
                            if(targetNum == -1) 
                                break;
                            targetAlly = user.group.get(targetNum - 1); //subtract 1 from targetNum for correct ArrayList index
                            //make sure the target is not dead, loop back to target selection if it is
                            if(targetAlly.isDead()) {
                                System.out.println("\nAustin's shop has a 'No dead customers' policy.\n");
                                continue;
                            }
                            //at this point, a target has been selected and it is not dead
                            validTarget = true;
                            //upgrade defense!
                            targetAlly.setDefense(1);
                            System.out.println("\n" + targetAlly.getName() + " now has " + (targetAlly.getDefense()) + " defense.");
                            user.addGold(-3);
                            System.out.println("\nYour group has " + (user.getGold()) + " gold left.");                                
                            //change flag for loop, selection is complete at this point
                            selectionComplete = true;
                        }
                        //check for go back, loop back to action selection menu if chosen
                        if(targetNum == -1) 
                            continue;
                    }
                    //if Attack is chosen
                    else if(decision == 2) {
                        validTarget = false;
                        if (user.getGold()<2) {//makes sure user has enough gold first
                            System.out.println("You do not have enough gold for this option!\n");
                            continue;
                        }
                        //loop while the target is invalid (not valid input or target is dead)
                        while(!validTarget) {
                            printGroup();
                            targetNum = getValidResponse(1, user.group.size(), "Which character's attack would you like to upgrade? (1-" + user.group.size() + " or b to go back) : ", "Invalid choice.", 2);
                            //check for go back, break if chosen
                            if(targetNum == -1) 
                                break;
                            targetAlly = user.group.get(targetNum - 1); //subtract 1 from targetNum for correct ArrayList index
                            //make sure the target is not dead, loop back to target selection if it is
                            if(targetAlly.isDead()) {
                                System.out.println("\nAustin's shop has a 'No dead customers' policy. \n\n");
                                continue;
                            }
                            validTarget = true;
                            //upgrade attack!
                            targetAlly.setAttack(1);
                            System.out.println("\n" + targetAlly.getName() + " now has " + (targetAlly.getAttack()) + " attack.");
                            user.addGold(-2);
                            System.out.println("\nYour group has " + (user.getGold()) + " gold left.");
                            //change flag for loop, selection is complete at this point
                            selectionComplete = true;
                        }
                        if(targetNum == -1) 
                            continue;
                    }
                    //if health is chosen
                    else if(decision == 3) {
                        validTarget = false;
                        if (user.getGold()<2) {//makes sure user has enough gold first
                            System.out.println("You do not have enough gold for this option!\n");
                            continue;
                        }
                        //loop while the target is invalid (not valid input or target is dead)
                        while(!validTarget) {
                            printGroup();
                            targetNum = getValidResponse(1, user.group.size(), "Which character's health would you like to upgrade? (1-" + user.group.size() + " or b to go back) : ", "Invalid choice.", 2);
                            //check for go back, break if chosen
                            if(targetNum == -1)
                                break;
                            targetAlly = user.group.get(targetNum - 1); //subtract 1 from targetNum for correct ArrayList index
                            //make sure the target is not dead, loop back to target selection if it is
                            if(targetAlly.isDead()) {
                                System.out.println("\nAustin's shop has a 'No dead customers' policy. \n\n");
                                continue;
                            }
                            validTarget = true;
                            //upgrade health!
                            targetAlly.setMaxHp(1);
                            System.out.println("\n" + targetAlly.getName() + " now has " + (targetAlly.getMaxHp()) + " health.");
                            user.addGold(-2);
                            System.out.println("\nYour group has " + (user.getGold()) + " gold left.");
                            //change flag for loop, selection is complete at this point
                            selectionComplete = true;
                        }
                        if(targetNum == -1)
                            continue;
                    }
                    //if resurrect is chosen
                    else if(decision == 4) {
                        validTarget = false;
                        if (user.getGold()<20) {//makes sure user has enough gold first
                            System.out.println("You do not have enough gold for this option!\n");
                            continue;
                        }
                        //loop while the target is invalid (not valid input or target is not dead)
                        while(!validTarget) {
                            printGroup();
                            targetNum = getValidResponse(1, user.group.size(), "Which character would you like to resurrect? (1-" + user.group.size() + " or b to go back) : ", "Invalid choice.", 2);
                            //check for go back, break if chosen
                            if(targetNum == -1)
                                break;
                            targetAlly = user.group.get(targetNum - 1); //subtract 1 from targetNum for correct ArrayList index
                            //make sure the target is dead, loop back to target selection if it is not
                            if(!targetAlly.isDead()) {
                                System.out.println("\nAustin's shop can only resurrect the dead. \n\n");
                                continue;
                            }
                            validTarget = true;
                            //resurrect
                            targetAlly.fullHeal();
                            System.out.println("\n" + targetAlly.getName() + " has been resurrected with " + (targetAlly.getHp()) + " health.");
                            user.addGold(-20);
                            System.out.println("\nYour group has " + (user.getGold()) + " gold left.");
                            //change flag for loop, selection is complete at this point
                            selectionComplete = true;
                        }
                        if(targetNum == -1)
                            continue;
                    }
                    //check for quit, exits program if chosen
                    else if(decision == -1) {
                        decision = getValidResponse(1, 2, "Are you sure you would like to quit shopping?(1 to quit, 2 to keep shopping) : ", "Invalid choice.", 1);
                        if(decision == 1) {
                            selectionComplete=true;
                            System.out.print("Exiting the shop. Good luck, come again!\n");
                            break;
                        }
                        continue;
                    }
                    if(user.getGold() < 2) {
                        System.out.println("Your group is unable to buy anything else in the shop.\nExiting the shop. Good luck, come again!\n");
                        break;
                    }
                      selectionComplete=false;
                } //end shop loop
            } //end shop if statement
            //ask to continue
            finalInput = getValidResponse(1, 2, "Continue playing(1) or Quit(2) : ", "Invalid choice.", 1);
            if(finalInput != 1) {
                finalInput = getValidResponse(1, 2, "All progress will be lost and the game will exit.\nAre you sure you would like to quit?(1 to quit, 2 to keep playing) : ", "Invalid choice.", 1);
                if(finalInput == 1) {
                    System.out.print("Exiting.");
                    break;
                }
            }
            //scale enemies after every 3 waves
            if(waveCounter++ % 4 == 3) {
                System.out.println("Your group senses that the enemies from this point on will be stronger.");
                Character.scaleEnemies();
            }
        }//end game loop


        userInput.close();
    }//end main

    /**
     * Prints the player's group with corresponding identifiers and information for use in the shop. 
     */
    public static void printGroup() {
        int counter = 1;
        System.out.printf("\n\t%-15s%-10s%-10s%-10s%-10s\n", "Name", "Class", "HP", "Atk", "Def");
        System.out.println("\t------------------------------------------------");
        for(Character c: user.group) {
            System.out.println(counter++ + "\t" + c);
        }
        System.out.println();
    }
    /**
     * Error checking method. Takes bounds for an integer input, a prompt, an error message, and a question type. 
     * Loops until a valid input is received (exception for b and q, for the back and quit options in some menus). 
     * type == 1 means no extra options. type == 2 means b is also a valid response (return -1 if input is b). 
     * type == 3 means q is also a valid response (return -1 if input is q). 
     *
     * @param low Used to determine minimum group size
     * @param high Used to determine maximum group size
     * @param prompt Initial message to display to user
     * @param error Error message to produce if user input is out of bounds
     * @param type Used during certain parts of the game to determine if user wishes to go back or quit
     * @return User input
     */
    public static int getValidResponse(int low, int high, String prompt, String error, int type) {
        boolean valid = false;
        int input = 0;
        //loop while invalid input
        while(!valid) {
            try {
                System.out.print(prompt);
                input = userInput.nextInt();
            } catch(InputMismatchException e) {
                String n = userInput.nextLine();
                //if special cases are met, return -1 to signal back or quit (depending on type)
                if(n.equals("b") && type == 2)
                    return -1;
                else if(n.equals("q") && type == 3)
                    return -1;
                else {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }
            }
            //input matches expected type at this point
            //ensure value is in appropriate range
            if(input > high || input < low) {
                System.out.println(error + " Please try again.");
                //read in the next line to clear junk values
                //the game will loop infinitely and skip asking for inputs if you don't do this because the scanner doesn't actually read in the value that triggers the input mismatch
                //so it just keeps reading that in over and over when it would normally ask for input
                userInput.nextLine();
            }
            else
                valid = true;
        }
        return input;
    }

    /**
     *  Prints the current stats for each group member and enemy
     * @param turnCounter Integer used to keep track of current character's turn
     */
    public static void printState(int turnCounter) {
        System.out.printf("\n\t%-15s%-10s%-10s%-10s%-10s", "Name", "Class", "HP", "Atk", "Def");
        System.out.printf("%-10s%-10s%-10s%-10s%n", "Type", "HP", "Atk", "Def");
        System.out.println("\t----------------------------------------------------------------------------------------");
        int lineCount = 1;
        Character tempC;
        Character tempE;
        //for loop to print character info
        //loops until it reaches the end of either the enemy list or character list, whichever is bigger
        for(int x = 0; x < Math.max(user.group.size(), user.enemies.size()); x++) {
            if(x < user.group.size()) {
                tempC = user.group.get(x);
                //we can put the character directly into the print statement because we overrode toString in the Character class
                //that method gets called when we put a Character object into a print statement, returns a string
                System.out.print(lineCount++ + "\t" + tempC );
            }
            else {
                //set up spacing for when there are no characters left
                System.out.printf("%d\t%-55s", lineCount++, " ");
            }
            //print enemies
            if(x < user.enemies.size()) {
                tempE = user.enemies.get(x);
                //same as above with the Enemy class
                System.out.print(tempE);
            }
            System.out.println();
        }
            System.out.println("It is " + user.group.get(turnCounter % user.group.size()).getName() + "'s turn.\n");
        
    }
}