package game;

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

    private final static Integer screen_width;
    private final static Integer screen_height;

    static {

        screen_width = 900;
        screen_height = 715;
    }

    static class Player {
        private final Integer player_w;
        private final Integer player_h;
        private Integer player_x;
        private final Integer player_y;
        private final Integer bullet_w;
        private final Integer bullet_h;
        private Integer change_x;
        private final ArrayList<ArrayList<Integer>> bullets = new ArrayList<>();

        public Player() {
            this.player_w = 50;
            this.player_h = 30;
            this.player_x = screen_width / 2 - player_w / 2;
            this.player_y = 630;
            this.bullet_w = 10;
            this.bullet_h = 20;
            this.bullets.add(new ArrayList<>(Arrays.asList(this.player_x + this.player_w / 2 - this.bullet_w / 2, this.player_y)));

        }

        public void drawPlayer(Graphics g) {
            g.setColor(Color.GREEN);
            g.fillRect(this.player_x, this.player_y, this.player_w, this.player_h);
        }

        public void drawBullets(Graphics g) {
            g.setColor(Color.YELLOW);
            for(ArrayList<Integer> number : this.bullets)
            {
                g.fillRect(number.get(0), number.get(1), this.bullet_w, this.bullet_h);
            }
        }

        public void moveBullets() {
            for(ArrayList<Integer> number : this.bullets)
            {
                number.set(1, number.get(1) - 1);
            }
        }

        public void addNewBullets() {
            ArrayList<Integer> number = this.bullets.get(this.bullets.size() - 1);
            if(this.player_y - number.get(1) >= 50)
            {
                this.bullets.add(new ArrayList<>(Arrays.asList(this.player_x + this.player_w / 2 - this.bullet_w / 2, this.player_y)));
            }
        }

        public void deleteExtraBullets() {
            this.bullets.removeIf(number -> number.get(1) < -this.bullet_h);
        }

    }


    private final Player player = new Player();
    private final Timer t = new Timer(5, this);

    public Main() {
        t.start();
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        Main game = new Main();

        frame.add(game);
        frame.addKeyListener(game);

        frame.setBounds(200, 20, screen_width, screen_height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.player.drawPlayer(g);
        this.player.drawBullets(g);
        this.player.moveBullets();
        this.player.addNewBullets();
        this.player.deleteExtraBullets();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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

    }
}
