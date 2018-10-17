package bingo;

import java.util.LinkedList;

public class Logic {

    int number, actionKey;
    static int sequenceNumber = 0;
    final int doesNotAppear = 1, doesNotAppearSingle = 2, isOnlySameDigitDouble = 3, isOnlyConsecutive = 4;

    int b = 1, i = 2, n = 3, g = 4, o = 5;
    int p = 1, l = 2, a = 3, y = 4, s = 5;
    LinkedList<bingoCard> location;
    static LinkedList<int[]> eventSequence = new LinkedList<>();

    //constructor accepting all content from the fields of control pane in bingo class
    public Logic(String number, String action, String row, String column, String square) {
        //verify
        //that the number is an integer between 0 and 75 inclusive
        try {
            this.number = Integer.valueOf(number.trim());
        } catch (Exception e) {
            Bingo.errorLabel.setText("The number in number field is not an integer");
            return;
        }
        if (this.number < 0 || this.number > 75) {
            Bingo.errorLabel.setText("The number is out of range");
            return;
        }
        
        //set the action key to the actions constant based on the selected action in bingo
        switch (action) {
            case "does not appear":
                actionKey = doesNotAppear;
                break;
            case "does not appear as a single digit":
                actionKey = doesNotAppearSingle;
                break;
            case "is only same digit double number combination":
                actionKey = isOnlySameDigitDouble;
                break;
            case "is only consecutive digit combination in either order":
                actionKey = isOnlyConsecutive;
                break;
        }
        
        //initialize the location list/ holds a list of locations where numbers will be eliminated
        location = new LinkedList<bingoCard>();
        
        //verify that the row is in the form p,l,a,y,s
        row = row.trim();
        for (int i = 0; i < row.length(); i++) {
            if (i % 2 == 0 && !isPlays(row.charAt(i), 0)) {
                Bingo.errorLabel.setText("incorrect row input, has to be p, l, a, y or s");
                return;
            } else {
                switch (row.charAt(i)) {
                    case 'p':
                    case 'P':
                        for (int c = 1; c < 6; c++) {
                            location.add(Bingo.cards[1][c]);
                        }
                        break;
                    case 'l':
                    case 'L':
                        for (int c = 1; c < 6; c++) {
                            location.add(Bingo.cards[2][c]);
                        }
                        break;
                    case 'a':
                    case 'A':
                        for (int c = 1; c < 6; c++) {
                            if (c == 3) {
                                continue;
                            }
                            location.add(Bingo.cards[3][c]);
                        }
                        break;
                    case 'y':
                    case 'Y':
                        for (int c = 1; c < 6; c++) {
                            location.add(Bingo.cards[4][c]);
                        }
                        break;
                    case 's':
                    case 'S':
                        for (int c = 1; c < 6; c++) {
                            location.add(Bingo.cards[5][c]);
                        }
                        break;
                }
            }
            if (i % 2 != 0 && row.charAt(i) != ',' && i != row.length() - 1) {
                Bingo.errorLabel.setText("valid letters must be seperated with comma");
                return;
            } else if (i % 2 != 0 && row.charAt(i) == ',' && i == row.length() - 1) {
                Bingo.errorLabel.setText("field must not end with comma");
                return;
            }
        }
        
        //verify that the column field is in the form b, i, n, g, o
        column = column.trim();
        for (int i = 0; i < column.length(); i++) {
            if (i % 2 == 0 && !isPlays(column.charAt(i), 1)) {
                Bingo.errorLabel.setText("incorrect row input, has to be b, i, n, g or o");
                return;
            } else {
                switch (column.charAt(i)) {
                    case 'b':
                    case 'B':
                        for (int c = 1; c < 6; c++) {
                            location.add(Bingo.cards[c][1]);
                        }
                        break;
                    case 'i':
                    case 'I':
                        for (int c = 1; c < 6; c++) {
                            location.add(Bingo.cards[c][2]);
                        }
                        break;
                    case 'n':
                    case 'N':
                        for (int c = 1; c < 6; c++) {
                            if (c == 3) {
                                continue;
                            }
                            location.add(Bingo.cards[c][3]);
                        }
                        break;
                    case 'g':
                    case 'G':
                        for (int c = 1; c < 6; c++) {
                            location.add(Bingo.cards[c][4]);
                        }
                        break;
                    case 'o':
                    case 'O':
                        for (int c = 1; c < 6; c++) {
                            location.add(Bingo.cards[c][5]);
                        }
                        break;
                }
            }
            if (i % 2 != 0 && column.charAt(i) != ',' && i != column.length() - 1) {
                Bingo.errorLabel.setText("valid letters must be seperated with comma");
                return;
            } else if (i % 2 != 0 && column.charAt(i) == ',' && i == column.length() - 1) {
                Bingo.errorLabel.setText("field must not end with comma");
                return;
            }
        }
        
        //verify that square is a string that starts with p l a y s and ends with b i n g o seperated by commas
        square = square.trim();
        for (int i = 0; i < square.length(); i++) {
            int ro = 0, co = 0;
            for (int c = 0; c < 3; c++) {
                if (c == 0 && isPlays(square.charAt(i), 0)) {
                    switch (square.charAt(i)) {
                        case 'p':
                        case 'P':
                            ro = p;
                            break;
                        case 'l':
                        case 'L':
                            ro = l;
                            break;
                        case 'a':
                        case 'A':
                            ro = a;
                            break;
                        case 'y':
                        case 'Y':
                            ro = y;
                            break;
                        case 's':
                        case 'S':
                            ro = s;
                            break;
                    }
                    i++;
                } else if (c == 1 && isPlays(square.charAt(i), 1)) {
                    switch (square.charAt(i)) {
                        case 'b':
                        case 'B':
                            co = b;
                            break;
                        case 'i':
                        case 'I':
                            co = this.i;
                            break;
                        case 'n':
                        case 'N':
                            co = n;
                            break;
                        case 'g':
                        case 'G':
                            co = g;
                            break;
                        case 'o':
                        case 'O':
                            co = o;
                            break;
                    }
                    location.add(Bingo.cards[ro][co]);
                    i++;
                } else if (c == 2 && i < square.length() && square.charAt(i) == ',') {
                    break;
                } else if (i >= square.length()) {
                    break;
                } else {
                    Bingo.errorLabel.setText("invalid input, input must be in the form pn, li...");
                    return;
                }
            }
        }
        //call the appropriate method based on the action key
        switch (actionKey) {
            case doesNotAppear:
                doesNotAppear(Integer.valueOf(number), location.toArray(new bingoCard[(location.size())]));
                break;
            case doesNotAppearSingle:
                doesNotAppearAsSingleDigit(Integer.valueOf(number), location.toArray(new bingoCard[(location.size())]));
                break;
            case isOnlySameDigitDouble:
                isOnlySameDigitCombination(Integer.valueOf(number), location.toArray(new bingoCard[(location.size())]));
                break;
            case isOnlyConsecutive:
                isOnlyConsecutiveInEitherOrder(Integer.valueOf(number), location.toArray(new bingoCard[(location.size())]));
        }
    }

    //the logic for eliminating all of the given number that do not appear in the given locations
    private void doesNotAppear(int number, bingoCard[] locations) {
        sequenceNumber++;
        for (int i = 0; i < locations.length; i++) {
            for (int c = 0; c < locations[i].digits.length; c++) {
                if (locations[i].digits[c] == number) {
                    for (int m = 0; m < Bingo.cards.length; m++) {
                        for (int n = 0; n < Bingo.cards[m].length; n++) {
                            if (Bingo.cards[m][n].equals(locations[i])) {
                                eventSequence.add(new int[]{m, n, c, locations[i].digits[c], sequenceNumber});
                            }
                        }
                    }
                    locations[i].digits[c] = bingoCard.ELIMINATED_NUMBER;

                    locations[i].repaint();
                } else if (locations[i].digits[c] >= 10) {
                    //last digit
                    if (locations[i].digits[c] % 10 == number) {
                        for (int m = 0; m < Bingo.cards.length; m++) {
                            for (int n = 0; n < Bingo.cards[m].length; n++) {
                                if (Bingo.cards[m][n].equals(locations[i])) {
                                    eventSequence.add(new int[]{m, n, c, locations[i].digits[c], sequenceNumber});
                                }
                            }
                        }
                        locations[i].digits[c] = bingoCard.ELIMINATED_NUMBER;

                        locations[i].repaint();
                    } //first digit
                    else if ((locations[i].digits[c] - (locations[i].digits[c] % 10)) / 10 == number) {
                        for (int m = 0; m < Bingo.cards.length; m++) {
                            for (int n = 0; n < Bingo.cards[m].length; n++) {
                                if (Bingo.cards[m][n].equals(locations[i])) {
                                    eventSequence.add(new int[]{m, n, c, locations[i].digits[c], sequenceNumber});
                                }
                            }
                        }
                        locations[i].digits[c] = bingoCard.ELIMINATED_NUMBER;
                        locations[i].repaint();
                    }
                }
            }
        }
    }

    //the logic for eliminating all of the given number that do not appear in the given locations as a single digit
    private void doesNotAppearAsSingleDigit(int number, bingoCard[] locations) {
        sequenceNumber++;
        for (int i = 0; i < locations.length; i++) {
            for (int c = 0; c < locations[i].digits.length; c++) {
                if (locations[i].digits[c] == number && locations[i].digits[c] < 10) {
                    for (int m = 0; m < Bingo.cards.length; m++) {
                        for (int n = 0; n < Bingo.cards[m].length; n++) {
                            if (Bingo.cards[m][n].equals(locations[i])) {
                                eventSequence.add(new int[]{m, n, c, locations[i].digits[c], sequenceNumber});
                            }
                        }
                    }
                    locations[i].digits[c] = bingoCard.ELIMINATED_NUMBER;

                    locations[i].repaint();
                }
            }
        }
    }

    //the logic for removing every other same digit combination found in the given locations, that is not the given number
    private void isOnlySameDigitCombination(int number, bingoCard[] locations) {
        sequenceNumber++;
        for (int i = 0; i < locations.length; i++) {
            for (int c = 0; c < locations[i].digits.length; c++) {
                if (locations[i].digits[c] % 10 == (locations[i].digits[c] - (locations[i].digits[c] % 10)) / 10 && locations[i].digits[c] % 10 != number % 10) {
                    for (int m = 0; m < Bingo.cards.length; m++) {
                        for (int n = 0; n < Bingo.cards[m].length; n++) {
                            if (Bingo.cards[m][n].equals(locations[i])) {
                                eventSequence.add(new int[]{m, n, c, locations[i].digits[c], sequenceNumber});
                            }
                        }
                    }
                    locations[i].digits[c] = bingoCard.ELIMINATED_NUMBER;
                    locations[i].repaint();
                }
            }
        }
    }

    //the logic for removing every other consecutive digit number found in the given locations, that is not the given number
    private void isOnlyConsecutiveInEitherOrder(int number, bingoCard[] locations) {
        sequenceNumber++;
        for (int i = 0; i < locations.length; i++) {
            for (int c = 0; c < locations[i].digits.length; c++) {
                if (locations[i].digits[c] >= 10 && Math.abs(locations[i].digits[c] % 10 - ((locations[i].digits[c] - (locations[i].digits[c] % 10)) / 10)) == 1 && locations[i].digits[c] != number) {
                    for (int m = 0; m < Bingo.cards.length; m++) {
                        for (int n = 0; n < Bingo.cards[m].length; n++) {
                            if (Bingo.cards[m][n].equals(locations[i])) {
                                eventSequence.add(new int[]{m, n, c, locations[i].digits[c], sequenceNumber});
                            }
                        }
                    }
                    locations[i].digits[c] = bingoCard.ELIMINATED_NUMBER;
                    locations[i].repaint();
                }
            }
        }
    }

    //returns true if a character is b i n g o, or p l a y s depending on the use case specified by int i
    private boolean isPlays(char c, int i) {
        if (i == 0) {
            return (c == 'p' || c == 'P' || c == 'l' || c == 'L' || c == 'a' || c == 'A' || c == 'y' || c == 'Y' || c == 's' || c == 'S');
        }
        return (c == 'b' || c == 'B' || c == 'i' || c == 'I' || c == 'n' || c == 'N' || c == 'g' || c == 'G' || c == 'o' || c == 'O');
    }
}
