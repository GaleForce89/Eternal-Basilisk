import java.util.ArrayList;

/**
 * Creates the mystical mage and while his magic may be superior his lack of physical strength leaves him vulnerable
 * to attack.
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Mage extends Character {

    /**
     * <p>Construct a single mage for the group</p>
     * <p>Base stats are set to:</p>
     *  <ul>
     *     <li><strong>Health:</strong> 35</li>
     *     <li><strong>Attack:</strong> 9</li>
     *     <li><strong>Defense:</strong> 2</li>
     * </ul>
     *
     * @param team ArrayList of Character objects representing the player's group
     */
    public Mage(ArrayList<Character> team) {
        System.out.print("Enter a name for your mage: ");
        setName(userInput.nextLine());
        setMaxHp(35);
        setAttack(9);
        setDefense(2);
        setType("Mage");
        team.add(this);
        System.out.println(getName() + " has been added to your group.");
    }
}
