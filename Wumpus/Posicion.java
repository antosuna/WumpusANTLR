/**
* Clase que almacena la posicion en el tablero, y 
* simplifica el continuo uso que debe hacerse de esta
*
* @author Antonio Osuna Caballero
* @version 1.0
*/


public class Posicion
{
	/*Variable que almacena la posicion en el eje X(fila) del tablero*/
	private int x;

	/*Variable que almacena la posicion en el eje Y(columna) del tablero*/
	private int y;

	
	
	/*Constructor vacio de la clase, que inicializa las variables*/
	Posicion()
	{
		this.x=-1;
		this.y=-1;
	}

	/*Constructor parametrizado con las coordenadas*/
	Posicion(int fil, int col)
	{
		this.x=fil;
		this.y=col;
	}

	/*Constructor copia*/
	Posicion(Posicion nueva)
	{
		this.x=nueva.x;
		this.y=nueva.y;
	}

	/*Funcion que permite establecer el valor de la coordenada X*/
	public void setX(int fil)
	{
		this.x=fil;
	}

	/*Funcion que permite establecer el valor de la coordenada Y*/
	public void setY(int col)
	{
		this.y=col;	
	}

	/*Funcion que devuelve el vlor de la coordenada X*/
	public int getX(){
		return this.x;
	}

	/*Funcion que devuelve el vlor de la coordenada Y*/
	public int getY(){
		return this.y;
	}

	/*Funcion que establece la posicion a partir de otra pasada por parametro*/
	public void setPos(Posicion pos)
	{
		this.x=pos.getX();
		this.y=pos.getY();
	}


}
