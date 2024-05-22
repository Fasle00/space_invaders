public class PlayerProjectile extends Projectile {
    /**
     * The starting position for the players projectiles
     * This variable is universal for all the players projectiles (statically bound)
	 * @see #PlayerProjectile(int)
     */
    public static int startY = 0;

    /**
     * Creates a new projectile centered on x
     * @param x the x position that the projectile is centered on
     */
    public PlayerProjectile(int x) {
        super(x, startY - Projectile.HEIGHT, -1);
    }
}
