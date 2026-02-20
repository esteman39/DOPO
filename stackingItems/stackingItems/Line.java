/**
* Write a description of class Line here.
*      
* @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
* @version 1.0 (14/02/2026)
*/
public class Line{
    private int xPosition;
    private int yPosition;
    private Rectangle point;
    private Rectangle pointX;
        
    /**
    * Clase que ayuda a crear las marcas visuales que indican la altura.
    * @param: scaleLine es el tamaño a la que se van a encontrar las marcas.
    * @param: x es la posicion en el eje de la anchura del centro.
    * @param: y es la posicion en el eje de la altura del centro.
    * @param: timesY es la cantidad de marcas que se van a poner en el eje de altura.
    * @param: timesX es un entero que representa la cantidad de veces que se hara un marcaje de referencia en la altura maxima sobre el eje de la anchura.
    */  
    public Line(double scaleLine, int x, int y, int timesY, int timesX){
        y -= scaleLine;
        for(int i = 0; i < timesY; i++){
            point = new Rectangle();
            point.changeSize(2, 10);
            point.changePosition(x, y);
            y -= scaleLine;
            point.changeColor("white");
            point.makeVisible();
        }
        y += scaleLine;
        x += scaleLine/2;
        for(int i = 0; i < timesX*2; i++){
            pointX = new Rectangle();
            pointX.changeSize(2, 10);
            pointX.changePosition(x, y);
            x += scaleLine/2;
            pointX.changeColor("white");
            pointX.makeVisible();
        }
    }
}