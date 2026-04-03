package Tests;
import TowerGroup.*;
import Shapes.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class TowerContestTest {
    private TowerContest tc;

    @Before
    public void setUp(){
        tc = new TowerContest(0, 0);
    }

    @After
    public void tearDown(){
        tc = null;
    }

    // ¿Qué retorna solve con el caso del enunciado?
    @Test
    public void accordingGMShouldSolveOfficialCase(){
        tc.changeCase(4, 9);
        ArrayList<Integer> expectedOutput = new ArrayList<>(Arrays.asList(7, 3, 5, 1));
        assertEquals(expectedOutput, tc.solve());
    }

    // ¿Qué retorna solve cuando es imposible?
    @Test
    public void accordingGMShouldReturnImpossibleWhenNoSolution(){
        tc.changeCase(4, 2);
        ArrayList<Integer> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, tc.solve());
    }

    // ¿Qué retorna solve con una sola taza?
    @Test
    public void accordingGMShouldSolveWithOneCup(){
        tc.changeCase(1, 1);
        ArrayList<Integer> expectedOutput = new ArrayList<>(Arrays.asList(1));
        assertEquals(expectedOutput, tc.solve());
    }
}