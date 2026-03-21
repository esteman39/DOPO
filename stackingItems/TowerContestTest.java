import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class TowerContestTest {

    private TowerContest tc;

    @Before
    public void setUp(){
        tc = new TowerContest();
    }

    @After
    public void tearDown(){
        tc = null;
    }

    // ¿Qué retorna solve con el caso del enunciado?
    @Test
    public void accordingGMShouldSolveOfficialCase(){
        assertEquals("7 3 5 1", tc.solve(4, 9));
    }

    // ¿Qué retorna solve cuando es imposible?
    @Test
    public void accordingGMShouldReturnImpossibleWhenNoSolution(){
        assertEquals("impossible", tc.solve(4, 2));
    }

    // ¿Qué retorna solve con una sola taza?
    @Test
    public void accordingGMShouldSolveWithOneCup(){
        assertEquals("1", tc.solve(1, 1));
    }
}