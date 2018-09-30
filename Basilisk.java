import java.util.ArrayList;

/**
 * Creates a basilisk that will engage the player's group in battle.
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.4 (The version that the class was first added to the project)
 */
public class Basilisk extends Character {

    /**
     * <p>Construct a single legendary basilisk as an enemy</p>
     * <p>Base stats are set to:</p>
     *  <ul>
     *     <li><strong>Health:</strong> 60</li>
     *     <li><strong>Attack:</strong> 9</li>
     *     <li><strong>Defense:</strong> 4</li>
     * </ul>
     *
     * @param enemies ArrayList of Character objects representing the enemies
     */
    public Basilisk(ArrayList<Character> enemies) {
        setName("Basilisk");
        setMaxHp((int)(60 * getEnemyScaling()));
        setAttack((int)(9 * getEnemyScaling()));
        setDefense((int)(4 * getEnemyScaling()));
        setType("Enemy");
        enemies.add(this);
    }
}