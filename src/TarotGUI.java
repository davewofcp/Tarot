/* TarotGUI.java - Interactive Tarot deck with GUI, extension of Tarot class
 * Usage: java TarotGUI
 * ------------------------------------------------------------------------
 *  Copyright (c) 2017 Dave Wollyung, Clifton Park, NY, United States
 *  GUI class created by Oleg Wahl, Germany.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 */
 
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
 
public class TarotGUI extends Tarot
{
    // instance variables
    private static int drawn = 0;
 
    private static JFrame mainWindow = new JFrame("");
 
    private static JPanel outputPanel = new JPanel();
 
    private static JTextArea outputTextArea = new JTextArea("", 5,20);
   
    private static JLabel outputLabel = new JLabel("Your question: [None]");
 
    private static JPanel inputPanel = new JPanel();
    private static JLabel inputLabel = new JLabel("Input: ");
    private static JTextField inputTextfield = new JTextField("");
 
    private static JPanel buttonPanel = new JPanel();
    private static JButton firstButton = new JButton("Ask");
    private static JButton secondButton = new JButton("Draw");
    private static JButton thirdButton = new JButton("Deck");
    private static JButton fourthButton = new JButton("Shuffle");
    private static JButton fifthButton = new JButton("Reversals");
    private static JButton helpButton = new JButton("Help");
 
    /**
     * Constructor for objects of class TarotGUI
     */
 
    public static void main(String[] args){
        //TarotGUI tarotGui = new TarotGUI();  
        //tarotGui.mainMethod();      
        mainWindow = new JFrame("Tarot GUI -- Have a good reading :)");
        mainWindow.setSize(800, 600);
        mainWindow.setLayout(new BorderLayout(10, 10));
        outputPanel.setLayout(new BorderLayout(10, 10));
        inputPanel.setLayout(new BorderLayout(10, 10));
 
        GridLayout myGrid = new GridLayout(3,2);
        myGrid.setHgap(5);
        myGrid.setVgap(5);
        buttonPanel.setLayout(myGrid);
 
        //Output panel
        mainWindow.add(outputPanel, BorderLayout.CENTER);
 
        outputPanel.add(outputTextArea, BorderLayout.CENTER);
        outputPanel.add(outputLabel, BorderLayout.SOUTH);
 
        //Input panel
        mainWindow.add(inputPanel, BorderLayout.SOUTH);
 
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(inputTextfield, BorderLayout.CENTER);
 
        //Button panel (inside inputPanel)
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);
 
        //event listeners on buttons
 
        firstButton.setActionCommand("ask");
        secondButton.setActionCommand("draw");
        thirdButton.setActionCommand("deck");
        fourthButton.setActionCommand("shuffle");
        fifthButton.setActionCommand("rev");
        helpButton.setActionCommand("help");
 
        firstButton.addActionListener(new ButtonClickListener());
        secondButton.addActionListener(new ButtonClickListener());
        thirdButton.addActionListener(new ButtonClickListener());
        fourthButton.addActionListener(new ButtonClickListener());
        fifthButton.addActionListener(new ButtonClickListener());
        helpButton.addActionListener(new ButtonClickListener());
 
        buttonPanel.add(firstButton);
        buttonPanel.add(secondButton);
        buttonPanel.add(thirdButton);
        buttonPanel.add(fourthButton);
        buttonPanel.add(fifthButton);
        buttonPanel.add(helpButton);
 
        //start with the help test displayed
        outputTextArea.setText("Welcome! \nHere's what the buttons do: \n\n"
                    + "Ask: Takes a question from the input and seeds the randomising algorithm with it.\n"
                    + "Draw: Draws the number of cards you give in the input, draws one card without input. \n"
                    + "Deck: Tells you how many cards you have drawn and how many remain in the deck. \n"
                    + "Shuffle: Returns all cards to the deck and generates a new random seed -- this shuffles and removes your question. \n"
                    + "Reversals: Toggles reversals on or off -- off by default. \n"
                    + "Help: Displays this text :)");
        //create a seed and initialise at startup, so the user can draw directly, without an error :)
        random.setSeed(System.currentTimeMillis());
        init();
 
        mainWindow.setVisible(true);
 
        mainWindow.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    System.exit(0);
                }        
            });
    }
 
    private static class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();  
 
            //Here, all the stuff is supposed to happen :)
 
            if( command.equals( "ask" ))  {
 
                try {
                    final String input = inputTextfield.getText();
                    random.setSeed(hash(input) + System.currentTimeMillis()); //This should factor the question into the random seed.
                    outputLabel.setText("Your question: " + input);
                    inputTextfield.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    outputLabel.setText("Error.");
                }
 
            } else if( command.equals( "draw" ) )  {
                int cards = 1;
                if (inputTextfield.getText().equals("")) {
                    cards = 1; // will draw 1 card, even when another number was given on a previous run
                    //outputLabel.setText("Drawing one card"); //works
 
                } else {
                    try {
                        cards = Integer.parseInt(inputTextfield.getText());
                    } catch (Exception ex) {
                        outputLabel.setText("Error. Your input needs to be the number of cards to draw.");
                    }    
                }
 
                if (cards > deck.size()) {
                    outputTextArea.setText("There aren't enough cards in the deck -- you need to shuffle");
                } else {
 
                    // Here comes the complicated part, I think. Copying from TCL and modifying:
                    ArrayList<Card> chosen = new ArrayList<Card>();
 
                    for (int i = 0; i < cards; i++) {
                        int idx = random.nextInt(deck.size());
                        Card card = deck.get(idx);
                        if (ENABLE_REVERSALS) {
                            int rev = random.nextInt(2);
                            if (rev > 0) card.setReversed(true);
                        }
                        chosen.add(card);
                        drawn++;
                        deck.remove(idx);
                    }
 
                    String accumulateOutput = "";
                    accumulateOutput += "Your card"+(cards > 1 ? "s":"")+":\n";
                    for (Card card : chosen) {
                        String rev = (ENABLE_REVERSALS && card.isReversed() ? ", reversed" : "");
                        accumulateOutput += card.getName()+rev+" : "+ BASE_URL+card.getUrl() + "\n";
                    }
 
                    outputTextArea.setText(accumulateOutput);
                }
 
            } else if( command.equals( "deck" ) )  {
 
                outputTextArea.setText("You have drawn "+drawn+" cards \n" + deck.size() +" cards remain in the deck");
                inputTextfield.setText("");
 
            } else if (command.equals("shuffle")) {
                deck.clear();
                init();
                drawn = 0;
                random.setSeed(System.currentTimeMillis()); //Why does this not factor in the question? Well, since it doesn't , I probably should reset it too.
                outputLabel.setText("Your question: [None]");
                outputTextArea.setText("Deck shuffled.");
 
            } else if (command.equals("rev")) {
                if (ENABLE_REVERSALS){
                    ENABLE_REVERSALS = false;
                    outputTextArea.setText("Reversals disabled.");
                } else {
                    ENABLE_REVERSALS = true;
                    outputTextArea.setText("Reversals enabled.");
                }
            } else { //help
                outputTextArea.setText("Welcome! \nHere's what the buttons do: \n\n"
                    + "Ask: Takes a question from the input and seeds the randomising algorithm with it.\n"
                    + "Draw: Draws the number of cards you give in the input, draws one card without input. \n"
                    + "Deck: Tells you how many cards you have drawn and how many remain in the deck. \n"
                    + "Shuffle: Returns all cards to the deck and generates a new random seed -- this shuffles and removes your question. \n"
                    + "Reversals: Toggles reversals on or off -- off by default. \n"
                    + "Help: Displays this text :)");
 
            }          
        }        
    }
 
    //At this time, I don't understand what this does, copied it from TCL -- ah, this converts the question into a randomseed
    public static long hash(String string) {
        long h = 1125899906842597L; // prime
        int len = string.length();
 
        for (int i = 0; i < len; i++) {
            h = 31*h + string.charAt(i);
        }
        return h;
    }
 
}