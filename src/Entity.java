import java.awt.*;

/**
 * This is the base-class that all moving things grows on.
 */
public class Entity extends Rectangle{
    /**
     * The speed and direction variable for x-axis movement.
     * <p>A positive value moves the <code>Entity</code> right and a negative moves left.</p>
     * @see #move()
     * @see #getVx()
     * @see #setVx(int)
     */
    public int vx = 0;
    /**
     * The speed and direction variable for y-axis movement.
     * <p>A positive value moves the <code>Entity</code> down and a negative moves up</p>
     * @see #move()
     * @see #getVy()
     * @see #setVy(int)
     */
    protected int vy = 0;

    /**
     * The health of the <code>Entity</code>.
     * <p>Default value is 1</p>
     */
    protected int hp = 1;

    /**
     * This variable is used to filter out <code>Entity</code>'s that should be removed at the end of each update cycle in the games <code>Engine</code>.
     * <p>Marking this variable as {@code true} makes the <code>Engine</code> think this <code>Entity</code> is "dead".</p>
     */
    protected boolean markedForDeletion = false;

    /**
     * Constructs a new <code>Entity</code> at {@code (0,0)} with a width and height of 0.
     */
    public Entity() {
        this(0,0,0,0);
    }

    /**
     * Constructs a new <code>Entity</code> at {@code (0,0)} with a width and height of 0.
     * <p>This <code>Entity</code>'s hp is specified by the argument of the same name</p>
     * @param hp the <code>Entity</code>'s health.
     */
    public Entity(int hp) {
        this(0,0,0,0);
        this.hp = hp;
    }

    /**
     * Constructs a new <code>Entity</code> whose upper-left corner is specified as {@code (0,0)}
     * and whose width and height are specified by the arguments of the same name.
     * @param width the width of the <code>Entity</code>.
     * @param height the height of the <code>Entity</code>.
     */
    public Entity(int width, int height) {
        this(0,0,width,height);
    }

    /**
     * Constructs a new <code>Entity</code> whose upper-left corner is specified as {@code (x,y)}
     * and whose width and height are specified by the arguments of the same name.
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @param width the width of the <code>Entity</code>
     * @param height the height of the <code>Entity</code>
     */
    public Entity(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    /**
     * Constructs a new <code>Entity</code> whose upper-left corner is specified as {@code (x,y)}
     * and whose width, height and hp are specified by the arguments of the same name.
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @param width the width of the <code>Entity</code>
     * @param height the height of the <code>Entity</code>
     * @param hp the hp of the <code>Entity</code>
     */
    public Entity(int x, int y, int width, int height, int hp) {
        this(x,y,width,height);
        this.hp = hp;
    }

    /**
     * Draw the <code>Entity</code>'s borders.
     * @param g the <code>Graphics</code> context.
     */
    public void draw(Graphics g) {
        g.drawRect(x,y,width,height);
    }

    /**
     * Updates the <code>Entity</code>.
     * <p>This checks if it is "alive", if so then move according to {@link #vx} and {@link #vy}</p>
     */
    public void update() {
        if (markedForDeletion) return;
        move();

        checkHp();
    }

    /**
     * Checks if the {@code hp} is over 0
     * <p>If {@code hp} is lower or equal to 0 it sets the {@link #markedForDeletion} flag to true</p>
     * @see #hp
     * @see #markForDeletion()
     */
    protected void checkHp() {
        if (hp <= 0) markForDeletion();
    }

    /**
     * Moves the <code>Entity</code> according to the {@code vx} and {@code vy} variables
     * @see #vx
     * @see #vy
     */
    protected void move() {
        translate(vx,vy);
    }

    /**
     * Returns the {@link #vx} value.
     * @return the speed and direction of the <code>Entity</code> in the x-axis.
     */
    public int getVx() {
        return vx;
    }

    /**
     * Sets the speed and direction of the <code>Entity</code> in the x-axis.
     * <p>A positive value for moving right and negative value for moving left.</p>
     * @param vx the speed and direction on the x-axis.
     * @see #vx
     */
    public void setVx(int vx) {
        this.vx = vx;
    }

    /**
     * Returns the {@link #vy} value.
     * @return the speed and direction of the <code>Entity</code> in the y-axis.
     */
    public int getVy() {
        return vy;
    }

    /**
     * Sets the speed and direction of the <code>Entity</code> in the y-axis.
     * <p>A positive value for moving down and negative value for moving up.</p>
     * @param vy the speed and direction on the y-axis.
     * @see #vy
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

    /**
     * This method sets the {@code markedForDeletion} flag to {@code true}.
     * <p>This will make the engine delete this entity at the end of the update cycle</p>
     */
    public void markForDeletion() {
        markedForDeletion = true;
    }
}