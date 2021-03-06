import java.util.ArrayList;

/**
* Clase que maneja el tablero y el juego.
*
* @author Antonio Osuna Caballero
* @version 1.0
*/



public class Escenario {

	/* Matriz de tipo string que va a representar
	 * el escenario y los elementos contenidos en cada casilla
	 */
	private String[][] escenario;

	/* Matriz de tipo booleano que representa las casillas visitadas para
	 * poder visualizar solo los lugares visitados y ocultar los que no
	 */
	private boolean [][] casillasVisitadas;

	/* Variable que almacena el numero de filas del escenario */
	private int nFil;

	/* Variable que almacena el numero de columnas del escenario */
	private int nCol;

	/* Lista que almacena las posiciones y a la vez nos permite saber
	 * el numero de flechas que hay en la partida
	 */
	private ArrayList<Posicion> posFlechas;

	/* Lista que almacena las posiciones y a la vez nos permite saber
	 * el numero de ambrosias que hay en la partida
	 */
	private ArrayList<Posicion> posAmbrosias;

	/* Lista que almacena las posiciones y a la vez nos permite saber
	 * el numero de minas que hay en la partida
	 */
	private ArrayList<Posicion> posMinas;

	/* Lista que almacena las posiciones y a la vez nos permite saber
	 * el numero de pozos que hay en la partida
	 */
	private ArrayList<Posicion> posPozos;

	/* Instancia de la clase Posicion que nos permite saber donde esta
	 * colocada la casilla de inicio de la partida
	 */
	private Posicion casillaIni;

	/* Instancia de la clase Posicion que nos permite saber donde esta
	 * colocada la casilla de salida de la partida
	 */
	private Posicion casillaFin;

	/* Instancia de la clase Posicion que nos permite saber donde esta
	 * situado el mounstro Wumpus
	 */
	private Posicion casillaWumpus;

	/* Instancia de la clase Posicion que nos permite saber donde esta
	 * colocado el tesoro dentro del tablero
	 */
	private Posicion casillaTesoro;
	
	/* Variable booleana que nos permite saber que el juego ha terminado
	 */
	private boolean finJuego;

	/* Variable booleana que nos permite saber si el wumpus esta o no vivo
	 */
	private boolean vidaWumpus;

	/* Instnacia de la clase jugador que almacena informacion del aventurero
	 * en la partida actual
	 */
	private Jugador jugador;



	/* Contructor vacio de la clase Escenario que inicializa las variables
	 */
	Escenario()
	{	
		nFil=0;
		nCol=0;
		
		this.casillaIni=new Posicion();
		this.casillaFin=new Posicion();
		this.casillaWumpus=new Posicion();
		this.casillaTesoro=new Posicion();
		this.posFlechas=new ArrayList<Posicion>();
		this.posAmbrosias=new ArrayList<Posicion>();
		this.posMinas=new ArrayList<Posicion>();
		this.posPozos=new ArrayList<Posicion>();

		this.inicializaEscenario();
		this.jugador=new Jugador();

		this.vidaWumpus=false;
		this.finJuego=false;
		
	}

	/* Funcion que devuelve el numero de filas del tablero
	 */
	public int getFilas(){
		return this.nFil;
	}

	/* Funcion que devuelve el numero de columnas del tablero
	 */
	public int getColumnas(){
		return this.nCol;
	}

	/* Funcion que asigna un nuevo tamaño al escenario
	 */
	public void setTam(int fil, int col){
		this.nFil=fil;
		this.nCol=col;
		inicializaEscenario();
	}

	

	/* Funcion que inicializa la matriz del tablero o escenario y la lista
	 * de casillas visitadas
	 */
	public void inicializaEscenario()
	{
		escenario = new String [this.nFil][this.nCol];
		casillasVisitadas = new boolean [this.nFil][nCol];
		
		for(int x=0;x<this.nFil;x++) {
			for(int y=0;y<this.nCol;y++){
				this.escenario[x][y]=new String("vacia");
				
				casillasVisitadas[x][y]=false;
			}
		}

	}

	/* Funcion que devuelve el contenido de una casilla del tablero
	 */
	public String leerCasilla(Posicion pos)
	{
		return this.escenario[pos.getX()][pos.getY()];
	}

	/* Funcion que almacena un elemento en una casilla del tablero
	 */
	public boolean escribeCasilla(Posicion pos, String objeto)
	{
		boolean hecho=false;
		if(leerCasilla(pos).equals("vacia")){
			this.escenario[pos.getX()][pos.getY()]=new String(objeto);
			hecho=true;
		}
		return hecho;
	}

	/* Funcion que elimina un objeto de una casilla
	 */
	public boolean eliminarObjeto(Posicion pos, String objeto)
	{
		boolean hecho=false;
		
		if(leerCasilla(pos).equals(objeto)){
			this.escenario[pos.getX()][pos.getY()]=new String("vacia");
			hecho=true;
		}
		return hecho;
	}

	/* Funcion que devuelve si una casilla ha sido visitada con true
	 * y false en caso contrario
	 */
	public boolean estaVisitada(Posicion pos)
	{
		return casillasVisitadas[pos.getX()][pos.getY()];
	}

	/* Funcion que edita una casilla visitadas
	 */
	public void editaVisitada(Posicion pos, boolean visitada)
	{
		this.casillasVisitadas[pos.getX()][pos.getY()]=visitada;
	}

	/* Funcion que devuelve la posicion de la casilla de inicio
	 */
	public Posicion getPosInicio(){
		return this.casillaIni;
	}

	/* Funcion que devuelve la posicion de la casilla de fin
	 */
	public Posicion getPosFin(){
		return this.casillaFin;
	}

	/* Funcion que devuelve la posicion del tesoro
	 */
	public Posicion getPosTesoro(){
		return this.casillaTesoro;
	}
	
	/* Funcion que devuelve la posicion del wumpus
	 */
	public Posicion getPosWumpus(){
		return this.casillaWumpus;
	}

	/* Funcion que coloca la casilla de inicio en el escenario de juego
	 */
	public boolean setPosInicio(Posicion pos)
	{
		boolean hecho=false;

		if(getPosInicio().getX()!=-1 && getPosInicio().getY()!=-1)
			eliminarObjeto(getPosInicio(),"inicio");
	
		if(posCorrecta(pos))
		{
			if(this.escenario[pos.getX()][pos.getY()].equals("vacia"))
			{
				this.escenario[pos.getX()][pos.getY()]=new String("inicio");
				this.casillaIni=new Posicion(pos);
				hecho=true;
			}else
			{
				System.out.println("La posicion esta ocupada por: "+leerCasilla(pos));			
			}
		}else{
			System.out.println("La posicion excede los limites del escenario");
		}
		return hecho;
	}

	/* Funcion que coloca el tesoro en una casilla
	 */
	public boolean setPosTesoro(Posicion pos)
	{
		boolean hecho=false;

		if(getPosTesoro().getX()!=-1 && getPosTesoro().getY()!=-1)
			eliminarObjeto(getPosTesoro(),"tesoro");
	
		if(posCorrecta(pos))
		{
			if(leerCasilla(pos).equals("vacia"))
			{
				this.escenario[pos.getX()][pos.getY()]=new String("tesoro");
				this.casillaTesoro=new Posicion(pos);
				hecho=true;
			}else
			{
				System.out.println("La posicion esta ocupada por: "+leerCasilla(pos));	
			}
		}else{
			System.out.println("La posicion excede los limites del escenario");
		}
		return hecho;
	}

	/* Funcion que coloca la casilla de fin en el escenario
	 */
	public boolean setPosFin(Posicion pos)
	{
		boolean hecho=false;

		if(getPosFin().getX()!=-1 && getPosFin().getY()!=-1)
			eliminarObjeto(getPosFin(),"salida");
	
		if(posCorrecta(pos))
		{
			if(leerCasilla(pos).equals("vacia"))
			{
				this.escenario[pos.getX()][pos.getY()]=new String("salida");
				this.casillaFin=new Posicion(pos);
				hecho=true;
			}else
			{
				System.out.println("La posicion esta ocupada por: "+leerCasilla(pos));		
			}
		}else{
			System.out.println("La posicion excede los limites del escenario");
		}
		return hecho;
	}

	/* Funcion que coloca al mounstruo wumpus y su olor
	 */
	public boolean setPosWumpus(Posicion pos)
	{
		boolean hecho=false;

		if(getPosWumpus().getX()!=-1 && getPosWumpus().getY()!=-1)
			eliminarWumpus();
		
		if(posCorrecta(pos))
		{
			if(leerCasilla(pos).equals("vacia"))
			{
				this.escenario[pos.getX()][pos.getY()]=new String("wumpus");
				getPosWumpus().setX(pos.getX());
				getPosWumpus().setY(pos.getY());
				this.vidaWumpus=true;

				Posicion hedor=new Posicion(pos.getX()-1,pos.getY());
				if(posCorrecta(hedor))
					this.escenario[pos.getX()-1][pos.getY()]=new String("hedor");
				hedor=new Posicion(pos.getX()+1,pos.getY());
				if(posCorrecta(hedor))
					this.escenario[pos.getX()+1][pos.getY()]=new String("hedor");
				hedor=new Posicion(pos.getX(),pos.getY()-1);
				if(posCorrecta(hedor))
					this.escenario[pos.getX()][pos.getY()-1]=new String("hedor");
				hedor=new Posicion(pos.getX(),pos.getY()+1);
				if(posCorrecta(hedor))
					this.escenario[pos.getX()][pos.getY()+1]=new String("hedor");
				hecho=true;
								
			}else{
				System.out.println("La posicion esta ocupada por: "+leerCasilla(pos));
			}
	
		}else{
			System.out.println("Esta posicion excede los limites del escenario");
		}
		return hecho;
	}

	/* Funcion que elimina al wumpus del tablero y su hedor
	 */
	public boolean eliminarWumpus()
	{
		boolean hecho=false;

		this.escenario[getPosWumpus().getX()][getPosWumpus().getY()]=new String("vacia");
		vidaWumpus=false;
		Posicion pos=new Posicion(getPosWumpus());

		Posicion hedor=new Posicion(pos.getX()-1,pos.getY());
				if(posCorrecta(hedor))
					this.escenario[pos.getX()-1][pos.getY()]=new String("vacia");
				hedor=new Posicion(pos.getX()+1,pos.getY());
				if(posCorrecta(hedor))
					this.escenario[pos.getX()+1][pos.getY()]=new String("vacia");
				hedor=new Posicion(pos.getX(),pos.getY()-1);
				if(posCorrecta(hedor))
					this.escenario[pos.getX()][pos.getY()-1]=new String("vacia");
				hedor=new Posicion(pos.getX(),pos.getY()+1);
				if(posCorrecta(hedor))
					this.escenario[pos.getX()][pos.getY()+1]=new String("vacia");
		hecho=true;

		return hecho;

	}

	/* Funcion que permite al Wumpus moverse por el escenario
	 */
	public void movimientoWumpus()
	{
		int dir=(int)(Math.random()*(4-1+1)+1);
		//TODO
	}

	/* Funcion que devuelve la lista de posiciones de las flechas
	 */
	public ArrayList<Posicion> getPosFlechas(){
		return this.posFlechas;
	}

	/* Funcion que devuelve la lista de posiciones de las minas
	 */
	public ArrayList<Posicion> getPosMinas(){
		return this.posMinas;
	}

	/* Funcion que devuelve la lista de posiciones de las ambrosias
	 */
	public ArrayList<Posicion> getPosAmbrosias(){
		return this.posAmbrosias;
	}

	/* Funcion que devuelve la lista de posiciones de los pozos
	 */
	public ArrayList<Posicion> getPosPozos(){
		return this.posPozos;
	}

	/* Funcion que añade un pozo a la lista en la posicion que se
	 * pasa como parametro, devuelve el numero de pozos
	 */
	public int setPosPozo(Posicion pos)
	{
		int nPozos=-1;
	
		if(posCorrecta(pos))
		{
			if(leerCasilla(pos).equals("vacia"))
			{
				escribeCasilla(pos,"pozo");
				Posicion pozo=new Posicion(pos);
				getPosPozos().add(pozo);

				nPozos=getPosPozos().size()-1;
	
				Posicion viento=new Posicion(pos.getX()-1,pos.getY());
				if(posCorrecta(viento))
					this.escenario[pos.getX()-1][pos.getY()]=new String("viento");
				viento=new Posicion(pos.getX()+1,pos.getY());
				if(posCorrecta(viento))
					this.escenario[pos.getX()+1][pos.getY()]=new String("viento");
				viento=new Posicion(pos.getX(),pos.getY()-1);
				if(posCorrecta(viento))
					this.escenario[pos.getX()][pos.getY()-1]=new String("viento");
				viento=new Posicion(pos.getX(),pos.getY()+1);
				if(posCorrecta(viento))
					this.escenario[pos.getX()][pos.getY()+1]=new String("viento");
			}else{
				System.out.println("La casilla esta ocupada con "+leerCasilla(pos));
			}
		}else{
			System.out.println("Esta posicion excede los limites del escenario");
		}
		return nPozos;
	}

	/* Funcion que coloca una flecha en la posicion pasada como parametro
	 * y devuelve el lugar que ocupa en la lista de flechas
	 */
	public int setPosFlecha(Posicion pos)
	{
		int nFlecha=-1;

		if(posCorrecta(pos))
		{
			if(leerCasilla(pos).equals("vacia"))
			{
				escribeCasilla(pos,"flecha");
				getPosFlechas().add(pos);

				nFlecha=getPosFlechas().size()-1;
			}else
				System.out.println("La casilla esta ocupada con "+leerCasilla(pos));

		}else
			System.out.println("Esta posicion excede los limites del escenario");
	
		return nFlecha;
	}

	/* Funcion que coloca una mina en la posicion pasada como parametro
	 * y devuelve el lugar que ocupala mina añadida en la lista de minas
	 */
	public int setPosMina(Posicion pos)
	{
		int nMina=-1;

		if(posCorrecta(pos))
		{
			if(leerCasilla(pos).equals("vacia"))
			{
				escribeCasilla(pos,"mina");
				getPosMinas().add(pos);

				nMina=getPosMinas().size()-1;
			}else
				System.out.println("La casilla esta ocupada con "+leerCasilla(pos));

		}else
			System.out.println("Esta posicion excede los limites del escenario");
	
		return nMina;
	}

	/* Funcion que coloca una flecha en la posicion pasada como parametro
	 * y devuelve el lugar que ocupa en la lista de ambrosias
	 */
	public int setPosAmbrosia(Posicion pos)
	{
		int nAmbrosia=-1;

		if(posCorrecta(pos))
		{
			if(leerCasilla(pos).equals("vacia"))
			{
				escribeCasilla(pos,"ambrosia");
				getPosAmbrosias().add(pos);

				nAmbrosia=getPosAmbrosias().size()-1;
			}else
				System.out.println("La casilla esta ocupada con "+leerCasilla(pos));

		}else
			System.out.println("Esta posicion excede los limites del escenario");
	
		return nAmbrosia;
	}


	/* Funcion que asegura que una posicion pasada como parametro
	 * este dentro del rango del tablero
	 */
	public boolean posCorrecta(Posicion pos)
	{
		boolean resultado=false;

		if(pos.getX()<this.nFil && pos.getX()>=0 && pos.getY()<this.nCol && pos.getY()>=0)
			resultado=true;

		return resultado;

	}

	/* Funcion que devuelve la instancia del jugador en el escenario
	 */
	public Jugador getJugador(){
		return this.jugador;
	}

	/* Funcion que devuelve la posicion que tiene el jugador
	 */
	public Posicion getPosJugador(){
		return getJugador().getPos();
	}

	/* Funcion que coloca el jugador en la posicion pasada como parametro
	 */
	public void setPosJugador(Posicion pos){
		getJugador().setPos(pos);
	}

	/* Funcion que establece un numero de vidas para el jugador
	 */
	public void setVidasJugador(int n)
	{
		getJugador().setVidas(n);
	}

	/* Funcion que devuelve el numero de flechas del objeto jugador
	 */
	public int getFlechasJugador(){
		return getJugador().getFlechas();
	}

	/* Funcion que incrementa el numero de flechas que el jugador tiene
	 */
	public void incFlechasJugador(){
		getJugador().incFlechas();
	}

	/* Funcion que decrementa el numero de flechas que el jugador tiene
	 */
	public void decFlechasJugador(){
		getJugador().decFlechas();
	}

	/* Funcion que devuelve el numero de vidas del objeto jugador
	 */
	public int getVidasJugador(){
		return getJugador().getVidas();
	}

	/* Funcion que incrementa el numero de vidas que el jugador tiene
	 */
	public void incVidasJugador(){
		getJugador().incVida();
	}
	
	/* Funcion que mueve al jugador dependiendo de la direccion pasada
	 * como parametro en forma de cadena
	 */
	public void moverJugador(String mov)
	{
		Posicion pos=new Posicion();

		if(mov.equals("izquierda"))
			pos=new Posicion(getPosJugador().getX(),getPosJugador().getY()-1);
		if(mov.equals("derecha"))
			pos=new Posicion(getPosJugador().getX(),getPosJugador().getY()+1);
		if(mov.equals("arriba"))
			pos=new Posicion(getPosJugador().getX()-1,getPosJugador().getY());
		if(mov.equals("abajo"))
			pos=new Posicion(getPosJugador().getX()+1,getPosJugador().getY());

		if(mueveJugador(pos)){
			editaVisitada(pos,true);
		}
	}

	/* Funcion que decrementa el numero de vidas que el jugador tiene
	 */
	public void decVidasJugador(){
		getJugador().decVida();
	}

	/* Funcion que mueve al jugador una casilla a la derecha
	 *
	public void movJugadorD(){
		Posicion pos = new Posicion(getPosJugador().getX(),getPosJugador().getY()+1);
	}

	/* Funcion que mueve al jugador una casilla a la izquierda
	 *
	public void movJugadorI(){
		Posicion pos = new Posicion(getPosJugador().getX(),getPosJugador().getY()-1);
	}

	/* Funcion que mueve al jugador una casilla hacia arriba
	 *
	public void movJgadorA(){
		Posicion pos = new Posicion(getPosJugador().getX()+1,getPosJugador().getY());
	}

	/* Funcion que mueve al jugador una casilla hacia abajo
	 *
	public void movJugadorB(){
		Posicion pos = new Posicion(getPosJugador().getX()-1,getPosJugador().getY());
	}*/

	/* Funcion que coloca al jugador en su nueva casilla
	 */
	public boolean mueveJugador(Posicion pos)
	{
		boolean hecho=false;

		if(compruebaMov(pos))	
		{
			//getJugador().setPos(pos);
			hecho=true;
		}

		return hecho;
	}

	/* Funcion que verifica la accion tras el movimiento del jugador
	 * devuelve true si puede realizarse y false en caso contrario
	 * notificando por consola el resultado del movimiento
	 */
	private boolean compruebaMov(Posicion pos)
	{
		boolean hecho=true;

		if(posCorrecta(pos))
		{
			String elemento=leerCasilla(pos);
			
			if(elemento.equals("wumpus"))
			{
				System.out.println("¡El Wumpus te ha quitado una vida!");
				decVidasJugador();
				if(getVidasJugador()<=0){
					System.out.println("¡El Wumpus te ha quitado una vida!, tienes 0, FIN DEL JUEGO");
					terminarJuego();
					hecho=false;
				}else{
					pos.setPos(getPosInicio());
				}
					
			}else if(elemento.equals("pozo"))
			{
				System.out.println("¡Has caido en un pozo!");
				decVidasJugador();
				if(getVidasJugador()<=0){
					System.out.println("¡Has caido en un pozo! tienes 0 vidas, FIN DEL JUEGO");
					terminarJuego();
					hecho=false;
				}else{
					pos.setPos(getPosInicio());
				}

			}else if(elemento.equals("mina"))
			{
				System.out.println("¡Has pisado una mina!");
				decVidasJugador();
				if(getVidasJugador()<=0){
					System.out.println("¡Has pisado una mina! tienes 0 vidas, FIN DEL JUEGO");
					terminarJuego();
					hecho=false;
				}else{
					pos.setPos(getPosInicio());
				}

			}else if(elemento.equals("flecha"))
			{
				System.out.println("¡Has encontrado una flecha!, tienes "+getFlechasJugador()+". ¿Deseas cogerla?");
				//incFlechasJugador();
				//eliminarObjeto(pos,"flecha");				
			}else if(elemento.equals("ambrosia"))
			{
				System.out.println("¡Has encontrado una ambrosia!, tienes "+getVidasJugador()+". ¿Deseas tomarla?");
				//getPosAmbrosias().remove(pos);
				//incVidasJugador();
				//eliminarObjeto(pos,"ambrosia");
			}else if(elemento.equals("viento"))
			{
				System.out.println("En esta zona hay bastante viento...");

			}else if(elemento.equals("hedor"))
			{
				System.out.println("En esta zona huele mal");
	
			}else if(elemento.equals("tesoro"))	
			{
				System.out.println("¡ENHORABUENA! Has encontrado el tesoro, dirigete a la casilla de salida");
				getJugador().setTesoro();
				eliminarObjeto(pos,"tesoro");

			}else if(elemento.equals("salida"))
			{
				if(getJugador().checkTesoro())
				{
					System.out.println("¡HAS TERMINADO LA PARTIDA VICTORIOSO");
					terminarJuego();	
				}else
					System.out.println("Lo siento, debe encontrar el tesoro para terminar el juego");
			}

			getJugador().setPos(pos);

		}else
		{
			hecho=false;
			System.out.println("\n¡FALLO! en la ultima instruccion: La casilla excede el limite del tablero");
		}
		return hecho;
	}

	/* Funcion establece el indicador de fin de juego
	 */
	public void terminarJuego(){
		this.finJuego=true;
	}

	/* Funcion que devuelve si el juego esta finalizado
	 */
	public boolean estaTerminadoJuego(){
		return this.finJuego;
	}

	/* Funcion que permite disparar una flecha al jugador en las distintas
	 * direcciones, pudiendo o no matar al wumpus, lo que se ve reflejado en
	 * la variable booleana de retorno
	 */
	public boolean disparar(String direccion)
	{
		boolean hecho=false;
		boolean acierto=false;

		if(getJugador().getFlechas()>0)
		{
			hecho=true;
			decFlechasJugador();

			if(direccion.equals("arriba"))
			{
				if(getPosJugador().getY()==getPosWumpus().getY()){
					if(getPosJugador().getX()<getPosWumpus().getX()){
						acierto=true;
					}
				}
			}else if(direccion.equals("derecha"))
			{
				if(getPosJugador().getX()==getPosWumpus().getX()){
					if(getPosJugador().getY()<getPosWumpus().getY()){
						acierto=true;
					}
				}
			}else if(direccion.equals("izquierda"))
			{
				if(getPosJugador().getX()==getPosWumpus().getX()){
					if(getPosJugador().getY()>getPosWumpus().getY()){
						acierto=true;
					}
				}
			}else if(direccion.equals("abajo"))
			{
				if(getPosJugador().getY()==getPosWumpus().getY()){
					if(getPosJugador().getX()>getPosWumpus().getX()){
						acierto=true;
					}
				}
			}

			if(acierto)
			{
				this.vidaWumpus=false;
				eliminarWumpus();
				System.out.println("\nInfo: Has alcanzado al Wumpus, puedes caminar tranquilo");
			}else{
				System.out.println("Mala suerte, no has alcanzado el objetivo");
			}
		}else
			System.out.println("¡ADVERTENCIA! No tienes flechas");

		return hecho;
	}
	
	/* Funcion que imprime el escenario y todos sus componentes en una cadena
	 */
	public String imprimeEscenario()
	{
		String aux=new String();
		Posicion pos=new Posicion();

		if(getFilas()!=0 && getColumnas()!=0)
		{
			//for(int i=getFilas()-1;i>=0;i--)
			for(int i=0;i<getFilas();i++)
			{
				aux +="\n";
				for(int j=0;j<getColumnas();j++)
				{
					aux +="\t";
					if(getPosJugador().getX()==i && getPosJugador().getY()==j){
						aux += "Jug";
					}else
					{
						String objeto=new String("");
						pos=new Posicion(i,j);

						if(leerCasilla(pos).equals("tesoro"))
							objeto=" T ";
						else if(leerCasilla(pos).equals("wumpus"))
							objeto=" W ";
						else if(leerCasilla(pos).equals("pozo"))
							objeto=" o ";
						else if(leerCasilla(pos).equals("salida"))
							objeto="Fin";
						else if(leerCasilla(pos).equals("inicio"))
							objeto="Ini";
						else if(leerCasilla(pos).equals("hedor"))
							objeto=" ~ ";
						else if(leerCasilla(pos).equals("flecha"))
							objeto="-->";
						else if(leerCasilla(pos).equals("mina"))
							objeto=" * ";
						else if(leerCasilla(pos).equals("ambrosia"))
							objeto=" ª ";
						else if(leerCasilla(pos).equals("viento"))
							objeto=" = ";
						else 
							objeto=" - ";

						aux +=objeto;
					}
				}
			
			}
		}else
			aux += "No se ha definido escenario";	

		return aux;
	}

	/* Funcion que devuelve si el mapa esta completo al iniciar un nuevo
	 */
	public boolean checkInicioJuego()
	{
		boolean hecho=true;

		if(getFilas()==0 || getColumnas()==0)
		{
			System.out.println("FATAL: el mapa no esta definido");
		}else{
			if(getPosInicio().getX()==-1 && getPosInicio().getY()==-1)
			{
				hecho=false;
				System.out.println("Error. Casilla de inicio no definida");
			}
			if(getPosFin().getX()==-1 && getPosFin().getY()==-1)
			{
				hecho=false;
				System.out.println("Error. Casilla de salida no definida");
			}
			if(getPosWumpus().getX()==-1 && getPosWumpus().getY()==-1)
			{
				hecho=false;
				System.out.println("Error. El Wumpus no esta en el mapa");
			}
			if(getPosTesoro().getX()==-1 && getPosTesoro().getY()==-1)
			{
				hecho=false;
				System.out.println("Error. Sin tesoro no puede comenzar");
			}

			if(hecho)
			{
				setPosJugador(getPosInicio());
				editaVisitada(getPosInicio(),true);
			}

		}
		return hecho;
	}
	
	/* Funcion que imprime el escenario a lo largo del juego
	 */
	public void mostrarEstadoJuego()
	{
		String aux=new String();
		Posicion pos=new Posicion();

		if(getFilas()!=0 && getColumnas()!=0)
		{
			//for(int i=getFilas()-1;i>=0;i--)
			for(int i=0;i<getFilas();i++)
			{
				aux +="\n";
				for(int j=0;j<getColumnas();j++)
				{
					aux +="\t";
					if(getPosJugador().getX()==i && getPosJugador().getY()==j){
						aux += "Jug";
					}else
					{
						String objeto=new String("");
						pos=new Posicion(i,j);

						if(estaVisitada(new Posicion(i,j)))
						{
						if(leerCasilla(pos).equals("tesoro"))
							objeto=" T ";
						else if(leerCasilla(pos).equals("wumpus"))
							objeto=" W ";
						else if(leerCasilla(pos).equals("pozo"))
							objeto=" o ";
						else if(leerCasilla(pos).equals("salida"))
							objeto="Fin";
						else if(leerCasilla(pos).equals("inicio"))
							objeto="Ini";
						else if(leerCasilla(pos).equals("hedor"))
							objeto=" ~ ";
						else if(leerCasilla(pos).equals("flecha"))
							objeto="-->";
						else if(leerCasilla(pos).equals("mina"))
							objeto=" * ";
						else if(leerCasilla(pos).equals("ambrosia"))
							objeto=" ª ";
						else if(leerCasilla(pos).equals("viento"))
							objeto=" = ";
						else 
							objeto=" - ";
						}else {
							objeto=" ? ";
						}
						aux +=objeto;
					}
				}
			
			}

			aux +="\n\nFlechas: "+getFlechasJugador();
			aux +="\nVidas: "+getVidasJugador();
			aux +="\nWumpus: ";
			if(vidaWumpus)
				aux +="Vivo";
			else
				aux +="Muerto";

			aux +="\nTesoro: ";
			if(getJugador().checkTesoro())
				aux +="Encontrado";
			else
				aux +="Perdido";
			
		}else
			aux += "No se ha definido escenario";	

		System.out.println(aux);

	}


}
