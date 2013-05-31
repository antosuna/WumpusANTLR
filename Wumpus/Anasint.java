// $ANTLR 2.7.6 (2005-12-22): "Anasint.g" -> "Anasint.java"$

	// Para leer del teclado
	import java.util.Scanner;
	import java.awt.*;
	import java.util.Vector;
	import java.util.List;
	import java.util.ArrayList;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

public class Anasint extends antlr.LLkParser       implements AnasintTokenTypes
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


protected Anasint(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public Anasint(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected Anasint(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public Anasint(TokenStream lexer) {
  this(lexer,1);
}

public Anasint(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final void programa() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			_loop3:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					sentencia(true);
					match(PUNTO_COMA);
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case CONFIG:
			{
				configuracion();
				{
				_loop6:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(true);
						match(PUNTO_COMA);
					}
					else {
						break _loop6;
					}
					
				} while (true);
				}
				break;
			}
			case EOF:
			case JUEGO:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case JUEGO:
			{
				jugadas();
				{
				_loop9:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(true);
						match(PUNTO_COMA);
					}
					else {
						break _loop9;
					}
					
				} while (true);
				}
				break;
			}
			case EOF:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final void sentencia(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		Token  s1 = null;
		Token  s2 = null;
		Token  s3 = null;
		Token  s4 = null;
		Token  s5 = null;
		Token  s6 = null;
		Token  s7 = null;
		Token  s8 = null;
		Token  s9 = null;
		Token  s10 = null;
		Token  s11 = null;
		Token  s12 = null;
		Token  s13 = null;
		Token  s14 = null;
		Token  s15 = null;
		Token  s16 = null;
		Token  s17 = null;
		Token  s18 = null;
		Token  s19 = null;
		Token  s20 = null;
		Variable v1, v2;
		
		try {      // for error handling
			switch ( LA(1)) {
			case SET_TABLERO:
			{
				s1 = LT(1);
				match(SET_TABLERO);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_INICIO:
			{
				s2 = LT(1);
				match(SET_INICIO);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_FIN:
			{
				s3 = LT(1);
				match(SET_FIN);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_TESORO:
			{
				s4 = LT(1);
				match(SET_TESORO);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_WUMPUS:
			{
				s5 = LT(1);
				match(SET_WUMPUS);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_POZO:
			{
				s6 = LT(1);
				match(SET_POZO);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_FLECHA:
			{
				s7 = LT(1);
				match(SET_FLECHA);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_AMBROSIA:
			{
				s8 = LT(1);
				match(SET_AMBROSIA);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_MINA:
			{
				s9 = LT(1);
				match(SET_MINA);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(COMA);
				v2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case MOSTRAR_TABLERO:
			{
				s10 = LT(1);
				match(MOSTRAR_TABLERO);
				match(PARENTESIS_IZ);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case MOVER_JUGADOR:
			{
				s11 = LT(1);
				match(MOVER_JUGADOR);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case COGER_FLECHA:
			{
				s12 = LT(1);
				match(COGER_FLECHA);
				match(PARENTESIS_IZ);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case DISPARAR:
			{
				s13 = LT(1);
				match(DISPARAR);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case BEBER:
			{
				s14 = LT(1);
				match(BEBER);
				match(PARENTESIS_IZ);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case MOSTRAR_FLECHAS:
			{
				s15 = LT(1);
				match(MOSTRAR_FLECHAS);
				match(PARENTESIS_IZ);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case MOSTRAR_VIDAS:
			{
				s16 = LT(1);
				match(MOSTRAR_VIDAS);
				match(PARENTESIS_IZ);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case REINICIAR:
			{
				s17 = LT(1);
				match(REINICIAR);
				match(PARENTESIS_IZ);
				match(PARENTESIS_DE);
				
							if(ejecutar)
								tablero=new Escenario();
						
				break;
			}
			case SET_FLECHAS_INI:
			{
				s18 = LT(1);
				match(SET_FLECHAS_INI);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case SET_VIDAS_INI:
			{
				s19 = LT(1);
				match(SET_VIDAS_INI);
				match(PARENTESIS_IZ);
				v1=expresion(ejecutar);
				match(PARENTESIS_DE);
				
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
						
				break;
			}
			case ESTADO_JUEGO:
			{
				s20 = LT(1);
				match(ESTADO_JUEGO);
				match(PARENTESIS_IZ);
				match(PARENTESIS_DE);
				
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
						
						
				break;
			}
			case IDENTIFICADOR:
			{
				asignacion(ejecutar);
				break;
			}
			case LEER:
			case LEER_CADENA:
			{
				lectura(ejecutar);
				break;
			}
			case ESCRIBIR:
			case ESCRIBIR_CADENA:
			{
				escritura(ejecutar);
				break;
			}
			case CONDICIONAL_SI:
			{
				condicional(ejecutar);
				break;
			}
			case MIENTRAS:
			{
				bucle_mientras(ejecutar);
				break;
			}
			case REPETIR:
			{
				bucle_repetir(ejecutar);
				break;
			}
			case PARA:
			{
				bucle_para(ejecutar);
				break;
			}
			case BORRAR:
			{
				borrar(ejecutar);
				break;
			}
			case LUGAR:
			{
				lugar(ejecutar);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final void configuracion() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(CONFIG);
			modo=new String("configurando");
			{
			_loop12:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					sentencia(true);
					match(PUNTO_COMA);
				}
				else {
					break _loop12;
				}
				
			} while (true);
			}
			match(FIN_CONFIG);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
	}
	
	public final void jugadas() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(JUEGO);
			if(tablero.checkInicioJuego()) modo=new String("jugando");
			{
			_loop15:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					sentencia(true);
					match(PUNTO_COMA);
				}
				else {
					break _loop15;
				}
				
			} while (true);
			}
			match(FIN_JUEGO);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final Variable  expresion(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		Variable resultado=new Variable("","","");;
		
		Variable e1,e2;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PARENTESIS_IZ:
			case IDENTIFICADOR:
			case NUMERO:
			case CADENA:
			{
				e1=sumando(ejecutar);
				resultado=e1;
				{
				_loop60:
				do {
					switch ( LA(1)) {
					case OP_MAS:
					{
						{
						match(OP_MAS);
						e2=sumando(ejecutar);
						
												if(ejecutar==true)
												{
												if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) {
										 			resultado = new Variable("", "numero", String.valueOf(Float.parseFloat(resultado.getValor()) + Float.parseFloat(e2.getValor())));
												}else if(e2.getTipo().equals("numero") && e1.getTipo().equals("cadena") || e2.getTipo().equals("cadena") && e1.getTipo().equals("numero"))
													System.err.println("Error: " + e1.getNombre() + " y "+e2.getNombre()+" no son del mismo tipo, use el operador || para concatenar");
												}
											
						}
						break;
					}
					case OP_MENOS:
					{
						{
						match(OP_MENOS);
						e2=sumando(ejecutar);
						
												if(ejecutar==true)
												{
												if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero"))
										 			resultado = new Variable("", "numero", String.valueOf(Float.parseFloat(resultado.getValor()) - Float.parseFloat(e2.getValor())));
												else
													System.err.println("Error: Operando sobre terminos que no son del mismo tipo");
												}
											
						}
						break;
					}
					case OP_MODULO:
					{
						{
						match(OP_MODULO);
						e2=sumando(ejecutar);
						
											if(ejecutar==true)
											{
												if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
													resultado=new Variable("","numero",String.valueOf(Float.parseFloat(resultado.getValor()) % Float.parseFloat(e2.getValor())));
											}
										
						}
						break;
					}
					default:
					{
						break _loop60;
					}
					}
				} while (true);
				}
				break;
			}
			case OP_MENOS:
			{
				e1=negativo(ejecutar);
				resultado=e1;
				{
				_loop65:
				do {
					switch ( LA(1)) {
					case OP_MAS:
					{
						{
						match(OP_MAS);
						e2=sumando(ejecutar);
						
											if(ejecutar==true)
												if(e2.getTipo().equals("numero") && resultado.getTipo().equals("numero")) {
													resultado = new Variable("", "numero", String.valueOf(Float.parseFloat(resultado.getValor()) + Float.parseFloat(e2.getValor())));
												}
										
						}
						break;
					}
					case OP_MENOS:
					{
						{
						match(OP_MENOS);
						e2=sumando(ejecutar);
						
											if(ejecutar==true)
										 		if(e2.getTipo().equals("numero") && resultado.getTipo().equals("numero")) {
										 			resultado = new Variable("", "numero", String.valueOf(Float.parseFloat(resultado.getValor()) - Float.parseFloat(e2.getValor())));
												}
										
						}
						break;
					}
					case OP_MODULO:
					{
						{
						match(OP_MODULO);
						e2=sumando(ejecutar);
						
											if(ejecutar==true)
											{
												if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
													resultado=new Variable("","numero",String.valueOf(Float.parseFloat(resultado.getValor()) % Float.parseFloat(e2.getValor())));
											}
										
						}
						break;
					}
					default:
					{
						break _loop65;
					}
					}
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
		return resultado;
	}
	
	public final void asignacion(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		Token  i = null;
		Variable e;
		
		try {      // for error handling
			i = LT(1);
			match(IDENTIFICADOR);
			match(OP_ASIGNACION);
			{
			e=expresion(ejecutar);
			
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
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final void lectura(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		Token  i = null;
		Token  n = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LEER:
			{
				match(LEER);
				match(PARENTESIS_IZ);
				i = LT(1);
				match(IDENTIFICADOR);
				match(PARENTESIS_DE);
				
				
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
					
				break;
			}
			case LEER_CADENA:
			{
				match(LEER_CADENA);
				match(PARENTESIS_IZ);
				n = LT(1);
				match(IDENTIFICADOR);
				match(PARENTESIS_DE);
				
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
					
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final void escritura(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		Variable e1,e2;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ESCRIBIR:
			{
				match(ESCRIBIR);
				match(PARENTESIS_IZ);
				e1=expresion(ejecutar);
				match(PARENTESIS_DE);
				
						if(ejecutar==true)
							System.out.printf(e1.getValor().replace("\\n","\n").replace("\\'","\'"));
				
					
				break;
			}
			case ESCRIBIR_CADENA:
			{
				match(ESCRIBIR_CADENA);
				match(PARENTESIS_IZ);
				e2=expresion(ejecutar);
				match(PARENTESIS_DE);
				
						if(ejecutar==true)
							System.out.printf(e2.getValor().replace("\\n","\n").replace("\\'","\'"));
					
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final void condicional(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		boolean cond;
		
		try {      // for error handling
			match(CONDICIONAL_SI);
			match(PARENTESIS_IZ);
			cond=condicion(ejecutar);
			match(PARENTESIS_DE);
			match(ENTONCES);
			{
			if (((_tokenSet_0.member(LA(1))))&&(cond==true)) {
				{
				int _cnt26=0;
				_loop26:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(ejecutar);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt26>=1 ) { break _loop26; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt26++;
				} while (true);
				}
				{
				switch ( LA(1)) {
				case ALTERNATIVA_SI:
				{
					match(ALTERNATIVA_SI);
					{
					int _cnt29=0;
					_loop29:
					do {
						if ((_tokenSet_0.member(LA(1)))) {
							sentencia(false);
							match(PUNTO_COMA);
						}
						else {
							if ( _cnt29>=1 ) { break _loop29; } else {throw new NoViableAltException(LT(1), getFilename());}
						}
						
						_cnt29++;
					} while (true);
					}
					break;
				}
				case FIN_CONDICIONAL:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
			}
			else if (((_tokenSet_0.member(LA(1))))&&(cond==false)) {
				{
				int _cnt31=0;
				_loop31:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(false);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt31>=1 ) { break _loop31; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt31++;
				} while (true);
				}
				{
				switch ( LA(1)) {
				case ALTERNATIVA_SI:
				{
					match(ALTERNATIVA_SI);
					{
					int _cnt34=0;
					_loop34:
					do {
						if ((_tokenSet_0.member(LA(1)))) {
							sentencia(ejecutar);
							match(PUNTO_COMA);
						}
						else {
							if ( _cnt34>=1 ) { break _loop34; } else {throw new NoViableAltException(LT(1), getFilename());}
						}
						
						_cnt34++;
					} while (true);
					}
					break;
				}
				case FIN_CONDICIONAL:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			match(FIN_CONDICIONAL);
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final void bucle_mientras(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		boolean cond;
		int marca=-1;
		
		try {      // for error handling
			marca=mark();
			match(MIENTRAS);
			match(PARENTESIS_IZ);
			cond=condicion(ejecutar);
			match(PARENTESIS_DE);
			match(HACER);
			{
			if (((_tokenSet_0.member(LA(1))))&&(cond==false)) {
				{
				int _cnt38=0;
				_loop38:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(false);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt38>=1 ) { break _loop38; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt38++;
				} while (true);
				}
				match(FIN_MIENTRAS);
			}
			else if (((_tokenSet_0.member(LA(1))))&&(cond==true)) {
				{
				int _cnt40=0;
				_loop40:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(ejecutar);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt40>=1 ) { break _loop40; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt40++;
				} while (true);
				}
				match(FIN_MIENTRAS);
				
										rewind(marca);
										this.bucle_mientras(ejecutar);
									
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void bucle_repetir(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		boolean cond=false;
			 int marca=-1;
		
		try {      // for error handling
			marca=mark();
			match(REPETIR);
			{
			if (((_tokenSet_0.member(LA(1))))&&(cond==true)) {
				{
				int _cnt44=0;
				_loop44:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(false);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt44>=1 ) { break _loop44; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt44++;
				} while (true);
				}
				match(HASTA);
				match(PARENTESIS_IZ);
				cond=condicion(ejecutar);
				match(PARENTESIS_DE);
			}
			else if (((_tokenSet_0.member(LA(1))))&&(cond==false)) {
				{
				int _cnt46=0;
				_loop46:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(ejecutar);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt46>=1 ) { break _loop46; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt46++;
				} while (true);
				}
				match(HASTA);
				match(PARENTESIS_IZ);
				cond=condicion(ejecutar);
				match(PARENTESIS_DE);
				
											if(cond==false)
											{
												rewind(marca);
												this.bucle_repetir(ejecutar);
											}
										
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void bucle_para(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		Token  i = null;
		
				boolean condicion=false;
				int marca=-1, indice=-1;
				Variable inicio=new Variable("","numero","-1"), fin=new Variable("","numero","-1"), inc=new Variable("","numero","-1");
			
		
		try {      // for error handling
			if(ejecutar) marca=mark();
			match(PARA);
			i = LT(1);
			match(IDENTIFICADOR);
			match(DESDE);
			inicio=expresion(ejecutar);
			match(HASTA);
			fin=expresion(ejecutar);
			match(PASO);
			inc=expresion(ejecutar);
			match(HACER);
			
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
					
			{
			if (((_tokenSet_0.member(LA(1))))&&(!condicion)) {
				{
				int _cnt50=0;
				_loop50:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(false);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt50>=1 ) { break _loop50; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt50++;
				} while (true);
				}
				match(FIN_PARA);
			}
			else if (((_tokenSet_0.member(LA(1))))&&(condicion)) {
				{
				int _cnt52=0;
				_loop52:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(ejecutar);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt52>=1 ) { break _loop52; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt52++;
				} while (true);
				}
				
									tablaSimbolos.getSimbolo(indice).setValor(String.valueOf(Float.parseFloat(tablaSimbolos.getSimbolo(indice).getValor())+Float.parseFloat(inc.getValor())));
				match(FIN_PARA);
				
									if(ejecutar)
									{
										rewind(marca);
										this.bucle_para(ejecutar);
									}
								
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			primeraVuelta=true;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void borrar(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(BORRAR);
				
					if(ejecutar==true)
						System.out.printf("\33[2J");
				
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final void lugar(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		Variable e1,e2;
		
		try {      // for error handling
			match(LUGAR);
			match(PARENTESIS_IZ);
			e1=expresion(ejecutar);
			match(COMA);
			e2=expresion(ejecutar);
			match(PARENTESIS_DE);
			
							if(ejecutar==true)
							{
								System.out.print("\033["+e1.getValor()+";"+e2.getValor()+"H");
							}
						
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final boolean  condicion(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		boolean resultado=false;;
		
		
				Variable e1, e2;
				String cad1, cad2;
			
		
		try {      // for error handling
			e1=expresion(ejecutar);
			{
			switch ( LA(1)) {
			case OP_IGUALDAD:
			{
				match(OP_IGUALDAD);
				e2=expresion(ejecutar);
				
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
								
				break;
			}
			case OP_DISTINTO_QUE:
			{
				match(OP_DISTINTO_QUE);
				e2=expresion(ejecutar);
				
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
								
				break;
			}
			case OP_MAYOR_QUE:
			{
				match(OP_MAYOR_QUE);
				e2=expresion(ejecutar);
				
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
								
				break;
			}
			case OP_MAYOR_IGUAL:
			{
				match(OP_MAYOR_IGUAL);
				e2=expresion(ejecutar);
				
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
								
				break;
			}
			case OP_MENOR_QUE:
			{
				match(OP_MENOR_QUE);
				e2=expresion(ejecutar);
				
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
								
				break;
			}
			case OP_MENOR_IGUAL:
			{
				match(OP_MENOR_IGUAL);
				e2=expresion(ejecutar);
				
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
								
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
		return resultado;
	}
	
	public final Variable  sumando(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		Variable resultado=new Variable("","","");;
		
		Variable e1,e2;
		
		try {      // for error handling
			e1=factor(ejecutar);
			resultado=e1;
			{
			_loop70:
			do {
				switch ( LA(1)) {
				case OP_PRODUCTO:
				{
					{
					match(OP_PRODUCTO);
					e2=termino(ejecutar);
					
										if(ejecutar==true)
										{
										if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
											resultado=new Variable("","numero",String.valueOf(Float.parseFloat(resultado.getValor()) * Float.parseFloat(e2.getValor())));
										//ALTERNATIVA - control de errores
										}
									
					}
					break;
				}
				case OP_DIVISION:
				{
					{
					match(OP_DIVISION);
					e2=termino(ejecutar);
					
										if(ejecutar==true)
										{
										if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
											resultado=new Variable("","numero",String.valueOf(Float.parseFloat(resultado.getValor()) / Float.parseFloat(e2.getValor())));
										}					
									
					}
					break;
				}
				default:
				{
					break _loop70;
				}
				}
			} while (true);
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
		return resultado;
	}
	
	public final Variable  negativo(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		Variable resultado = new Variable("","","");;
		
		Variable e;
		
		try {      // for error handling
			match(OP_MENOS);
			e=factor(ejecutar);
			
						if(ejecutar==true)
							if(e.getTipo().equals("numero"))
								resultado=new Variable("","numero",String.valueOf(Float.parseFloat(e.getValor()) * -1));
					
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
		return resultado;
	}
	
	public final Variable  factor(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		Variable resultado=new Variable("","","");;
		
		Variable e1,e2;
		
		try {      // for error handling
			e1=termino(ejecutar);
			resultado=e1;
			{
			_loop75:
			do {
				switch ( LA(1)) {
				case OP_CONCAT:
				{
					{
					match(OP_CONCAT);
					e2=termino(ejecutar);
					
											if(ejecutar==true)
											{
											if(e1.getTipo().equals("cadena") && e2.getTipo().equals("cadena"))
												resultado=new Variable("", "cadena", resultado.getValor() + e2.getValor());
											else
												System.err.println("Error: Operando sobre terminos que no son del mismo tipo");
											}
										
					}
					break;
				}
				case OP_POTENCIA:
				{
					{
					match(OP_POTENCIA);
					e2=termino(ejecutar);
					
										if(ejecutar==true)
										{
										if(e2.getTipo().equals("numero") && e1.getTipo().equals("numero")) 
											resultado=new Variable("","numero",String.valueOf(Math.pow(Float.parseFloat(resultado.getValor()),Float.parseFloat(e2.getValor()))));
										}
									
					}
					break;
				}
				default:
				{
					break _loop75;
				}
				}
			} while (true);
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
		return resultado;
	}
	
	public final Variable  termino(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		Variable resultado=new Variable("","","");;
		
		Token  n = null;
		Token  n1 = null;
		Token  i = null;
		Variable e;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUMERO:
			{
				n = LT(1);
				match(NUMERO);
				
								if(ejecutar==true)
									resultado=new Variable("","numero",n.getText());
							
				break;
			}
			case CADENA:
			{
				n1 = LT(1);
				match(CADENA);
				
								if(ejecutar==true)
									resultado=new Variable("","cadena",n1.getText());
							
				break;
			}
			case IDENTIFICADOR:
			{
				i = LT(1);
				match(IDENTIFICADOR);
				
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
						
				break;
			}
			case PARENTESIS_IZ:
			{
				match(PARENTESIS_IZ);
				e=expresion(ejecutar);
				match(PARENTESIS_DE);
				
								if(ejecutar==true)
					    			resultado=e;
						
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
		return resultado;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"PUNTO_COMA",
		"CONFIG",
		"FIN_CONFIG",
		"JUEGO",
		"FIN_JUEGO",
		"SET_TABLERO",
		"PARENTESIS_IZ",
		"COMA",
		"PARENTESIS_DE",
		"SET_INICIO",
		"SET_FIN",
		"SET_TESORO",
		"SET_WUMPUS",
		"SET_POZO",
		"SET_FLECHA",
		"SET_AMBROSIA",
		"SET_MINA",
		"MOSTRAR_TABLERO",
		"MOVER_JUGADOR",
		"COGER_FLECHA",
		"DISPARAR",
		"BEBER",
		"MOSTRAR_FLECHAS",
		"MOSTRAR_VIDAS",
		"REINICIAR",
		"SET_FLECHAS_INI",
		"SET_VIDAS_INI",
		"ESTADO_JUEGO",
		"LUGAR",
		"BORRAR",
		"LEER",
		"IDENTIFICADOR",
		"LEER_CADENA",
		"ESCRIBIR",
		"ESCRIBIR_CADENA",
		"OP_ASIGNACION",
		"CONDICIONAL_SI",
		"ENTONCES",
		"ALTERNATIVA_SI",
		"FIN_CONDICIONAL",
		"MIENTRAS",
		"HACER",
		"FIN_MIENTRAS",
		"REPETIR",
		"HASTA",
		"PARA",
		"DESDE",
		"PASO",
		"FIN_PARA",
		"OP_IGUALDAD",
		"OP_DISTINTO_QUE",
		"OP_MAYOR_QUE",
		"OP_MAYOR_IGUAL",
		"OP_MENOR_QUE",
		"OP_MENOR_IGUAL",
		"OP_MAS",
		"OP_MENOS",
		"OP_MODULO",
		"OP_PRODUCTO",
		"OP_DIVISION",
		"OP_CONCAT",
		"OP_POTENCIA",
		"NUMERO",
		"CADENA"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 722928895255040L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 722928895255170L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 722928895255042L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 16L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 4605247278335137808L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	
	}
