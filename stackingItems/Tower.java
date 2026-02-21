package stackingItems;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * Simulador de torre de tazas apilables.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 14/02/2026
 */
public class Tower {
    // Modelo de datos
    private ArrayList<Cup> stack;
    private ArrayList<Integer> lids;
    private int maxCups;
    private int height;
    
    // Logica de apilamiento
    private int currentHeight;      // estabilizador
    private int currentLevel;       // momentoY
    private ArrayList<Integer> space;     // faltante
    
    // Historial para deshacer
    private ArrayList<States> history;  // [height, level, space]
    
    // Componentes visuales
    private TowerGrid grid;
    private ArrayList<CupVisual> cupVisuals;
    private boolean isVisible;
    
    /**
     * Constructor de Tower
     * @param maxHeight altura maxima en cm
     * @param maxWidth ancho maximo en cm
     */
    public Tower(int maxHeight, int maxWidth) {
        // Inicializar modelo
        this.height = maxHeight;
        this.maxCups = (maxWidth + 1) / 2;
        this.stack = new ArrayList<>();
        this.lids = new ArrayList<>();
        this.history = new ArrayList<>();
        
        // Estado inicial
        this.currentHeight = 0;
        this.currentLevel = 0;
        this.space = new ArrayList<>();
        
        // Inicializar vista
        this.grid = new TowerGrid(maxHeight, maxWidth);
        this.cupVisuals = new ArrayList<>();
        this.isVisible = true;
        
        grid.makeVisible();
    }
    
    /**
     * Añade una taza al stack
     * @param cupNumber numero de la taza
     */
    public void pushCup(int cupNumber) {
        if(cupNumber > maxCups){
            showError("La taza supera el ancho maximo");
            return;
        }
        
        if (cupNumber <= 0) {
            showError("El numero de taza debe ser positivo");
            return;
        }
        
        if (stack.size() >= maxCups) {
            showError("La torre esta llena");
            return;
        }
        
        for(int i = 0; i < stack.size(); i++){
            if(stack.get(i).getNumber() == cupNumber){
                showError("Esta taza ya esta dentro de la torre");
                return;
            }
        }
        
        // Guardar estado actual
        States state = new States(currentHeight, currentLevel, new ArrayList<>(space));
        history.add(state);
        
        // Crear taza
        Cup newCup = new Cup(cupNumber);
        stack.add(newCup);
        
        // Actualizar logica de apilamiento
        updateStackLogic(newCup.getHeight());
        
        // Crear y dibujar visual
        CupVisual visual = new CupVisual(newCup);
        cupVisuals.add(visual);
        
        if (isVisible) {
            drawCup(visual, currentLevel);
        }
        currentLevel += 1;
        
        //Verificacion final
        if(currentHeight > height){
            showError("Has superado el limite maximo");
            popCup();
            return;
        }
    }
    
    /**
     * Remueve la ultima taza del stack
     */
    public void popCup() {
        if (stack.isEmpty()){
            showError("No hay tazas para remover");
            return;
        }
        
        // Remover visual
        int lastIndex = cupVisuals.size() - 1;
        cupVisuals.get(lastIndex).erase();
        cupVisuals.remove(lastIndex);
        
        // Remover taza
        stack.remove(lastIndex);
        
        // Restaurar estado previo
        if (!history.isEmpty()) {
            States prev = history.remove(history.size() - 1);
            currentHeight = prev.getCurrentHeight();
            currentLevel = prev.getCurrentLevel();
            space = new ArrayList<>();
            space = prev.getSpace();
        }
    }
    
    /**
     * Remueve una taza especifica del stack
     * @param cupNumber numero de la taza a remover
     */
    public void removeCup(int cupNumber) {
        // Buscar la taza
        int index = -1;
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getNumber() == cupNumber) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            showError("No se encontro la taza " + cupNumber);
            return;
        }
        
        // Guardar tazas encima
        ArrayList<Integer> above = new ArrayList<>();
        for (int i = stack.size() - 1; i > index; i--) {
            above.add(stack.get(i).getNumber());
            popCup();
        }
        
        // Remover la taza objetivo
        popCup();
        
        // Re-apilar las tazas que estaban encima
        for (int i = above.size() - 1; i >= 0; i--) {
            pushCup(above.get(i));
        }
    }
    
    /**
     * Añade una tapa a la ultima taza
     * @param lidNumber numero de la tapa
     */
    public void pushLid(int lidNumber) {
        if (stack.isEmpty()) {
            showError("No hay tazas en la torre");
            return;
        }
        
        int maximo = space.get(space.size() - 1);
        
        if (maximo != ((lidNumber*2)-1)) {
            showError("La tapa no corresponde a la taza superior");
            return;
        }
        
        for(int i = 0; i < lids.size(); i++){
            if(lids.get(i) == lidNumber) {
                showError("La taza ya tiene tapa");
                return;
            }
        }
        // Añadir tapa
        int index = 0;
        for(int i = 0; i < stack.size(); i++){
            if(stack.get(i).getNumber() == lidNumber){
                index = i;
                break;
            }
        }
        Cup topCup = stack.get(index);
        Lid lid = new Lid(lidNumber);
        topCup.setLid(lid);
        
        // Actualizar altura (la tapa suma 1 cm)
        currentLevel = currentHeight;
        currentHeight += 1;
        space.add(0);
        lids.add(lidNumber);
        
        // Actualizar visual
        if (isVisible) {
            int lastIndex = cupVisuals.size() - 1;
            int xPos = calculateXPosition(topCup.getNumber());
            //int yOffset = calculateYOffset(lastIndex);
            int yPos = grid.getYOrigin() - (int)grid.getScale();
            
            cupVisuals.get(lastIndex).updateLid(xPos, yPos - (int)(currentLevel * grid.getScale()), grid.getScale());
        }
    }
    
    /**
     * Remueve la tapa de la ultima taza
     */
    public void popLid() {
        if (stack.isEmpty()) {
            showError("No hay tazas en la torre");
            return;
        }
        
        if(lids.isEmpty()){
            showError("No hay tazas que quitar de la torre");
            return;
        }
        
        int lid = lids.get(lids.size() - 1);
        lids.remove(lids.size() - 1);
        
        // Remover tapa y taza juntas
        removeCup(lid);
    }
    
    /**
     * Remueve la tapa de una taza especifica
     * @param lidNumber numero de la tapa
     */
    public void removeLid(int lidNumber) {
        // Buscar taza con esa tapa
        int index = -1;
        for (int i = 0; i < lids.size(); i++) {
            if(lids.get(i) == lidNumber){
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            showError("No se encontro tapa " + lidNumber);
            return;
        }
        
        int indexL = 0;
        for(int i = 0; i < stack.size(); i++){
            if(stack.get(i).getNumber() == lidNumber){
                indexL = i;
                break;
            }
        }
        Cup cup = stack.get(indexL);
        cup.removeLid();
        
        // Actualizar altura
        currentHeight -= 1;
        currentLevel = currentHeight;
        space.remove(space.size());
        
        // Actualizar visual
        if (isVisible) {
            int xPos = calculateXPosition(cup.getNumber());
            //int yOffset = calculateYOffset(index);
            int yPos = grid.getYOrigin() - (int)grid.getScale();
            
            cupVisuals.get(index).updateLid(xPos, yPos - (int)(currentLevel * grid.getScale()), grid.getScale());
        }
    }
    
    /**
     * Ordena las tazas de mayor a menor
     */
    public void orderTower() {
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
    }
    
    /**
     * Invierte el orden de las tazas
     */
    public void reverseTower() {
        if (stack.isEmpty()) {
            return;
        }
        
        // Guardar numeros en orden actual
        int[] numbers = new int[stack.size()];
        for (int i = 0; i < stack.size(); i++) {
            numbers[i] = stack.get(i).getNumber();
        }
        
        // Vaciar torre
        while (!stack.isEmpty()) {
            popCup();
        }
        
        // Re-llenar en orden inverso
        for (int i = numbers.length - 1; i >= 0; i--) {
            pushCup(numbers[i]);
        }
    }
    
    /**
     * Retorna la altura actual de la torre
     */
    public int height() {
        return currentHeight;
    }
    
    /**
     * Retorna los numeros de tazas con tapa
     */
    public int[] lidedCups() {
        ArrayList<Integer> lided = new ArrayList<>();
        
        for (Cup cup : stack) {
            if (cup.hasLid()) {
                lided.add(cup.getNumber());
            }
        }
        
        // Convertir a array y ordenar
        int[] result = new int[lided.size()];
        for (int i = 0; i < lided.size(); i++) {
            result[i] = lided.get(i);
        }
        Arrays.sort(result);
        
        return result;
    }
    
    /**
     * Retorna informacion de elementos apilados
     * Formato: {{"cup", "4"}, {"lid", "4"}, {"cup", "2"}, ...}
     */
    public String[][] stackingItems() {
        ArrayList<String[]> items = new ArrayList<>();
        
        for (Cup cup : stack) {
            items.add(new String[]{"cup", String.valueOf(cup.getNumber())});
            if (cup.hasLid()) {
                items.add(new String[]{"lid", String.valueOf(cup.getNumber())});
            }
        }
        
        String[][] result = new String[items.size()][2];
        for (int i = 0; i < items.size(); i++) {
            result[i] = items.get(i);
        }
        
        return result;
    }
    
    /**
     * Hace visible el simulador
     */
    public void makeVisible() {
        if (!isVisible) {
            isVisible = true;
            grid.makeVisible();
            
            for (CupVisual visual : cupVisuals) {
                visual.makeVisible();
            }
        }
    }
    
    /**
     * Hace invisible el simulador
     */
    public void makeInvisible() {
        if (isVisible) {
            isVisible = false;
            grid.makeInvisible();
            
            for (CupVisual visual : cupVisuals) {
                visual.makeInvisible();
            }
        }
    }
    
    /**
     * Finaliza el simulador
     */
    public void exit() {
        JOptionPane.showMessageDialog(null, 
            "MUCHAS GRACIAS POR PROBAR EL SIMULADOR", 
            "stackingItems", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
    /**
     * Retorna true (para compatibilidad)
     */
    public boolean ok() {
        return true;
    }
    
    // ========== METODOS PRIVADOS ==========
    
    /**
     * Muestra un mensaje de error si es visible
     */
    private void showError(String message) {
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message, 
                "stackingItems", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Actualiza la logica de apilamiento
     */
    private void updateStackLogic(int cupHeight){
        int n = currentLevel;
        while(n < space.size()){
            if(space.get(n) < cupHeight){
                currentLevel += 1;
            }
            else{
                break;
            }
            n += 1;
        }
        while((currentLevel + cupHeight) > space.size()){
            space.add(-1);
        }
        for(int i = currentLevel; i < (currentLevel + cupHeight); i++){
            space.set(i, cupHeight);
        }
        currentHeight = space.size();
    }
    
    /**
     * Calcula la posicion X para una taza
     */
    private int calculateXPosition(int cupNumber) {
        int xOffset = (maxCups - cupNumber) * (int)grid.getScale();
        return grid.getXOrigin() + xOffset;
    }
    
    /**
     * Dibuja una taza en su posicion
     */
    private void drawCup(CupVisual visual, int level) {
        int xPos = calculateXPosition(visual.getCup().getNumber());
        int yPos = grid.getYOrigin() - (int)grid.getScale();
        visual.draw(xPos, yPos - (int)(level * grid.getScale()), grid.getScale());
    }
    
    /**
     * Invierte un array
     */
    private void reverseArray(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }
}
