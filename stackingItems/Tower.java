import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JOptionPane;
import java.util.Collections;

/**
 * Simulador de torre de tazas apilables (stackingItems).
 *
 * 
 *
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 2.0 (04/03/2026)
 */
public class Tower {

    private ArrayList<Cup> stack;
    private ArrayList<Lid> stackLids;
<<<<<<< HEAD
=======
    private ArrayList<Integer> lids;
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
    private int maxCups;
    private int maxHeight;
    private int currentHeight;
    private int currentLevel;
    private ArrayList<Integer> space;
    private ArrayList<States> history;
    private TowerGrid grid;
    private ArrayList<CupVisual> cupVisuals;
    private ArrayList<LidVisual> lidVisuals;
    private boolean isVisible;

    // =========================================================
    // ===================== CICLO 1 ===========================
    // =========================================================

    // ---------------------------------------------------------
    // REQUISITO 1 - Crear torre dados ancho y alto
    // ---------------------------------------------------------

    /**
     * Crea una torre vacia con dimensiones especificas.
     * Requisito 1 - Ciclo 1.
     *
     * @param maxHeight altura maxima de la torre en cm
     * @param maxWidth  ancho maximo de la torre en cm
     */
    public Tower(int maxHeight, int maxWidth) {
<<<<<<< HEAD
        initializeTower(maxHeight, maxWidth);
=======
        // Inicializar modelo
        this.height = maxHeight;
        this.maxCups = (maxWidth + 1) / 2;
        this.stack = new ArrayList<>();
        this.stackLids = new ArrayList<>();
        this.lids = new ArrayList<>();
        this.history = new ArrayList<>();
        
        // Estado inicial
        this.currentHeight = 0;
        this.currentLevel = 0;
        this.space = new ArrayList<>();
        
        // Inicializar vista
        this.grid = new TowerGrid(maxHeight, maxWidth);
        this.cupVisuals = new ArrayList<>();
        this.lidVisuals = new ArrayList<>();
        this.isVisible = true;
        
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
        grid.makeVisible();
    }

    // ---------------------------------------------------------
    // REQUISITO 2 - Adicionar o eliminar una taza
    // ---------------------------------------------------------

    /**
<<<<<<< HEAD
     * Agrega una taza a la cima de la torre.
     * Valida numero positivo, que quepa en ancho y alto, y unicidad.
     * Requisito 2 - Ciclo 1.
     *
     * @param cupNumber numero de la taza (>= 1)
=======
     * Este metodo se encarga de crear una cantidad n de copas que se le son dadas
     * @param: recibe cups que es un entero que representa la cantidad de copas que se deben agregar
     */
    public void createTower(int cups){
        if(cups > maxCups){
            showError("El numero de tazas que quiere ingresar supera el tamaño maximo");
            return;
        }
        for(int i = cups; i > 0; i--){
            pushCup(i);
        }
    }
    
    /**
     * Añade una taza al stack
     * @param cupNumber numero de la taza
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
     */
    public void pushCup(int cupNumber) {
        if (cupNumber <= 0) {
            showError("El numero de taza debe ser positivo");
            return;
        }
<<<<<<< HEAD
        else if (cupNumber > maxCups) {
            showError("La taza " + cupNumber + " supera el ancho maximo");
            return;
        }
        else if (cupExistsInStack(cupNumber)) {
            showError("La taza " + cupNumber + " ya esta en la torre");
            return;
=======

        for(int i = 0; i < stack.size(); i++){
            if (stack.get(i) != null){
                if(stack.get(i).getNumber() == cupNumber){
                    showError("Esta taza ya esta dentro de la torre");
                    return;
                }
            }
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
        }

        history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));

        Cup newCup = new Cup(cupNumber);
<<<<<<< HEAD
        int lidIdx = findLidIndex(cupNumber);
        if (lidIdx != -1) newCup.setLid(stackLids.get(lidIdx));

        stack.add(newCup);
        stackLids.add(null);
=======
        int index = -1;
        for(int i = 0; i < stackLids.size(); i++){
            if(stackLids.get(i) != null){   
                if(stackLids.get(i).getNumber() == cupNumber){
                    index = i;
                    break;
                }
            }
        }
        if(index != -1){
            Lid lid = stackLids.get(index);
            newCup.setLid(lid);
        }
        stack.add(newCup);
        stackLids.add(null);
        
        // Actualizar logica de apilamiento
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
        updateStackLogic(newCup.getHeight());

        CupVisual visual = new CupVisual(newCup);
        cupVisuals.add(visual);
        if (isVisible) drawCup(visual, currentLevel);
        currentLevel += 1;

        if (currentHeight > maxHeight) {
            showError("La taza " + cupNumber + " supera la altura maxima");
            popCup();
        }
    }

    /**
     * Remueve la taza en la cima de la torre.
     * Guarda las tapas que hubiera encima y las vuelve a colocar.
     * Requisito 2 - Ciclo 1.
     */
    public void popCup() {
<<<<<<< HEAD
        if (!hasCupsInStack()) {
            showError("No hay tazas para remover");
            return;
        }

        ArrayList<Integer> lidsAbove = new ArrayList<>();
        int topCupIdx = getLastCupIndex();
        for (int i = stack.size() - 1; i > topCupIdx; i--) {
            if (stackLids.get(i) != null) {
                lidsAbove.add(stackLids.get(i).getNumber());
                removeLidVisualLast();
                removeLastFromStack();
                restoreLastState();
            }
        }

        removeCupVisualLast();
        removeLastFromStack();
        restoreLastState();

        for (int i = lidsAbove.size() - 1; i >= 0; i--) {
            pushLid(lidsAbove.get(i));
=======
        int count = 0;
        if (stack.isEmpty()){
            showError("No hay tazas para remover");
            return;
        }
        for(int i = 0; i < stack.size(); i++){
            if(stack.get(i) == null){
                count += 1;
            }
        }
        if(count == stack.size()){
            showError("No hay tazas para remover");
            return;
        }
        
        // Remover visual
        int lastIndex = cupVisuals.size() - 1;
        ArrayList<Lid> lidsCount = new ArrayList<>();
        int i = stack.size()-1;
        while(stack.get(i) == null){
            lidsCount.add(stackLids.get(i));
            popLid();
            i--; 
        }
        
        cupVisuals.get(lastIndex).erase();
        cupVisuals.remove(lastIndex);
        
        // Remover taza
        int taza = stack.get(i).getNumber();
        stack.remove(i);
        stackLids.remove(i);
        
        // Restaurar estado previo
        if (!history.isEmpty()) {
            States prev = history.remove(history.size() - 1);
            currentHeight = prev.getCurrentHeight();
            currentLevel = prev.getCurrentLevel();
            space = new ArrayList<>();
            space = prev.getSpace();
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
        }
        
        for(int h = lidsCount.size()-1; h >= 0; h--){
            pushLid(lidsCount.get(h).getNumber());
            /*
            if(lidsCount.get(h).getNumber() != taza){
                pushLid(lidsCount.get(h).getNumber());
            }
            */
            lidsCount.remove(h);
        }
    }

    /**
     * Remueve una taza especifica de cualquier posicion.
     * Los elementos encima se guardan y se vuelven a apilar.
     * Requisito 2 - Ciclo 1.
     *
     * @param cupNumber numero de la taza a remover
     */
    public void removeCup(int cupNumber) {
<<<<<<< HEAD
        int index = findCupIndex(cupNumber);
=======
        // Buscar la taza
        int index = -1;
        for (int i = 0; i < stack.size(); i++) {
            if(stack.get(i) != null){
                if (stack.get(i).getNumber() == cupNumber) {
                    index = i;
                    break;
                }
            }
        }
        
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
        if (index == -1) {
            showError("No se encontro la taza " + cupNumber);
            return;
        }
<<<<<<< HEAD
        ArrayList<String[]> above = collectAbove(index);
        popCup();
        restoreAbove(above);
=======
        
        // Guardar tazas encima
        ArrayList<Integer> aboveCups = new ArrayList<>();
        ArrayList<Integer> aboveLids = new ArrayList<>();
        for (int i = stack.size() - 1; i > index; i--){
            if(stack.get(i) != null){
                aboveCups.add(stack.get(i).getNumber());
                aboveLids.add(null);
                popCup();
            }
            else{
                aboveLids.add(stackLids.get(i).getNumber());
                aboveCups.add(null);
                popLid();
            }
        }
        
        // Remover la taza objetivo
        popCup();
        
        // Re-apilar las tazas que estaban encima
        for (int i = aboveCups.size() - 1; i >= 0; i--){
            if(aboveCups.get(i) != null){
                pushCup(aboveCups.get(i));
            }
            else{
                pushLid(aboveLids.get(i));
            }
        }
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
    }

    // ---------------------------------------------------------
    // REQUISITO 3 - Adicionar o eliminar una tapa
    // ---------------------------------------------------------

    /**
     * Agrega una tapa a la cima de la torre.
     * Si existe una taza con el mismo numero, la asocia automaticamente.
     * Requisito 3 - Ciclo 1.
     *
     * @param lidNumber numero de la tapa (>= 1)
     */
    public void pushLid(int lidNumber) {
<<<<<<< HEAD
        if (lidNumber <= 0) {
            showError("El numero de tapa debe ser positivo");
            return;
        }
        else if (lidNumber > maxCups) {
            showError("La tapa " + lidNumber + " supera el ancho maximo");
            return;
        }
        else if (lidExistsInStack(lidNumber)) {
            showError("La tapa " + lidNumber + " ya esta en la torre");
            return;
        }

        history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));

        Lid newLid = new Lid(lidNumber);
        int cupIdx = findCupIndex(lidNumber);
        if (cupIdx != -1) stack.get(cupIdx).setLid(newLid);

        stack.add(null);
        stackLids.add(newLid);
        updateStackLogicLid(newLid.getWidth());

        LidVisual visual = new LidVisual(newLid);
        lidVisuals.add(visual);
        if (isVisible) drawLid(visual, currentLevel);
        currentLevel += 1;

        if (currentHeight > maxHeight) {
            showError("La tapa " + lidNumber + " supera la altura maxima");
            popLid();
=======
        if(lidNumber > maxCups){
            showError("La tapa supera el ancho maximo");
            return;
        }
        
        if (lidNumber <= 0) {
            showError("La tapa de taza debe ser positivo");
            return;
        }
        
        for(int i = 0; i < stackLids.size(); i++){
            if (stackLids.get(i) != null){
                if(stackLids.get(i).getNumber() == lidNumber){
                    showError("Esta tapa ya esta dentro de la torre");
                    return;
                }
            }
        }
        
        States state = new States(currentHeight, currentLevel, new ArrayList<>(space));
        history.add(state);
        
        Lid newLid = new Lid(lidNumber);
        int index = -1;
        for(int i = 0; i < stack.size(); i++){
            if(stack.get(i) != null){   
                if(stack.get(i).getNumber() == lidNumber){
                    index = i;
                    break;
                }
            }
        }
        if(index != -1){
            Cup cup = stack.get(index);
            cup.setLid(newLid);
        }
        stackLids.add(newLid);
        stack.add(null);
        
        updateStackLogicLid(newLid.getWidth());
        
        LidVisual visual = new LidVisual(newLid);
        lidVisuals.add(visual);
        
        if (isVisible) {
            drawLid(visual, currentLevel);
        }
        currentLevel += 1;
        
        //Verificacion final
        if(currentHeight > height){
            showError("Has superado el limite maximo");
            popLid();
            return;
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
        }
    }

    /**
     * Remueve la tapa en la cima de la torre.
     * Guarda las tazas que hubiera encima y las vuelve a colocar.
     * Requisito 3 - Ciclo 1.
     */
<<<<<<< HEAD
    public void popLid() {
        if (!hasLidsInStack()) {
            showError("No hay tapas para remover");
            return;
        }

        ArrayList<Integer> cupsAbove = new ArrayList<>();
        int topLidIdx = getLastLidIndex();
        for (int i = stack.size() - 1; i > topLidIdx; i--) {
            if (stack.get(i) != null) {
                cupsAbove.add(stack.get(i).getNumber());
                removeCupVisualLast();
                removeLastFromStack();
                restoreLastState();
            }
        }

        int lidNum = stackLids.get(topLidIdx).getNumber();
        int cupIdx = findCupIndex(lidNum);
        if (cupIdx != -1) stack.get(cupIdx).removeLid();

        removeLidVisualLast();
        removeLastFromStack();
        restoreLastState();

        for (int i = cupsAbove.size() - 1; i >= 0; i--) {
            pushCup(cupsAbove.get(i));
        }
=======
    public void popLid(){
        int count = 0;
        if (stackLids.isEmpty()){
            showError("No hay tapas para remover");
            return;
        }
        for(int i = 0; i < stackLids.size(); i++){
            if(stackLids.get(i) == null){
                count += 1;
            }
        }
        if(count == stackLids.size()){
            showError("No hay tapas para remover");
            return;
        }
        
        // Remover visual
        int lastIndex = lidVisuals.size() - 1;
        ArrayList<Cup> cupCount = new ArrayList<>();
        int i = stackLids.size()-1;
        while(stackLids.get(i) == null){
            cupCount.add(stack.get(i));
            popCup();
            i--; 
        }
        
        lidVisuals.get(lastIndex).erase();
        for(int c = 0; c < stack.size(); c++){
            if(stack.get(c) != null){
                if(stack.get(c).getNumber() == stackLids.get(i).getNumber()){
                    stack.get(c).removeLid();
                    break;
                }
            }
        }
        lidVisuals.remove(lastIndex);
        
        // Remover tapa
        stack.remove(i);
        stackLids.remove(i);
        
        // Restaurar estado previo
        if (!history.isEmpty()) {
            States prev = history.remove(history.size() - 1);
            currentHeight = prev.getCurrentHeight();
            currentLevel = prev.getCurrentLevel();
            space = new ArrayList<>();
            space = prev.getSpace();
        }
        
        for(int h = cupCount.size()-1; h >= 0; h--){
            pushCup(cupCount.get(h).getNumber());
            cupCount.remove(h);
        }
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
    }

    /**
     * Remueve una tapa especifica de cualquier posicion.
     * Los elementos encima se guardan y se vuelven a apilar.
     * Requisito 3 - Ciclo 1.
     *
     * @param lidNumber numero de la tapa a remover
     */
    public void removeLid(int lidNumber) {
<<<<<<< HEAD
        int index = findLidIndex(lidNumber);
=======
        // Buscar la tapa
        System.out.println(stackLids);
        int index = -1;
        for (int i = 0; i < stackLids.size(); i++) {
            if(stackLids.get(i) != null){
                if (stackLids.get(i).getNumber() == lidNumber) {
                    index = i;
                    break;
                }
            }
        }
        
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
        if (index == -1) {
            showError("No se encontro la tapa " + lidNumber);
            return;
        }
<<<<<<< HEAD
        ArrayList<String[]> above = collectAbove(index);
        popLid();
        restoreAbove(above);
=======
        
        // Guardar tazas y tapas encima
        ArrayList<Integer> aboveCups = new ArrayList<>();
        ArrayList<Integer> aboveLids = new ArrayList<>();
        for (int i = stackLids.size() - 1; i > index; i--){
            if(stackLids.get(i) != null){
                aboveLids.add(stackLids.get(i).getNumber());
                aboveCups.add(null);
                popLid();
            }
            else{
                aboveCups.add(stack.get(i).getNumber());
                aboveLids.add(null);
                popCup();
            }
        }
        
        // Remover la tapa objetivo
        popLid();
        
        // Re-apilar las tazas y tapas que estaban encima
        for (int i = aboveLids.size() - 1; i >= 0; i--){
            if(aboveLids.get(i) != null){
                pushLid(aboveLids.get(i));
            }
            else{
                pushCup(aboveCups.get(i));
            }
        }
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
    }

    // ---------------------------------------------------------
    // REQUISITO 4 - Ordenar de mayor a menor
    // ---------------------------------------------------------

    /**
     * Ordena las tazas de mayor a menor numero. Solo incluye las que caben.
     * El menor numero siempre queda en la cima.
     * Requisito 4 - Ciclo 1.
     */
<<<<<<< HEAD
    public void orderTower() {
        if (!hasCupsInStack()) return;
        int[] nums = getCupNumbers();
        Arrays.sort(nums);
        reverseArray(nums);
        rebuildTowerCupsOnly(nums);
=======
    public void orderTower(){
        if (stack.isEmpty()) {
            return;
        }
        
        // Guardar numeros de tazas
        int[] numbers = new int[stack.size()];
        for (int i = 0; i < stack.size(); i++) {
            numbers[i] = stack.get(i).getNumber();
        }
        
        // Ordenar de mayor a menor
        Arrays.sort(numbers);
        reverseArray(numbers);
        
        // Vaciar torre
        while (!stack.isEmpty()) {
            popCup();
        }
        
        // Re-llenar ordenada
        for (int num : numbers) {
            pushCup(num);
        }
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
    }

    // ---------------------------------------------------------
    // REQUISITO 5 - Colocar en orden inverso
    // ---------------------------------------------------------

    /**
     * Invierte el orden actual de las tazas en la torre.
     * Solo incluye las que caben.
     * Requisito 5 - Ciclo 1.
     */
    public void reverseTower() {
        if (!hasCupsInStack()) return;
        int[] nums = getCupNumbers();
        reverseArray(nums);
        rebuildTowerCupsOnly(nums);
    }

    // ---------------------------------------------------------
    // REQUISITO 6 - Consultar altura
    // ---------------------------------------------------------

    /**
<<<<<<< HEAD
     * Retorna la altura actual de la torre en cm.
     * Requisito 6 - Ciclo 1.
     *
     * @return altura en cm
=======
     * Este metodo se encarga de intercambiar 2 objetos que estan en pantalla, cambiandolos de posicion
     * @param: obj1 es el vector de strings que representa que objeto se debe cambiar
     * @param: obj2 es el vector de strings que representa que objeto se debe cambiar
     */
    
    public void swap(String[] obj1, String[] obj2){
        int indexP = -1;
        int indexS = -1;
        int numObj1 = Integer.parseInt(obj1[1]);
        int numObj2 = Integer.parseInt(obj2[1]);
        ArrayList<Integer> changeCup = new ArrayList<>();
        ArrayList<Integer> changeLid = new ArrayList<>();
        for (int i = 0; i < stack.size(); i++) {
            if(obj1[0].equals("cup")){
                if(stack.get(i) != null){
                    if (stack.get(i).getNumber() == numObj1){
                        indexP = i;
                        break;
                    }
                }
            }
            else{
                if(stackLids.get(i) != null){
                    if (stackLids.get(i).getNumber() == numObj1){
                        indexP = i;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < stack.size(); i++) {
            if(obj2[0].equals("cup")){
                if(stack.get(i) != null){
                    if (stack.get(i).getNumber() == numObj2){
                        indexS = i;
                        break;
                    }
                }
            }
            else{
                if(stackLids.get(i) != null){
                    if (stackLids.get(i).getNumber() == numObj2){
                        indexS = i;
                        break;
                    }
                }
            }
        }
        if (indexP == -1){
            showError("No se encontro el objeto " + obj1);
            return;
        }
        if (indexS == -1) {
            showError("No se encontro el objeto " + obj2);
            return;
        }
        
        //Se crea una lista de tazas ya cambiadas de posicion
        for(int i = 0; i < stack.size(); i++){
            if(stack.get(i) != null){
                int numCup = stack.get(i).getNumber();
                changeCup.add(numCup);
                changeLid.add(null);
            }
            else{
                int numLid = stackLids.get(i).getNumber();
                changeLid.add(numLid);
                changeCup.add(null);
            }
        }
        Collections.swap(changeCup, indexP, indexS); //Se utiliza para intercambiar posiciones en un ArrayList de manera mucho mas eficiente
        Collections.swap(changeLid, indexP, indexS); //Se utiliza para intercambiar posiciones en un ArrayList de manera mucho mas eficiente
        
        //Se elimina la torre actual y construimos la correguida
        popAll();
        for(int i = 0; i < changeCup.size(); i++){
            if(changeCup.get(i) != null){
                int cup = changeCup.get(i);
                pushCup(cup);
            }
            else{
                int lid = changeLid.get(i);
                pushLid(lid);
            }
        }
    }
    
    /**
     * Retorna la altura actual de la torre
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
     */
    public int height() {
        return currentHeight;
    }

    // ---------------------------------------------------------
    // REQUISITO 7 - Consultar tazas tapadas y elementos apilados
    // ---------------------------------------------------------

    /**
     * Retorna los numeros de las tazas que tienen tapa asignada,
     * ordenados de menor a mayor.
     * Requisito 7 - Ciclo 1.
     *
     * @return array con numeros de tazas tapadas
     */
    public int[] lidedCups() {
        ArrayList<Integer> lided = new ArrayList<>();
<<<<<<< HEAD
        for (Cup c : stack) {
            if (c != null && c.hasLid()) lided.add(c.getNumber());
=======
        
        for (Cup cup : stack) {
            if(cup != null){
                if (cup.hasLid()) {
                    lided.add(cup.getNumber());
                }
            }
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
        }
        int[] result = new int[lided.size()];
        for (int i = 0; i < lided.size(); i++) result[i] = lided.get(i);
        Arrays.sort(result);
        return result;
    }

    /**
     * Retorna todos los elementos apilados de base a cima.
     * Formato: {{"cup","4"},{"lid","4"},{"cup","2"}, ...}
     * Requisito 7 - Ciclo 1.
     *
     * @return matriz con tipo y numero de cada elemento
     */
    public String[][] stackingItems() {
        ArrayList<String[]> items = new ArrayList<>();
<<<<<<< HEAD
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i) != null) {
                items.add(new String[]{"cup", String.valueOf(stack.get(i).getNumber())});
            } else if (stackLids.get(i) != null) {
                items.add(new String[]{"lid", String.valueOf(stackLids.get(i).getNumber())});
=======
        
        for (Cup cup : stack){
            if(cup != null){
                items.add(new String[]{"cup", String.valueOf(cup.getNumber())});
                if (cup.hasLid()) {
                    items.add(new String[]{"lid", String.valueOf(cup.getNumber())});
                }
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
            }
        }
        String[][] result = new String[items.size()][2];
        for (int i = 0; i < items.size(); i++) result[i] = items.get(i);
        return result;
    }

    // ---------------------------------------------------------
    // REQUISITO 8 - Hacer visible o invisible
    // ---------------------------------------------------------

    /**
     * Hace visible el simulador y todos sus elementos.
     * Requisito 8 - Ciclo 1.
     */
    public void makeVisible() {
        if (!isVisible) {
            isVisible = true;
            grid.makeVisible();
            for (CupVisual cv : cupVisuals) cv.makeVisible();
            for (LidVisual lv : lidVisuals) lv.makeVisible();
        }
    }

    /**
     * Hace invisible el simulador. Sigue funcionando en modo invisible.
     * Requisito 8 - Ciclo 1.
     */
    public void makeInvisible() {
        if (isVisible) {
            isVisible = false;
            grid.makeInvisible();
            for (CupVisual cv : cupVisuals) cv.makeInvisible();
            for (LidVisual lv : lidVisuals) lv.makeInvisible();
        }
    }

    // ---------------------------------------------------------
    // REQUISITO 9 - Terminar simulador
    // ---------------------------------------------------------

    /**
     * Termina el simulador con mensaje de despedida.
     * Requisito 9 - Ciclo 1.
     */
    public void exit() {
        JOptionPane.showMessageDialog(null,
            "MUCHAS GRACIAS POR PROBAR EL SIMULADOR",
            "stackingItems", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    /**
     * Indica si el simulador esta operativo.
     *
     * @return true siempre que el simulador este activo
     */
    public boolean ok() {
        return true;
    }

    // =========================================================
    // ===================== CICLO 2 ===========================
    // =========================================================

    // ---------------------------------------------------------
    // REQUISITO 10 - Crear torre con N tazas automaticas
    // ---------------------------------------------------------

    /**
     * Crea una torre con n tazas generadas automaticamente, sin tapas.
     * Las tazas se numeran 1 a cups y se apilan de mayor a menor.
     * La taza mas grande queda en la base.
     * Requisito 10 - Ciclo 2.
     *
     * Ejemplo: cups=4 genera tazas 4,3,2,1 (alturas 7,5,3,1 cm).
     *
     * @param cups numero de tazas a generar (>= 1)
     */
    public Tower(int cups) {
        int autoMax = (2 * cups) - 1;
        initializeTower(autoMax, autoMax);
        grid.makeVisible();
        for (int i = cups; i >= 1; i--) {
            pushCup(i);
        }
    }

    /**
<<<<<<< HEAD
     * Agrega n tazas automaticamente a una torre existente, de mayor a menor.
     * Si cups supera el maximo permitido, muestra error y no agrega nada.
     * Requisito 10 - Ciclo 2.
     *
     * @param cups numero de tazas a crear
=======
     * Actualiza la logica de apilamiento
     * @param: cupHeight es un atributo que representa la altura de la copa que se quiere ingresar
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
     */
    public void createTower(int cups) {
        if (cups > maxCups) {
            showError("El numero de tazas supera el ancho maximo de la torre");
            return;
        }
        for (int i = cups; i > 0; i--) {
            pushCup(i);
        }
    }

    // ---------------------------------------------------------
    // REQUISITO 11 - Intercambiar posicion de dos objetos
    // ---------------------------------------------------------

    /**
     * Intercambia la posicion de dos objetos en la torre.
     * Los objetos se identifican por tipo ("cup" o "lid") y numero.
     * Una taza tapada se mueve siempre con su tapa asociada.
     * Requisito 11 - Ciclo 2.
     *
     * Ejemplo: swap({"cup","3"}, {"lid","1"}) intercambia la taza 3
     * con la tapa 1 en la torre.
     *
     * @param obj1 identificador del primer objeto, ej: {"cup","3"}
     * @param obj2 identificador del segundo objeto, ej: {"lid","1"}
     */
    public void swap(String[] obj1, String[] obj2) {
        int idx1 = findIndexOf(obj1);
        int idx2 = findIndexOf(obj2);

        if (idx1 == -1) {
            showError("No se encontro el objeto " + obj1[0] + " " + obj1[1]);
            return;
        }
        if (idx2 == -1) {
            showError("No se encontro el objeto " + obj2[0] + " " + obj2[1]);
            return;
        }
        if (idx1 == idx2) {
            showError("Los dos objetos son el mismo");
            return;
        }

        // Construir secuencia actual, intercambiar y reconstruir
        ArrayList<String[]> sequence = buildSequence();
        Collections.swap(sequence, idx1, idx2);

        popAll();
        for (String[] item : sequence) {
            if (item[0].equals("cup")) pushCup(Integer.parseInt(item[1]));
            else                       pushLid(Integer.parseInt(item[1]));
        }
    }

    // ---------------------------------------------------------
    // REQUISITO 12 - Tapar tazas con tapas disponibles en torre
    // ---------------------------------------------------------

    /**
     * Tapa automaticamente las tazas cuya tapa esta en la posicion
     * inmediatamente encima dentro de la torre.
     * Condicion: taza[i] y tapa[i+1] tienen el mismo numero.
     * Requisito 12 - Ciclo 2.
     */
    public void cover() {
        if (stack.size() < 2) return;

        for (int i = 0; i < stack.size() - 1; i++) {
            if (stack.get(i) == null) continue;
            Cup cup = stack.get(i);

            if (stackLids.get(i + 1) == null) continue;
            Lid lid = stackLids.get(i + 1);

            // Si la taza y la tapa tienen el mismo numero y la taza no tiene tapa aun
            if (cup.getNumber() == lid.getNumber() && !cup.hasLid()) {
                cup.setLid(lid);
            }
        }

        if (isVisible) redrawAll();
    }

    // ---------------------------------------------------------
    // REQUISITO 13 - Consultar swap que reduzca la altura
    // ---------------------------------------------------------

    /**
     * Consulta que intercambio de dos elementos reduciria la altura.
     * Prueba todos los pares posibles simulando sin modificar el estado real.
     * Retorna null si ningun swap reduce la altura.
     * Requisito 13 - Ciclo 2.
     *
     * @return par {obj1, obj2} a intercambiar, o null si ninguno reduce.
     *         Ejemplo: {{"cup","4"},{"lid","2"}}
     */
    public String[][] swapToReduce() {
        ArrayList<String[]> sequence = buildSequence();
        int alturaActual = currentHeight;

        for (int i = 0; i < sequence.size(); i++) {
            for (int j = i + 1; j < sequence.size(); j++) {
                int simulada = simulateSwapHeight(sequence, i, j);
                if (simulada < alturaActual) {
                    return new String[][]{ sequence.get(i), sequence.get(j) };
                }
            }
        }
        return null;
    }
    // =========================================================
    // METODOS PRIVADOS - compartidos Ciclo 1 y Ciclo 2
    // =========================================================

    /**
     * Inicializa todos los atributos internos de la torre.
     * 
     *
     * @param maxHeight altura máxima permitida para la torre.
     * @param maxWidth ancho máximo permitido para la torre.
     */
    private void initializeTower(int maxHeight, int maxWidth) {
        this.maxHeight  = maxHeight;
        this.maxCups    = (maxWidth + 1) / 2;
        this.stack      = new ArrayList<>();
        this.stackLids  = new ArrayList<>();
        this.history    = new ArrayList<>();
        this.currentHeight = 0;
        this.currentLevel  = 0;
        this.space      = new ArrayList<>();
        this.grid       = new TowerGrid(maxHeight, maxWidth);
        this.cupVisuals = new ArrayList<>();
        this.lidVisuals = new ArrayList<>();
        this.isVisible  = true;
    }

    /**
     * Actualiza la estructura lógica de ocupación al agregar una taza.
     * 
     * @param cupHeight altura calculada de la taza a insertar.
     */
    private void updateStackLogic(int cupHeight) {
        int n = currentLevel;
        while (n < space.size()) {
            if (space.get(n) < cupHeight) currentLevel++;
            else break;
            n++;
        }
        while ((currentLevel + cupHeight) > space.size()) space.add(-1);
        for (int i = currentLevel; i < currentLevel + cupHeight; i++) space.set(i, cupHeight);
        currentHeight = space.size();
    }

    /**
<<<<<<< HEAD
     * Actualiza la estructura lógica de ocupación al agregar una tapa
     *
     * @param lidWidth ancho calculado de la tapa a insertar.
=======
     * Actualiza la logica de apilamiento
     * @param: lidWidth es un atributo que representa la anchura de la tapa que se quiere ingresar
     */
    private void updateStackLogicLid(int lidWidth){
        int n = currentLevel;
        while(n < space.size()){
            if(space.get(n) <= lidWidth){
                currentLevel += 1;
            }
            else{
                break;
            }
            n += 1;
        }
        while((currentLevel + 1) > space.size()){
            space.add(-1);
        }
        for(int i = currentLevel; i < (currentLevel + 1); i++){
            space.set(i, 0);
        }
        currentHeight = space.size();
    }
    
    /**
     * Este metodo nos ayuda a borrar toda la lista despues de una ejecucion, lo que implica que ya no queda nada en pantalla.
     */
    private void popAll(){
        while(!stack.isEmpty()){
            if(stack.get(stack.size()-1) != null){
                popCup();
            }
            else{
                popLid();
            }
        }
    }
    
    /**
     * Calcula la posicion X para una taza
>>>>>>> 486419819cda08f41fbd9697b993520c2041e99b
     */
    private void updateStackLogicLid(int lidWidth) {
        int n = currentLevel;
        while (n < space.size()) {
            if (space.get(n) <= lidWidth) currentLevel++;
            else break;
            n++;
        }
        while ((currentLevel + 1) > space.size()) space.add(-1);
        space.set(currentLevel, 0);
        currentHeight = space.size();
    }

    /**
     * Simula la altura resultante de intercambiar dos elementos
     * en la secuencia actual SIN modificar el estado real de la torre.
     *
     * @param sequence secuencia actual de elementos (cup/lid).
     * @param i índice del primer elemento.
     * @param j índice del segundo elemento.
     * @return altura simulada después del intercambio.
     */
    private int simulateSwapHeight(ArrayList<String[]> sequence, int i, int j) {
        ArrayList<String[]> copy = new ArrayList<>(sequence);
        Collections.swap(copy, i, j);

        int simLevel = 0, simHeight = 0;
        ArrayList<Integer> simSpace = new ArrayList<>();

        for (String[] item : copy) {
            int num = Integer.parseInt(item[1]);
            if (item[0].equals("cup")) {
                int h = (2 * num) - 1;
                int n = simLevel;
                while (n < simSpace.size()) {
                    if (simSpace.get(n) < h) simLevel++;
                    else break;
                    n++;
                }
                while ((simLevel + h) > simSpace.size()) simSpace.add(-1);
                for (int k = simLevel; k < simLevel + h; k++) simSpace.set(k, h);
                simHeight = simSpace.size();
            } else {
                int w = (2 * num) - 1;
                int n = simLevel;
                while (n < simSpace.size()) {
                    if (simSpace.get(n) <= w) simLevel++;
                    else break;
                    n++;
                }
                while ((simLevel + 1) > simSpace.size()) simSpace.add(-1);
                simSpace.set(simLevel, 0);
                simHeight = simSpace.size();
            }
            simLevel++;
        }
        return simHeight;
    }
    
    
    /**
     * Busca el índice de una taza en la pila según su número.
     *
     * @param number número identificador de la taza.
     * @return índice donde se encuentra o -1 si no existe.
     */
    private int findCupIndex(int number) {
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i) != null && stack.get(i).getNumber() == number) return i;
        }
        return -1;
    }

    /**
     * Busca el índice de una tapa en la pila según su número.
     *
     * @param number número identificador de la tapa.
     * @return índice donde se encuentra o -1 si no existe.
     */
    private int findLidIndex(int number) {
        for (int i = 0; i < stackLids.size(); i++) {
            if (stackLids.get(i) != null && stackLids.get(i).getNumber() == number) return i;
        }
        return -1;
    }

    /**
     * Obtiene el índice correspondiente a un objeto representado
     * como {"cup"/"lid", número}.
     *
     * @param obj representación del objeto.
     * @return índice dentro de la pila.
     */
    private int findIndexOf(String[] obj) {
        int num = Integer.parseInt(obj[1]);
        return obj[0].equals("cup") ? findCupIndex(num) : findLidIndex(num);
    }

    /**
     * Verifica si una taza existe actualmente en la pila.
     *
     * @param number número de la taza.
     * @return true si existe, false en caso contrario.
     */
    private boolean cupExistsInStack(int number) { return findCupIndex(number) != -1; }
    
    /**
     * Verifica si una tapa existe actualmente en la pila.
     *
     * @param number número de la tapa.
     * @return true si existe, false en caso contrario.
     */
    private boolean lidExistsInStack(int number) { return findLidIndex(number) != -1; }
    
    /**
     * Determina si hay al menos una taza en la pila.
     *
     * @return true si existe alguna taza.
     */
    private boolean hasCupsInStack() {
        for (Cup c : stack) if (c != null) return true;
        return false;
    }

    /**
     * Determina si hay al menos una tapa en la pila.
     *
     * @return true si existe alguna tapa.
     */
    private boolean hasLidsInStack() {
        for (Lid l : stackLids) if (l != null) return true;
        return false;
    }

    
    /**
     * Obtiene el índice de la última taza agregada.
     *
     * @return índice de la última taza o -1 si no hay.
     */
    private int getLastCupIndex() {
        for (int i = stack.size() - 1; i >= 0; i--) if (stack.get(i) != null) return i;
        return -1;
    }

    /**
     * Obtiene el índice de la última tapa agregada.
     *
     * @return índice de la última tapa o -1 si no hay.
     */
    private int getLastLidIndex() {
        for (int i = stackLids.size() - 1; i >= 0; i--) if (stackLids.get(i) != null) return i;
        return -1;
    }

    
    /**
     * Construye una secuencia lineal representando la pila actual
     * como pares {"cup"/"lid", número}.
     *
     * @return lista con la secuencia actual.
     */
    private ArrayList<String[]> buildSequence() {
        ArrayList<String[]> seq = new ArrayList<>();
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i) != null)
                seq.add(new String[]{"cup", String.valueOf(stack.get(i).getNumber())});
            else if (stackLids.get(i) != null)
                seq.add(new String[]{"lid", String.valueOf(stackLids.get(i).getNumber())});
        }
        return seq;
    }

    
    /**
     * Obtiene los números de todas las tazas actuales en orden.
     *
     * @return arreglo con los números de las tazas.
     */
    private int[] getCupNumbers() {
        ArrayList<Integer> nums = new ArrayList<>();
        for (Cup c : stack) if (c != null) nums.add(c.getNumber());
        int[] arr = new int[nums.size()];
        for (int i = 0; i < nums.size(); i++) arr[i] = nums.get(i);
        return arr;
    }

    
    /**
     * Reconstruye la torre utilizando únicamente tazas
     * en el orden especificado.
     *
     * @param numbers arreglo con los números de las tazas.
     */
    private void rebuildTowerCupsOnly(int[] numbers) {
        popAll();
        for (int num : numbers) pushCup(num);
    }

    
    /**
     * Recolecta y elimina todos los elementos que están
     * por encima de un índice dado.
     *
     * @param index índice base.
     * @return lista con los elementos removidos.
     */
    private ArrayList<String[]> collectAbove(int index) {
        ArrayList<String[]> above = new ArrayList<>();
        for (int i = stack.size() - 1; i > index; i--) {
            if (stack.get(i) != null) {
                above.add(new String[]{"cup", String.valueOf(stack.get(i).getNumber())});
                popCup();
            } else {
                above.add(new String[]{"lid", String.valueOf(stackLids.get(i).getNumber())});
                popLid();
            }
        }
        return above;
    }

    
    /**
     * Restaura elementos previamente removidos respetando
     * su orden original.
     *
     * @param above lista de elementos a restaurar.
     */
    private void restoreAbove(ArrayList<String[]> above) {
        for (int i = above.size() - 1; i >= 0; i--) {
            if (above.get(i)[0].equals("cup")) pushCup(Integer.parseInt(above.get(i)[1]));
            else                               pushLid(Integer.parseInt(above.get(i)[1]));
        }
    }

    
    /**
     * Elimina todos los elementos de la torre.
     */
    private void popAll() {
        while (!stack.isEmpty()) {
            if (stack.get(stack.size() - 1) != null) popCup();
            else popLid();
        }
    }

    /**
     * Restaura el último estado almacenado en el historial.
     */
    private void restoreLastState() {
        if (!history.isEmpty()) {
            States prev = history.remove(history.size() - 1);
            currentHeight = prev.getCurrentHeight();
            currentLevel  = prev.getCurrentLevel();
            space         = new ArrayList<>(prev.getSpace());
        }
    }

    
    /**
     * Elimina el último elemento lógico de la pila.
     */
    private void removeLastFromStack() {
        int last = stack.size() - 1;
        stack.remove(last);
        stackLids.remove(last);
    }

    
    /**
     * Elimina la última representación visual de una taza.
     */
    private void removeCupVisualLast() {
        int last = cupVisuals.size() - 1;
        cupVisuals.get(last).erase();
        cupVisuals.remove(last);
    }

    
    /**
     * Elimina la última representación visual de una tapa.
     */
    private void removeLidVisualLast() {
        int last = lidVisuals.size() - 1;
        lidVisuals.get(last).erase();
        lidVisuals.remove(last);
    }

    
    /**
     * Dibuja una taza en el nivel especificado.
     *
     * @param visual representación visual de la taza.
     * @param level nivel vertical donde se dibuja.
     */
    private void drawCup(CupVisual visual, int level) {
        int xPos = calculateXPosition(visual.getCup().getNumber());
        int yPos = grid.getYOrigin() - (int) grid.getScale();
        visual.draw(xPos, yPos - (int)(level * grid.getScale()), grid.getScale());
    }

    
    private void drawLid(LidVisual visual, int level){
        int xPos = calculateXPosition(visual.getLid().getNumber());
        int yPos = grid.getYOrigin() - (int)grid.getScale();
        visual.draw(xPos, yPos - (int)(level * grid.getScale()), grid.getScale());
    }
    
    /**
     * Dibuja una tapa en el nivel especificado.
     *
     * @param visual representación visual de la tapa.
     * @param level nivel vertical donde se dibuja.
     */
    private void drawLid(LidVisual visual, int level) {
        int xPos = calculateXPosition(visual.getLid().getNumber());
        int yPos = grid.getYOrigin() - (int) grid.getScale();
        visual.draw(xPos, yPos - (int)(level * grid.getScale()), grid.getScale());
    }

    
    /**
     * Calcula la posición horizontal en función del número del objeto.
     *
     * @param number número identificador.
     * @return coordenada X calculada.
     */
    private int calculateXPosition(int number) {
        int xOffset = (maxCups - number) * (int) grid.getScale();
        return grid.getXOrigin() + xOffset;
    }

   /**
     * Redibuja completamente la torre respetando el orden actual.
     */
    private void redrawAll() {
        int cupIdx = 0, lidIdx = 0, level = 0;
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i) != null)      drawCup(cupVisuals.get(cupIdx++), level);
            else if (stackLids.get(i) != null) drawLid(lidVisuals.get(lidIdx++), level);
            level++;
        }
    }

    /**
     * Muestra un mensaje de error si la interfaz está visible.
     *
     * @param message mensaje a mostrar.
     */
    private void showError(String message) {
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message,
                "stackingItems", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    /**
     * Invierte el orden de un arreglo de enteros.
     *
     * @param arr arreglo a invertir.
     */
    private void reverseArray(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i]   = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }
}