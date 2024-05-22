import java.awt.*;

public class Projectile extends Entity {
    /**
     * The width of projectiles, this variable can not be changed at any other place
     */
    public static final int WIDTH = 16;
    /**
     * The height of projectiles, this variable can not be changed at any other place
     */
    public static final int HEIGHT = 64;
    /*
    /**
     * vy represents the speed variable for the projectiles
	 * @see #getVy()
	 * @see #setVy(int)
     */
    //protected int vy;
/*
    /**
     * This variable is used to filter out projectiles that should be removed at the end of each update cycle in the game <code>Engine</code>
     */
    //protected boolean markedForDeletion = false;

    /**
     * Constructor that makes a new projectile at (0,0)
	 * <p> The speed and direction of the projectile is set by vy
	 * <p> The width and height of the projectile is final and set by WIDTH and HEIGHT
     */
    public Projectile() {
        super(WIDTH,HEIGHT);
    }

    /**
     * Makes a new projectile at (0,0)
	 * <p> The speed and direction of the projectile is set by vy
	 * <p> The width and height of the projectile is final and set by WIDTH and HEIGHT
     * @param vy speed and direction of the projectile
     */
    public Projectile(int vy) {
        super(WIDTH,HEIGHT);
        this.vy = vy;
    }

    /**
     * Makes a new projectile that is centered at x and starts at y width the speed and direction vy
	 * <p> The width and height of the projectile is final and set by WIDTH and HEIGHT
     * @param x the x position that the projectile is centered on
     * @param y start y position of the projectile
     * @param vy speed and direction of the projectile
     */
    public Projectile(int x, int y, int vy) {
        super(x - WIDTH/2,y,WIDTH,HEIGHT);
        this.vy = vy;
    }

    /**
     * Draws the projectile
     * @param g the graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(x,y,WIDTH,HEIGHT);
    }

    /**
     * Adds vy to y to update the position
     */
    public void update() {
        move();
    }

    /**
     * Returns the vy variable
     * @return vy (int)
     */
    public int getVy() {
        return vy;
    }

    /**
     * @param vy controls the speed and direction of the projectile
     */
    public void setVy(int vy) {
        this.vy = vy;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public void setMarkedForDeletion(boolean markedForDeletion) {
        this.markedForDeletion = markedForDeletion;
    }

    public void markForDeletion() {
        markedForDeletion = true;
    }
}
