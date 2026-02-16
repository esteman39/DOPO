/**
 * Representa la cuadricula visual de la torre.
 * Dibuja los ejes y las marcas de medicion.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 1.0 (14/02/2026)
 */
public class TowerGrid {
    private Rectangle leftAxis;
    private Rectangle rightAxis;
    private Rectangle bottomAxis;
    private Line heightMarks;
    
    private int xOrigin;
    private int yOrigin;
    private double scale;
    private int maxCups;
    
    /**
     * Constructor de TowerGrid
     * @param maxHeight altura maxima en cm
     * @param maxWidth ancho maximo en cm
     */
    public TowerGrid(int maxHeight, int maxWidth) {
        // Calcular escala
        int heightPx = 690;
        this.scale = (double) heightPx / maxHeight;
        int widthPx = (int)(heightPx * ((double)maxWidth / maxHeight));
        
        // Calcular numero maximo de tazas
        this.maxCups = (maxWidth + 1) / 2;
        
        // Crear ejes
        leftAxis = new Rectangle();
        rightAxis = new Rectangle();
        bottomAxis = new Rectangle();
        
        leftAxis.changeSize(heightPx, 2);
        rightAxis.changeSize(heightPx, 2);
        bottomAxis.changeSize(2, widthPx);
        
        leftAxis.changePosition(70, 15);
        rightAxis.changePosition(70 + widthPx, 15);
        bottomAxis.changePosition(70, 15 + heightPx);
        
        leftAxis.changeColor("white");
        rightAxis.changeColor("white");
        bottomAxis.changeColor("white");
        
        this.xOrigin = 70;
        this.yOrigin = 15 + heightPx;
        
        // Crear marcas
        heightMarks = new Line(scale, xOrigin, yOrigin, maxHeight, maxWidth);
    }
    
    /**
     * Hace visible la cuadricula
     */
    public void makeVisible() {
        leftAxis.makeVisible();
        rightAxis.makeVisible();
        bottomAxis.makeVisible();
    }
    
    /**
     * Hace invisible la cuadricula
     */
    public void makeInvisible() {
        leftAxis.makeInvisible();
        rightAxis.makeInvisible();
        bottomAxis.makeInvisible();
    }
    
    public int getXOrigin() {
        return xOrigin;
    }
    
    public int getYOrigin() {
        return yOrigin;
    }
    
    public double getScale() {
        return scale;
    }
    
    public int getMaxCups() {
        return maxCups;
    }
}