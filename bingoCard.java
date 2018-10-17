package bingo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class bingoCard extends java.awt.Canvas {

    private boolean isNumber, isString;
    int[] digits;
    private int start, fontSize = 30;
    final static int ELIMINATED_NUMBER = 100;
    private String string;

    //initializer with range of numbers contained in bingo card
    public bingoCard(int firstNum) {
        super();
        setSize(72, 75);
        setBackground(Color.white);
        setForeground(Color.black);
        addComponentListener(new ComponentAdapter() {
            public void ComponentResized(ComponentEvent e) {
                repaint();
            }
        });
        setDigits(firstNum);
        setNumber(true);
    }

    //initializer with letter contained in bingo card e.g "free"
    public bingoCard(String str) {
        setSize(72, 75);
        setBackground(Color.white);
        setForeground(Color.black);
        addComponentListener(new ComponentAdapter() {
            public void ComponentResized(ComponentEvent e) {
                repaint();
            }
        });
        setString(str);
        setIsString(true);
    }

    public void setNumber(boolean b) {
        isNumber = b;
    }

    public boolean getNumber() {
        return isNumber;
    }
    
    //initialize the array containing the list of numbers in bingo card
    public void setDigits(int firstNum) {
        digits = new int[15];
        for (int i = 0; i < 15; i++) {
            digits[i] = firstNum;
            firstNum++;
        }
    }

    public int[] getDigits() {
        return digits;
    }

    public void setIsString(boolean b) {
        isString = b;
    }

    public boolean getIsString() {
        return isString;
    }

    public void setString(String str) {
        string = str;
    }

    public String getString() {
        return string;
    }

    @Override
    //draw the numbers or strings in the card
    public void paint(Graphics g) {
        if (isNumber) {
            int positiony = 20;
            int positionx = 10;
            for (int i = 0, index = 0; i < 4; i++) {
                for (int c = 0; c < 4; c++, index++) {
                    if (index < digits.length && digits[index] != ELIMINATED_NUMBER) {
                        g.drawString(String.valueOf(digits[index]), positionx, positiony);
                        positionx += (int) Math.floor(getWidth() / 4);
                    }
                }
                positiony += (int) Math.floor(this.getHeight() / 4);
                positionx = 10;
            }
        } else {
            g.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
            g.drawString(string, (int) Math.floor(this.getHeight() / 2), (int) Math.floor(this.getWidth() / 2));
        }
    }
}
