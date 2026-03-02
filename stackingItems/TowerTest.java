import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Simulador de torre de tazas apilables.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 28/02/2026
 */
public class TowerTest{
    int maxHeight;
    int maxWidth;
    Tower tower;
    
    @Before
    public void setUp(){
        maxHeight = 9;
        maxWidth = 7;
        tower = new Tower(maxHeight, maxWidth);
    }
    
    /**
     * Este metodo de prueba se encarga de probar createTower y verificar que esta funcionando correctamente.
     */
    @Test
    public void shouldCreateTower(){
        tower.createTower(4);
    }
    
    /**
     * Este metodo se encarga de prograr swap y verificar que esta funcionando de manera correcta
     */
    
    @Test
    public void shouldSwap(){
        tower.pushCup(2);
        tower.pushCup(3);
        tower.swap(2, 3);
    }
    
    @After
    public void tearDown(){}
}