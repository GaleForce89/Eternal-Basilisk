import java.util.ArrayList;

/**
 * Creates a goblin that will engage the player's group in battle.
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Goblin extends Character {

    /**
     * <p>Construct a single goblin as an enemy</p>
     * <p>Base stats are set to:</p>
     *  <ul>
     *     <li><strong>Health:</strong> 20</li>
     *     <li><strong>Attack:</strong> 7</li>
     *     <li><strong>Defense:</strong> 2</li>
     * </ul>
     *
     * @param enemies ArrayList of Character objects representing the enemies
     */
    public Goblin(ArrayList<Character> enemies) {
        setName("Goblin");
        setMaxHp((int)(20 * getEnemyScaling()));
        setAttack((int)(7 * getEnemyScaling()));
        setDefense((int)(2 * getEnemyScaling()));
        setType("Enemy");
        enemies.add(this);
    }
}