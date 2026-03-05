import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas de unidad para Tower - Ciclo 2.
 * Todas las pruebas corren en modo invisible (sin interfaz grafica).
 * Cubre requisitos funcionales 10 al 13.
 * Incluye pruebas de regresion para requisitos 1-9.
 *
 * Para cada requisito se prueban dos preguntas:
 *   - ¿Que DEBERIA hacer?  (casos positivos)
 *   - ¿Que NO deberia hacer? (casos negativos / limites)
 *
 * Convencion de nombres: accordingGMShould... (GM: Garcia, Muñoz)
 *
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 2.0 (28/02/2026)
 */
public class TowerC2Test {

    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(15, 7);
        tower.makeInvisible();
    }

    @After
    public void tearDown() {
        tower = null;
    }

    // =========================================================
    // REQUISITO 10 - Crear torre con N tazas automaticas
    // =========================================================

    /** Deberia: Tower(cups) crea exactamente N tazas */
    @Test
    public void accordingGMShouldCreateTowerWithNCups() {
        Tower t = new Tower(4);
        t.makeInvisible();
        assertEquals(4, t.stackingItems().length);
    }

    /** Deberia: Tower(cups) apila de mayor a menor (taza grande en la base) */
    @Test
    public void accordingGMShouldStackCupsLargestFirst() {
        Tower t = new Tower(3);
        t.makeInvisible();
        String[][] items = t.stackingItems();
        assertEquals("3", items[0][1]); // base
        assertEquals("2", items[1][1]);
        assertEquals("1", items[2][1]); // cima
    }

    /** Deberia: createTower agrega N tazas a una torre existente */
    @Test
    public void accordingGMShouldCreateTowerUsingMethod() {
        tower.createTower(3);
        assertEquals(3, tower.stackingItems().length);
    }

    /** Deberia: createTower apila de mayor a menor */
    @Test
    public void accordingGMShouldCreateTowerMethodStacksDescending() {
        tower.createTower(3);
        String[][] items = tower.stackingItems();
        assertEquals("3", items[0][1]);
        assertEquals("1", items[2][1]);
    }

    /** Deberia: Tower(1) crea exactamente una taza */
    @Test
    public void accordingGMShouldCreateTowerWithOneCup() {
        Tower t = new Tower(1);
        t.makeInvisible();
        assertEquals(1, t.stackingItems().length);
        assertEquals("1", t.stackingItems()[0][1]);
    }

    /** NO deberia: createTower con mas tazas que el maximo no agrega nada */
    @Test
    public void accordingGMShouldNotCreateTowerWhenCupsExceedMax() {
        tower.createTower(100);
        assertEquals(0, tower.stackingItems().length);
    }

    /** NO deberia: createTower con cero no agrega tazas */
    @Test
    public void accordingGMShouldNotCreateTowerWithZeroCups() {
        tower.createTower(0);
        assertEquals(0, tower.stackingItems().length);
    }

    // =========================================================
    // REQUISITO 11 - Intercambiar posicion de dos objetos
    // =========================================================

    /** Deberia: swap intercambia dos tazas correctamente */
    @Test
    public void accordingGMShouldSwapTwoCups() {
        tower.pushCup(3);
        tower.pushCup(1);
        String[] c3 = {"cup", "3"};
        String[] c1 = {"cup", "1"};
        tower.swap(c3, c1);
        String[][] items = tower.stackingItems();
        assertEquals("1", items[0][1]); // cup 1 ahora en la base
        assertEquals("3", items[1][1]); // cup 3 ahora en la cima
    }

    /** Deberia: swap entre taza y tapa funciona */
    @Test
    public void accordingGMShouldSwapCupAndLid() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.pushCup(1);
        // Secuencia antes: [cup2(0), lid2(1), cup1(2)]
        String[] c1   = {"cup", "1"};
        String[] lid2 = {"lid", "2"};
        tower.swap(c1, lid2);
        // Secuencia despues: [cup2(0), cup1(1), lid2(2)]
        String[][] items = tower.stackingItems();
        assertEquals("cup", items[1][0]);
        assertEquals("1",   items[1][1]);
        assertEquals("lid", items[2][0]);
        assertEquals("2",   items[2][1]);
    }

    /** Deberia: la altura puede cambiar despues de un swap */
    @Test
    public void accordingGMShouldChangeHeightAfterSwap() {
        tower.pushCup(1);
        tower.pushCup(3);
        int heightAntes = tower.height();
        String[] c1 = {"cup", "1"};
        String[] c3 = {"cup", "3"};
        tower.swap(c1, c3);
        // no necesariamente cambia pero no debe lanzar excepcion
        assertTrue(tower.height() >= 0);
    }

    /** Deberia: swap con tres elementos intercambia solo los indicados */
    @Test
    public void accordingGMShouldSwapOnlySpecifiedElements() {
        tower.pushCup(3);
        tower.pushCup(2);
        tower.pushCup(1);
        String[] c3 = {"cup", "3"};
        String[] c1 = {"cup", "1"};
        tower.swap(c3, c1);
        String[][] items = tower.stackingItems();
        assertEquals("1", items[0][1]); // intercambiado
        assertEquals("2", items[1][1]); // sin cambio
        assertEquals("3", items[2][1]); // intercambiado
    }

    /** NO deberia: swap con objeto inexistente no modifica la torre */
    @Test
    public void accordingGMShouldNotSwapWhenFirstObjectNotFound() {
        tower.pushCup(2);
        tower.pushCup(1);
        String[][] itemsAntes = tower.stackingItems();
        String[] missing = {"cup", "9"};
        String[] c1      = {"cup", "1"};
        tower.swap(missing, c1);
        String[][] itemsDespues = tower.stackingItems();
        assertEquals(itemsAntes.length, itemsDespues.length);
        assertEquals(itemsAntes[0][1], itemsDespues[0][1]);
    }

    /** NO deberia: swap del mismo objeto consigo mismo no modifica nada */
    @Test
    public void accordingGMShouldNotSwapObjectWithItself() {
        tower.pushCup(2);
        tower.pushCup(1);
        String[] c1 = {"cup", "1"};
        tower.swap(c1, c1);
        assertEquals("2", tower.stackingItems()[0][1]);
        assertEquals("1", tower.stackingItems()[1][1]);
    }

    /** NO deberia: swap en torre vacia no lanza excepcion */
    @Test
    public void accordingGMShouldNotFailSwapOnEmptyTower() {
        String[] c1 = {"cup", "1"};
        String[] c2 = {"cup", "2"};
        tower.swap(c1, c2);
        assertEquals(0, tower.stackingItems().length);
    }

    // =========================================================
    // REQUISITO 12 - Tapar tazas con tapas disponibles en torre
    // =========================================================

    /** Deberia: cover asocia tapa a taza cuando son adyacentes con mismo numero */
    @Test
    public void accordingGMShouldCoverCupWithAdjacentLid() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.cover();
        int[] lided = tower.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(2, lided[0]);
    }

    /** Deberia: cover con multiple pares adyacentes los tapa todos */
    @Test
    public void accordingGMShouldCoverMultiplePairs() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushCup(1);
        tower.pushLid(1);
        tower.cover();
        int[] lided = tower.lidedCups();
        assertEquals(2, lided.length);
    }

    /** Deberia: cover no modifica la altura de la torre */
    @Test
    public void accordingGMShouldKeepHeightAfterCover() {
        tower.pushCup(2);
        tower.pushLid(2);
        int heightAntes = tower.height();
        tower.cover();
        assertEquals(heightAntes, tower.height());
    }

    /** Deberia: cover no modifica el numero de elementos apilados */
    @Test
    public void accordingGMShouldKeepStackSizeAfterCover() {
        tower.pushCup(2);
        tower.pushLid(2);
        int sizeAntes = tower.stackingItems().length;
        tower.cover();
        assertEquals(sizeAntes, tower.stackingItems().length);
    }

     /** NO deberia: cover no asocia tapa cuando no es adyacente a su taza */
    @Test
    public void accordingGMShouldNotCoverWhenLidIsNotAdjacent() {
        tower.pushCup(2);
        tower.pushCup(1);
        tower.pushLid(2); // tapa 2 no esta justo encima de taza 2
        tower.cover();
        assertEquals(0, tower.lidedCups().length);
    }

    /** NO deberia: cover en torre vacia no lanza excepcion */
    @Test
    public void accordingGMShouldNotFailCoverOnEmptyTower() {
        tower.cover();
        assertEquals(0, tower.stackingItems().length);
    }

    /** NO deberia: cover no tapa dos veces una taza ya tapada */
    @Test
    public void accordingGMShouldNotCoverAlreadyCoveredCup() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.cover();
        tower.cover(); // segunda llamada no debe romper nada
        assertEquals(1, tower.lidedCups().length);
    }

    // =========================================================
    // REQUISITO 13 - Consultar swap que reduzca la altura
    // =========================================================

    /** Deberia: swapToReduce retorna un par cuando existe swap que reduce */
    @Test
    public void accordingGMShouldReturnSwapWhenHeightCanBeReduced() {
        tower.pushCup(1);
        tower.pushCup(3); // orden suboptimo: 1,3 es mas alto que 3,1
        String[][] result = tower.swapToReduce();
        assertNotNull(result);
        assertEquals(2, result.length);
    }

    /** Deberia: aplicar el swap sugerido efectivamente reduce la altura */
    @Test
    public void accordingGMShouldReduceHeightAfterApplyingSwapToReduce() {
        tower.pushCup(1);
        tower.pushCup(3);
        int heightAntes  = tower.height();
        String[][] pair  = tower.swapToReduce();
        assertNotNull(pair);
        tower.swap(pair[0], pair[1]);
        assertTrue(tower.height() < heightAntes);
    }

    /** Deberia: swapToReduce retorna par con tipo y numero validos */
    @Test
    public void accordingGMShouldReturnValidIdentifiersInSwapToReduce() {
        tower.pushCup(1);
        tower.pushCup(3);
        String[][] pair = tower.swapToReduce();
        assertNotNull(pair);
        assertTrue(pair[0][0].equals("cup") || pair[0][0].equals("lid"));
        assertTrue(pair[1][0].equals("cup") || pair[1][0].equals("lid"));
    }

    /** NO deberia: swapToReduce retorna null cuando ningun swap reduce */
    @Test
    public void accordingGMShouldReturnNullWhenNoSwapReducesHeight() {
        // Orden 3,2,1 ya es el optimo, ningun swap lo mejora
        tower.pushCup(3);
        tower.pushCup(2);
        tower.pushCup(1);
        assertNull(tower.swapToReduce());
    }

    /** NO deberia: swapToReduce en torre vacia retorna null */
    @Test
    public void accordingGMShouldReturnNullForEmptyTower() {
        assertNull(tower.swapToReduce());
    }

    /** NO deberia: swapToReduce en torre con una sola taza retorna null */
    @Test
    public void accordingGMShouldReturnNullForSingleCupTower() {
        tower.pushCup(2);
        assertNull(tower.swapToReduce());
    }

    // =========================================================
    // PRUEBAS DE REGRESION - Requisitos 1-9 siguen funcionando
    // =========================================================

    /** Regresion: caso oficial del enunciado sigue dando altura 9 */
    @Test
    public void accordingGMShouldStillMatchOfficialSampleHeight() {
        tower.pushCup(4);
        tower.pushCup(2);
        tower.pushCup(3);
        tower.pushCup(1);
        assertEquals(9, tower.height());
    }

    /** Regresion: orderTower sigue funcionando despues de refactoring */
    @Test
    public void accordingGMShouldStillOrderTowerCorrectly() {
        tower.pushCup(1);
        tower.pushCup(3);
        tower.pushCup(2);
        tower.orderTower();
        assertEquals("3", tower.stackingItems()[0][1]);
        assertEquals("1", tower.stackingItems()[2][1]);
    }

    /** Regresion: popLid no elimina la taza, solo la tapa */
    @Test
    public void accordingGMShouldStillPopLidWithoutRemovingCup() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.popLid();
        assertEquals(1, tower.stackingItems().length);
        assertEquals("cup", tower.stackingItems()[0][0]);
    }
}