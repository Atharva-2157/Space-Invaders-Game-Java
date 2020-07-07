package game;

// Basic imports required for this game
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;

public class Main extends JPanel implements KeyListener, ActionListener{

//    Basic screen resolution
    private final static Integer screen_width;
    private final static Integer screen_height;

    static {

        screen_width = 900;
        screen_height = 715;
    }

    // Player plane class
    static class Player {
        private final Integer player_w;    // width of plane
        private final Integer player_h;    // height of plane
        private Integer player_x;          // X position of plane
        private final Integer player_y;    // Y position of plane
        private final Integer bullet_w;    // bullet width
        private final Integer bullet_h;    // bullet height
        private Integer change_x;          // It handle the movement of plane
        private Integer life;
        // Bullets of the player plane
        private final ArrayList<ArrayList<Integer>> bullets = new ArrayList<>();

        public Player() {
            this.player_w = 50;
            this.player_h = 30;
            this.player_x = screen_width / 2 - player_w / 2;
            this.player_y = 600;
            this.bullet_w = 10;
            this.bullet_h = 20;
            this.change_x = 0;
            this.life = 10;
            // adding bullet in the bullets list
            this.bullets.add(new ArrayList<>(Arrays.asList(this.player_x + this.player_w / 2 - this.bullet_w / 2, this.player_y)));

        }

        // This function will draw the plane on the screen
        public void drawPlayer(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillRect(this.player_x, this.player_y, this.player_w, this.player_h);
        }

        // This function will draw the bullets on the screen
        public void drawBullets(Graphics g) {
            g.setColor(Color.YELLOW);
            for(ArrayList<Integer> number : this.bullets)
            {
                g.fillRect(number.get(0), number.get(1), this.bullet_w, this.bullet_h);
            }
        }

        // This function will move bullets in upward direction
        public void moveBullets() {
            for(ArrayList<Integer> number : this.bullets)
            {
                number.set(1, number.get(1) - 2);
            }
        }

        // This function will load new bullet in plane to fire
        public void addNewBullets() {
            ArrayList<Integer> number = this.bullets.get(this.bullets.size() - 1);
            if(this.player_y - number.get(1) >= 50)
            {
                this.bullets.add(new ArrayList<>(Arrays.asList(this.player_x + this.player_w / 2 - this.bullet_w / 2, this.player_y)));
            }
        }

        // This function will delete those bullets which are goes out of the screen
        public void deleteExtraBullets() {
            this.bullets.removeIf(number -> number.get(1) < -this.bullet_h);
        }

        // This function draws the health bar of player
        public void drawHealthBar(Graphics g)
        {
            g.setColor(Color.GREEN);
            g.fillRect(this.player_x, this.player_y + 40, this.player_w, 10);

            g.setColor(Color.RED);
            g.fillRect(this.player_x, this.player_y + 40, (10 - this.life) * 5, 10);
        }
    }

    static class Enemy
    {
        private final Integer enemy_w;    // width of enemy
        private final Integer enemy_h;    // height of enemy
        private final Integer enemy_x;          // X position of enemy
        private Integer enemy_y;          // Y position of enemy
        private final Integer bullet_w;   // bullet width
        private final Integer bullet_h;   // bullet height
        private final ArrayList<ArrayList<Integer>> bullets = new ArrayList<>();


        public Enemy()
        {
            this.enemy_w = 25;
            this.enemy_h = 25;
            this.enemy_x = ThreadLocalRandom.current().nextInt(1, screen_width - this.enemy_w);
            this.enemy_y = -enemy_h - 10;
            this.bullet_w = 10;
            this.bullet_h = 10;
        }

        public void drawEnemy(Graphics g)
        {
            g.setColor(Color.RED);
            g.fillRect(this.enemy_x, this.enemy_y, this.enemy_w, this.enemy_h);
        }

        public void drawBullets(Graphics g) {
            g.setColor(Color.YELLOW);
            for(ArrayList<Integer> number : this.bullets)
            {
                g.fillRect(number.get(0), number.get(1), this.bullet_w, this.bullet_h);
            }
        }

        public void moveEnemy()
        {
            this.enemy_y += 1;
        }

        // This function will move bullets in downward direction
        public void moveBullets() {
            for(ArrayList<Integer> number : this.bullets)
            {
                number.set(1, number.get(1) + 2);
            }
        }

        // This function will load new bullet in plane to fire
        public void addNewBullets() {
            ArrayList<Integer> number = this.bullets.get(this.bullets.size() - 1);
            if(number.get(1) - this.enemy_y > ThreadLocalRandom.current().nextInt(220, 250))
            {
                this.bullets.add(new ArrayList<>(Arrays.asList(this.enemy_x + this.enemy_w / 2 - this.bullet_w / 2, this.enemy_y + this.enemy_w - this.bullet_w)));
            }
        }

        // This function will delete those bullets which are goes out of the screen
        public void deleteExtraBullets() {
            this.bullets.removeIf(number -> number.get(1) > screen_height);
        }
    }

    // This is the player plane object
    private final Player player = new Player();
    // this is the Enemy plane object
    private final ArrayList<Enemy> enemy = new ArrayList<>();

    public Main() {
        Timer t = new Timer(5, this);
        t.start();
    }

    // This is the main method
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");  // screen object
        Main game = new Main();

        frame.add(game);                // adding game component in frame
        frame.addKeyListener(game);     // adding key listener in frame

        // some required functions
        frame.setBounds(200, 20, screen_width, screen_height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void addNewEnemy()
    {
        Enemy e = this.enemy.get(this.enemy.size() - 1);
        if(e.enemy_y > ThreadLocalRandom.current().nextInt(50, 100))
        {
            this.enemy.add(new Enemy());
        }
    }

    public void atLeastOneEnemy()
    {
        if(this.enemy.size() <= 0)
            this.enemy.add(new Enemy());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.player.drawPlayer(g);
        this.player.drawBullets(g);
        this.player.drawHealthBar(g);
        this.player.moveBullets();
        this.player.addNewBullets();
        this.player.deleteExtraBullets();

        this.atLeastOneEnemy();

        for(Enemy e : enemy)
        {
            e.drawEnemy(g);
            e.drawBullets(g);
            e.moveBullets();
            e.moveEnemy();
            e.addNewBullets();
            e.deleteExtraBullets();
        }
        this.addNewEnemy();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.player.player_x < 0)
        {
            this.player.player_x = 0;
            this.player.change_x = 0;
        }

        if(this.player.player_x > screen_width - this.player.player_w - 15)
        {
            this.player.player_x = screen_width - this.player.player_w - 15;
            this.player.change_x = 0;
        }

        this.player.player_x += this.player.change_x;

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if(c == KeyEvent.VK_RIGHT)
        {
            this.player.change_x = 2;
        }
        if(c == KeyEvent.VK_LEFT)
        {
            this.player.change_x = -2;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.player.change_x = 0;
    }
}
