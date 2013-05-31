/**
* Clase que representa al jugador y todos los elementos y
* metodos que se necesitan para interactuar con el escenario
*
* @author Antonio Osuna Caballero
* @version 1.0
*/

public class Jugador
{

	/*Instancia de la clase Posicion que almacena la situacion del jugador en el tablero*/
	private Posicion posicion;

	/*Variable que almacena las vidas que tiene el jugador*/
	private int vidas;

	/*Variable que almacena el numero de flechas de las que dipone el jugador*/
	private int flechas;

	/* Variable booleana que indica si el jugador ha encontrado el tesoro
	 */
	private boolean tesoro=false;


	/*Constructor vacio de la clase jugador que inicializa las variables*/
	Jugador()
	{
		posicion=new Posicion();
		vidas=1;
		flechas=0;
	}

	/*Funcion que incrementa el numero de vidas*/
	public void incVida(){
		this.vidas++;
	}

	/*Funcion que decrementa el numero de vidas*/
	public void decVida(){
		this.vidas--;	
	}

	/*Funcion que incrementa el numero de flechas*/
	public void incFlechas(){
		this.flechas++;
	}

	/*Funcion que decrementa el numero de flechas*/
	public void decFlechas(){
		this.flechas--;
	}

	/*Funcion que devuelve el numero de vidas*/
	public int getVidas(){
		return this.vidas;
	}
	
	/*Funcion que establece un numero de vidas pasadas por parametro
	 */
	public void setVidas(int v){
		this.vidas=v;
	}

	/*Funcion que devuelve el numero de flechas*/
	public int getFlechas(){
		return this.flechas;
	}

	/*Funcion que establece un numero de flechas pasadas como parametro
	 */
	public void setFlechas(int f){
		this.flechas=f;
	}

	/* Funcion que devuelve la posicion establecida para el jugador*/
	public Posicion getPos(){
		return posicion;
	}

	/* Funcion que establece la posicion a partir del parametro pasado*/
	public void setPos(Posicion pos)
	{
		this.posicion.setX(pos.getX());
		this.posicion.setY(pos.getY());
	}

	/* Funcion que establece si el jugador ha encontrado el tesoro
	 */
	public void setTesoro(){
		this.tesoro=true;
	}

	/* Funcion que devuelve si el tesoro ha sido encontrado
     */
	public boolean checkTesoro(){
		return this.tesoro;
	}

	
	
	

	
	



}
