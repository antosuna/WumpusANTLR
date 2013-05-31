/*
	Analizador léxico para intéprete de pseudocódigo "IPE"
*/



class Analex extends Lexer;

options
{
	//Unicodes usuales
	charVocabulary='\3'..'\377';

	//se indica que los literales se comprueben localmente
	testLiterals=false;

	//no diferencia entre mayusculas y minusculas
	caseSensitiveLiterals=false;

	// utilizado en el analisis sintactico
	importVocab = Anasint;
	
	// lookahead: número de símbolos de anticipación
	k=2;
}


//Componentes léxicos predefinidos o plabras reservadas
tokens
{
	
	LOGICO_O="__o";
	LOGICO_Y="__y";
	LOGICO_NO="__no";
	LEER="leer";
	LEER_CADENA="leer_cadena";
	ESCRIBIR="escribir";
	ESCRIBIR_CADENA="escribir_cadena";
	CONDICIONAL_SI="si";
	ENTONCES="entonces";
	ALTERNATIVA_SI="si_no";
	FIN_CONDICIONAL="fin_si";
	MIENTRAS="mientras";
	HACER="hacer";
	FIN_MIENTRAS="fin_mientras";
	REPETIR="repetir";
	HASTA="hasta";
	PARA="para";
	DESDE="desde";
	PASO="paso";
	FIN_PARA="fin_para";
	BORRAR="borrar";
	LUGAR="lugar";
}

protected NL :
	(
		// MS-DOS
		// Se usa un predicado sintáctico para resolver el conflicto
		 ("\r\n") => "\r\n" 

		// UNIX
		| '\n'

		// MACINTOSH
		| '\r' 	

	)
		{ newline(); }
	;


BLANCO :
		 ( ' '
		 | '\t'
		 | NL
		 ) 	 { $setType(Token.SKIP); }
	;

// Dígitos 
protected DIGITO : '0'..'9'
                 ;

NUMERO : (DIGITO)+ ('.'(DIGITO)+)? ('e'('+'|'-')?DIGITO)?
       ;


//  Letras
protected LETRA : 'a'..'z'
		| 'A'..'Z'
		;

// Regla para reconocer los literales (y palabras reservadas).
IDENTIFICADOR
	// Se indica que se comprueben las palabras reservadas
	options {testLiterals=true;} 
	: (LETRA)(LETRA|DIGITO|'_'(LETRA|DIGITO))*
	;


CADENA: '\''! 
                (	options {greedy=false;}: ~('\\')
                 	|"\\"("\'"|"n")
				)* 
        '\''!
          ;

//Concatenacion
OP_CONCAT : "||"
	;


// Operadores aritmeticos

OP_MAS : '+' 
	;

OP_MENOS : '-' 
	;

OP_PRODUCTO : '*' 
	;

OP_DIVISION : '/' 
	;

OP_POTENCIA : "**"
	;

OP_MODULO : "__mod"
	;


// Operador de asignación
OP_ASIGNACION: '='
	     ; 		


// Operadores relacionales
OP_IGUALDAD: "=="
	   ;

OP_DISTINTO_QUE: "<>"
	   ;

OP_MAYOR_QUE: '>'
        ;


OP_MAYOR_IGUAL: ">="
        ;

OP_MENOR_QUE: '<'
        ;

OP_MENOR_IGUAL: "<="
        ;

// Delimitadores
PARENTESIS_IZ : '(' 
	  ;
 
PARENTESIS_DE : ')' 
	  ;

protected LLAVE_IZ : '{' 
	  ;
 
protected LLAVE_DE : '}' 
	  ;

CORCHETE_IZ : '[' 
	  ;
 
CORCHETE_DE : ']' 
	  ;


// Separadores
PUNTO_COMA 
	   options {paraphrase = "punto y coma --> ;" ; }
	   : ';' 
           ;

COMA 
	   options {paraphrase = "coma --> ," ; }
	   : ',' 
           ;


//Comentarios
protected COMENTARIO_SIMPLE: '#' (~('\n'|'\r') )*
		;

protected COMENTARIO_COMP: LLAVE_IZ (options {greedy=false;} : . )*  LLAVE_DE
		;

COMENTARIO:
	  (
	    COMENTARIO_SIMPLE
	  | COMENTARIO_COMP
          )	{$setType(Token.SKIP);}
	  ;


