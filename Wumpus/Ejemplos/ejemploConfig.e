{
	Ejemplo de configuracion interactiva del juego Wumpus
	Autor: Antonio Osuna Caballero
}


configuraJuego

filas=0;
columnas=0;

borrar;
lugar(10,10);
escribir_cadena('\nInserta el numero de filas del mapa: ');
leer(filas);
escribir_cadena('\nInserta el numero de columnas del mapa: ');
leer(columnas);
setTablero(filas,columnas);

x=0;
y=0;
borrar;

lugar(10,10);
escribir_cadena('\nBien, Ahora vamos a colocar la casilla de inicio.');
escribir_cadena('\nInserta x: ');
leer(x);
escribir_cadena('\nInserta y: ');
leer(y);
setInicio(x,y);

borrar;

lugar(10,10);
escribir_cadena('\nCorrecto, Procedemos a introducir las coordenadas de la casilla de salida');
escribir_cadena('\nInserta x: ');
leer(x);
escribir_cadena('\nInserta y: ');
leer(y);
setFin(x,y);

borrar;

lugar(10,10);
escribir_cadena('\nUn elemento que no puede faltar es el tesoro, introducelo');
escribir_cadena('\nInserta x: ');
leer(x);
escribir_cadena('\nInserta y: ');
leer(y);
setTesoro(x,y);

borrar;

lugar(10,10);
escribir_cadena('\nPor ultimo, sin Wumpus no tendria gracia');
escribir_cadena('\nInserta x: ');
leer(x);
escribir_cadena('\nInserta y: ');
leer(y);
setWumpus(x,y);

borrar;

lugar(10,10);
escribir_cadena('\nPerfecto, ahora introduzcamos mas elementos del mapa\n');

opcion=-1;

repetir
	escribir_cadena('\nElige una de las siguientes opciones:');
	escribir_cadena('\n1. Introducir mina');
	escribir_cadena('\n2. Introducir pozo');
	escribir_cadena('\n3. Introducir flecha');
	escribir_cadena('\n4. Introducir ambrosia');
	escribir_cadena('\n5. Mostrar mapa');
	escribir_cadena('\n0. Salir');
	escribir_cadena('\nIntroduce opcion: ');
	leer(opcion);

	si (opcion==1) entonces
		escribir_cadena('\nIntroduce la coordenada X de la mina: ');
		leer(x);
		escribir_cadena('\nIntroduce la coordenada Y de la mina: ');
		leer(y);
		borrar;
		lugar(10,10);
		setMina(x,y);
	si_no

		si (opcion==2) entonces
			escribir_cadena('\nIntroduce la coordenada X del pozo: ');
			leer(x);
			escribir_cadena('\nIntroduce la coordenada Y del pozo: ');
			leer(y);
			borrar;
			lugar(10,10);
			setPozo(x,y);
		si_no

			si (opcion==3) entonces
				escribir_cadena('\nIntroduce la coordenada X de la flecha: ');
				leer(x);
				escribir_cadena('\nIntroduce la coordenada Y de la flecha: ');
				leer(y);
				borrar;
				lugar(10,10);
				setFlecha(x,y);
			si_no

				si (opcion==4) entonces
					escribir_cadena('\nIntroduce la coordenada X de la ambrosia: ');
					leer(x);
					escribir_cadena('\nIntroduce la coordenada Y de la ambrosia: ');
					leer(y);
					borrar;
					lugar(10,10);
					setAmbrosia(x,y);
				si_no
			
					si (opcion==5) entonces
						mostrarTablero();
					fin_si;
				fin_si;
			fin_si;
		fin_si;
	fin_si;

hasta (opcion==0);


fin_configuraJuego

borrar;
lugar(10,10);
escribir_cadena('\nPulsa una tecla para comenzar el juego: ');
leer_cadena(pausa);

#comienza el modo juego
jugar

estadoJuego();

repetir
	sel=-1;
	
	escribir_cadena('\nQue desea hacer:');
	escribir_cadena('\n1. Moverse');
	escribir_cadena('\n2. Coger Flecha');
	escribir_cadena('\n3. Beber ambrosia');
	escribir_cadena('\n4. Disparar flecha');
	escribir_cadena('\n5. Salir');

	escribir_cadena('\n\nIntroduce la opcion: ');
	leer(sel);

	si (sel==1) entonces
		escribir_cadena('\nW-Arriba');
		escribir_cadena('\nS-Abajo');
		escribir_cadena('\nA-Izquierda');
		escribir_cadena('\nD-Derecha');
		escribir_cadena('\n\nDireccion: ');
		leer_cadena(dir);

		si (dir=='W') entonces
			moverJugador('arriba');
		si_no
			si (dir=='S') entonces
				moverJugador('abajo');
			si_no
				si (dir=='A') entonces
					moverJugador('izquierda');
				si_no
					si (dir=='D') entonces
						moverJugador('derecha');
					fin_si;
				fin_si;
			fin_si;
		fin_si;

	fin_si;

	si (sel==2) entonces
		cogerFlecha();
	fin_si;

	si (sel==3) entonces
		beber();
	fin_si;
	
	si (sel==4) entonces
		escribir_cadena('\nW-Adelante');
		escribir_cadena('\nS-Atras');
		escribir_cadena('\nA-Izquierda');
		escribir_cadena('\nD-Derecha');
		escribir_cadena('\n\nDireccion: ');
		leer_cadena(dir);

		escribir_cadena('\nW-Arriba');
		escribir_cadena('\nS-Abajo');
		escribir_cadena('\nA-Izquierda');
		escribir_cadena('\nD-Derecha');
		escribir_cadena('\n\nDireccion: ');
		leer_cadena(dir);

		si (dir=='W') entonces
			disparar('arriba');
		si_no
			si (dir=='S') entonces
				disparar('abajo');
			si_no
				si (dir=='A') entonces
					disparar('izquierda');
				si_no
					si (dir=='D') entonces
						disparar('derecha');
					fin_si;
				fin_si;
			fin_si;
		fin_si;

	fin_si;

hasta (sel==5);


fin_jugar

