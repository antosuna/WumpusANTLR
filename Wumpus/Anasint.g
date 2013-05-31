/*
	Analizador léxico para intéprete de pseudocódigo "IPE"

	Etapa 08/05/2013: Añadiendo la tabla de simbolos y vinculando las reglas a codigo en Java

	Etapa 15/05/2013: Terminando los atributos de las reglas

	Etapa 16/05/2013: Corrigiendo errores semanticos

	Etapa 17/05/2013: Unificando expresiones numericas y alfanumericas
	
	Etapa 17/05/2013: Modificando los atributos segun el cambio anterior

	Etapa 20/05/2013: Corrigiendo condicionales anidados

	Etapa 20/05/2013: Añadiendo atributos heredados para controlar la ejecucion

	Etapa 20/05/2013: Añadiendo reglas para numeros negativos

	Etapa 21/05/2013: Incluyendo excepciones para el control de errores
	
	Etapa 21/05/2013: Añadidos macros de posicion y borrado de pantalla

	Etapa 21/05/2013: Corregido bucle para; añadidos saltos de linea a cadenas

	Etapa 22/05/2013: Añadiendo reglas de juego

	Etapa 22/05/2013: Codificando esqueleto de ejecucion de reglas

	Etapa 25/05/2013: Completando funcionalidades del juego en java
	
	Etapa 27/05/2013: Terminando la codificacion

	Etapa 28/05/2013: Depurando errores

	Etapa 31/05/2013: Terminado

	Autor: Antonio Osuna Caballero

*/

//////////////////////////////////
// Analizador sintáctico		//
//////////////////////////////////

header{
	// Para leer del teclado
	import java.util.Scanner;
	import java.awt.*;
	import java.util.Vector;
	import java.util.List;
	import java.util.ArrayList;
}

class Anasint extends Parser;

options 
{
	// Se exporta el vocabulario de componentes lexicos 
	// utilizado en el analisis sintactico
	exportVocab = Anasint;
}


/////////////////////////////////
// Codigo auxiliar			   //
/////////////////////////////////
{

	/* Declaracion de la tabla de simbolos */
	private TablaSimbolos tablaSimbolos = new TablaSimbolos();

	/*Variable de control global para bucle for*/
	boolean primeraVuelta=true;

	/*Variable cadena para controlar el modo de ejecucion*/
	String modo=new String("Indeterminado");

	/* Variable que representa el escenario de juego*/
	Escenario tablero=new Escenario();

	/* Metodo para acceder a la tabla de sÃ­mbolos desde fuera de la clase */
	public TablaSimbolos getTablaSimbolos()
	{
		return tablaSimbolos;
	}

	/* Metodo para insertar un identificador en la tabla de simbolos con un valor */
	private void insertarIdentificador(String nombre, String tipo, String valorCadena)
		{
			
			// Busca el identificador en la tabla de simbolos
			int indice = tablaSimbolos.existeSimbolo(nombre);

			// Si encuentra el identificador, le modifica su valor
			if (indice >= 0)
			{
				tablaSimbolos.getSimbolo(indice).setValor(valorCadena);
			}
			// Si no lo encuentra, lo inserta en la tabla de sÃ­mbolos
			else
			{
				// Se crea la variable
				Variable v = new Variable (nombre,tipo,valorCadena);

				// Se inserta la variable en la tabla de sÃ­mbolos
				tablaSimbolos.insertarSimbolo(v);
			}
		}

	// Funcion para mostrar un mensaje de error
	private	void mostrarExcepcion(RecognitionException re)
	{
		System.out.println("Error en la li­nea " + re.getLine() + " --> " + re.getMessage());
		//reportError(re);
		try {
				//Consume the token problem
				consume(); 
    			consumeUntil(PUNTO_COMA);
			} 
		catch (Exception e) 
			{
			}
	}

}



//////////////////////////////////////////
//Zona de reglas gramaticales			//
//////////////////////////////////////////

programa : (sentencia[true] PUNTO_COMA)* (configuracion (sentencia[true] PUNTO_COMA)*)? (jugadas (sentencia[true] PUNTO_COMA)*)?
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		 }

configuracion:	CONFIG 
					{modo=new String("configurando");}
	                (sentencia[true] PUNTO_COMA)*
				FIN_CONFIG
		;

jugadas:	JUEGO
				{if(tablero.checkInicioJuego()) modo=new String("jugando");}
				(sentencia[true] PUNTO_COMA)*
			FIN_JUEGO
		;

sentencia 

	[boolean ejecutar]

	{Variable v1, v2;}
	
	: 
	
	s1:SET_TABLERO PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{	
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					tablero.setTam(f,c);
					System.out.println("Se ha establecido el tablero con: "+f+"-filas y: "+c+"-columnas");
				}
				else
				{
					System.err.println("Linea: "+s1.getLine()+" Col: "+s1.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s1.getLine()+" Col: "+s1.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}
	
	|s2:SET_INICIO PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					Posicion posInicio=new Posicion(f-1,c-1);
					if(!tablero.setPosInicio(posInicio))
						System.out.printf(" - Instrucción en la Linea: "+s2.getLine());
					else
						System.out.println("Se ha establecido la casilla de inicio en fila :"+f+" columna: "+c);
				}
				else
				{
					System.out.println("FALLA EN LA CONDICION SET INICIO");
					System.err.println("Linea: "+s2.getLine()+" Col: "+s2.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s2.getLine()+" Col: "+s2.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s3:SET_FIN PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{	
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					Posicion posFin=new Posicion(f-1,c-1);
					if(!tablero.setPosFin(posFin))
						System.out.printf(" - Instrucción en la Linea: "+s3.getLine());
					else
						System.out.println("Se ha establecido la casilla de salida en fila: "+f+" columna: "+c);
				}
				else
				{
					System.err.println("Linea: "+s3.getLine()+" Col: "+s3.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s3.getLine()+" Col: "+s3.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s4:SET_TESORO PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{	
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					Posicion posTesoro=new Posicion(f-1,c-1);
					if(!tablero.setPosTesoro(posTesoro))
						System.out.printf(" - Instrucción en la Linea: "+s4.getLine());
					else
						System.out.println("Se ha establecido la casilla de tesoro en fila: "+f+" columna: "+c);
				}
				else
				{
					System.err.println("Linea: "+s4.getLine()+" Col: "+s4.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s4.getLine()+" Col: "+s4.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s5:SET_WUMPUS PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					Posicion posWumpus=new Posicion(f-1,c-1);
					if(!tablero.setPosWumpus(posWumpus))
						System.out.printf(" - Instrucción en la Linea: "+s5.getLine());
					else
						System.out.println("Se ha establecido la casilla del wumpus en fila: "+f+" columna: "+c);
				}else
				{
					System.err.println("Linea: "+s5.getLine()+" Col: "+s5.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s5.getLine()+" Col: "+s5.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s6:SET_POZO PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{	
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					Posicion posPozo=new Posicion(f-1,c-1);
					int nPozos=tablero.setPosPozo(posPozo);
					if(nPozos!=-1)
						System.out.println("El tesoro num."+(nPozos+1)+" - Colocado en la fila: "+f+" y columna: "+c);

					else
						System.out.printf(" - Instrucción en la Linea: "+s6.getLine());
				}
				else
				{
					System.err.println("Linea: "+s6.getLine()+" Col: "+s6.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s6.getLine()+" Col: "+s6.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s7:SET_FLECHA PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{	
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					Posicion posFlecha=new Posicion(f-1,c-1);
					int nFlechas=tablero.setPosFlecha(posFlecha);
					if(nFlechas!=-1)
						System.out.println("La flecha num."+(nFlechas+1)+" - Colocada en la fila: "+f+" y columna: "+c);
					else
						System.out.printf(" - Instrucción en la Linea: "+s7.getLine());
				}
				else
				{
					System.err.println("Linea: "+s7.getLine()+" Col: "+s7.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s7.getLine()+" Col: "+s7.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s8:SET_AMBROSIA PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{	
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					Posicion posAmb=new Posicion(f-1,c-1);
					int nAmb=tablero.setPosAmbrosia(posAmb);
					if(nAmb!=-1)
						System.out.println("La ambrosia num."+(nAmb+1)+" - Colocada en la fila: "+f+" y columna: "+c);
					else
						System.out.printf(" - Instrucción en la Linea: "+s8.getLine());
				}
				else
				{
					System.err.println("Linea: "+s8.getLine()+" Col: "+s8.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s8.getLine()+" Col: "+s8.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s9:SET_MINA PARENTESIS_IZ v1=expresion[ejecutar] COMA v2=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				if(v1.esNumero() && v2.esNumero())
				{	
					int f=(int)Float.parseFloat(v1.getValor());
					int c=(int)Float.parseFloat(v2.getValor());
					Posicion posMina=new Posicion(f-1,c-1);
					int nMinas=tablero.setPosMina(posMina);
					if(nMinas!=-1)
						System.out.println("La mina num."+(nMinas+1)+" - Colocada en la fila: "+f+" y columna: "+c);
					else
						System.out.printf(" - Instrucción en la Linea: "+s9.getLine());
				}
				else
				{
					System.out.println("FALLA EN LA CONDICION");
					System.err.println("Linea: "+s9.getLine()+" Col: "+s9.getColumn()+" -> Los parametros deben ser numericos");
				}
			}
			else
			{
				System.err.println("Linea: "+s9.getLine()+" Col: "+s9.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s10:MOSTRAR_TABLERO PARENTESIS_IZ PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				String aux=tablero.imprimeEscenario();
				System.out.println(aux);
			}
			else
			{
				System.err.println("Linea: "+s10.getLine()+" Col: "+s10.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	//|s11:MOVER_JUGADOR PARENTESIS_IZ (DERECHA|IZQUIERDA|ARRIBA|ABAJO) PARENTESIS_DE

	|s11:MOVER_JUGADOR PARENTESIS_IZ v1=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("jugando"))
			{
				List<String> dir = new ArrayList<String>();
				dir.add("izquierda");
				dir.add("derecha");
				dir.add("arriba");
				dir.add("abajo");
				String aux=v1.getValor().toLowerCase();
				if(v1.esCadena() && (dir.contains(aux)))
				{
					if(!tablero.estaTerminadoJuego())
					{
						tablero.moverJugador(aux);
						tablero.mostrarEstadoJuego();
					}else
						System.out.println("No puede moverse, porque en este momento no esta jugando");
				}
				else
				{
					System.err.println("Linea: "+s11.getLine()+" Col: "+s11.getColumn()+" -> La funcion de mover jugador no acepta el parametro");
				}
			}
			else
			{
				System.err.println("Linea: "+s11.getLine()+" Col: "+s11.getColumn()+" -> Esta funcion pertenece al modo juego");
			}
			}
		}

	|s12:COGER_FLECHA PARENTESIS_IZ PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("jugando"))
			{
				if(tablero.leerCasilla(tablero.getPosJugador()).equals("flecha"))
				{
					tablero.incFlechasJugador();
					if(tablero.eliminarObjeto(tablero.getPosJugador(),"flecha"))
						System.out.println("\nInfo: Perfecto, has consegido una flecha, ahora tienes: "+tablero.getFlechasJugador());
					else
						System.out.println("Ha ocurrido algun error, quitando la flecha del mapa, revise el codigo fuente");
				}else
					System.out.println("Aviso, en esta casilla no hay una flecha");
			}
			else
			{
				System.err.println("Linea: "+s12.getLine()+" Col: "+s12.getColumn()+" -> Esta funcion pertenece al modo juego");
			}
			}
		}

	//|DISPARAR PARENTESIS_IZ (DERECHA|IZQUIERDA|ARRIBA|ABAJO) PARENTESIS_DE

	|s13:DISPARAR PARENTESIS_IZ v1=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("jugando"))
			{
				List<String> dir = new ArrayList<String>();
				dir.add("izquierda");
				dir.add("derecha");
				dir.add("arriba");
				dir.add("abajo");
				String aux=v1.getValor().toLowerCase();
				if(v1.esCadena() && (dir.contains(aux)))
				{
					if(!tablero.estaTerminadoJuego())
					{
						tablero.disparar(aux);
						//escenario.mostrarEstadoJuego();
					}else
						System.out.println("No puede disparar, porque en este momento no esta jugando");
				}
				else
				{
					System.err.println("Linea: "+s13.getLine()+" Col: "+s13.getColumn()+" -> La funcion de disparar flecha no acepta el parametro");
				}
			}
			else
			{
				System.err.println("Linea: "+s13.getLine()+" Col: "+s13.getColumn()+" -> Esta funcion pertenece al modo juego");
			}
			}
		}

	|s14:BEBER PARENTESIS_IZ PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("jugando"))
			{
				if(tablero.leerCasilla(tablero.getPosJugador()).equals("ambrosia"))
				{
					tablero.incVidasJugador();
					tablero.getPosAmbrosias().remove(tablero.getPosJugador());
					if(tablero.eliminarObjeto(tablero.getPosJugador(),"ambrosia"))
						System.out.println("\nInfo: Perfecto, has consegido una vida, ahora tienes: "+tablero.getVidasJugador());
					else
						System.out.println("Ha ocurrido algun error, quitando la ambrosia del mapa, revise el codigo fuente");
				}else
					System.out.println("Aviso, en esta casilla no hay una ambrosia");
			}
			else
			{
				System.err.println("Linea: "+s14.getLine()+" Col: "+s14.getColumn()+" -> Esta funcion pertenece al modo juego");
			}
			}
		}

	|s15:MOSTRAR_FLECHAS PARENTESIS_IZ PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("jugando"))
			{
				System.out.println("Dispones de: "+tablero.getFlechasJugador()+" Flechas");
			}
			else
			{
				System.err.println("Linea: "+s15.getLine()+" Col: "+s15.getColumn()+" -> Esta funcion pertenece al modo juego");
			}
			}
		}

	|s16:MOSTRAR_VIDAS PARENTESIS_IZ PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("jugando"))
			{
				System.out.println("Dispones de: "+tablero.getVidasJugador()+" Vidas");
			}
			else
			{
				System.err.println("Linea: "+s16.getLine()+" Col: "+s16.getColumn()+" -> Esta funcion pertenece al modo juego");
			}
			}
		}

	|s17:REINICIAR PARENTESIS_IZ PARENTESIS_DE
		{
			if(ejecutar)
				tablero=new Escenario();
		}

	|s18:SET_FLECHAS_INI PARENTESIS_IZ v1=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				int f=(int)Float.parseFloat(v1.getValor());
				tablero.getJugador().setFlechas(f);

			}else
			{
				System.err.println("Linea: "+s18.getLine()+" Col: "+s18.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s19:SET_VIDAS_INI PARENTESIS_IZ v1=expresion[ejecutar] PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("configurando"))
			{
				int v=(int)Float.parseFloat(v1.getValor());
				tablero.setVidasJugador(v);				

			}else
			{
				System.err.println("Linea: "+s18.getLine()+" Col: "+s18.getColumn()+" -> Esta funcion pertenece al modo de configuracion");
			}
			}
		}

	|s20:ESTADO_JUEGO PARENTESIS_IZ PARENTESIS_DE
		{
			if(ejecutar)
			{
			if(modo.equals("jugando"))
			{
				tablero.mostrarEstadoJuego();
			}else
			{
				System.err.println("Linea: "+s19.getLine()+" Col: "+s19.getColumn()+" -> Esta funcion pertenece al modo de juego");
			}
			}
		
		}

	| asignacion[ejecutar]
	| lectura[ejecutar]
	| escritura[ejecutar]
	| condicional[ejecutar]
	| bucle_mientras[ejecutar]
	| bucle_repetir[ejecutar]
	| bucle_para[ejecutar]
	| borrar[ejecutar]
	| lugar[ejecutar]
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		 }

lugar

	[boolean ejecutar]

	{Variable e1,e2;}
	: 
		LUGAR PARENTESIS_IZ e1=expresion[ejecutar] COMA e2=expresion[ejecutar] PARENTESIS_DE
			{
				if(ejecutar==true)
				{
					System.out.print("\033["+e1.getValor()+";"+e2.getValor()+"H");
				}
			}
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

borrar
	
	[boolean ejecutar]	
	
	: BORRAR
	{	
		if(ejecutar==true)
			System.out.printf("\33[2J");
	}
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

lectura 

	[boolean ejecutar]	

	: LEER PARENTESIS_IZ i:IDENTIFICADOR PARENTESIS_DE
	{

		if(ejecutar == true)
		{
			//Obtenemos el numero desde el teclado
			Scanner entrada=new Scanner(System.in);
			String valor=entrada.next();

			//Insertamos el identificador en la tabla de simbolos
			insertarIdentificador(i.getText(),"numero",valor);

			//DEPURACION
			//System.out.println("Op: Lectura - leido en "+i.getText()+" valor= "+valor+" - Tipo:"+i.getType()); 
		}
	}
	| LEER_CADENA PARENTESIS_IZ n:IDENTIFICADOR PARENTESIS_DE
	{
		if(ejecutar==true)
		{
			//Obtenemos la cadena desde el teclado
			Scanner entrada=new Scanner(System.in);
			String cadena=entrada.next();

			//Insertamos el identificador en la tabla de simbolos
			insertarIdentificador(n.getText(),"cadena",cadena);

			//DEPURACION
			//System.out.println("Op: Lectura - leido en "+n.getText()+" valor: "+cadena);
		}
	}
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

escritura

	[boolean ejecutar]

	{Variable e1,e2;}

	: ESCRIBIR PARENTESIS_IZ e1=expresion[ejecutar] PARENTESIS_DE
	{
		if(ejecutar==true)
			System.out.printf(e1.getValor().replace("\\n","\n").replace("\\'","\'"));

	}
	| ESCRIBIR_CADENA PARENTESIS_IZ e2=expresion[ejecutar] PARENTESIS_DE
	{
		if(ejecutar==true)
			System.out.printf(e2.getValor().replace("\\n","\n").replace("\\'","\'"));
	}
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

asignacion 

	[boolean ejecutar]

	{Variable e;}

	: i:IDENTIFICADOR OP_ASIGNACION 
	(e=expresion[ejecutar]
	{
		if(ejecutar==true)
		{
		//Tomamos el nombre del identificador
		String ident=i.getText();

		//Buscamos si existe en la tabla de simbolos
		int indice=tablaSimbolos.existeSimbolo(ident);

		//Si se encuentra el identificador
		if (indice >= 0)
		{
			String tipo1=new String(tablaSimbolos.getSimbolo(indice).getTipo());
			String tipo2=new String(e.getTipo());
			
			if(tipo1.equals("numero") && tipo2.equals("numero"))
			{

				//obtenemos su valor en forma de cadena
				String valorCadena=e.getValor();

				//DEPURACION
				//System.out.println("Op: Asignacion - "+i.getText()+"= "+e);

				//cambiamos el valor del identificador en la tabla de simbolos
				insertarIdentificador(ident,"numero",valorCadena);
			}
			else if(tipo1.equals("cadena") && tipo2.equals("cadena"))
			{
				//obtenemos su valor en forma de cadena
				String valorCadena=e.getValor();

				//DEPURACION
				//System.out.println("Op: Asignacion - "+i.getText()+"= "+e);

				//cambiamos el valor del identificador en la tabla de simbolos
				insertarIdentificador(ident,"cadena",valorCadena);
			}
			else if(tipo1.equals("cadena") && tipo2.equals("numero") || tipo1.equals("numero") && tipo2.equals("cadena"))
			{
				System.out.println("Advertencia: Esta cambiando el tipo del identificador '"+i.getText()+"' de "+tipo1+" a tipo "+tipo2);
				String valorCadena=e.getValor();

				insertarIdentificador(ident,e.getTipo(),valorCadena);
			}
				
		}
		else //si no existe el identificador
		{
			String valorCadena=e.getValor();

			//lo introducimos en la tabla de simbolos
			insertarIdentificador(ident,e.getTipo(),valorCadena);

		}
		}
	}
	)
	;
	exception
		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

condicional 
	
	[boolean ejecutar]

	{boolean cond;}
	
	: CONDICIONAL_SI PARENTESIS_IZ cond=condicion[ejecutar] PARENTESIS_DE
	ENTONCES
	(
		//Si la condicion es verdadera, se ejecuta el consecuente
		{cond==true}? (sentencia[ejecutar] PUNTO_COMA)+ 

		//Se omite la parte alternativa del condicional
		//(ALTERNATIVA_SI (options {greedy=false;}:.)+)? 
		(ALTERNATIVA_SI (sentencia[false] PUNTO_COMA)+)? 

	|

		//Si la condicion es falsa, se omite el consecuente
		//{cond==false}? (options {greedy=false;}:.)+
		{cond==false}? (sentencia[false] PUNTO_COMA)+
	
		//Si hay parte alternativa, se ejecuta
		//(ALTERNATIVA_SI (sentencia PUNTO_COMA)+ )?
		(ALTERNATIVA_SI (sentencia[ejecutar] PUNTO_COMA)+)?	
	)
	FIN_CONDICIONAL
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

bucle_mientras 

	[boolean ejecutar]

	{boolean cond;
    int marca=-1;}
	:
	//Marca que indica el punto de inicio del bucle
	{marca=mark();}
	 
	MIENTRAS PARENTESIS_IZ cond=condicion[ejecutar] PARENTESIS_DE
	HACER
	(
		//Si la condicion es falsa, se omiten las sentencias del cuerpo
		//{cond==false}? (options {greedy=false;}:.)* FIN_MIENTRAS
		{cond==false}? (sentencia[false] PUNTO_COMA)+ FIN_MIENTRAS

	| {cond==true}? (sentencia[ejecutar] PUNTO_COMA)+ FIN_MIENTRAS
					{
						rewind(marca);
						this.bucle_mientras(ejecutar);
					}
	)
	;


//revisar logica de cotrol hay errores
bucle_repetir

	[boolean ejecutar]

	{boolean cond=false;
	 int marca=-1;}
	:
	{marca=mark();}

	REPETIR 
	(

		{cond==true}? (sentencia[false] PUNTO_COMA)+ HASTA PARENTESIS_IZ cond=condicion[ejecutar] PARENTESIS_DE
		

		|{cond==false}? (sentencia[ejecutar] PUNTO_COMA)+ HASTA PARENTESIS_IZ cond=condicion[ejecutar] PARENTESIS_DE
						{
							if(cond==false)
							{
								rewind(marca);
								this.bucle_repetir(ejecutar);
							}
						}
	)
	;


bucle_para

	[boolean ejecutar]

	{
		boolean condicion=false;
		int marca=-1, indice=-1;
		Variable inicio=new Variable("","numero","-1"), fin=new Variable("","numero","-1"), inc=new Variable("","numero","-1");
	}
	:
		{if(ejecutar) marca=mark();}
	
		PARA i:IDENTIFICADOR
		DESDE inicio=expresion[ejecutar]
		HASTA fin=expresion[ejecutar]
		PASO  inc=expresion[ejecutar]
		HACER 
		{
			if(ejecutar)
			{
				//Obtenemos el nombre del identificador
				String nombreId=i.getText();
	
				if(primeraVuelta) //Si es la primera iteracion asignamos el valor de inicio al identificador
				{
					String cadenaValor=inicio.getValor();
	
					insertarIdentificador(nombreId,"numero",cadenaValor);
					primeraVuelta=false;
				}
			
				indice=tablaSimbolos.existeSimbolo(nombreId);
			
				condicion=Float.parseFloat(tablaSimbolos.getSimbolo(indice).getValor()) <= Float.parseFloat(fin.getValor());
			}
		}

		(
		{!condicion}? (sentencia[false] PUNTO_COMA)+ FIN_PARA

		|{condicion}? (sentencia[ejecutar] PUNTO_COMA)+
				{
					tablaSimbolos.getSimbolo(indice).setValor(String.valueOf(Float.parseFloat(tablaSimbolos.getSimbolo(indice).getValor())+Float.parseFloat(inc.getValor())));} FIN_PARA
				{
					if(ejecutar)
					{
						rewind(marca);
						this.bucle_para(ejecutar);
					}
				}
		){primeraVuelta=true;}
	;
	

//se debe contemplar la posibilidad de comparar cadenas
condicion

	[boolean ejecutar]

	returns [boolean resultado=false;]

	{
		Variable e1, e2;
		String cad1, cad2;
	}


	: e1=expresion[ejecutar]

		( 
			OP_IGUALDAD e2=expresion[ejecutar]
				{
					if (ejecutar == false)
						resultado = false;
		  			else
					{
					if(e1.getTipo().equals("numero") && e2.getTipo().equals("numero")) {
						if (Float.parseFloat(e1.getValor()) == Float.parseFloat(e2.getValor()))
							resultado=true;
						else 
							resultado=false;
					}else if(e1.esCadena() && e2.esCadena())
					{
						if(e1.getValor().equals(e2.getValor()))
							resultado=true;
						else
							resultado=false;
					}
					else
						System.err.println("Error: no se permiten operaciones logicas sobre tipos distintos");
					}
				}
			|OP_DISTINTO_QUE e2=expresion[ejecutar]
				{
					if (ejecutar == false)
						resultado = false;
		  			else
					{
					if(e1.getTipo().equals("numero") && e2.getTipo().equals("numero")) {
						if (Float.parseFloat(e1.getValor()) != Float.parseFloat(e2.getValor()))
							resultado=true;
						else 
							resultado=false;
					}else if(e1.esCadena() && e2.esCadena())
					{
						if(!e1.getValor().equals(e2.getValor()))		
							resultado=true;
						else
							resultado=false;
					}	
					else
						System.err.println("Error: no se permiten operaciones logicas sobre tipos distintos");
					}
				}
			|OP_MAYOR_QUE e2=expresion[ejecutar]
				{
					if (ejecutar == false)
						resultado = false;
		  			else
					{
					if(e1.getTipo().equals("numero") && e2.getTipo().equals("numero")) {
						if (Float.parseFloat(e1.getValor()) > Float.parseFloat(e2.getValor()))
							resultado=true;
						else 
							resultado=false;
					}else
						System.err.println("Error: no se permiten operaciones logicas sobre cadenas");
					}
				}
			|OP_MAYOR_IGUAL e2=expresion[ejecutar]
				{
					if (ejecutar == false)
						resultado = false;
		  			else
					{
					if(e1.getTipo().equals("numero") && e2.getTipo().equals("numero")) {
						if (Float.parseFloat(e1.getValor()) >= Float.parseFloat(e2.getValor()))
							resultado=true;
						else 
							resultado=false;
					}else
						System.err.println("Error: no se permiten operaciones logicas sobre cadenas");
					}
				}
			|OP_MENOR_QUE e2=expresion[ejecutar]
				{
					if (ejecutar == false)
						resultado = false;
		  			else
					{
					if(e1.getTipo().equals("numero") && e2.getTipo().equals("numero")) {
						if (Float.parseFloat(e1.getValor()) < Float.parseFloat(e2.getValor()))
							resultado=true;
						else 
							resultado=false;
					}else
						System.err.println("Error: no se permiten operaciones logicas sobre cadenas");
					}
				}
			|OP_MENOR_IGUAL e2=expresion[ejecutar]
				{
					if (ejecutar == false)
						resultado = false;
		  			else
					{
					if(e1.getTipo().equals("numero") && e2.getTipo().equals("numero")) {
						if (Float.parseFloat(e1.getValor()) <= Float.parseFloat(e2.getValor()))
							resultado=true;
						else 
							resultado=false;
					}else
						System.err.println("Error: no se permiten operaciones logicas sobre cadenas");
					}
				}
		)
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}


//CUIDADO: precedencia operadores y concatenacion y parentesis
expresion

	[boolean ejecutar]

	returns [Variable resultado=new Variable("","","");]

	{Variable e1,e2;}

	: 
		e1=sumando[ejecutar] {resultado=e1;}
		( 
			(
				OP_MAS e2=sumando[ejecutar]
					{
						if(ejecutar==true)
						{
						if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) {
				 			resultado = new Variable("", "numero", String.valueOf(Float.parseFloat(resultado.getValor()) + Float.parseFloat(e2.getValor())));
						}else if(e2.getTipo().equals("numero") && e1.getTipo().equals("cadena") || e2.getTipo().equals("cadena") && e1.getTipo().equals("numero"))
							System.err.println("Error: " + e1.getNombre() + " y "+e2.getNombre()+" no son del mismo tipo, use el operador || para concatenar");
						}
					}
			)
			| (OP_MENOS e2=sumando[ejecutar]
					{
						if(ejecutar==true)
						{
						if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero"))
				 			resultado = new Variable("", "numero", String.valueOf(Float.parseFloat(resultado.getValor()) - Float.parseFloat(e2.getValor())));
						else
							System.err.println("Error: Operando sobre terminos que no son del mismo tipo");
						}
					}
			)

			|(
				OP_MODULO e2=sumando[ejecutar]
				{
					if(ejecutar==true)
					{
						if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
							resultado=new Variable("","numero",String.valueOf(Float.parseFloat(resultado.getValor()) % Float.parseFloat(e2.getValor())));
					}
				}
			)
		)*
		|e1=negativo[ejecutar] {resultado=e1;}
		(
			// Suma
			(
		 		OP_MAS e2=sumando[ejecutar]
				{
					if(ejecutar==true)
						if(e2.getTipo().equals("numero") && resultado.getTipo().equals("numero")) {
							resultado = new Variable("", "numero", String.valueOf(Float.parseFloat(resultado.getValor()) + Float.parseFloat(e2.getValor())));
						}
				}
			)
		 	// Resta cuando hay numero negativo
			|(
		 		OP_MENOS e2=sumando[ejecutar]
				{
					if(ejecutar==true)
				 		if(e2.getTipo().equals("numero") && resultado.getTipo().equals("numero")) {
				 			resultado = new Variable("", "numero", String.valueOf(Float.parseFloat(resultado.getValor()) - Float.parseFloat(e2.getValor())));
						}
				}
			)
			|(
				OP_MODULO e2=sumando[ejecutar]
				{
					if(ejecutar==true)
					{
						if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
							resultado=new Variable("","numero",String.valueOf(Float.parseFloat(resultado.getValor()) % Float.parseFloat(e2.getValor())));
					}
				}
			)
		)*
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

sumando

	[boolean ejecutar]

	returns [Variable resultado=new Variable("","","");]

	{Variable e1,e2;}

	: 
		e1=factor[ejecutar] {resultado=e1;}
		( 
			(OP_PRODUCTO e2=termino[ejecutar]
				{
					if(ejecutar==true)
					{
					if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
						resultado=new Variable("","numero",String.valueOf(Float.parseFloat(resultado.getValor()) * Float.parseFloat(e2.getValor())));
					//ALTERNATIVA - control de errores
					}
				}
			)

			|(OP_DIVISION e2=termino[ejecutar]
				{
					if(ejecutar==true)
					{
					if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
						resultado=new Variable("","numero",String.valueOf(Float.parseFloat(resultado.getValor()) / Float.parseFloat(e2.getValor())));
					}					
				}
			)
	)*
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

factor

	[boolean ejecutar]

	returns [Variable resultado=new Variable("","","");] 

	{Variable e1,e2;}

	: 
		e1=termino[ejecutar] {resultado=e1;}
		(
			(OP_CONCAT e2=termino[ejecutar]
					{
						if(ejecutar==true)
						{
						if(e1.getTipo().equals("cadena") && e2.getTipo().equals("cadena"))
							resultado=new Variable("", "cadena", resultado.getValor() + e2.getValor());
						else
							System.err.println("Error: Operando sobre terminos que no son del mismo tipo");
						}
					}
			)

			|(OP_POTENCIA e2=termino[ejecutar]
				{
					if(ejecutar==true)
					{
					if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
						resultado=new Variable("","numero",String.valueOf(Math.pow(Float.parseFloat(resultado.getValor()),Float.parseFloat(e2.getValor()))));
					}
				}
			)
		)*
	;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}


termino

	[boolean ejecutar]

	returns [Variable resultado=new Variable("","","");] 

	{Variable e;}
	: 
		n:NUMERO
			{
				if(ejecutar==true)
					resultado=new Variable("","numero",n.getText());
			}

		|n1:CADENA
			{
				if(ejecutar==true)
					resultado=new Variable("","cadena",n1.getText());
			}

    	|i:IDENTIFICADOR
    		{
				if(ejecutar==true)
				{
    				// Busca el identificador en la tabla de sÃ­mbolos
					int indice = tablaSimbolos.existeSimbolo(i.getText());

					// Si encuentra el identificador, devuelve su valor
					if (indice >= 0)
					{
						// Se recupera el valor almacenado del tipo Variable
						resultado=tablaSimbolos.getSimbolo(indice);
					}
					else
						System.err.println("Error: el identificador " + i.getText() + " esta indefinido");
				}
    		}

    	|PARENTESIS_IZ e=expresion[ejecutar] PARENTESIS_DE
    		{
				if(ejecutar==true)
	    			resultado=e;
    		}
    ;
	exception
 		catch [RecognitionException re] {
			mostrarExcepcion(re);
		}

negativo
	
	[boolean ejecutar]

	returns [Variable resultado = new Variable("","","");] 

	{Variable e;}
 
	:
		OP_MENOS e = factor[ejecutar]
		{
			if(ejecutar==true)
				if(e.getTipo().equals("numero"))
					resultado=new Variable("","numero",String.valueOf(Float.parseFloat(e.getValor()) * -1));
		}
	;
