// $ANTLR 2.7.6 (2005-12-22): "Anasint.g" -> "Anasint.java"$

	// Para leer del teclado
	import java.util.Scanner;
	import java.awt.*;
	import java.util.Vector;

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
			int _cnt3=0;
			_loop3:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					sentencia(true);
					match(PUNTO_COMA);
				}
				else {
					if ( _cnt3>=1 ) { break _loop3; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt3++;
			} while (true);
			}
		}
		catch (RecognitionException re) {
			
						mostrarExcepcion(re);
					
		}
	}
	
	public final void sentencia(
		boolean ejecutar
	) throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
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
							System.out.print("Introduce el valor: ");
							Scanner entrada=new Scanner(System.in);
							String valor=entrada.next();
				
							//Insertamos el identificador en la tabla de simbolos
							insertarIdentificador(i.getText(),"numero",valor);
				
							//DEPURACION
							//System.out.println("Op: Lectura - leido en "+i.getText()+" valor= "+valor); 
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
							System.out.print(" ");
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
				int _cnt14=0;
				_loop14:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(ejecutar);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt14>=1 ) { break _loop14; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt14++;
				} while (true);
				}
				{
				switch ( LA(1)) {
				case ALTERNATIVA_SI:
				{
					match(ALTERNATIVA_SI);
					{
					int _cnt17=0;
					_loop17:
					do {
						if ((_tokenSet_0.member(LA(1)))) {
							sentencia(false);
							match(PUNTO_COMA);
						}
						else {
							if ( _cnt17>=1 ) { break _loop17; } else {throw new NoViableAltException(LT(1), getFilename());}
						}
						
						_cnt17++;
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
				int _cnt19=0;
				_loop19:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(false);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt19>=1 ) { break _loop19; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt19++;
				} while (true);
				}
				{
				switch ( LA(1)) {
				case ALTERNATIVA_SI:
				{
					match(ALTERNATIVA_SI);
					{
					int _cnt22=0;
					_loop22:
					do {
						if ((_tokenSet_0.member(LA(1)))) {
							sentencia(ejecutar);
							match(PUNTO_COMA);
						}
						else {
							if ( _cnt22>=1 ) { break _loop22; } else {throw new NoViableAltException(LT(1), getFilename());}
						}
						
						_cnt22++;
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
				int _cnt26=0;
				_loop26:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(false);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt26>=1 ) { break _loop26; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt26++;
				} while (true);
				}
				match(FIN_MIENTRAS);
			}
			else if (((_tokenSet_0.member(LA(1))))&&(cond==true)) {
				{
				int _cnt28=0;
				_loop28:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(ejecutar);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt28>=1 ) { break _loop28; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt28++;
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
			recover(ex,_tokenSet_1);
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
				int _cnt32=0;
				_loop32:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						sentencia(false);
						match(PUNTO_COMA);
					}
					else {
						if ( _cnt32>=1 ) { break _loop32; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt32++;
				} while (true);
				}
				match(HASTA);
				match(PARENTESIS_IZ);
				cond=condicion(ejecutar);
				match(PARENTESIS_DE);
			}
			else if (((_tokenSet_0.member(LA(1))))&&(cond==false)) {
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
			recover(ex,_tokenSet_1);
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
				match(FIN_PARA);
			}
			else if (((_tokenSet_0.member(LA(1))))&&(condicion)) {
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
			recover(ex,_tokenSet_1);
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
				_loop48:
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
						break _loop48;
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
				_loop53:
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
						break _loop53;
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
									}else
										System.err.println("Error: no se permiten operaciones logicas sobre cadenas");
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
									}else
										System.err.println("Error: no se permiten operaciones logicas sobre cadenas");
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
			_loop58:
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
					break _loop58;
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
			recover(ex,_tokenSet_2);
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
			_loop63:
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
					break _loop63;
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
		"LUGAR",
		"PARENTESIS_IZ",
		"COMA",
		"PARENTESIS_DE",
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
		long[] data = { 43089440L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 16L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 274494128528L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	
	}
