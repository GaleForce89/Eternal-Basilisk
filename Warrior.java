import java.util.ArrayList;

/**
 * Creates the mighty warrior to smash foes, though he is reckless combatant he more than makes up with his ability to
 * withstand damage.
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Warrior extends Character {

    /**
     * <p>Construct a single warrior for the group</p>
     * <p>Base stats are set to:</p>
     *  <ul>
     *     <li><strong>Health:</strong> 50</li>
     *     <li><strong>Attack:</strong> 6</li>
     *     <li><strong>Defense:</strong> 4</li>
     * </ul>
     *
     * @param team ArrayList of Character objects representing the player's group
     */
    public Warrior(ArrayList<Character> team) {
        System.out.print("Enter a name for your warrior: ");
        setName(userInput.nextLine());
        setMaxHp(50);
        setAttack(6);
        setDefense(4);
        setType("Warrior");
        team.add(this);
        System.out.println(getName() + " has been added to your group.");
    }

}