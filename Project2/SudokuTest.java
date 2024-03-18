import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.Assert.*;

public class SudokuTest {
    private char[][] puzzle;
    private static final int RANDOM_REMOVES = 10;
    private static final int ITERATIONS = 100;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Before
    public void generatePuzzle() {
        puzzle = SudokuP.puzzle();
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        protected void failed(Throwable e, org.junit.runner.Description description) {
            System.out.println("Failed test: " + description.getMethodName());
            System.out.println("Puzzle:");
            printPuzzle();
        }
    };

    public void printPuzzle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testCheck_Valid() {
        for (int i = 0; i < ITERATIONS; i++)
            assertTrue(Sudoku.check(puzzle));
    }

    @Test
    public void testCheck_Empty() {
        char[][] empty = new char[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                empty[i][j] = '.';
        assertTrue(Sudoku.check(empty));
    }

    @Test
    public void testCheck_Empty_Random() {
        for (int i = 0; i < ITERATIONS; i++) {
            int[] coords = new int[RANDOM_REMOVES];
            int size = puzzle.length * puzzle[0].length;
            Random random = new Random();
            for (int k = 0; k < 10; k++)
                coords[k] = random.nextInt(size);
            for (int k = 0; k < coords.length; k++) {
                int row = coords[k] / 9;
                int col = coords[k] % 9;
                puzzle[row][col] = '.';
            }

            assertTrue(Sudoku.check(puzzle));
        }
    }

    @Test
    public void testCheck_Invalid_Row() {
        for (char c = '1'; c <= '9'; c++) {
            puzzle[0][0] = c;
            puzzle[0][1] = c;
            assertFalse(Sudoku.check(puzzle));

            for (int i = 1; i < 9; i++) {
                generatePuzzle();
                puzzle[0][0] = c;
                puzzle[0][i] = c;
                assertFalse(Sudoku.check(puzzle));
            }
        }
    }

    @Test
    public void testCheck_Invalid_Col() {
        for (char c = '1'; c <= '9'; c++) {
            generatePuzzle();
            puzzle[0][0] = c;
            puzzle[1][0] = c;
            assertFalse(Sudoku.check(puzzle));

            for (int i = 1; i < 9; i++) {
                generatePuzzle();
                puzzle[0][0] = c;
                puzzle[i][0] = c;
                assertFalse(Sudoku.check(puzzle));
            }
        }
    }

    @Test
    public void testCheck_Invalid_Box() {
        for (char c = '1'; c <= '9'; c++) {
            generatePuzzle();
            puzzle[0][0] = c;
            puzzle[1][1] = c;
            assertFalse(Sudoku.check(puzzle));

            for (int i = 1; i < 9; i++) {
                generatePuzzle();
                puzzle[0][0] = c;
                puzzle[i / 3][i % 3] = c;
                assertFalse(Sudoku.check(puzzle));
            }
        }
    }

    @Test
    public void testSolve_Valid() {
        char[][] original = puzzle.clone();
        for (int i = 0; i < ITERATIONS; i++) {
            assertTrue(Sudoku.solve(puzzle));
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertNotEquals('.', puzzle[row][col]);
                if (original[row][col] != '.')
                    continue;
                assertEquals(original[row][col], puzzle[row][col]);
            }
        }
    }

    @Test
    public void testSolve_Unsolvable() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = '.';
            }
        }
        assertFalse(Sudoku.solve(puzzle));
        assertEquals("This puzzle is unsolvable\n", outputStreamCaptor.toString());
    }

    @Test
    public void testSolve_Unsolvable_Manually() {
        puzzle = new char[][]{
                {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
        };
        for (int i = 1; i < 9; i++) {
            puzzle[i] = new char[9];
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = '.';
            }
        }

        assertFalse(Sudoku.solve(puzzle));
        assertEquals("This puzzle is unsolvable\n", outputStreamCaptor.toString());
    }

    @Test
    public void testSolve_Invalid() {
        puzzle[0][0] = '1';
        puzzle[0][1] = '1';
        assertFalse(Sudoku.solve(puzzle));
        assertEquals("This puzzle is invalid\n", outputStreamCaptor.toString());
    }
}
