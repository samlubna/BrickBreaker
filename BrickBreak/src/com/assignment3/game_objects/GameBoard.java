package com.assignment3.game_objects;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;

public class GameBoard extends JPanel implements ActionListener {

    // This panel's dimensions
    private final int width = Constants.BOARD_WIDTH;
    private final int height = Constants.BOARD_HEIGHT;

    /* The row and column variables which determine how much space is provided for the tile matrix */
    private final int tile_rows = 7;
    private final int tile_columns = 14;

    /*
    The start and enter variables act as control managers to assist in state transitions:
    Win - > Lose, Lose - > Win ...
    * */
    private boolean start = false;
    private boolean enter = false;

    // The delay between each frame
    private final int DELAY = 18;

    // A timer for controlling the panel's update frequency
    private Timer timer;

    // The paddle object. This is a singleton
    private Paddle p;

    // The ball object
    private Ball b;

    // A list for storing and accessing the blocks
    private LinkedList<BlockObject> bl;

    // A tile generator for randomly acquiring new block formations
    private TileGenerator t = new TileGenerator(tile_rows,tile_columns);

    // The player's level and score
    private int score = 0, level = 1;

    // The heads-up display for cycling through all the messages on this panel
    private JLabel jl= new JLabel(getMessages(1));

    // The three borders
    Line2D top = new Line2D.Double(0,Constants.BLOCK_HEIGHT,Constants.BOARD_WIDTH-1,Constants.BLOCK_HEIGHT);
    Line2D left = new Line2D.Double(0,Constants.BLOCK_HEIGHT,0,Constants.BOARD_HEIGHT);
    Line2D right = new Line2D.Double(Constants.BOARD_WIDTH-1,Constants.BLOCK_HEIGHT,
            Constants.BOARD_WIDTH-1,Constants.BOARD_HEIGHT);

    // The board's constructor
    public GameBoard () {
        // The background color
        setBackground(Color.black);
        // Allow event dispatching
        setFocusable(true);
        // Set the dimensions of the panel
        setPreferredSize(new Dimension(width,height));
        // Initiate the paddle
        p = Paddle.getInstance();
        // Position the paddle
        p.moveTo(width/2-p.getWidth()/2,height-p.getWidth()/2);
        // Initiate the ball
        b = new Ball(0,0,Constants.BALL_WIDTH,Constants.BALL_HEIGHT);
        // Move the ball on top of the paddle
        b.moveTo(p.getX() + p.getWidth() / 2 - Constants.BALL_WIDTH / 2,
                p.getY() - Constants.BALL_HEIGHT - 2);
        // Adding a mouse listener for controlling the paddle
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // The paddle's move function
                p.mouseDragged(e);
                /*
                The ball is stuck to the paddle when a new level starts, or when it respawns.
                It is only removed after the space key is pressed.
                * */
                if (!start) {
                    // Stick the ball onto the paddle at the start
                    b.moveTo(
                            p.getX() + p.getWidth() / 2 - b.getWidth() / 2 + 3,
                            p.getY() - b.getHeight() - 2
                    );
                }
            }
        });

        // Setting the color of the text inside the label/display
        jl.setForeground(Color.white);
        // Setting the font and text size
        jl.setFont(new Font("Helvetica Neue", Font.BOLD, 25));
        // Setting the background color
        jl.setBackground(Color.black);
        // Making the display opaque so that longer messages don't get obstructed
        jl.setOpaque(true);
        // Adding the display/label to the panel
        add(jl);
        // Getting the initial tiles
        bl = t.getTiles();
        // Adding a key listener for the enter and space inputs
        addKeyListener(new Start());
        // Initializing the timer
        timer = new Timer(DELAY,this);
        // Starting the timer
        timer.start();
    }

    // The actual drawing function which gets called every time the panel "updates"
    public void paintComponent (Graphics g) {
        // reset the background color
        g.setColor(getBackground());
        // replace the entire screen with the aforementioned color
        g.fillRect(0,0,width,height);
        // Get the current graphics context
        Graphics2D g2 = (Graphics2D) g;
        // Drawing the paddle
        p.paint(g2);
        // Drawing the ball
        b.paint(g2);
        // Drawing the block
        for (GameObject bp:bl) {
            bp.paint(g2);
        }
        // Giving the borders some thickness and drawing them
        g2.setStroke( new BasicStroke(3));
        g2.setColor(Color.red);
        g2.draw(top);
        g2.draw(left);
        g2.draw(right);
        // Synchronize graphic states
        Toolkit.getDefaultToolkit().sync();

    }

    /*
    * This method is tied to the timer. Whenever the delay ends, the code snippet inside of this function
    * gets executed. So if any event-based activities are required after the delay, they're added here.
    * */
    @Override
    public void actionPerformed(ActionEvent e) {
        /*The start keyword ensures that any and all movement, except the paddle,
        is done after the space key is pressed */
        if (start) {
            // Checking for collisions between the ball and the blocks
            for (int i = 0; i < bl.size(); i++) {
                BlockObject temp = bl.get(i);
                if (temp.getBound().intersects(b.getBound())) {
                    b.collision(temp.getBound());
                    // Breaking after one collision for consistency
                    if (temp.getHits() == 0) {
                        bl.remove(i);
                        score += 1;
                        jl.setText(getMessages(3));
                    }
                    break;
                }
            }
            // Moving the ball
            b.move(p);
        }
            // Repaint the components
            repaint();
        // Check whether the player lost
        if (Constants.PLAYER_LOST) {
            // Block all movement for the ball
            start = false;
            // Check if all lives have expired
            if (Constants.PLAYER_LIFE == 0) {
                // Show the relevant message
                jl.setText(getMessages(2));
                // Stop the timer, and hence all activity
                timer.stop();
            }
            else {
                // Some lives left. Display the relevant message
                jl.setText(getMessages(3));
                // Reset only the paddle and the ball's positions
               Reset();
               // Change the Player_Lost condition to false as some lives remain
               Constants.PLAYER_LOST = false;
            }
        }
        // If all blocks were destroyed
        else if (bl.size() == 0) {
            // Move to the next level
            level+=1;
            // Update the message
            jl.setText(getMessages(3));
            // Get new tiles
            bl = t.getTiles();
            // Reset the paddle and the ball
            Reset();
        }
    }

    // The key adapter class for registering user inputs
    private class  Start extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            // If the space key is pressed
            if (key == KeyEvent.VK_SPACE && !start && Constants.PLAYER_LIFE > 0) {
                // Release the ball
                start = true;
                jl.setText(getMessages(3));
            }
            // The condition for when the user has lost all loves
            else if (key == KeyEvent.VK_ENTER && !enter && Constants.PLAYER_LIFE == 0) {
                // Enter was pressed. It acts as a semaphore to enact further changes in the reset method
                enter = true;
                // Call the reset
                Reset();
                // Reset the enter to it's initial state
                enter =false;
                // Restart the timer, and, in turn, all activities
                timer.start();
            }
        }
    }

    // The reset function itself
    private void Reset () {
        // Move the paddle and the ball to their initial positions.
        p.moveTo(width/2-p.getWidth()/2,height-p.getWidth()/2);
        b.moveTo(p.getX() + p.getWidth() / 2 - Constants.BALL_WIDTH / 2,
                p.getY() - Constants.BALL_HEIGHT - 2);
        // Reset the ball's speed
        b.resetSpeed();
        // Wait for the user to press start again
        start = false;
        // If enter was pressed, then call the following as well
        if (enter) {
            Constants.PLAYER_LOST = false;
            Constants.PLAYER_LIFE = 3;
            score = 0; level = 1;
            jl.setText(getMessages(3));
            bl = t.getTiles();
        }
    }

    // A function with all the messages that appear on the display/label. Used HTML for some editing wiggle room
    private String getMessages (int ch) {
        if (ch == 1)
        return "Press the space bar key to start";
        else if (ch == 2)
            return "<html><center>You Lost. Press Enter to restart<br/>Score - " + score + "&nbsp;&nbsp;&nbsp;" +
                    "Level - "+level+"</center></html>";
        else
            return "<html><center>Score - " + score + "&nbsp;&nbsp;&nbsp;" +
                    "Lives - " + Constants.PLAYER_LIFE + "&nbsp;&nbsp;&nbsp;Level - "+level+"</center></html>";
    }
}
