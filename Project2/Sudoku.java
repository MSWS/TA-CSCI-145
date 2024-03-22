// Cole Levy
// CSCI 145 - Project 2
// I certify that the work in this file is my own.

public class Sudoku {
    static int N = 9; //board size
    static int C = 3; //cube size
    static char[][] pRows = SudokuP.puzzle(); //<--
    static char[][] pColumns = new char[N][N]; 
    static char[][] pCubes = new char[N][N];


    //-> at runtime check if generated sudoku puzzle is valid, then solve
    public static void main(String[] args) {
        System.out.println("~~ Sudoku ~~~~~~~~~~~");
        printFormatted(pRows);
        solve(pRows);
    }


    //-> returns true when the given puzzle is valid, otherwise false
    public static boolean check(char[][] puzzle) {
        for(int row=0; row<N; row++) {
            for(int column=0; column<N; column++) {
                //for each cell, make sure it is a digit or '.' and unique to its surroundings
                if( Character.isDigit(puzzle[row][column])) {
                    //subtracted '0' here because int value of '(int)' char is (int) greater than int value of '0' on ASCII table
                    int element = (puzzle[row][column] - '0');
                    //handle digit less than 1 or greater than board size
                    if(element<1 || element>N) { 
                        return false; 
                    }
                //handle invalid non-digit character
                } else if(puzzle[row][column] != '.') { 
                    return false; 
                }
                //handle element not unique to its row, column, and cube
                if(!validUnique(row, column, puzzle[row][column])) { 
                    return false; 
                } 
            }
        } //else
        return true;
    }


    //-> take pRows[][] input and find a solution. return true if solved
    public static boolean solve(char[][] puzzle) {
        pRows = puzzle;
        if(check(pRows)) {
            boolean solved = true;
            //assuming a valid puzzle, do this for each cell
            for(int row=0; row<N; row++) {
                for(int column=0; column<N; column++) {
                    //if element is empty, repeat for 1-N possible values
                    if(puzzle[row][column] == '.') {
                        for(int i=1; i<=N; i++) {
                            //if 0<i<N is a NONDUPLICATE possible value for this empty element, attempt to solve with this value
                            //used +'0' to convert to char because of ASCII value, same as in check()
                            if(validUnique(row, column, (char)(i+'0'))) {
                                puzzle[row][column] = (char)(i+'0');
                                if (solve(puzzle)) { 
                                    solved = true;
                                    return true; 
                                } else { 
                                    puzzle[row][column] = '.'; //reset to empty if this i doesn't work
                                }
                            }
                        }
                        solved = false;
                        return false; //if no solution for current element, puzzle is unsolvable
                    }
                }
            } //once all cells are checked
            if(solved) {
                System.out.println("Here\'s a solution:"+System.lineSeparator()+"~~ Solution ~~~~~~~~~");
                printFormatted(pRows);
                return true;
            } else {
                System.out.println("This puzzle is unsolveable");
                return false;
            }
        // if puzzle is not valid
        } else {
            System.out.println("The given sudoku puzzle is invalid");
            return false;
        }
    } 


    //-> accept element input and return true if element value is unique to its row, column, and cube (without checking whole puzzle)
    private static boolean validUnique(int row, int column, char key) {
        loadPuzzle(); 
        //if char is unique to its row and column, return based on cube check
        if(checkDuplicates(pRows[row], key, column) && checkDuplicates(pColumns[column], key, row)) {
            /* determining mathematically what CxC cube an element is in using indices took some thought, but after a notepage i came up with this:
            expression before the plus in elementCube equals number of cubes above those which overlap with pRows[row]; either 0, 3, or 6
            expression after the plus equals which of the 3 cubes that overlap with pRows[row] contains the element; either 0, 1, or 2  */
            int elementCube = (row-(row%3))+((column-(column%C))/C);
            int elementCubeIndex = ((row%C)*C)+(column%C);
            return checkDuplicates(pCubes[elementCube], key, elementCubeIndex);
        } return false;
    }


    //-> iterate through elements to find pColumns[][] array of char columns and pCubes[][] array of char CxC cubes
    private static void loadPuzzle() { 
        for(int row=0; row<N; row++) {
            for(int column=0; column<N; column++) {
                //add element to pColumns by switching axis
                pColumns[column][row] = pRows[row][column]; 
                //using the same method as in validUnique() to determine element position in pCubes array
                pCubes[ (row-(row%3))+((column-(column%C))/C) ][ ((row%C)*C)+(column%C) ] = pRows[row][column];
            }
        }
    }


    //-> take any sudokuChars[] input and return true if the char key at index is unique to the array
    private static boolean checkDuplicates(char[] rowArr, char key, int index) {
        if(key != '.') {
            for(int element=0; element<N; element++) {
                if(element !=index && rowArr[element] == key) { 
                    return false; 
                }
            }
        } return true; //else blank ".", char is valid
    }


    //-> take pRows[][] input and format for commandline output
    private static void printFormatted(char[][] puzzle) {
        for(int row=0; row<N; row++) {
            StringBuilder output = new StringBuilder();
            for(int column=0; column<N; column++) {
                if(column!=0 && column%C==0) { 
                    output.append("| "); //add vertical lines to board
                }
                output.append(puzzle[row][column]+" ");
            }
            if(row!=0 && row%C==0) { 
                System.out.println("------+-------+------"); //add horizontal lines to board
            }
            System.out.println(output);
        }
        System.out.println(System.lineSeparator());
    }
//end
}