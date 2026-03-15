import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas de unidad para Tower - Ciclo 1.
 * Todas las pruebas corren en modo invisible (sin interfaz grafica).
 * Cubre requisitos funcionales 1 al 9.
 *
 * Para cada requisito se prueban dos preguntas:
 *   - ¿Que DEBERIA hacer?  (casos positivos)
 *   - ¿Que NO deberia hacer? (casos negativos / limites)
 *
 * Convencion de nombres: accordingGMShould... (GM: Garcia, Muñoz)
 *
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (14/02/2026)
 */
public class TowerTest {

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
    // REQUISITO 1 - Crear torre dados ancho y alto
    // =========================================================

    /** Deberia: la torre nueva empieza vacia con altura cero */
    @Test
    public void accordingGMShouldCreateEmptyTower() {
        assertEquals(0, tower.height());
        assertEquals(0, tower.stackingItems().length);
    }

    /** Deberia: ok() retorna true cuando la torre esta operativa */
    @Test
    public void accordingGMShouldBeOperativeAfterCreation() {
        assertTrue(tower.ok());
    }

    // =========================================================
    // REQUISITO 2 - Adicionar o eliminar una taza
    // =========================================================

    /** Deberia: agregar una taza la registra en stackingItems */
    @Test
    public void accordingGMShouldPushCup() {
        tower.pushCup(3);
        String[][] items = tower.stackingItems();
        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("3",   items[0][1]);
    }

    /** Deberia: agregar varias tazas las apila en orden de insercion */
    @Test
    public void accordingGMShouldPushMultipleCupsInOrder() {
        tower.pushCup(3);
        tower.pushCup(1);
        String[][] items = tower.stackingItems();
        assertEquals("3", items[0][1]);
        assertEquals("1", items[1][1]);
    }

    /** Deberia: popCup remueve la ultima taza ingresada */
    @Test
    public void accordingGMShouldPopCup() {
        tower.pushCup(2);
        tower.pushCup(1);
        tower.popCup();
        assertEquals(1, tower.stackingItems().length);
        assertEquals("2", tower.stackingItems()[0][1]);
    }

    /** Deberia: removeCup elimina una taza especifica del medio */
    @Test
    public void accordingGMShouldRemoveCupFromMiddle() {
        tower.pushCup(3);
        tower.pushCup(2);
        tower.pushCup(1);
        tower.removeCup(2);
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
        assertEquals("3", items[0][1]);
        assertEquals("1", items[1][1]);
    }

    /** Deberia: removeCup de la base deja el resto intacto */
    @Test
    public void accordingGMShouldRemoveCupFromBase() {
        tower.pushCup(3);
        tower.pushCup(1);
        tower.removeCup(3);
        assertEquals(1, tower.stackingItems().length);
        assertEquals("1", tower.stackingItems()[0][1]);
    }

    /** NO deberia: no permite agregar taza con numero cero */
    @Test
    public void accordingGMShouldNotPushCupWithZero() {
        tower.pushCup(0);
        assertEquals(0, tower.stackingItems().length);
    }

    /** NO deberia: no permite agregar taza con numero negativo */
    @Test
    public void accordingGMShouldNotPushCupWithNegativeNumber() {
        tower.pushCup(-3);
        assertEquals(0, tower.stackingItems().length);
    }

    /** NO deberia: no permite agregar la misma taza dos veces */
    @Test
    public void accordingGMShouldNotPushDuplicateCup() {
        tower.pushCup(2);
        tower.pushCup(2);
        assertEquals(1, tower.stackingItems().length);
    }

    /** NO deberia: no permite agregar taza que supera el ancho maximo */
    @Test
    public void accordingGMShouldNotPushCupExceedingMaxWidth() {
        tower.pushCup(100);
        assertEquals(0, tower.stackingItems().length);
    }

    /** NO deberia: popCup en torre vacia no modifica nada */
    @Test
    public void accordingGMShouldNotPopCupFromEmptyTower() {
        tower.popCup();
        assertEquals(0, tower.stackingItems().length);
    }

    /** NO deberia: removeCup con numero inexistente no modifica nada */
    @Test
    public void accordingGMShouldNotRemoveCupThatDoesNotExist() {
        tower.pushCup(2);
        tower.removeCup(5);
        assertEquals(1, tower.stackingItems().length);
    }

    // =========================================================
    // REQUISITO 3 - Adicionar o eliminar una tapa
    // =========================================================

    /** Deberia: agregar una tapa la registra en stackingItems */
    @Test
    public void accordingGMShouldPushLid() {
        tower.pushCup(2);
        tower.pushLid(2);
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
        assertEquals("lid", items[1][0]);
        assertEquals("2",   items[1][1]);
    }

    /** Deberia: pushLid asocia la tapa a la taza cuando es la cima */
    @Test
    //Error, hay que mirarlo
    public void accordingGMShouldAssociateLidToCupAfterCover() {
        tower.pushCup(2);
        tower.pushLid(2);
        int[] lided = tower.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(2, lided[0]);
    }

    /** Deberia: popLid remueve solo la tapa, la taza permanece */
    @Test
    public void accordingGMShouldPopLidLeavingCupIntact() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.popLid();
        String[][] items = tower.stackingItems();
        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("2",   items[0][1]);
    }

    /** Deberia: removeLid elimina tapa del medio, elementos encima permanecen */
    @Test
    public void accordingGMShouldRemoveLidFromMiddle() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.pushCup(1);
        tower.removeLid(2);
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("cup", items[1][0]);
    }

    /** NO deberia: no permite agregar tapa con numero negativo */
    @Test
    public void accordingGMShouldNotPushLidWithNegativeNumber() {
        tower.pushLid(-1);
        assertEquals(0, tower.stackingItems().length);
    }

    /** NO deberia: no permite agregar la misma tapa dos veces */
    @Test
    public void accordingGMShouldNotPushDuplicateLid() {
        tower.pushCup(2);
        tower.pushLid(2);
        tower.pushLid(2);
        assertEquals(2, tower.stackingItems().length);
    }

    /** NO deberia: popLid en torre sin tapas no modifica nada */
    @Test
    public void accordingGMShouldNotPopLidWhenNoLidsExist() {
        tower.pushCup(2);
        tower.popLid();
        assertEquals(1, tower.stackingItems().length);
    }

    /** NO deberia: removeLid con numero inexistente no modifica nada */
    @Test
    public void accordingGMShouldNotRemoveLidThatDoesNotExist() {
        tower.pushCup(2);
        tower.removeLid(5);
        assertEquals(1, tower.stackingItems().length);
    }

    // =========================================================
    // REQUISITO 4 - Ordenar de mayor a menor
    // =========================================================

    /** Deberia: orderTower ordena tazas de mayor a menor */
    @Test
    public void accordingGMShouldOrderTowerDescending() {
        tower.pushCup(1);
        tower.pushCup(3);
        tower.pushCup(2);
        tower.orderTower();
        String[][] items = tower.stackingItems();
        assertEquals("3", items[0][1]);
        assertEquals("2", items[1][1]);
        assertEquals("1", items[2][1]);
    }

    /** Deberia: orderTower en torre ya ordenada no la modifica */
    @Test
    public void accordingGMShouldKeepOrderWhenAlreadySorted() {
        tower.pushCup(3);
        tower.pushCup(2);
        tower.pushCup(1);
        tower.orderTower();
        String[][] items = tower.stackingItems();
        assertEquals("3", items[0][1]);
        assertEquals("1", items[2][1]);
    }

    /** NO deberia: orderTower en torre vacia no falla */
    @Test
    public void accordingGMShouldNotFailOrderOnEmptyTower() {
        tower.orderTower();
        assertEquals(0, tower.stackingItems().length);
    }

    // =========================================================
    // REQUISITO 5 - Colocar en orden inverso
    // =========================================================

    /** Deberia: reverseTower invierte el orden actual */
    @Test
    public void accordingGMShouldReverseTower() {
        tower.pushCup(3);
        tower.pushCup(2);
        tower.pushCup(1);
        tower.reverseTower();
        String[][] items = tower.stackingItems();
        assertEquals("1", items[0][1]);
        assertEquals("2", items[1][1]);
        assertEquals("3", items[2][1]);
    }

    /** Deberia: reverseTower dos veces regresa al orden original */
    @Test
    public void accordingGMShouldReturnToOriginalAfterDoubleReverse() {
        tower.pushCup(3);
        tower.pushCup(2);
        tower.pushCup(1);
        tower.reverseTower();
        tower.reverseTower();
        String[][] items = tower.stackingItems();
        assertEquals("3", items[0][1]);
        assertEquals("1", items[2][1]);
    }

    /** NO deberia: reverseTower en torre vacia no falla */
    @Test
    public void accordingGMShouldNotFailReverseOnEmptyTower() {
        tower.reverseTower();
        assertEquals(0, tower.stackingItems().length);
    }

    // =========================================================
    // REQUISITO 6 - Consultar altura
    // =========================================================

    /** Deberia: altura es cero para torre vacia */
    @Test
    public void accordingGMShouldReturnZeroHeightForEmptyTower() {
        assertEquals(0, tower.height());
    }

    /** Deberia: caso oficial del enunciado - 4,2,3,1 da altura 9 */
    @Test
    public void accordingGMShouldMatchOfficialSampleHeight() {
        tower.pushCup(4);
        tower.pushCup(2);
        tower.pushCup(3);
        tower.pushCup(1);
        assertEquals(9, tower.height());
    }

    /** Deberia: altura aumenta al agregar tapa */
    @Test
    public void accordingGMShouldIncreaseHeightWhenLidAdded() {
        tower.pushCup(2);
        int heightSinTapa = tower.height();
        tower.pushLid(2);
        assertEquals(heightSinTapa + 1, tower.height());
    }

    /** Deberia: altura disminuye al quitar tapa */
    @Test
    public void accordingGMShouldDecreaseHeightWhenLidRemoved() {
        tower.pushCup(2);
        tower.pushLid(2);
        int heightConTapa = tower.height();
        tower.popLid();
        assertEquals(heightConTapa - 1, tower.height());
    }

    // =========================================================
    // REQUISITO 7 - Tazas tapadas y elementos apilados
    // =========================================================

    /** Deberia: lidedCups retorna tazas con tapa asignada */
    @Test
    //Error, hay que mirar
    public void accordingGMShouldReturnLidedCups() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushCup(1);
        int[] lided = tower.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(3, lided[0]);
    }

    /** Deberia: lidedCups retorna array vacio cuando no hay tazas tapadas */
    @Test
    public void accordingGMShouldReturnEmptyLidedCupsWhenNone() {
        tower.pushCup(2);
        assertEquals(0, tower.lidedCups().length);
    }

    /** Deberia: lidedCups retorna numeros ordenados de menor a mayor */
    @Test
    //Error, hay que mirarlo
    public void accordingGMShouldReturnLidedCupsSorted() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushCup(1);
        tower.pushLid(1);
        int[] lided = tower.lidedCups();
        assertEquals(1, lided[0]);
        assertEquals(3, lided[1]);
    }

    /** Deberia: stackingItems refleja tipo y numero de cada elemento */
    @Test
    public void accordingGMShouldReturnCorrectStackingItems() {
        tower.pushCup(2);
        tower.pushLid(2);
        String[][] items = tower.stackingItems();
        assertEquals("cup", items[0][0]);
        assertEquals("2",   items[0][1]);
        assertEquals("lid", items[1][0]);
        assertEquals("2",   items[1][1]);
    }

    /** Deberia: stackingItems retorna array vacio para torre vacia */
    @Test
    public void accordingGMShouldReturnEmptyStackingItemsForEmptyTower() {
        assertEquals(0, tower.stackingItems().length);
    }

    // =========================================================
    // REQUISITO 8 - Hacer visible o invisible
    // =========================================================

    /** Deberia: funcionar correctamente en modo invisible */
    @Test
    public void accordingGMShouldWorkInInvisibleMode() {
        tower.pushCup(2);
        tower.pushLid(2);
        assertEquals(2, tower.stackingItems().length);
        // cup2 tiene altura 2*2-1=3 cm, mas 1 cm de tapa = 4 cm total
        assertEquals(4, tower.height());
    }

    /** Deberia: makeVisible y makeInvisible no rompen el estado */
    @Test
    public void accordingGMShouldToggleVisibilityWithoutLosingState() {
        tower.pushCup(2);
        tower.makeVisible();
        tower.makeInvisible();
        assertEquals(1, tower.stackingItems().length);
        assertEquals("2", tower.stackingItems()[0][1]);
    }
    
}