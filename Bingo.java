package bingo;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Bingo extends JFrame {

    JPanel bingoPane, controlPane;
    static JLabel errorLabel;
    //the array of bingo cards
    static bingoCard[][] cards = new bingoCard[6][6];
    GridBagConstraints gc = new GridBagConstraints();

    public Bingo() {
        layFrame();
        layBingoPane();
        layCards();
        layControlPane();
        repaint();
        //layControlElements();
    }

    public static void main(String[] args) {
        Bingo j = new Bingo();
    }

    //initialize the frame
    public void layFrame() {
        //initialize the frame
        setSize(500, 500);
        setTitle("Bingo");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
    }

    //intialize the bingo plays panel
    public void layBingoPane() {
        gc.insets = new Insets(10, 10, 10, 10);
        bingoPane = new JPanel(new GridLayout(6, 6, 3, 3));
        bingoPane.setBorder(new TitledBorder("Bingo board"));
        bingoPane.setBackground(Color.red);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 8;
        gc.weighty = 8;
        add(bingoPane, gc);
    }

    //lay the cards on the bingo plays panel
    public void layCards() {
        for (int i = 0, cardIndex = 1, numberIndex; i < cards.length; i++) {
            numberIndex = 1;
            for (int c = 0; c < cards[0].length; c++, cardIndex++) {
                switch (cardIndex) {
                    case 1:
                        cards[i][c] = new bingoCard("");
                        break;
                    case 2:
                        cards[i][c] = new bingoCard("B");
                        break;
                    case 3:
                        cards[i][c] = new bingoCard("I");
                        break;
                    case 4:
                        cards[i][c] = new bingoCard("N");
                        break;
                    case 5:
                        cards[i][c] = new bingoCard("G");
                        break;
                    case 6:
                        cards[i][c] = new bingoCard("O");
                        break;
                    case 7:
                        cards[i][c] = new bingoCard("P");
                        break;
                    case 13:
                        cards[i][c] = new bingoCard("L");
                        break;
                    case 19:
                        cards[i][c] = new bingoCard("A");
                        break;
                    case 22:
                        cards[i][c] = new bingoCard("FREE");
                        numberIndex += 15;
                        break;
                    case 25:
                        cards[i][c] = new bingoCard("Y");
                        break;
                    case 31:
                        cards[i][c] = new bingoCard("S");
                        break;
                    default:
                        cards[i][c] = new bingoCard(numberIndex);
                        numberIndex += 15;
                }
                bingoPane.add(cards[i][c]);
            }
        }
    }

    //lay the control panel with the fields and labels inside
    public void layControlPane() {
        gc.gridx = 1;
        gc.gridy = 0;
        gc.weightx = 2;
        gc.weighty = 8;
        controlPane = new JPanel(new GridBagLayout());
        controlPane.setBackground(Color.yellow);
        controlPane.setBorder(new TitledBorder("Controls"));
        add(controlPane, gc);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.anchor = GridBagConstraints.FIRST_LINE_START;
        g.fill = GridBagConstraints.BOTH;

        g.gridx = 0;
        g.gridy = 0;
        errorLabel = new JLabel("There is no error yet");
        errorLabel.setForeground(Color.red);
        controlPane.add(errorLabel, g);
        
        g.gridy++;
        JLabel num = new JLabel("The number");
        controlPane.add(num, g);

        g.gridy++;
        JLabel action = new JLabel("Action");
        controlPane.add(action, g);

        g.gridy++;
        JLabel location = new JLabel("Location");
        controlPane.add(location, g);

        g.gridy++;
        JLabel row = new JLabel("In row");
        controlPane.add(row, g);

        g.gridy++;
        JLabel column = new JLabel("In column");
        controlPane.add(column, g);

        g.gridy++;
        JLabel square = new JLabel("In square");
        controlPane.add(square, g);

        g.anchor = GridBagConstraints.CENTER;
        g.gridy++;
        JButton enter = new JButton("Enter");
        controlPane.add(enter, g);

        g.gridy++;
        JButton undo = new JButton("Undo");
        controlPane.add(undo, g);


        //set things
        g.anchor = GridBagConstraints.LINE_START;
        g.gridx = 1;
        g.gridy = 1;
        JTextField numField = new JTextField(5);
        controlPane.add(numField, g);

        g.gridy++;
        String[] options = {"does not appear", "does not appear as a single digit", "is only same digit double number combination", "is only consecutive digit combination in either order"};
        JComboBox actionCombo = new JComboBox(options);
        controlPane.add(actionCombo, g);

        g.gridy += 2;
        JTextField rowField = new JTextField(5);
        controlPane.add(rowField, g);

        g.gridy++;
        JTextField columnField = new JTextField(5);
        controlPane.add(columnField, g);

        g.gridy++;
        JTextField squareField = new JTextField(5);
        controlPane.add(squareField, g);

        //actionlistener for enter button retrieves all content of the fields and sends it to logic class to be processed
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Bingo.errorLabel.setText("");
                new Logic(numField.getText(), (String) actionCombo.getSelectedItem(), rowField.getText(), columnField.getText(), squareField.getText());
            }
        });
        ///actionlistener for undo button undos the last action by accessing the most recent elements in the event sequence list
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int currentSequence = Logic.eventSequence.getLast()[4];
                    for (Iterator<int[]> it = Logic.eventSequence.iterator(); it.hasNext();) {
                        int[] es = it.next();
                        if (es[4] == currentSequence) {
                            cards[es[0]][es[1]].digits[es[2]] = es[3];
                            cards[es[0]][es[1]].repaint();
                            it.remove();
                        }
                    }
                } catch (Exception ex) {
                    Bingo.errorLabel.setText("end of undo");
                }
            }
        });
    }
}
