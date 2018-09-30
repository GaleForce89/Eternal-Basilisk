import java.util.ArrayList;

/**
 * Creates a troll that will engage the player's group in battle.
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Troll extends Character {

    /**
     * <p>Construct a single troll as an enemy</p>
     * <p>Base stats are set to:</p>
     *  <ul>
     *     <li><strong>Health:</strong> 25</li>
     *     <li><strong>Attack:</strong> 8</li>
     *     <li><strong>Defense:</strong> 3</li>
     * </ul>
     *
     * @param enemies ArrayList of Character objects representing the enemies
     */
    public Troll(ArrayList<Character> enemies) {
        setName("Troll");
        setMaxHp((int)(25 * getEnemyScaling()));
        setAttack((int)(8 * getEnemyScaling()));
        setDefense((int)(3 * getEnemyScaling()));
        setType("Enemy");
        enemies.add(this);
    }
}