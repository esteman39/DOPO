import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;

/**
 * The test class SolutionTest.
 *
 * @author  Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (14/02/2026)
 */
public class SolutionTest{
    Solution solution;
    
    @Before
    public void setUp(){
        solution = new Solution(4, 9);
    }
    
    @After
    public void tearDown(){
        solution = null;
    }
    
    /**
     * Deberia: Mostrar la salida esperada que se nos esta brindado en el caso de prueba del enunciado del ejercicio.
     */
    
    @Test
    public void shouldGiveSolution(){
        ArrayList<Integer> expectedOutput = new ArrayList<>();
        expectedOutput.add(7);
        expectedOutput.add(3);
        expectedOutput.add(5);
        expectedOutput.add(1);
        ArrayList<Integer> output;
        output = solution.getSolution();
        System.out.println(output);
        assertEquals(expectedOutput, output);
    }
}