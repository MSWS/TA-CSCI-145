import java.util.HashSet;
import java.util.Set;

public class Sudoku {
    public static boolean check(char[][] puzzle) {
        for (int i = 0; i < 9; i++) {
            if (!checkRow(puzzle, i) || !checkColumn(puzzle, i) || !checkBox(puzzle, i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkRow(char[][] puzzle, int row) {
        char[] rowArray = puzzle[row];
        return checkGeneric(rowArray);
    }

    private static boolean checkColumn(char[][] puzzle, int column) {
        char[] columnArray = new char[9];
        for (int i = 0; i < 9; i++) {
            columnArray[i] = puzzle[i][column];
        }
        return checkGeneric(columnArray);
    }

    private static boolean checkGeneric(char[] array) {
        if (array.length != 9) {
            throw new IllegalArgumentException("Array must have length 9");
        }
        Set<Character> seen = new HashSet<>();
        for (char c : array) {
            if (c != '.' && !seen.add(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkBox(char[][] puzzle, int box) {
        int row = (box / 3) * 3;
        int column = (box % 3) * 3;
        char[] boxArray = new char[9];
        int index = 0;
        for (int i = row; i < row + 3; i++) {
            for (int j = column; j < column + 3; j++) {
                boxArray[index++] = puzzle[i][j];
            }
        }

        return checkGeneric(boxArray);
    }

    private static boolean solveRecurse(char[][] puzzle, boolean top) {
        int[] empty = findEmpty(puzzle);
        if (empty == null) {
            return true;
        }

        int row = empty[0];
        int column = empty[1];
        for (char c = '1'; c <= '9'; c++) {
            puzzle[row][column] = c;
            if (check(puzzle) && solveRecurse(puzzle, false)) {
                return true;
            }
            puzzle[row][column] = '.';
        }
        return false;
    }

    private static int countNumbers(char[][] puzzle) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] != '.') {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean solve(char[][] puzzle) {
        if (!check(puzzle)) {
            System.out.println("This puzzle is invalid");
            return false;
        }
        if (countNumbers(puzzle) < 17) {
            System.out.println("This puzzle is unsolvable");
            return false;
        }
        return solveRecurse(puzzle, true);
    }

    private static int[] findEmpty(char[][] puzzle) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] == '.') {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
