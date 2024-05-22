import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * This class represents the enemy in the game.
 * for the time being, the enemy is a rectangle that moves left and right.
 * the plan is to make this a boilerplate for all enemies in the game.
 */
public class Enemy extends Entity {

    /**
     * The width of all enemies.
	 * <p> This variable is final and can not be changed.
     */
    private static final int WIDTH = 60;

    /**
     * The height of all enemies.
	 * <p> This variable is final and can not be changed.
     */
    private static final int HEIGHT = 50;


    /**
     * Where the enemy starts at, and bounces on when moving left.
	 * @see #move()
     */
    protected int startX;

    /**
     * The Max x, where the enemy bounces when moving right.
	 * @see #move()
     */
    protected int maxX;


    /**
     * The enemy image
	 * <p> This image is static and is shared between all instances of the enemy class
     */
    private static BufferedImage image = null;

    /**
     * Saves the last time this enemy moved
     */
    long lastMoveTimer = System.currentTimeMillis();

    /**
     * This is where all the enemies projectiles is stored
     */
    public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    /**
     * Instantiates a new Enemy.
     * This enemy is placed at <code>(0,0)</code>
     */
    public Enemy() {
        super(WIDTH,HEIGHT);
        this.vx = 2;
        this.vy = 2;
        try {
            image = ImageIO.read(getClass().getResource("Space_Invaders.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Instantiates a new Enemy placed at <code>(x,y)</code>.
     *
     * @param x the x position
     * @param y the y position
     */
    public Enemy(int x, int y) {
        super(x,y,WIDTH,HEIGHT);
        this.vx = 2;
        this.vy = 2;
        startX = x;
        maxX = x + WIDTH + 20 * vx;
        try {
            image = ImageIO.read(getClass().getResource("Space_Invaders.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Instantiates a new Enemy placed at <code>(x,y)</code> with hp specified by the argument of the same name.
     *
     * @param x the x position
     * @param y the y position
     * @param hp the <code>Enemy</code>'s hp
     */
    public Enemy(int x, int y, int hp) {
        super(x,y,WIDTH,HEIGHT);
        this.vx = 2;
        this.vy = 2;
        this.hp = hp;
        startX = x;
        maxX = x + WIDTH + 20 * vx;
        try {
            image = ImageIO.read(getClass().getResource("Space_Invaders.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the enemy.
     *
     * @param g the Graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x,y,width,height);
        g.drawImage(image,x,y,width,height,null);
    }

    /**
     * Updates the enemy.
     * Makes the enemy move every 500ms.
	 * <p> The enemy has a 2% chance of shooting a projectile
     */
    @Override
    public void update() {
        if (markedForDeletion) return; // checks if the enemy needs to be updated

        long currentTime = System.currentTimeMillis();
        long delta = currentTime - lastMoveTimer;
        if (delta >= 500) {
            move();
            lastMoveTimer = currentTime;
        }
        double shooting = Math.random();
        if (shooting >= 0.99) {
			shoot();
        }

        checkHp();
    }

    /**
     * Moves the Enemy
     */
    @Override
    public void move() {
        if (vx > 0) { // when enemies are moving right
            if (x < maxX) { // if enemies has not moved to their right-most position
                x += vx;
            } else { // when enemies should move down
                y += vy;
                vx *= -1;
            }
        } else { // when enemies are moving left
            if (x > startX) { // if enemies has not moved back to their start x position
                x += vx;
            } else { // when enemies should move down
                y += vy;
                vx *= -1;
            }
        }
    }

    /**
     * This method adds a projectile to the {@link #projectiles} list that is statically bound to the <code>Enemy</code> class.
     * <p>This is used to make the Enemy shoot a projectile</p>
     */
    public void shoot() {
        projectiles.add(new Projectile(x+width/2, y, 2));
    }

}
