package TowerGroup;
import Shapes.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 * Simulador de torre de tazas apilables (stackingItems).
 *
 * ============================================================
 * CICLO 1 - Requisitos 1 al 9 (base del simulador)
 * ============================================================
 *   1. Crear torre dados ancho y alto          → Tower(maxHeight, maxWidth)
 *   2. Adicionar o eliminar taza               → pushCup, popCup, removeCup
 *   3. Adicionar o eliminar tapa               → pushLid, popLid, removeLid
 *   4. Ordenar de mayor a menor                → orderTower
 *   5. Colocar en orden inverso                → reverseTower
 *   6. Consultar altura                        → height
 *   7. Consultar tazas tapadas y apilados      → lidedCups, stackingItems
 *   8. Hacer visible o invisible               → makeVisible, makeInvisible
 *   9. Terminar simulador                      → exit
 *
 * ============================================================
 * CICLO 2 - Requisitos 10 al 13 (refactoring + extension)
 * ============================================================
 *  10. Crear torre con N tazas automaticas     → Tower(cups), createTower
 *  11. Intercambiar posicion de dos objetos    → swap
 *  12. Tapar tazas con tapas disponibles       → cover
 *  13. Consultar swap que reduzca la altura    → swapToReduce
 *
 * Modelo de datos:
 * - stack y stackLids son listas paralelas del mismo tamanio.
 * - En cada posicion i, exactamente uno tiene valor no nulo:
 *     stack.get(i) != null     → posicion i es una taza
 *     stackLids.get(i) != null → posicion i es una tapa
 *
 * ============================================================
 * CICLO 3 - Requisitos 14 al 15 (solucion del simulador)
 * ============================================================
 * 14. Crear una clase especial la cual pueda imprimir la solucion del problema
 * 15. Se muestra la solucion del problema
 * 
 * ============================================================
 * CICLO 4 - Requisitos 16 al 17 (Nuevos elementos)
 * ============================================================
 * 16. Agregar nuevos elementos al simulador    → pushCupType(type, numCup), pushLidType(type, numLid)
 * 17. Se agrega un elemento propio             → pushCupType(type, numCup)
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 6.0 (16/04/2026)
 */
public class Tower{

    // =========================================================
    // ATRIBUTOS
    // =========================================================
    
    private ArrayList<Item> stack;
    private int[] historyLids;
    private int maxCups;
    private int maxHeight;
    private int currentHeight;
    private int currentLevel;
    private ArrayList<Integer> space;
    private ArrayList<States> history;
    private TowerGrid grid;
    private ArrayList<Visual> visuals;
    private boolean isVisible;
    private boolean isOk;

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
        initializeTower(maxHeight, maxWidth);
        grid.makeVisible();
    }

    // ---------------------------------------------------------
    // REQUISITO 2 - Adicionar o eliminar una taza
    // ---------------------------------------------------------

    /**
     * Agrega una taza a la cima de la torre.
     * Valida numero positivo, que quepa en ancho y alto, y unicidad.
     * Requisito 2 - Ciclo 1.
     * @param cupNumber numero de la taza (>= 1)
     */
    public void pushCup(int cupNumber) {
        if (cupNumber <= 0) {
            showError("El numero de taza debe ser positivo");
            isOk = false;
            return;
        }
        if (cupNumber > maxCups) {
            showError("La taza " + cupNumber + " supera el ancho maximo");
            isOk = false;
            return;
        }
        if (itemExistsInStack(cupNumber, "Cup")) {
            showError("La taza " + cupNumber + " ya esta en la torre");
            isOk = false;
            return;
        }
        
        history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));

        Cup newCup = new Cup(cupNumber);
        int lidIdx = findItemIndex(cupNumber, "Lid");
        if (lidIdx != -1){
            Lid lidFromTower = (Lid) stack.get(lidIdx);
            newCup.setLid(lidFromTower);
        }

        stack.add(newCup);
        updateStackLogic(newCup.getHeight());

        CupVisual visual = new CupVisual(newCup);
        visuals.add(visual);
        if (isVisible){
            drawItem(visual, currentLevel);
        }
        else{
            drawItem(visual, currentLevel);
            visual.makeInvisible();
        }
        currentLevel += 1;
        
        int i = space.size() - 1;
        while(space.get(i) != newCup.getHeight()){
            i -= 1;
        }
        newCup.setPositionInSpace(i);

        if (currentHeight > maxHeight) {
            showError("La taza " + cupNumber + " supera la altura maxima");
            isOk = false;
            popCup();
        }
        
        if(!itemExistsInStack(cupNumber, "Lid")){
            if(historyLids[cupNumber - 1] == 1){
                pushLid(cupNumber);
            }
            else if(historyLids[cupNumber - 1] == 2){
                pushLidType("Fearful", cupNumber);
            }
            else if(historyLids[cupNumber - 1] == 3){
                pushLidType("Crazy", cupNumber);
            }
        }
        
        isOk = true;
    }
    
    /**
     * Este metodo se encarga de ingresar un tipo de copa especifico teniendo en cuenta que cada uno tiene una logica diferente.
     * @param Type es el tipo especifico de la copa, este tipo solo puede ser "Normal", "Opener", "Hierarchical" o la copa propia "Reverse"
     * @param cupNumber es el numero de la copa que se va a ingresar, teniendo en cuenta que no se puede repetir copas
     */
    public void pushCupType(String type, int cupNumber){
        if(!type.equals("Normal") && !type.equals("Opener") && !type.equals("Hierarchical") && !type.equals("Reverse")){
            showError("El tipo de taza que intento ingresar no es valido");
            isOk = false;
            return;
        }
        if (cupNumber <= 0) {
            showError("El numero de taza debe ser positivo");
            isOk = false;
            return;
        }
        if (cupNumber > maxCups) {
            showError("La taza " + cupNumber + " supera el ancho maximo");
            isOk = false;
            return;
        }
        if (itemExistsInStack(cupNumber, "Cup")) {
            showError("La taza " + cupNumber + " ya esta en la torre");
            isOk = false;
            return;
        }
        
        if(type.equals("Normal")){
            pushCup(cupNumber);
        }
        else{
            Visual newVisual = null;
            ArrayList<Item> smallerItems = new ArrayList<>();
            ArrayList<Item> original = new ArrayList<>(stack);
            
            //Se crea la logica exclusiva de cada una de los tipos especificos
            //En el caso de una copa de tipo "Opener"
            if(type.equals("Opener")){
                while(!stack.isEmpty() && stack.get(stack.size()-1).getType().equals("Lid")){
                    popLid();
                }
                history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));
                Opener newItem = new Opener(cupNumber);
                stack.add(newItem);
                int lidIdx = findItemIndex(cupNumber, "Lid");
                if (lidIdx != -1){
                    Lid lidFromTower = (Lid) stack.get(lidIdx);
                    newItem.setLid(lidFromTower);
                }
                updateStackLogic(newItem.getHeight());
                
                int i = space.size() - 1;
                while(space.get(i) != newItem.getHeight()){
                    i -= 1;
                }
                newItem.setPositionInSpace(i);
                
                newVisual = new VisualOpener(newItem);
            }
            //En el caso de una copa de tipo "Hierarchical"
            else if(type.equals("Hierarchical")){
                smallerItems = new ArrayList<>();
                while(!stack.isEmpty() && cupNumber > stack.get(stack.size() - 1).getNumber()){
                    smallerItems.add(stack.get(stack.size() - 1));
                    if(stack.get(stack.size() - 1).getType().equals("Cup")){
                        popCup();
                    }
                    else if(stack.get(stack.size() - 1).getType().equals("Lid")){
                        popLid();
                    }
                }
                Collections.reverse(smallerItems);  
                history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));
                Hierarchical newItem = new Hierarchical(cupNumber);
                stack.add(newItem);
                int lidIdx = findItemIndex(cupNumber, "Lid");
                if (lidIdx != -1){
                    Lid lidFromTower = (Lid) stack.get(lidIdx);
                    newItem.setLid(lidFromTower);
                }
                updateStackLogic(newItem.getHeight());
                
                int i = space.size() - 1;
                while(space.get(i) != newItem.getHeight()){
                    i -= 1;
                }
                newItem.setPositionInSpace(i);
                
                newVisual = new VisualHierarchical(newItem);
            }
            //En el caso del objeto especial Reverse
            else if(type.equals("Reverse")){
                history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));
                Reverse newItem = new Reverse(cupNumber);
                stack.add(newItem);
                int lidIdx = findItemIndex(cupNumber, "Lid");
                if (lidIdx != -1){
                    Lid lidFromTower = (Lid) stack.get(lidIdx);
                    newItem.setLid(lidFromTower);
                }
                updateStackLogicReverse(newItem.getHeight());
                
                int i = space.size() - 1;
                while(space.get(i) != newItem.getHeight()){
                    i -= 1;
                }
                newItem.setPositionInSpace(i);
                
                newVisual = new VisualReverse(newItem);
            }
            
            //Se crea el visualizador de cualquiera de los tipos de tapa
            visuals.add(newVisual);
            if (isVisible){
                drawItem(newVisual, currentLevel);
            }
            else{
                drawItem(newVisual, currentLevel);
                newVisual.makeInvisible();
            }
            currentLevel += 1;
            
            for(Item smaller : smallerItems){
                if(smaller.getType().equals("Cup")){
                    pushCupType(smaller.getSpecific(), smaller.getNumber());
                }
                else if(smaller.getType().equals("Lid")){
                    pushLidType(smaller.getSpecific(), smaller.getNumber());
                }
            }
            
            if (currentHeight > maxHeight) {
                showError("La tapa que ingreso no puede ser agregada porque el tamaño maximo de la torre es superado");
                popAll();
                for(Item item : original){
                    if(item.getType().equals("Cup")){
                        pushCupType(item.getSpecific(), item.getNumber());
                    }
                    else if(item.getType().equals("Lid")){
                        pushLidType(item.getSpecific(), item.getNumber());
                    }
                }
                isOk = false;
                return;
            }
            
            if(!itemExistsInStack(cupNumber, "Lid")){
                if(historyLids[cupNumber - 1] == 1){
                    pushLid(cupNumber);
                }
                else if(historyLids[cupNumber - 1] == 2){
                    pushLidType("Fearful", cupNumber);
                }
                else if(historyLids[cupNumber - 1] == 3){
                    pushLidType("Crazy", cupNumber);
                }
            }
        }
        
        isOk = true;
    }
    
    /**
     * Remueve la taza en la cima de la torre.
     * Guarda las tapas que hubiera encima y las vuelve a colocar.
     * Requisito 2 - Ciclo 1.
     */
    public void popCup(){
        if (!hasItemsInStack("Cup")) {
            showError("No hay tazas para remover");
            isOk = false;
            return;
        }
        
        if(stack.size() == 1 && stack.get(stack.size()-1).getSpecific().equals("Hierarchical")){
            showError("El tipo que se quiere sacar es tipo Hierarchical y esta en la ultima posicion, no se puede sacar");
            isOk = false;
            return;
        }

        int topCupIdx = getLastItemIndex("Cup");
        ArrayList<String[]> lidsAbove = collectAbove(topCupIdx);
        int numCup = stack.get(topCupIdx).getNumber();

        removeLastVisual("Cup");
        removeLastFromStack();
        restoreLastState();

        restoreAbove(lidsAbove);

        if(historyLids[numCup - 1] != 0){
            int lidIdx = findItemIndex(numCup, "Lid");
            if(lidIdx != -1){
                int type = 1;
                if(historyLids[numCup - 1] == 2){
                    isOk = true;
                    return;
                }
                else if(stack.get(lidIdx).getSpecific().equals("Crazy")) type = 3;
                removeLid(numCup);
                historyLids[numCup - 1] = type;
            }
        }
        
        isOk = true;
    }

    /**
     * Remueve una taza especifica de cualquier posicion.
     * Los elementos encima se guardan y se vuelven a apilar.
     * Requisito 2 - Ciclo 1.
     * @param cupNumber numero de la taza a remover
     */
    public void removeCup(int cupNumber) {
        int index = findItemIndex(cupNumber, "Cup");
        if (index == -1){
            showError("No se encontro la taza " + cupNumber);
            isOk = false;
            return;
        }
        
        if(index == 0 && stack.get(0).getSpecific().equals("Hierarchical")){
            showError("El tipo que se quiere sacar es tipo Hierarchical y esta en la ultima posicion, no se puede sacar");
            isOk = false;
            return;
        }
        
        ArrayList<String[]> above = collectAbove(index);
        popCup();
        restoreAbove(above);
        
        if(itemExistsInStack(cupNumber, "Lid")){
            removeLid(cupNumber);
            historyLids[cupNumber - 1] = 1;
        }
        
        isOk = true;
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
    public void pushLid(int lidNumber){
        if (lidNumber <= 0) {
            showError("El numero de tapa debe ser positivo");
            isOk = false;
            return;
        }
        if (lidNumber > maxCups) {
            showError("La tapa " + lidNumber + " supera el ancho maximo");
            isOk = false;
            return;
        }
        if (itemExistsInStack(lidNumber, "Lid")) {
            showError("La tapa " + lidNumber + " ya esta en la torre");
            isOk = false;
            return;
        }

        history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));
        Lid newLid = new Lid(lidNumber);
        
        stack.add(newLid);
        updateStackLogicLid(newLid.getHeight());

        LidVisual visual = new LidVisual(newLid);
        visuals.add(visual);
        if (isVisible){
            drawItem(visual, currentLevel);
        }
        else{
            drawItem(visual, currentLevel);
            visual.makeInvisible();
        }
        
        // Solo auto-asociar si la taza del mismo numero esta justo
        // en la cima actual (posicion inmediatamente anterior en el stack).
        // Si no es adyacente, cover() se encarga de la asociacion.
        if (itemExistsInStack(lidNumber, "Cup")){
            int cupIdx = findItemIndex(lidNumber, "Cup");
            Cup modificateCup = (Cup) stack.get(cupIdx);
            CupVisual modificateCupVisual = (CupVisual) visuals.get(cupIdx);
            modificateCup.setLid(newLid);
            int positionInSpace = modificateCup.getPositionInSpace();
            if(positionInSpace + 1 == currentLevel){
                modificateCup.coverCup();
                modificateCupVisual.isLided();
            }
        }
        
        currentLevel += 1;

        if (currentHeight > maxHeight){
            showError("La tapa " + lidNumber + " supera la altura maxima");
            if(stack.get(stack.size() - 2).getType().equals("Cup") && stack.get(stack.size() - 2).getNumber() == lidNumber){
                popCup();
            }
            else{
                popLid();
            }
            isOk = false;
            return;
        }
        
        historyLids[lidNumber - 1] = 1;
        isOk = true;
    }
    
    /**
     * Ingresa un tipo especifico de tapa a la torre
     * @param type es el tipo especifico el cual puede ser "Normal", "Fearful" o "Crazy"
     * @param lidNumber es el numero de la tapa que se desea ingresar, debe ser mayor a 0
     */
    public void pushLidType(String type, int lidNumber){
        if(!type.equals("Normal") && !type.equals("Fearful") && !type.equals("Crazy")){
            showError("El tipo de tapa que intento ingresar no es valido");
            isOk = false;
            return;
        }
        if (lidNumber <= 0) {
            showError("El numero de tapa debe ser positivo");
            isOk = false;
            return;
        }
        if (lidNumber > maxCups) {
            showError("La tapa " + lidNumber + " supera el ancho maximo");
            isOk = false;
            return;
        }
        if (itemExistsInStack(lidNumber, "Lid")) {
            showError("La tapa " + lidNumber + " ya esta en la torre");
            isOk = false;
            return;
        }
        
        if(type.equals("Normal")){
            pushLid(lidNumber);
        }
        else{
            ArrayList<Item> original = new ArrayList<>(stack);
            Visual newVisual = null;
            ArrayList<Item> items = new ArrayList<>();
            
            //Logica interna de las tapas especificas
            if(type.equals("Fearful")){
                if(!itemExistsInStack(lidNumber, "Cup")){
                    showError("La copa no esta en la torre y al ser una tapa Fearful no puede entrar");
                    return;
                }
                history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));
                Fearful newItem = new Fearful(lidNumber);
                int cupIdx = findItemIndex(lidNumber, "Cup");
                Cup modificateCup = (Cup) stack.get(cupIdx);
                CupVisual modificateCupVisual = (CupVisual) visuals.get(cupIdx);
                modificateCup.setLid(newItem);
                int positionInSpace = modificateCup.getPositionInSpace();
                
                stack.add(newItem);
                updateStackLogicLid(newItem.getHeight());
                
                if(positionInSpace + 1 == currentLevel){
                    modificateCup.coverCup();
                    modificateCupVisual.isLided();
                    // Registrar items que quedaron adentro
                    for(int n = cupIdx; n < stack.size(); n++){
                        modificateCup.addItemInside(stack.get(n));
                    }
                }
                historyLids[lidNumber - 1] = 2;
                newVisual = new VisualFearful(newItem);
            }
            else if(type.equals("Crazy")){
                if(itemExistsInStack(lidNumber, "Cup")){
                    while(!stack.isEmpty() && stack.get(stack.size()-1).getNumber() != lidNumber){
                        items.add(stack.get(stack.size()-1));
                        if(stack.get(stack.size()-1).getType().equals("Cup")){
                            popCup();
                        }
                        if(stack.get(stack.size()-1).getType().equals("Lid")){
                            popLid();
                        }
                    }
                    items.add(stack.get(stack.size()-1));
                    popCup();
                    Collections.reverse(items);
                }
                history.add(new States(currentHeight, currentLevel, new ArrayList<>(space)));
                Crazy newItem = new Crazy(lidNumber);
                stack.add(newItem);
                updateStackLogicLid(newItem.getHeight());
                historyLids[lidNumber - 1] = 3;
                newVisual = new VisualCrazy(newItem);
            }
            
            //Se crean las tapas visuales en la torre
            visuals.add(newVisual);
            if (isVisible){
                drawItem(newVisual, currentLevel);
            }
            else{
                drawItem(newVisual, currentLevel);
                newVisual.makeInvisible();
            }   
            
            currentLevel += 1;
            
            for(Item item : items){
                if(item.getType().equals("Cup")){
                    pushCupType(item.getSpecific(), item.getNumber());
                }
                else if(item.getType().equals("Lid")){
                    pushLidType(item.getSpecific(), item.getNumber());
                }
            }

            if (currentHeight > maxHeight){
                showError("El ingreso de la tapa no se puede realizar debido a que supera la altura maxima de la torre");
                popAll();
                for(Item item : items){
                    if(item.getType().equals("Cup")){
                        pushCupType(item.getSpecific(), item.getNumber());
                    }
                    else if(item.getType().equals("Lid")){
                        pushLidType(item.getSpecific(), item.getNumber());
                    }
                }
                isOk = false;
                return;
            }
            
            isOk = true;
        }
    }

    /**
     * Remueve la tapa en la cima de la torre.
     * Guarda las tazas que hubiera encima y las vuelve a colocar.
     * Requisito 3 - Ciclo 1.
     */
    public void popLid() {
        if (!hasItemsInStack("Lid")) {
            showError("No hay tapas para remover");
            isOk = false;
            return;
        }
        
        int topLidIdx = getLastItemIndex("Lid");
        if(stack.get(topLidIdx).getSpecific().equals("Fearful")){
            int idxCup = findItemIndex(stack.get(topLidIdx - 1).getNumber(), "Cup");
            if(idxCup != -1){
                Cup cup = (Cup) stack.get(idxCup);
                boolean isLided = cup.hasLid();
                if(isLided){
                    showError("No se puede sacar esta tapa debido a que es de tipo Fearful y esta tapando su taza");
                    return;
                }
            }
        }
        
        ArrayList<String[]> cupsAbove = collectAbove(topLidIdx);;        

        int lidNum = stack.get(topLidIdx).getNumber();
        int cupIdx = findItemIndex(lidNum, "Cup");
        if (cupIdx != -1){
            Cup cup = (Cup) stack.get(cupIdx);
            cup.removeLid();
            CupVisual cupVisual = (CupVisual) visuals.get(cupIdx);
            cupVisual.notLided();
        }

        removeLastVisual("Lid");
        removeLastFromStack();
        restoreLastState();
        
        restoreAbove(cupsAbove);
        
        historyLids[lidNum - 1] = 0;
        isOk = true;
    }

    /**
     * Remueve una tapa especifica de cualquier posicion.
     * Los elementos encima se guardan y se vuelven a apilar.
     * Requisito 3 - Ciclo 1.
     *
     * @param lidNumber numero de la tapa a remover
     */
    public void removeLid(int lidNumber) {
        int index = findItemIndex(lidNumber, "Lid");
        if (index == -1) {
            showError("No se encontro la tapa " + lidNumber);
            isOk = false;
            return;
        }
        ArrayList<String[]> above = collectAbove(index);
        popLid();
        restoreAbove(above);
        isOk = true;
    }

    // ---------------------------------------------------------
    // REQUISITO 4 - Ordenar de mayor a menor
    // ---------------------------------------------------------

    /**
     * Ordena las tazas de mayor a menor numero. Solo incluye las que caben.
     * El menor numero siempre queda en la cima.
     * Requisito 4 - Ciclo 1.
     */
    public void orderTower(){
        if (stack.isEmpty()) return;
        if(stack.get(0).getSpecific().equals("Hierarchical")){
            showError("No se puede ordenar la torre debido a que se encuentra un elemento Hierarchical que no se puede quitar");
            isOk = false;
            return;
        }
        ArrayList<Item> originalStack = (ArrayList<Item>) stack.clone();
        ArrayList<Item> itemsToOrder = new ArrayList<>(stack);
    
        popAll();
    
        ArrayList<Item> ordered = buildOrderedList(itemsToOrder);
    
        for (Item item : ordered) {
            if (item.getType().equals("Cup")) {
                pushCupType(item.getSpecific(), item.getNumber());
            } else if (item.getType().equals("Lid")) {
                pushLidType(item.getSpecific(), item.getNumber());
            }
    
            if (!isOk || currentHeight > maxHeight) {
                showError("No se pudo organizar la torre");
                popAll();
                // Restaurar torre original
                for (Item orig : originalStack) {
                    if (orig.getType().equals("Cup")) {
                        pushCupType(orig.getSpecific(), orig.getNumber());
                    } else if (orig.getType().equals("Lid")) {
                        pushLidType(orig.getSpecific(), orig.getNumber());
                    }
                }
                isOk = false;
                return;
            }
        }
        isOk = true;
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
        if (stack.isEmpty()) return;
        if(stack.get(0).getSpecific().equals("Hierarchical")){
            showError("No se puede ordenar la torre debido a que se encuentra un elemento Hierarchical que no se puede quitar");
            isOk = false;
            return;
        }
        
        ArrayList<Item> originalStack = (ArrayList<Item>) stack.clone();
        ArrayList<Item> reverse = new ArrayList<>();
        int[] copyOfLidHistory = historyLids.clone();
        
        for(int i = stack.size() - 1; i >= 0; i--){
            System.out.println("numero " + stack.get(i).getType() + " " + stack.get(i).getNumber() + " " + reverse);
            if(stack.get(i).getType().equals("Lid")){
                int num = stack.get(i).getNumber();
                if(!itemExistsInStack(num, "Cup")){
                    reverse.add(stack.get(i));
                }
            }
            else if(stack.get(i).getType().equals("Cup")){
                reverse.add(stack.get(i));
            }
        }
        
        popAll();
        for(int i = 0; i < copyOfLidHistory.length; i++){
            historyLids[i] = copyOfLidHistory[i];
        }
        
        for(Item i : reverse){
            if(i.getType().equals("Cup")){
                pushCupType(i.getSpecific(), i.getNumber());
                if(!isOk){
                    showError("No se ha podido revertir la torre");
                    popAll();
                    for(Item item : originalStack){
                        if(i.getType().equals("Cup")){
                            pushCupType(i.getSpecific(), i.getNumber());
                        }
                        else if(i.getType().equals("Lid")){
                            pushLidType(i.getSpecific(), i.getNumber());
                        }
                    }
                    isOk = false;
                    return;
                }
            }
            else if(i.getType().equals("Lid")){
                pushLidType(i.getSpecific(), i.getNumber());
                if(!isOk){
                    showError("No se ha podido revertir la torre");
                    popAll();
                    for(Item item : originalStack){
                        if(i.getType().equals("Cup")){
                            pushCupType(i.getSpecific(), i.getNumber());
                        }
                        else if(i.getType().equals("Lid")){
                            pushLidType(i.getSpecific(), i.getNumber());
                        }
                    }
                    isOk = false;
                    return;
                }
            }
        }
        
        isOk = true;
    }

    // ---------------------------------------------------------
    // REQUISITO 6 - Consultar altura
    // ---------------------------------------------------------

    /**
     * Retorna la altura actual de la torre en cm.
     * Requisito 6 - Ciclo 1.
     *
     * @return altura en cm
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
        for(int i = 0; i < stack.size()-1; i++){
            if(stack.get(i).getType().equals("Cup") && stack.get(i+1).getType().equals("Lid")){
                if(stack.get(i).getNumber() == stack.get(i+1).getNumber()){
                    lided.add(stack.get(i).getNumber());
                }
            }
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
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getType().equals("Cup")) {
                items.add(new String[]{"cup", String.valueOf(stack.get(i).getNumber())});
            } else if (stack.get(i).getType().equals("Lid")) {
                items.add(new String[]{"lid", String.valueOf(stack.get(i).getNumber())});
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
            for (Visual v : visuals) v.makeVisible();
        }
    }

    /**
     * Hace invisible el simulador. Sigue funcionando en modo invisible.
     * Requisito 8 - Ciclo 1.
     */
    public void makeInvisible() {
        if (isVisible) {
            isVisible = false;
            for (Visual v : visuals) v.makeInvisible();
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
        return isOk;
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
     * Agrega n tazas automaticamente a una torre existente, de mayor a menor.
     * Si cups supera el maximo permitido, muestra error y no agrega nada.
     * Requisito 10 - Ciclo 2.
     *
     * @param cups numero de tazas a crear
     */
    public void createTower(int cups) {
        popAll();
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
            if (stack.get(i).getType().equals("Lid")) continue;
            Cup cup = (Cup) stack.get(i);

            if (stack.get(i + 1).getType().equals("Cup")) continue;
            Lid lid = (Lid) stack.get(i + 1);

            // Si la taza y la tapa tienen el mismo numero y la taza no tiene tapa aun
            if (cup.getNumber() == lid.getNumber() && !cup.hasLid()) {
                cup.setLid(lid);
            }
        }
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
     * Inicializa todos los atributos. Usado por ambos constructores
     * para evitar duplicacion de codigo (principio DRY).
     */
    private void initializeTower(int maxHeight, int maxWidth) {
        this.maxHeight  = maxHeight;
        this.maxCups    = (maxWidth + 1) / 2;
        this.stack      = new ArrayList<>();
        this.history    = new ArrayList<>();
        this.historyLids   = new int[maxCups];
        this.currentHeight = 0;
        this.currentLevel  = 0;
        this.space      = new ArrayList<>();
        this.grid       = new TowerGrid(maxHeight, maxWidth);
        this.visuals    = new ArrayList<>();
        this.isVisible  = true;
        this.isOk       = true;
    }

    /** Actualiza el mapa de ocupacion al agregar una taza. */
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

    /** Actualiza el mapa de ocupacion al agregar una tapa. */
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
     * Ayuda a la apilacion especial de la clase Reverse
     * @param cupHeight es el numero de la copa que se va a ingresar
     */
    private void updateStackLogicReverse(int cupHeight) {
        if (space.isEmpty()) {
            for (int i = 0; i < cupHeight; i++) {
                space.add(cupHeight);
            }
            currentLevel = space.size() - 1;
            currentHeight = space.size();
            return;
        }    
        int n = currentLevel;
        while (n < space.size() && space.get(n) <= cupHeight) n++;
        int insertLevel = n;
        while (space.size() < insertLevel + 1) space.add(-1);
        for (int i = 0; i < cupHeight; i++) {
            int index = insertLevel - i;
            if (index < 0) {
                space.add(0, cupHeight);
                insertLevel++;
            } else {
                space.set(index, cupHeight);
            }
        }
        currentLevel = insertLevel;
        currentHeight = space.size();
    }

    /**
     * Simula la altura resultante de intercambiar i y j en la secuencia,
     * SIN modificar el estado real de la torre.
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
    
    private int findItemIndex(int number, String type){
        for(int i = 0; i < stack.size(); i++){
            if(stack.get(i).getType().equals(type) && stack.get(i).getNumber() == number) return i;
        }
        return -1;
    }

    private int findIndexOf(String[] obj) {
        int num = Integer.parseInt(obj[1]);
        return obj[0].equals("cup") ? findItemIndex(num, "Cup") : findItemIndex(num, "Lid");
    }
    
    private boolean itemExistsInStack(int number, String type){ return findItemIndex(number, type) != -1;}
    
    private boolean hasItemsInStack(String type){
        for (Item c : stack){
            if (c.getType().equals(type)){
                return true;
            }
        }
        return false;
    }
    
    private int getLastItemIndex(String type){
        for (int i = stack.size() - 1; i >= 0; i--) if (stack.get(i).getType().equals(type)) return i;
        return -1;   
    }

    private ArrayList<String[]> buildSequence() {
        ArrayList<String[]> seq = new ArrayList<>();
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getType().equals("Cup"))
                seq.add(new String[]{"cup", String.valueOf(stack.get(i).getNumber())});
            else if (stack.get(i).getType().equals("Lid"))
                seq.add(new String[]{"lid", String.valueOf(stack.get(i).getNumber())});
        }
        return seq;
    }
    
    private int[] getItemNumbers(String type){
        ArrayList<Integer> numItems = new ArrayList<>();
        for(Item c : stack){
            if(c.getType().equals(type)){
                int numItem = c.getNumber();
                numItems.add(numItem);
            }
        }
        int[] arr = new int[numItems.size()];
        for(int i = 0; i < numItems.size(); i++){
            arr[i] = numItems.get(i);
        }
        return arr;
    }

    private void rebuildTowerOnlyOneItem(int[] numbers, String type) {
        int[] lidMemory = Arrays.copyOf(historyLids, historyLids.length);
    
        popAll();
        
        historyLids = lidMemory;
        if(type.equals("Cup")){
            for(int i = 0; i < numbers.length; i++){
                if(height() + (numbers[i]*2 - 1) > maxHeight){
                    popAll();
                    return;
                }
                pushCup(numbers[i]);
            }
        }
        else if(type.equals("Lid")){
            for(int i = 0; i < numbers.length; i++){
                if(height() + (numbers[i]*2 - 1) > maxHeight){
                    popAll();
                    return;
                }
                pushLid(numbers[i]);
            }
        }
    }

    private ArrayList<String[]> collectAbove(int index){
        ArrayList<String[]> above = new ArrayList<>();
        for (int i = stack.size() - 1; i > index; i--) {
            if (stack.get(i).getType().equals("Cup")) {
                above.add(new String[]{"Cup", stack.get(i).getSpecific(), String.valueOf(stack.get(i).getNumber())});
                absolutePopCup();
            } else {
                above.add(new String[]{"Lid", stack.get(i).getSpecific(), String.valueOf(stack.get(i).getNumber())});
                absolutePopLid();
            }
        }
        return above;
    }

    private void restoreAbove(ArrayList<String[]> above) {
        for (int i = above.size() - 1; i >= 0; i--) {
            if (above.get(i)[0].equals("Cup")){
                pushCupType(above.get(i)[1], Integer.parseInt(above.get(i)[2]));
            }
            else{
                if(above.get(i)[1].equals("Fearful")){
                    if(!itemExistsInStack(Integer.parseInt(above.get(i)[2]), "Cup")){
                        historyLids[Integer.parseInt(above.get(i)[2]) - 1] = 2;
                    }
                    else{
                        pushLidType(above.get(i)[1], Integer.parseInt(above.get(i)[2]));
                    }
                }
                else{
                    pushLidType(above.get(i)[1], Integer.parseInt(above.get(i)[2]));
                }
            }
        }
    }

    private void popAll() {
        if(stack.isEmpty()){
            return;
        }
        Arrays.fill(historyLids, 0);
        while (!stack.isEmpty()) {
            if (stack.get(stack.size() - 1).getType().equals("Cup")) absolutePopCup();
            else absolutePopLid();
        }
    }
    
    private void absolutePopCup(){
        if (!hasItemsInStack("Cup")) {
            showError("No hay tazas para remover");
            return;
        }

        int topCupIdx = getLastItemIndex("Cup");
        ArrayList<String[]> lidsAbove = collectAbove(topCupIdx);
        int numCup = stack.get(topCupIdx).getNumber();

        removeLastVisual("Cup");
        removeLastFromStack();
        restoreLastState();

        restoreAbove(lidsAbove);

        if(historyLids[numCup - 1] == 1){
            removeLid(numCup);
            historyLids[numCup - 1] = 1;
        }
    }
    
    private void absolutePopLid(){
        if (!hasItemsInStack("Lid")) {
            showError("No hay tapas para remover");
            return;
        }

        ArrayList<Integer> cupsAbove = new ArrayList<>();
        int topLidIdx = getLastItemIndex("Lid");
        
        ArrayList<String[]> lidsAbove = collectAbove(topLidIdx);

        int lidNum = stack.get(topLidIdx).getNumber();
        int cupIdx = findItemIndex(lidNum, "Cup");
        if (cupIdx != -1){
            Cup cup = (Cup) stack.get(cupIdx);
            cup.removeLid();
            CupVisual cupVisual = (CupVisual) visuals.get(cupIdx);
            cupVisual.notLided();
        }

        removeLastVisual("Lid");
        removeLastFromStack();
        restoreLastState();
        
        restoreAbove(lidsAbove);
        
        historyLids[lidNum - 1] = 0;
    }

    private void restoreLastState() {
        if (!history.isEmpty()) {
            States prev = history.remove(history.size() - 1);
            currentHeight = prev.getCurrentHeight();
            currentLevel  = prev.getCurrentLevel();
            space         = new ArrayList<>(prev.getSpace());
        }
    }

    private void removeLastFromStack() {
        int last = stack.size() - 1;
        stack.remove(last);
    }
    
    private void removeLastVisual(String itemType){
        int last = visuals.size() - 1;
        String type = visuals.get(last).getItem().getType();
        while(!type.equals(itemType)){
            last -= 1;
            type = visuals.get(last).getItem().getType();
        }
        visuals.get(last).erase();
        visuals.remove(last);
    }
    
    private void drawItem(Visual visual, int level){
        int xPos = calculateXPosition(visual.getItem().getNumber());
        int yPos = grid.getYOrigin() - (int) grid.getScale();
        visual.draw(xPos, yPos - (int)(level * grid.getScale()), grid.getScale());
    }

    private int calculateXPosition(int number) {
        int xOffset = (maxCups - number) * (int) grid.getScale();
        return grid.getXOrigin() + xOffset;
    }

    private void redrawAll() {
        int level = 0;
        for (int i = 0; i < stack.size(); i++) {
            drawItem(visuals.get(i), level);
            level++;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "stackingItems", JOptionPane.ERROR_MESSAGE);
    }

    private void reverseArray(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i]   = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }
    
    /**
     * Construye recursivamente una lista ordenada de mayor a menor,
     * priorizando tazas, respetando las tapas que cubren tazas.
     *
     * @param items lista de items a ordenar
     * @return lista ordenada lista para insertar en la torre
     */
    private ArrayList<Item> buildOrderedList(ArrayList<Item> items) {
        ArrayList<Item> result = new ArrayList<>();
        ArrayList<Item> processed = new ArrayList<>();
    
        if (items.isEmpty()) return result;
    
        ArrayList<Item> cups = new ArrayList<>();
        ArrayList<Item> orphanLids = new ArrayList<>();
    
        for (Item item : items) {
            if (item.getType().equals("Cup")) {
                cups.add(item);
            } else if (item.getType().equals("Lid")) {
                boolean hasCup = false;
                for (Item other : items) {
                    if (other.getType().equals("Cup") && other.getNumber() == item.getNumber()) {
                        hasCup = true;
                        break;
                    }
                }
                if (!hasCup) orphanLids.add(item);
            }
        }
    
        cups.sort((a, b) -> b.getNumber() - a.getNumber());
    
        for (Item cup : cups) {
            if (processed.contains(cup)) continue;
    
            result.add(cup);
            processed.add(cup);
    
            Item associatedLid = null;
            for (Item item : items) {
                if (item.getType().equals("Lid") && item.getNumber() == cup.getNumber()) {
                    associatedLid = item;
                    break;
                }
            }
    
            if (associatedLid != null) {
                int cupIdx = items.indexOf(cup);
                int lidIdx = items.indexOf(associatedLid);
    
                if (cupIdx != -1 && lidIdx != -1 && lidIdx > cupIdx) {
                    ArrayList<Item> insideItems = new ArrayList<>();
                    for (int i = cupIdx + 1; i < lidIdx; i++) {
                        insideItems.add(items.get(i));
                    }
    
                    if (!insideItems.isEmpty()) {
                        ArrayList<Item> orderedInside = buildOrderedList(insideItems);
                        result.addAll(orderedInside);
                        // Marcar todos los internos como procesados
                        processed.addAll(insideItems);
                    }
                }
    
                result.add(associatedLid);
                processed.add(associatedLid);
            }
        }
    
        orphanLids.sort((a, b) -> b.getNumber() - a.getNumber());
        for (Item lid : orphanLids) {
            if (!processed.contains(lid)) {
                result.add(lid);
                processed.add(lid);
            }
        }
    
        return result;
    }
}