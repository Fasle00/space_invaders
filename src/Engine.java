import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * This class represents the game engine.
 */
public class Engine extends Canvas implements Runnable {
	/**
	 * The width of the game window.
	 */
    public static int WIDTH = 900;
	/**
	 * The height of the game window.
	 */
    public static int HEIGHT = 600;
	/**
	 * The title of the game window.
	 * This variable is final because it should not be changed.
	 */
    public static String title = "Space Invaders!";
	/**
	 * The icon of the game window.
	 */
    public static BufferedImage icon;

	/**
	 * The buffer strategy of the game.
	 * This variable is used to render the game.
	 */
    private BufferStrategy bs;
	/**
	 * The updates per second of the game.
	 */
    private final int ups = 120;
	/**
	 * The running variable of the game.
	 * This variable is used to determine if the game is running or not.
	 */
    private boolean running = false;
	/**
	 * The thread of the game.
	 */
    private Thread thread;
    /**
     * The games frame
     */
    private final JFrame frame;

	/**
	 * The list of enemies in the game.
	 */
    private final ArrayList<Enemy> enemies = new ArrayList<>();
	/**
	 * The player in the game.
	 */
    Player player = new Player(WIDTH,HEIGHT);

    /**
     * This variable is the last time the game was updated.
     */
    long lastTime = System.currentTimeMillis();

	/**
	 * This method draws the game.
	 * @param g the Grafics context
	 */
    public void draw(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight()); // Clear the screen
        setBackground(g); // Set the background color

        // checks if the player is defeated (hp <= 0) if so draw the game over screen and then return
        if (player.isMarkedForDeletion()){
            drawGameOver(g);
            return;
        }

        player.draw(g); // Draw the player
        for (Enemy enemy :
                enemies) {
            enemy.draw(g);
        } // Draw the enemies
        drawProjectiles(g);

        //drawPlayerProjectiles(g);
    }

    /**
     * Draw the "Game over" screen
     * @param g the <code>Graphics</code> context.
     */
    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("game over", WIDTH/2 - 60, HEIGHT / 2 - 20);
    }

    /**
     * Draw all projectiles on screen.
     * @param g the <code>Graphics</code> context
     */
    private void drawProjectiles(Graphics g) {
        if (Player.projectiles.size() != 0){
            for (PlayerProjectile projectile :
                    Player.projectiles) {
                projectile.draw(g);
            }
        } // Draw the players projectiles if there is any

        if (Enemy.projectiles.size() != 0) {
            for (Projectile projectile :
                    Enemy.projectiles) {
                projectile.draw(g);
            }
        } // Draw enemies projectiles if there is any
    }

    private void drawPlayerProjectiles(Graphics g) {
        if (Player.projectiles.size() == 0) return;

        for (int i = 0; i < Player.projectiles.size(); i++) {
            g.drawString(Player.projectiles.get(i).toString(),100, 50 + 20 * i);
        }
        player.markedForDeletion = false;
    }

	/**
	 * This method sets the background color.
	 * @param g the <code>Graphics</code> context.
	 */
    private void setBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);
    }


	/**
	 * This metod updates the game and all the objects in it.
	 */
    private void update() {
        if (player.isMarkedForDeletion()) return;

        checkProjectiles();

        player.update();

        for (Enemy enemy :
                enemies) {
            enemy.update();
        }
        for (Projectile projectile :
                Enemy.projectiles) {
            projectile.update();
        }
        for (PlayerProjectile projectile :
                Player.projectiles) {
            projectile.update();
        }
        filterProjectiles();
        filterEnemies();
    }
/*
    private void checkEnemyProgression() {
        for (Enemy e :
                enemies) {
            if ()
        }
    }
*/
    private void projectileOutOfBounds(Projectile p) {
        if (p.y > HEIGHT || p.y + p.height <= 0) p.markForDeletion();
    }

    // done
    private void filterEnemies() {
        if (enemies.size() == 0) return;

        for (Enemy e :
                enemies) {
            e.checkHp();
        }

        enemies.removeIf(Enemy::isMarkedForDeletion);
    }

    // done
    private void filterProjectiles() {
        if (Enemy.projectiles.size() > 0) {
            Enemy.projectiles.removeIf(Projectile::isMarkedForDeletion);
        }

        if (Player.projectiles.size() > 0) {
            Player.projectiles.removeIf(Projectile::isMarkedForDeletion);
        }
    }

    private void checkProjectiles() {
        if (Player.projectiles.size() == 0 && Enemy.projectiles.size() == 0) return;

        for (PlayerProjectile p : Player.projectiles) {
            for (Enemy e : enemies) {
                projectileHit(p,e);
            }
            for (Projectile ep : Enemy.projectiles) {
                checkProjectile(p,ep);
            }
            projectileOutOfBounds(p);
        }

        for (Projectile p : Enemy.projectiles) {
            if (p.intersects(player)){
                p.markForDeletion();
                player.hp--;
            }
            projectileOutOfBounds(p);
        }
    }

    private void projectileHit(Projectile p, Enemy e) {
        if (p.isMarkedForDeletion() || e.isMarkedForDeletion()) return;

        if (p.intersects(e)) {
            e.hp--;
            p.markForDeletion();
        }
    }

    /**
     * This function checks if two projectiles intersects each other
     * <p>If {@code p1} intersects {@code p2} both get their flag {@code markedForDeletion} set to true</p>
     * @param p1 projectile 1
     * @param p2 projectile 2
     */
    private void checkProjectile(Projectile p1, Projectile p2) {
        if (p1.isMarkedForDeletion() || p2.isMarkedForDeletion()) return;

        if (p1.intersects(p2)) {
            p1.markForDeletion();
            p2.markForDeletion();
        }
    }

	/**
	 * This is the constructor of the game engine.
	 */
    public Engine() {
        PlayerProjectile.startY = HEIGHT - 70;
        enemies.add(new Enemy(10,10));
        enemies.add(new Enemy(60,10));

        try {
            icon = ImageIO.read(getClass().getResource("Space_Invaders.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSize(WIDTH, HEIGHT);
        frame = new JFrame(title);
        frame.setIconImage(icon);
        frame.add(this);
        frame.addKeyListener(new MyKeyListener());
        this.addMouseListener(new MyMouseListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.requestFocus();
        frame.setVisible(true);
    }

	/**
	 * This method renders the game.
	 */
    public void render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        draw(g);

        g.dispose();
        bs.show();
    }

	/**
	 * This method starts the game.
	 */
    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

	/**
	 * This method stops the game.
	 */
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	/**
	 * This method runs the game.
	 */
    public void run() {
        double ns = 1000000000.0 / ups;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                // Uppdatera koordinaterna
                update();
                // Rita ut bilden med updaterad data
                render();
                delta--;
            }
        }
        stop();
    }


	/**
	 * This class is the games keyboard listener.
	 * <p> This is where all the inputs are handeld
	 */
    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyCode() == 39){

            } // Right arrow
            if (e.getKeyCode() == 37){

            } // Left arrow

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyCode());
            if (e.getKeyCode() == 39 || e.getKeyCode() == 68){
                System.out.println("right");
                player.setVx(5);
            } // Right arrow
            if (e.getKeyCode() == 37 || e.getKeyCode() == 65){
                System.out.println("left");
                player.setVx(-5);
            } // Left arrow
            if (e.getKeyCode() == 32){
                player.shoot();
            } // Pause (space)
            if (e.getKeyCode() == 10) {

            } // enter
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
                System.out.println("left up");
                player.setVx(0);
            }
            if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
                System.out.println("right up");
                player.setVx(0);
            }
        }

    }

    /**
     * This is just to get focus to the frame after you have clicked outside the frame
     */
    private class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            frame.requestFocus();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}


