package Tests;
import TowerGroup.*;
import Shapes.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Estas son las pruebas de unidad para el Tower - Ciclo 4
 * Todas las pruebas corren en modo invisible (sin interfaz grafica).
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
public class TowerC4Test{
    private Tower tower;
    private int number = 1;
    private Opener opener;
    private Hierarchical hierarchical;
    private Fearful fearful;
    private Crazy crazy;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        tower = new Tower(9, 7);
        tower.makeInvisible();
        opener = new Opener(number);
        hierarchical = new Hierarchical(number);
        fearful = new Fearful(number);
        crazy = new Crazy(number);
    }
    
    /**
     * Prueba que se le esta asignado correctamente los tipos especificos a cada una de las nuevas tazas
     */
    @Test
    public void accordingGMShouldAssignSpecific(){
        int cont = 0;
        if(opener.getSpecific().equals("Opener")) cont += 1;
        if(hierarchical.getSpecific().equals("Hierarchical")) cont += 1;
        if(fearful.getSpecific().equals("Fearful")) cont += 1;
        if(crazy.getSpecific().equals("Crazy")) cont += 1;
        assertEquals(4, cont);
    }
    
    /**
     * Muestra que se estan ingresando copas de manera correcta en la torre.
     */
    @Test
    public void accordingGMShouldPushCupAndPushLid(){
        tower.pushCupType("Opener", 3);
        tower.pushLidType("Crazy", 4);
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("3",   items[0][1]);
        assertEquals("lid", items[1][0]);
        assertEquals("4", items[1][1]);
    }
    
    /**
     * Verifica que los nuevos elementos se pueden quitar de manera satisfactoria en la torre.
     */
    @Test
    public void accordingGMShouldPopCupAndPopLid(){
        tower.pushCupType("Opener", 3);
        tower.pushLidType("Crazy", 4);
        tower.popCup();
        String[][] postPopCup = tower.stackingItems();
        assertEquals(1, postPopCup.length);
        tower.popLid();
        String[][] postPopLid = tower.stackingItems();
        assertEquals(0, postPopLid.length);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown(){
        tower = null;
        opener = null;
        hierarchical = null;
        fearful = null;
        crazy = null;
    }
}