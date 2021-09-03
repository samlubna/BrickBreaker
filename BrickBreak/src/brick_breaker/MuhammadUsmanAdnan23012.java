package brick_breaker;

import com.assignment3.game_objects.*;

import javax.swing.*;

// Entry point for the application
public class MuhammadUsmanAdnan23012 extends JFrame  {

    MuhammadUsmanAdnan23012 ()  {
        // Add the Jpanel game board
        add(new GameBoard());
        // Set the dimensions of the Jframe
        setSize(Constants.BOARD_WIDTH,Constants.BOARD_HEIGHT);
        // Set the title
        setTitle("Brick Breaker");
        // What operation to close on
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Null as nothing relative to this Jframe
        setLocationRelativeTo(null);
        // Display the Jframe
        setVisible(true);
    }
    public static void main(String [] args) {

        // Calling the JFrame
        MuhammadUsmanAdnan23012 ml = new MuhammadUsmanAdnan23012();


    }

}