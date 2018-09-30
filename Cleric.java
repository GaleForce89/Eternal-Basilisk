import java.util.ArrayList;

/**
 * Creates the good willed cleric to stand by your teams side, he is heavily armored and ready to stay userInput the fight
 * for the long haul.
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Cleric extends Character {

    /**
     * <p>Construct a single cleric for the group</p>
     * <p>Base stats are set to:</p>
     *  <ul>
     *     <li><strong>Health:</strong> 45</li>
     *     <li><strong>Attack:</strong> 5</li>
     *     <li><strong>Defense:</strong> 5</li>
     * </ul>
     *
     * @param team ArrayList of Character objects representing the player's group
     */
    public Cleric(ArrayList<Character> team) {
        System.out.print("Enter a name for your cleric: ");
        setName(userInput.nextLine());
        setMaxHp(45);
        setAttack(5);
        setDefense(5);
        setType("Cleric");
        team.add(this);
        System.out.println(getName() + " has been added to your group.");
    }
}
