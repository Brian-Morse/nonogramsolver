import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.awt.event.ActionEvent;

public class NonogramSolver {
    //Class variables
    private static int sizeRow;
    private static int sizeCol;
    private static JLabel[][] puzzle;
    private static JTextField[] colText;
    private static JTextField[] rowText;
    private static JPanel totPanel;
    private static JPanel puzzPanel;
    private static JPanel optionsPanel;
    private static JFrame mainFrame;
    private static JFrame introFrame;
    private static ArrayList<ArrayList<Integer>> rowInfo;
    private static ArrayList<ArrayList<Integer>> colInfo;
    private static ArrayList<ArrayList<Integer[]>> rowPossible;
    private static ArrayList<ArrayList<Integer[]>> colPossible;

    //GUI Info
    private static final String PROGRAM_NAME = "Nonogram Solver";

    private static final int INTRO_WIDTH = 450;
    private static final int INTRO_HEIGHT = 450;

    private static final String INSTRUCTIONS_STRING = 
            "Choose the size to solve a nonogram puzzle for:";
    private static final int X_INSTRUCTIONS = 50;
    private static final int Y_INSTRUCTIONS = 30;
    private static final int WIDTH_INSTRUCTIONS = 350;
    private static final int HEIGHT_INSTRUCTIONS = 40;

    private static final int FONT_SIZE_LABEL = 15;
    private static final Font LABEL_FONT = new Font(Font.SANS_SERIF,Font.PLAIN,
            FONT_SIZE_LABEL);
    
    private static final int SMALL_SIZE = 5;
    private static final String SMALL_BUTTON_STRING = String.format
            ("%dx%d",SMALL_SIZE,SMALL_SIZE);
    private static final int MED_SIZE = 10;
    private static final String MED_BUTTON_STRING = String.format
            ("%dx%d",MED_SIZE,MED_SIZE);
    private static final int LARGE_SIZE = 15;
    private static final String LARGE_BUTTON_STRING = String.format
            ("%dx%d",LARGE_SIZE,LARGE_SIZE);

    private static final Font BUTTON_FONT = new Font(Font.SANS_SERIF,Font.PLAIN,
            40);
    private static final int X_BUTTON = 10;
    private static final int Y_BUTTON_START = 100;
    private static final int Y_BUTTON_GAP = 20;
    private static final int WIDTH_BUTTON = 200;
    private static final int HEIGHT_BUTTON = 80;

    private static final String CUSTOM_STRING = "Custom Size: Row x Column";
    private static final int X_CUSTOM = 225;
    private static final int Y_CUSTOM = 100;
    private static final int WIDTH_CUSTOM = 200;
    private static final int HEIGHT_CUSTOM = 40;

    private static final int X_FIELD1 = 265;
    private static final int Y_FIELD = 150;
    private static final int WIDTH_FIELD = 40;
    private static final int HEIGHT_FIELD = 40;
    private static final int FIELD_GAP = 10;

    private static final String X_STRING = "X";
    private static final int X_X = X_FIELD1+WIDTH_FIELD+FIELD_GAP;
    private static final int Y_X = Y_FIELD - 5;
    private static final int WIDTH_X = 10;
    private static final int HEIGHT_X = 40;

    private static final int X_FIELD2 = X_FIELD1+WIDTH_FIELD+WIDTH_X
            +2*FIELD_GAP;
    
    private static final String CONFIRM_STRING = "Confirm";
    private static final int Y_CONFIRM = 200;
    private static final int HEIGHT_CONFIRM = 40;

    private static final int Y_HELP = 270;
    private static final int WIDTH_HELP = 200;
    private static final int HEIGHT_HELP = 40;
    private static final String POSITIVE = "<html>Make sure that the numbers " 
            + "are both positive</html>";
    private static final String NUMBER = "<html>Make sure that the fields are " 
            + "both positive integers</html>";

    private static final int PIX_GAP = 2;
    private static final Color BACKGROUND_COLOR = new Color(200,200,200);
    private static final Dimension BLOCK_SIZE = new Dimension(40,40);
    private static final Dimension TEXT_SIZE = new Dimension(40,20);
    private static final int TEXT_HGAP = 0;
    private static final int TEXT_VGAP = 10;
    private static final Font TEXT_FONT = new Font(Font.SANS_SERIF,Font.PLAIN,
            10);

    private static final String BACK_STRING = "Back";
    private static final Dimension BACK_SIZE = new Dimension(60,20);

    private static final String CALCULATE_STRING = "Calculate";
    private static final Dimension CALCULATE_SIZE = new Dimension(80,20);

    private static final String DELIMITERS = "[ ,;/]";
    private static final String NUMS_ONLY = "<html>Please only use numbers and" 
            + " separators (\" \", \",\", \";\")</html>";
    private static final String POS_ONLY = "<html>Make sure all numbers are " + 
            "positive</html>";
    private static final String TOO_LARGE = "<html>Inputted numbers are too " + 
            "large for the puzzle</html>";
    private static final String FILL_OUT = "<html>Fill out all boxes</html>";
    private static final String MULTIPLE = "<html>Multiple Solutions Probable" 
            + "</html>";
    private static final String IMPOSSIBLE = "<html>Impossible Puzzle</html>";
    private static final String COMPLETE = "<html>Completed!</html>";
    

    private static final int STOP_POINT = 100;
    private static final int BLACK_NUM = 1;
    private static final Color BLACK_COLOR = new Color(0,0,0);
    private static final int WHITE_NUM = 0;
    private static final Color WHITE_COLOR = new Color(255,255,255);


    
    public static void main(String[] args) {
        initIntro();
    }

    /**
     * Initiates the intro screen of the solver
     */
    private static void initIntro() {
        //JFrame intro info
        introFrame = new JFrame(PROGRAM_NAME);
        introFrame.setSize(INTRO_WIDTH,INTRO_HEIGHT);
        introFrame.setLayout(null);
        introFrame.setLocationRelativeTo(null);

        //Instructions Info
        JLabel instructions = new JLabel(INSTRUCTIONS_STRING);
        instructions.setBounds(X_INSTRUCTIONS, Y_INSTRUCTIONS, 
                WIDTH_INSTRUCTIONS, HEIGHT_INSTRUCTIONS);
        instructions.setFont(LABEL_FONT);
        introFrame.add(instructions);

        //5x5 Button
        JButton smallButton = new JButton(SMALL_BUTTON_STRING);
        smallButton.setBounds(X_BUTTON,Y_BUTTON_START,WIDTH_BUTTON,
                HEIGHT_BUTTON);
        smallButton.setFont(BUTTON_FONT);
        smallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initMain(SMALL_SIZE,SMALL_SIZE);
                introFrame.dispose();
            }
        });
        introFrame.add(smallButton);

        //10x10 Button
        JButton medButton = new JButton(MED_BUTTON_STRING);
        medButton.setBounds(X_BUTTON,Y_BUTTON_START+Y_BUTTON_GAP+HEIGHT_BUTTON,
                WIDTH_BUTTON, HEIGHT_BUTTON);
        medButton.setFont(BUTTON_FONT);
        medButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initMain(MED_SIZE,MED_SIZE);
                introFrame.dispose();
            }
        });
        introFrame.add(medButton);

        //15x15 Button
        JButton largeButton = new JButton(LARGE_BUTTON_STRING);
        largeButton.setBounds(X_BUTTON,Y_BUTTON_START+2*Y_BUTTON_GAP+
                2*HEIGHT_BUTTON,WIDTH_BUTTON,HEIGHT_BUTTON);
        largeButton.setFont(BUTTON_FONT);
        largeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initMain(LARGE_SIZE,LARGE_SIZE);
                introFrame.dispose();
            }
        });
        introFrame.add(largeButton);

        //Custom Text
        JLabel customMessage = new JLabel(CUSTOM_STRING);
        customMessage.setBounds(X_CUSTOM,Y_CUSTOM,WIDTH_CUSTOM,HEIGHT_CUSTOM);
        customMessage.setFont(LABEL_FONT);
        introFrame.add(customMessage);

        //First text field
        JTextField field1 = new JTextField();
        field1.setBounds(X_FIELD1,Y_FIELD,WIDTH_FIELD,HEIGHT_FIELD);
        field1.setFont(LABEL_FONT);
        introFrame.add(field1);

        //X label
        JLabel xLabel = new JLabel(X_STRING);
        xLabel.setBounds(X_X,Y_X,WIDTH_X,HEIGHT_X);
        xLabel.setFont(LABEL_FONT);
        introFrame.add(xLabel);

        //Second text field
        JTextField field2 = new JTextField();
        field2.setBounds(X_FIELD2,Y_FIELD,WIDTH_FIELD,HEIGHT_FIELD);
        field2.setFont(LABEL_FONT);
        introFrame.add(field2);

        //Help label
        JLabel help = new JLabel();
        help.setBounds(X_CUSTOM,Y_HELP,WIDTH_HELP,HEIGHT_HELP);
        help.setFont(LABEL_FONT);
        introFrame.add(help);

        //Confirm button
        JButton confirm = new JButton(CONFIRM_STRING);
        confirm.setBounds(X_FIELD1,Y_CONFIRM,2*WIDTH_FIELD+2*FIELD_GAP+WIDTH_X,
                HEIGHT_CONFIRM);
        confirm.setFont(LABEL_FONT);
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    //Make sure inputs are positive integers
                    int rows = Integer.parseInt(field1.getText());
                    int cols = Integer.parseInt(field2.getText());
                    if (rows > 0 && cols > 0) {
                        initMain(rows, cols);
                        introFrame.dispose();
                    }
                    else {
                        help.setText(POSITIVE);
                    }
                }
                catch (NumberFormatException e1) {
                    help.setText(NUMBER);
                }
            }
        });
        introFrame.add(confirm);



        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setVisible(true);
    }

    /**
     * Initiates the main screen GUI of the solver 
     * @param row The number of rows desired
     * @param col The number of columns desired
     */
    private static void initMain(int row, int col) {
        //Initiate the size of the grid
        sizeRow = row;
        sizeCol = col;

        //Setting up the main frame
        mainFrame = new JFrame(PROGRAM_NAME);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Setting up the panel that will hold everything
        totPanel = new JPanel(new BorderLayout(PIX_GAP,PIX_GAP));
        totPanel.setBackground(BACKGROUND_COLOR);
        totPanel.setOpaque(true);

        //Setting up the panel that holds the puzzle
        puzzPanel = new JPanel(new GridLayout(sizeRow+1,sizeCol+1,PIX_GAP,
                PIX_GAP));
        puzzPanel.setBackground(BACKGROUND_COLOR);
        puzzPanel.setOpaque(true);

        //Putting in the empty square for top left corner
        JLabel empty = new JLabel();
        empty.setBackground(BACKGROUND_COLOR);
        empty.setOpaque(true);
        empty.setPreferredSize(BLOCK_SIZE);
        puzzPanel.add(empty);

        //Putting in the column text boxes
        colText = new JTextField[sizeCol];
        for (int i = 0; i < sizeCol; i++) {
            //Making the box to put text field in
            JPanel box = new JPanel(new FlowLayout(FlowLayout.CENTER,TEXT_HGAP,
                    TEXT_VGAP));
            box.setPreferredSize(BLOCK_SIZE);
            box.setBackground(BACKGROUND_COLOR);
            box.setOpaque(true);

            //Making the text field
            colText[i] = new JTextField();
            colText[i].setFont(LABEL_FONT);
            colText[i].setPreferredSize(TEXT_SIZE);
            colText[i].setFont(TEXT_FONT);

            //Adding text field in the box
            box.add(colText[i]);
            //Add box to the puzzle
            puzzPanel.add(box);
        }

        //Putting in the row text boxes and puzzle squares
        rowText = new JTextField[sizeRow];
        puzzle = new JLabel[sizeRow][sizeCol];
        for (int i = 0; i < sizeRow; i++) {
            //Making the box to put text field in
            JPanel box = new JPanel(new FlowLayout(FlowLayout.CENTER,TEXT_HGAP,
                    TEXT_VGAP));
            box.setPreferredSize(BLOCK_SIZE);
            box.setBackground(BACKGROUND_COLOR);
            box.setOpaque(true);

            //Making the text field
            rowText[i] = new JTextField();
            rowText[i].setFont(LABEL_FONT);
            rowText[i].setPreferredSize(TEXT_SIZE);
            rowText[i].setFont(TEXT_FONT);

            //Adding text field in the box
            box.add(rowText[i]);
            //Add box to the puzzle
            puzzPanel.add(box);

            //Adding in the puzzle squares
            for (int j = 0; j < sizeCol; j++) {
                puzzle[i][j] = new JLabel();
                puzzle[i][j].setBackground(BACKGROUND_COLOR);
                puzzle[i][j].setOpaque(true);
                puzzle[i][j].setPreferredSize(BLOCK_SIZE);
                puzzPanel.add(puzzle[i][j]);
            }
        }

        //Setting up the panel that will hold options and guide
        optionsPanel = new JPanel(new BorderLayout(PIX_GAP,PIX_GAP));

        //Setting up a back button
        JButton back = new JButton(BACK_STRING);
        back.setPreferredSize(BACK_SIZE);
        back.setFont(TEXT_FONT);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                introFrame.setVisible(true);
                mainFrame.dispose();
            }
        });
        optionsPanel.add(back,BorderLayout.WEST);

        //Setting up a guide button
        JLabel guide = new JLabel();
        guide.setFont(TEXT_FONT);
        optionsPanel.add(guide,BorderLayout.CENTER);

        //Setting up the calculate button
        JButton calculate = new JButton(CALCULATE_STRING);
        calculate.setPreferredSize(CALCULATE_SIZE);
        calculate.setFont(TEXT_FONT);
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    reset();
                    //Copy data from text into row information
                    rowInfo = new ArrayList<>();
                    for (int i = 0; i < sizeRow; i++) {
                        //Checking validity of data
                        ArrayList<Integer> info = new ArrayList<>();
                        //Ensuring that data is small enough
                        int sum = -1;
                        String[] data = rowText[i].getText().split(DELIMITERS);
                        for (int j = 0; j < data.length; j++) {
                            //Excused case for flexibility
                            if (data[j].equals("")) {
                                continue;
                            }
                            //Make sure number is valid for a nonogram
                            Integer number = Integer.parseInt(data[j]);
                            if (number <= 0) {
                                guide.setText(POS_ONLY);
                                return;
                            }
                            sum += number + 1;
                            info.add(number);
                        }
                        //Small enough puzzle for what we need
                        if (sum > sizeCol) {
                            guide.setText(TOO_LARGE);
                            return;
                        }
                        //Make sure there is data there
                        if (info.size() == 0) {
                            guide.setText(FILL_OUT);
                            return;
                        }
                        //This row seems good to add
                        rowInfo.add(info);
                    }
                    //Copy data from text into col information
                    colInfo = new ArrayList<>();
                    for (int i = 0; i < sizeCol; i++) {
                        //Checking validity of data
                        ArrayList<Integer> info = new ArrayList<>();
                        //Ensuring that data is small enough
                        int sum = -1;
                        String[] data = colText[i].getText().split(DELIMITERS);
                        for (int j = 0; j < data.length; j++) {
                            //Excused case for flexibility
                            if (data[j].equals("")) {
                                continue;
                            }
                            //Make sure number is valid for a nonogram
                            Integer number = Integer.parseInt(data[j]);
                            if (number <= 0) {
                                guide.setText(POS_ONLY);
                                return;
                            }
                            sum += number + 1;
                            info.add(number);
                        }
                        //Small enough puzzle for what we need
                        if (sum > sizeRow) {
                            guide.setText(TOO_LARGE);
                            return;
                        }
                        //Make sure there is data there
                        if (info.size() == 0) {
                            guide.setText(FILL_OUT);
                            return;
                        }
                        //This col seems good to add
                        colInfo.add(info);
                    }
                    guide.setText("");
                    solve();
                    guide.setText(COMPLETE);
                }
                catch (NumberFormatException e1) {
                    guide.setText(NUMS_ONLY);
                }
                catch (IllegalArgumentException e2) {
                    guide.setText(MULTIPLE);
                } 
                catch (InputMismatchException e3) {
                    guide.setText(IMPOSSIBLE);
                }
            }
        });
        optionsPanel.add(calculate,BorderLayout.EAST);

        //Adding everything together
        totPanel.add(puzzPanel,BorderLayout.NORTH);
        totPanel.add(optionsPanel,BorderLayout.SOUTH);
        mainFrame.add(totPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }

    /**
     * Overarching function to solve the nonogram puzzle
     */
    private static void solve() {
        //Get the different combinations for the rows
        rowPossible = new ArrayList<>();
        for (int i = 0; i < sizeRow; i++) {
            rowPossible.add(getPossibilities(rowInfo.get(i),true));
        }
        //Get the different combinations for the cols
        colPossible = new ArrayList<>();
        for (int i = 0; i < sizeCol; i++) {
            colPossible.add(getPossibilities(colInfo.get(i),false));
        }

        //Keep on trying to solve the puzzle until determined impossible
        int count = 0;
        while (!complete()) {
            if (count > STOP_POINT) {
                throw new IllegalArgumentException();
            }
            //Try to fill in squares in rows
            for (int i = 0; i < sizeRow; i++) {
                attemptFill(rowPossible.get(i),i, true);
            }
            //Try to fill in squares in columns
            for (int i = 0; i < sizeCol; i++) {
                attemptFill(colPossible.get(i),i,false);
            }
            count++;
        }
    }

    /**
     * Get all of the different possibilities of squares for a given row or 
     * column
     * @param info The length of groups for a row or column
     * @param row True if getting info for a row, false if for a column
     * @return An ArrayList of Integer arrays that hold possible values
     */
    private static ArrayList<Integer[]> getPossibilities(
            ArrayList<Integer> info, boolean row) {
        //Do work knowing you are dealing with a row
        if (row) {
            ArrayList<Integer[]> combos = new ArrayList<>();
            //Figure out how the combos will be made
            int numGroups = info.size();
            int sum = -1;
            for (int i = 0; i < numGroups; i++) {
                sum += info.get(i)+1;
            }
            getCombos(combos, new Integer[numGroups],0,numGroups
                    +sizeCol-sum-1, 0);
            //Start turning the combos into possible forms of the puzzle
            ArrayList<Integer[]> possibilities = new ArrayList<>();
            for (int i = 0; i < combos.size(); i++) {
                possibilities.add(buildPossibility(info,combos.get(i),sizeCol));
            }
            return possibilities;
        }

        //Do work knowing you are dealing with a column

        ArrayList<Integer[]> combos = new ArrayList<>();
        //Figure out how the combos will be made
        int numGroups = info.size();
        int sum = -1;
        for (int i = 0; i < numGroups; i++) {
            sum += info.get(i)+1;
        }
        getCombos(combos, new Integer[numGroups],0,numGroups
                +sizeRow-sum-1, 0);
        //Start turning the combos into possible forms of the puzzle
        ArrayList<Integer[]> possibilities = new ArrayList<>();
        for (int i = 0; i < combos.size(); i++) {
            possibilities.add(buildPossibility(info,combos.get(i),sizeRow));
        }
        return possibilities;
    }

    /**
     * Gets all possible combinations for a given number of groups and number 
     * to choose from
     * @param combos The list to add completed combos to
     * @param data The place to keep track of current attempted combo
     * @param start Traversing length of possible group choices
     * @param end End location for possible groups
     * @param index Index of how full the current combo is
     */
    private static void getCombos(ArrayList<Integer[]> combos, Integer[] data, 
            int start, int end, int index) {
        //Check if the combo is completed
        if (index == data.length) {
            combos.add(data.clone());
        }
        //Go to check for more combos by overriding data
        else if (start <= end) {
            data[index] = start;
            getCombos(combos,data,start+1,end,index+1);
            getCombos(combos,data,start+1,end,index);
        }
    }

    /**
     * Builds the possibility for the column or row to fulfill
     * @param info The length of groups information
     * @param combo The combo placements
     * @param size How large the resulting possibility should be
     * @return The completed possibility for the column or row
     */
    private static Integer[] buildPossibility(ArrayList<Integer> info, 
            Integer[] combo, int size) {
        Integer[] possibility = new Integer[size];
        //Set up indices to track when to insert things
        int infoIndex = 0;
        int comboIndex = 0;
        int traverse = 0;
        int possIndex = 0;
        while (comboIndex < combo.length) {
            //Check if it is time to put in a group
            if (traverse == combo[comboIndex]) {
                //Put in the group
                for (int i = 0; i < info.get(infoIndex); i++) {
                    possibility[possIndex++] = BLACK_NUM;
                }
                //If space, add a separator at the end
                if (possIndex < size) {
                    possibility[possIndex++] = WHITE_NUM;
                }
                infoIndex++;
                comboIndex++;
            }
            else {
                //Just add empty space
                possibility[possIndex++] = WHITE_NUM;
            }
            traverse++;
        }
        //Fill in the rest of the array
        while (possIndex < size) {
            possibility[possIndex++] = WHITE_NUM;
        }

        return possibility;
    }

    /**
     * Checks if the nonogram has been fully completed
     * @return True if the puzzle is done, false otherwise
     */
    private static boolean complete() {
        //Go through each square and make sure it is not still grey
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeCol; j++) {
                if (puzzle[i][j].getBackground().equals(BACKGROUND_COLOR)) {
                    return false;
                }
            }
        }
        //All squares have changed and is thus complete
        return true;
    }

    /**
     * Looks throw a row or column for squares to fill in and then eliminates 
     * possibilities that no longer fit
     * @param possible The column or row to try to fill in
     * @param num The row or column number possible is associated with
     * @param row True if representing a row, false if a column
     */
    private static void attemptFill(ArrayList<Integer[]> possible, int num, 
            boolean row) {
        //Check if there are any possibilities, if not, throw exception
        if (possible.size() == 0) {
            throw new InputMismatchException();
        }
        //Check if working on a row or column
        if (row) {
            //Go through each column space
            for (int i = 0; i < sizeCol; i++) {
                //Check if the square is already completed or not
                if (!puzzle[num][i].getBackground().equals(BACKGROUND_COLOR)) {
                    continue;
                }
                //Go through all possibilities and see if they agree
                int expected = possible.get(0)[i];
                boolean match = true;
                for (int j = 0; j < possible.size(); j++) {
                    if (possible.get(j)[i] != expected) {
                        match = false;
                        break;
                    }
                }
                //If they agree, change the square
                if (match) {
                    if (expected == BLACK_NUM) {
                        puzzle[num][i].setBackground(BLACK_COLOR);
                    }
                    else {
                        puzzle[num][i].setBackground(WHITE_COLOR);
                    }
                    //Eliminate column possibilities that do not match
                    eliminate(colPossible.get(i),num,expected);
                }
            }
        }
        else {
            //Go through each row space
            for (int i = 0; i < sizeRow; i++) {
                //Check if the square is already completed or not
                if (!puzzle[i][num].getBackground().equals(BACKGROUND_COLOR)) {
                    continue;
                }
                //Go through all possibilities and see if they agree
                int expected = possible.get(0)[i];
                boolean match = true;
                for (int j = 0; j < possible.size(); j++) {
                    if (possible.get(j)[i] != expected) {
                        match = false;
                        break;
                    }
                }
                //If they agree, change the square
                if (match) {
                    if (expected == BLACK_NUM) {
                        puzzle[i][num].setBackground(BLACK_COLOR);
                    }
                    else {
                        puzzle[i][num].setBackground(WHITE_COLOR);
                    }
                    //Eliminate column possibilities that do not match
                    eliminate(rowPossible.get(i),num,expected);
                }
            }
        }
    }

    /**
     * Eliminates all possibilities that do not agree with the expected value
     * @param possible The list of possibilities to check against
     * @param num The index of the possibility to check
     * @param expected The value that should be in the index
     */
    private static void eliminate(ArrayList<Integer[]> possible, int num, 
            int expected) {
        //Go through all the possibilities and remove those that do not agree 
        //with the expected value
        for (int i = possible.size()-1; i >= 0; i--) {
            if (possible.get(i)[num] != expected) {
                possible.remove(i);
            }
        }
        if (possible.size() == 0) {
            throw new InputMismatchException();
        }
    }

    private static void reset() {
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeCol; j++) {
                puzzle[i][j].setBackground(BACKGROUND_COLOR);
            }
        }
    }
}
