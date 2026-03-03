import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import java.util.Collections;

/**
 * Simulador de torre de tazas apilables.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 14/02/2026
 */
public class Tower {
    // Modelo de datos
    private ArrayList<Cup> stack;
    private ArrayList<Lid> stackLids;
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
    private ArrayList<LidVisual> lidVisuals;
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
        
        grid.makeVisible();
    }
    
    /**
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

        for(int i = 0; i < stack.size(); i++){
            if (stack.get(i) != null){
                if(stack.get(i).getNumber() == cupNumber){
                    showError("Esta taza ya esta dentro de la torre");
                    return;
                }
            }
        }
        
        // Guardar estado actual
        States state = new States(currentHeight, currentLevel, new ArrayList<>(space));
        history.add(state);
        
        // Crear taza
        Cup newCup = new Cup(cupNumber);
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
     * Remueve una taza especifica del stack
     * @param cupNumber numero de la taza a remover
     */
    public void removeCup(int cupNumber) {
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
        
        if (index == -1) {
            showError("No se encontro la taza " + cupNumber);
            return;
        }
        
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
    }
    
    /**
     * Añade una tapa a la ultima taza
     * @param lidNumber numero de la tapa
     */
    public void pushLid(int lidNumber) {
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
        }
    }
    
    /**
     * Remueve la tapa de la ultima taza
     */
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
    }
    
    /**
     * Remueve la tapa de una taza especifica
     * @param lidNumber numero de la tapa
     */
    public void removeLid(int lidNumber) {
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
        
        if (index == -1) {
            showError("No se encontro la tapa " + lidNumber);
            return;
        }
        
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
    }
    
    /**
     * Ordena las tazas de mayor a menor
     */
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
            if(cup != null){
                if (cup.hasLid()) {
                    lided.add(cup.getNumber());
                }
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
        
        for (Cup cup : stack){
            if(cup != null){
                items.add(new String[]{"cup", String.valueOf(cup.getNumber())});
                if (cup.hasLid()) {
                    items.add(new String[]{"lid", String.valueOf(cup.getNumber())});
                }
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
     * @param: cupHeight es un atributo que representa la altura de la copa que se quiere ingresar
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
    
    private void drawLid(LidVisual visual, int level){
        int xPos = calculateXPosition(visual.getLid().getNumber());
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