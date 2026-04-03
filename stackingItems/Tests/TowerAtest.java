package Tests;
import TowerGroup.*;
import Shapes.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import javax.swing.JOptionPane;

/**
 * Estas son las pruebas de aceptacion del proyecto que evidencian lo mejor para Tower - Ciclo 4
 * En este caso, para verificar que se esta cumpliendo de manera correcta serán con interfaz grafica.
 *
 * Para cada requisito se prueban dos preguntas:
 *   - ¿Que DEBERIA hacer?  (casos positivos)
 *   - ¿Que NO deberia hacer? (casos negativos / limites)
 * 
 * Convencion de nombres: accordingGMShould... (GM: Garcia, Muñoz)
 *
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class TowerAtest{
    private Tower tower;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        tower = new Tower(9, 7);
    }
    
    /**
     * Se verificara si se puede simular el escenario del caso base, donde pueden entrar 4 tazas como altura maxima 9
     */
    @Test
    public void accordingGMShouldCreateBaseCase(){
        tower.pushCup(4);
        tower.pushCup(2);
        tower.pushCup(3);
        tower.pushCup(1);
        int answerUser = JOptionPane.showConfirmDialog(null, "¿ACEPTA EL CASO MOSTRADO?", "DE UNA RESPUESTA", JOptionPane.YES_NO_OPTION);
        boolean input = (answerUser == JOptionPane.YES_OPTION);
        assertEquals(true, input);
    }
    
    /**
     * Se verifica si se puede simular un escenario con todo tipo de tazas y tapas especificas que han sido creadas
     * para este caso se utilizara el arreglo {1, 4, 3, 2} donde el tipo de cada uno puede variar, se va a agregar
     * 1 tapa que en este caso será la tapa 4 de tipo Crazy
     */
    @Test
    public void accordingGMShouldCreateTowerWithSpecifics(){
        tower.pushCupType("Opener", 1);
        tower.pushCupType("Normal", 4);
        tower.pushCupType("Hierarchical", 3);
        tower.pushCupType("Opener", 2);
        tower.pushLidType("Crazy", 4);
        int answerUser = JOptionPane.showConfirmDialog(null, "¿ACEPTA EL CASO MOSTRADO?", "DE UNA RESPUESTA", JOptionPane.YES_NO_OPTION);
        boolean input = (answerUser == JOptionPane.YES_OPTION);
        assertEquals(true, input);
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown(){
        tower.makeInvisible();
        tower = null;
    }
}