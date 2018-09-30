import java.util.Scanner;

/**
 * The character class is the base which provides a common set of methods for all subclasses.
 *
 * @author Chris 
 * @author GaleForce
 * @author Brian 
 * @author Austin 
 * @version 0.4 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
public abstract class Character {
    /**
     * Character's name
     */
    private String name;
    /**
     * Character's maximum health
     */
    private int maxHp = 0;
    /**
     * Character's current health
     */
    private int hp = 0;
    /**
     * Character's damage
     */
    private int attack = 0;
    /**
     * Character's defense from attacks
     */
    private int defense = 0;
    /**
     * Character type (Enemy for enemies, class name for group members)
     */
    private String type;
    /**
     * Value used for enemy scaling. Starts at 1, increased by 0.3 after every 3 waves of enemies in the game via the scaleEnemies method.
     */
    private static double enemyScaling = 1;
    
    Scanner userInput = new Scanner(System.in);

    /**
     * Used to obtain a character's name
     * @return Character's name
     */
    public String getName() {
        return name;
    }

    /**
     * Used to set character's name
     * @param n User input to set character's name
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * Used to get a character's attack
     * @return Character's attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Used to set initial attack and to increase attack via store
     * @param a Value to set base attack or increase current attack by
     */
    public void setAttack(int a) {
        attack += a;
    }

    /**
     * Used to get a character's defense
     * @return Character's defense
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Used to set initial defense and to increase defense via store
     * @param d Value to set base defense or increase current defense
     */
    public void setDefense(int d) {
        defense += d;
    }
    /**
     * Used to get a character's health
     * @return Character's health
     */
    public int getHp() {
        return hp;
    }

    /**
     * Used to get a character's class
     * @return Type of enemy
     */
    public String getType() {
        return type;
    }

    /**
     * Used to set a character's class
     * @param t Type of enemy to be set
     */
    public void setType(String t) {
        type  = t;
    }

    /**
     * Used to get character's maximum health
     * @return Character's maximum health
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Used to set character's maximum health or increase via store
     * @param h Value to set base maximum health or increase current maximum health
     */
    public void setMaxHp(int h) {
        maxHp += h;
        fullHeal();
    }
    
    /**
     * Used to set character's current health equal to the maximum value
     */
    public void fullHeal() {
        hp = maxHp;
    }

    /**
     * Used to modify a character's health to implement taking damage from an enemy. 
     * The character takes (enemy's damage value - character's defense value) damage. 
     * Also prints a statement saying how much damage was taken. 
     * If the character dies from an attack, prints a statement saying the character has died. 
     * @param damage Pre-mitigation damage taken (before defense is factored in)
     */
    public void takeDamage(int damage) {
        int damageTaken = damage - defense;
        if(damageTaken <= 0)
            damageTaken = 1;
        hp -= (damageTaken);
        System.out.print(name + " takes " + damageTaken + " damage. (Press Enter to continue)");
        userInput.nextLine();
        if(hp <= 0) {
            hp = 0;
            System.out.println(name + " has been slain. (Press Enter to continue)");
            userInput.nextLine();
        }
    }

    /**
     * Used to increase a character's health to implement healing. By default, heals for 5 health. 
     * Will not heal past a character's maximum health (if less than 5 health is missing, heals the character back to its max health). 
     * @return Amount the character is healed (default 5).
     */
    public int getHealed() {
        int healValue = maxHp - hp;
        if(healValue < 5) {
            hp = maxHp;
            return healValue;
        }
        else
            hp += 5;
        return 5;
    }

    /**
     * Determines if the character is alive or dead. 
     * @return True if the character is dead or False if the character is still alive.
     */
    public boolean isDead() {
        return hp == 0;
    }

    
    /**
     *  Used to implement enemy scaling, increases the value of the enemyScaling variable. 
     *  This is multiplied by the enemies' base stat values to implement scaling.
     */
    public static void scaleEnemies() {
        enemyScaling += 0.3;
    }
    
    /**
     *  Used to get the value for scaling enemies. 
     * @return The current value of the enemyScaling variable. 
     */
    public static double getEnemyScaling() {
        return enemyScaling;
    }
    
    /**
     *  Used to print Character objects. 
     * @return Formatted output of name, type, hp, attack, defense. Omits type for enemies. 
     */
    public String toString() {
        if(!(type.equals("Enemy")))
            return String.format("%-15s%-10s%-10d%-10d%-10d", name, type, hp, attack, defense);
        else
            return String.format("%-10s%-10s%-10s%-10s", name, hp, attack, defense);
    }

}
