public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            char[][] puzzle = SudokuP.puzzle();
//            if (!Sudoku.check(puzzle)) {
//                System.out.println("ERR: Reported invalid when it was valid");
//                printPuzzle(puzzle);
//                break;
//            }
            if (!Sudoku.solve(puzzle)) {
                System.out.println("ERR: Reported unsolvable when it was solvable");
                printPuzzle(puzzle);
                break;
            }
            System.out.println("Solved puzzle " + i);
            printPuzzle(puzzle);
        }
    }

    private static void printPuzzle(char[][] puzzle) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }
}
