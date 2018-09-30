import java.util.ArrayList;

/**
 * Creates the archer to shoot enemies from afar, quick and nimble he wears just enough leather armor to stay alive and
 * keep dealing damage.
 *
 * @author Chris Waterman
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Archer extends Character {

    /**
     * <p>Construct a single archer for the group</p>
     * <p>Base stats are set to:</p>
     *  <ul>
     *     <li><strong>Health:</strong> 40</li>
     *     <li><strong>Attack:</strong> 8</li>
     *     <li><strong>Defense:</strong> 3</li>
     * </ul>
     *
     * @param team ArrayList of Character objects representing the player's group
     */
    public Archer(ArrayList<Character> team) {
        System.out.print("Enter a name for your archer: ");
        setName(userInput.nextLine());
        setMaxHp(40);
        setAttack(8);
        setDefense(3);
        setType("Archer");
        team.add(this);
        System.out.println(getName() + " has been added to your group.");
    }
}