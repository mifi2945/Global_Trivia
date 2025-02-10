/**
 * Mikhail Filippov
 * 02/10/2025
 * Player class for storing player information
 */

package filippovm;

import java.util.Objects;

public class Player {
    private String name;
    private int id;
    private int score;

    public Player(String name) {
        this.name = name;
        this.id = Objects.hashCode(name);
        this.score = 0;
    }
    public Player(String name, int id) {
    }
}
