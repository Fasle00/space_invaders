import java.awt.*;
import java.util.ArrayList;

/**
 * This class represents the player in the game.
 * The player is a rectangle that can move left and right.
 * The player can shoot projectiles.
 */
public class Player extends Entity {
	/**
	 * This variable is the width of the game.
	 * It is used to keep the player within the bounds of the game.
	 * It is final because it should not be changed.
	 * @see #update()
	 */
    private final int GAME_WIDTH;



	/**
	 * This variable is a list of all the player's projectiles.
	 * @see #shoot()
	 */
    public static ArrayList<PlayerProjectile> projectiles = new ArrayList<>();

/**
 * This constructor creates a new player object with the given game width and height.
 * The player is created at the bottom of the screen in the middle.
 * The player's projectile's starting y position is set to the player's y position.
 * @param gameWidth The width of the game.
 * @param gameHeight The height of the game.
 */
    public Player(int gameWidth, int gameHeight) {
        super(gameWidth/2 - 50, gameHeight - 70, 100,50, 3);
        PlayerProjectile.startY = gameHeight - 70;
        GAME_WIDTH = gameWidth;
    }

/**
 * This method updates the player's position based on the velocity of the player.
 */
    public void update(){
        if (this.x + width >= GAME_WIDTH && this.vx > 0) {
            //System.out.println("right bound");
            this.x = GAME_WIDTH - width;
        } else if (this.x <= 0 && this.vx <= 0) {
            //System.out.println("left bound");
            this.x = 0;
        } else {
            move();
        }

        checkHp();
    }

/**
 * Draws the <code>Player</code> blue on the screen.
 * <p>This method changes the <code>Graphics</code>'s color to blue and then back to the last color</p>
 * @param g The <code>Graphics</code> context.
 */
@Override
    public void draw(Graphics g){
        Color lastColor = g.getColor();
        g.setColor(Color.BLUE);
        g.fillRect(x,y,width,height);
        g.setColor(lastColor);
    }

/**
 * This method adds a new <code>PlayerProjectile</code> in the {@link #projectiles} list.
 * <p>The projectile's center is aligned with the <code>Player</code>'s center in the x-axis</p>
 */
    public void shoot() {
        projectiles.add(new PlayerProjectile(x + width/2));
    }
}
