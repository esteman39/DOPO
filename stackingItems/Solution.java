import java.util.ArrayList;

/**
 * Write a description of class Solution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Solution{
    private int maxCups;
    private long reach;
    private ArrayList<Integer> listSolution;
    private int minHeight;
    private long maxHeight;
    private ArrayList<Integer> compliteList;
    
    /**
     * Esta es la clase que determina la solucion del ejercicio planteado.
     * @param: numCups es el entero que representa la cantidad de copas con las que se puede trabajar, sin que estas se repitan
     * @param: numHeight es el entero que representa la altura que se desea alcanzar en el ejercicio.
     */
    public Solution(int numCups, long numHeight){
        this.maxCups = numCups;
        this.reach = numHeight;
        this.listSolution = new ArrayList<>();
        this.minHeight = (maxCups * 2) - 1;
        this.maxHeight = 0;
        this.compliteList = new ArrayList<>();
        for(int i = 1; i <= maxCups; i++){
            this.maxHeight += (long)(i * 2) - 1;
            this.compliteList.add((i * 2) - 1);
        }
    }
    
    /**
     * Verifica que el numero que se esta buscando alcanzar este dentro del rango de los posibles valores que puede tener de altura la torre
     * @return: retorna un booleano el cual indica si cumple con la condicion o no.
     */
    public boolean firstInstance(){
        if(reach > maxHeight || reach < (long)minHeight){
            return false;
        }
        return true;
    }
    
    /**
     * Es una segunda verificacion la cual nos ayuda a saber si de todas las combinaciones posibles se puede dar la altura que se busca alcanzar.
     * @param: torre es la lista de copas que pueden estar por debajo de la torre mas grande para poder lograr que esta alcance el resultado buscado.
     * @param: objetivo es el entero que represnta la altura que la torre tiene que alcanzar
     * @return: retorna un ArrayList que representa el orden en la torre que deben estar las copas por debajo de la mas grande para poder alcanzar la altura deseada.
     */
    public ArrayList<Integer> secondInstance(ArrayList<Integer> torre, long objetivo){
        ArrayList<Integer> lista = new ArrayList<>();
        search(torre, objetivo, 0, 0, new ArrayList<>(), lista);
        return lista;
    }
    
    /**
     * Da el algoritmo correcto para resolver el problema planteado, en este caso, se decidio utilizar recurrencia.
     * @return: Retorna un ArrayList de enteros el cual simboliza la solucion del problema planteado.
     */
    public ArrayList<Integer> getSolution(){
        if(reach == minHeight){
            for(int i = maxCups; i > 0; i--){
                int cup = (i * 2) - 1;
                listSolution.add(cup);
            }
        }
        else if(reach == maxHeight){
            for(int i = 1; i <= maxCups; i++){
                int cup = (i * 2) - 1;
                listSolution.add(cup);
            }            
        }
        else if(firstInstance()){
            Solution internalSolution = new Solution(maxCups - 1, reach - 1);
            if(!internalSolution.firstInstance()){
                ArrayList<Integer> subInternalSolution = new ArrayList<>();
                ArrayList<Integer> lista = new ArrayList<>();
                for(int i = 1; i < maxCups; i++){
                    lista.add((i * 2) - 1);
                }
                subInternalSolution = secondInstance(lista, reach - ((long)(maxCups * 2) - 1));
                if(subInternalSolution.size() != 0){
                    for(int elemento: subInternalSolution){
                        listSolution.add(elemento);
                    }
                    listSolution.add((maxCups * 2) - 1);
                    for(int i = maxCups-1; i >= 0; i--){
                        if(!listSolution.contains(compliteList.get(i))){
                            listSolution.add(compliteList.get(i));
                        }
                    }
                }
            }
            else{
                listSolution.add((maxCups * 2) - 1);
                ArrayList<Integer> subSolution = new ArrayList<>();
                subSolution = internalSolution.getSolution();
                for(int elemento: subSolution){
                    listSolution.add(elemento);
                }
            }
        }
        return listSolution;
    }
    
    // =========================================================
    // METODOS PRIVADOS
    // =========================================================
    
    /**
     * Este metodo nos ayuda a determinar la posicion de las copas que van por debajo de la mas grande para poder llegar a la altura deseada
     * @param: torre son los elementos que pueden estar por debajo de la copa mas grande para llegar a la posicion mas alta.
     * @param: objetivo es el entero que representa la altura que se desea alcanzar
     * @param: index es el entero que representa la posicion en el que se van a analizar los datos.
     * @param: suma es un entero que empieza en 0 y que determina si la altura es alcanzada o no.
     * @param: actual es el orden de las copas que se van acumulando.
     * @param: lista es el resultado final que determina el orden final, es el resultado de las copas que tienen que ir debajo
     * @return: un booleano el cual nos indica si hay una combinacion de copas donde la altura maxima sea la altura que se desea llegar.
     */
    private boolean search(ArrayList<Integer> torre, long objetivo, int index, long suma, ArrayList<Integer> actual, ArrayList <Integer> lista){
        if(suma == objetivo){
            lista.addAll(actual);
            return true;
        }
        for(int i = index; i < torre.size(); i++){
            if(suma + torre.get(i) > objetivo){
                break;
            }
            actual.add(torre.get(i));
            if(search(torre, objetivo, i + 1, suma + torre.get(i), actual, lista)){
                return true;
            }
            actual.remove(actual.size()-1);
        }
        return false;
    }
}