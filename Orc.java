import java.util.ArrayList;

/**
 * Creates an orc that will engage the player's group in battle.
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public class Orc extends Character {

    /**
     * <p>Construct a single orc as an enemy</p>
     * <p>Base stats are set to:</p>
     *  <ul>
     *     <li><strong>Health:</strong> 30</li>
     *     <li><strong>Attack:</strong> 6</li>
     *     <li><strong>Defense:</strong> 4</li>
     * </ul>
     *
     * @param enemies ArrayList of Character objects representing the enemies
     */
    public Orc(ArrayList<Character> enemies) {
        setName("Orc");
        setMaxHp((int)(30 * getEnemyScaling()));
        setAttack((int)(6 * getEnemyScaling()));
        setDefense((int)(4 * getEnemyScaling()));
        setType("Enemy");
        enemies.add(this);
    }
}