#!/bin/sh
# La linea superior indica al SO el interprete

# Autor: http://www.omeyasweb.com/vocales-acentuadas-en-html/

#Este shell script sustituye los acentos y "eñes" por su equivalente HTML en todos los ficheros .html de un directorio y subdirectorios.

#Forzamos la salida si algun parametro no se ejecuta bien
#de lo contrario se seguiria pese a que hubise algun error
set -e
#Forzamos la salida si alguna variable no es definida
set -u

###########################

# Esta función procesa el directorio actual
procesar_este_directorio()
{
	# Creamos un fichero temporal
	touch ficheroTemporalHtml.html
	
	# Para cada fichero .html sustituimos los acentos y las "eñes" por su equivalente html
	for archivo in *html; do
		cp $archivo ${archivo}.bak
		cat ${archivo}.bak | sed -e 's/á/\&aacute;/g' -e 's/é/\&eacute;/g' -e 's/í/\&iacute;/g' -e 's/ó/\&oacute;/g' -e 's/ú/\&uacute;/g' -e 's/ñ/\&ntilde;/g' -e 's/Á/\&Aacute;/g' -e 's/É/\&Eacute;/g' -e 's/Í/\&Iacute;/g' -e 's/Ó/\&Oacute;/g' -e 's/Ú/\&Uacute;/g' -e 's/Ñ/\&Ntilde;/g' -e 's/©/\&copy;/g' -e 's/¡/\&iexcl;/g' -e 's/§/\&sect;/g' -e 's/ª/\&ordf;/g' -e 's/«/\&laquo;/g' -e 's/®/\&reg;/g' -e 's/±/\&plusmn;/g' -e 's/²/\&sup2;/g' -e 's/³/\&sup3;/g' -e 's/¶/\&para;/g' -e 's/º/\&ordm;/g' -e 's/»/\&raquo;/g' -e 's/¿/\&iquest;/g' -e 's/Æ/\&AElig;/g' -e 's/Ç/\&Ccedil;/g' -e 's/æ/\&aelig;/g' -e 's/ç/\&ccedil;/g' -e 's/Ü/\&Uuml;/g' -e 's/ü/\&uuml;/g' > $archivo
		rm ${archivo}.bak
	done
	
	#Borramos el fichero temporal
	rm ficheroTemporalHtml.html
}

# Esta función busca subdirectorios recursivamente y los procesa
buscar_subdirectorios()
{
	# Para todos los elementos que se encuentren en el directorio actual
	for i in *; do
		# Si es un directorio
		if [ -d $i ]; then
			# Procesamos el subdirectorio y buscamos subsubdirectorios
			echo "Procesamos el directorio $i..."
			cd $i
			procesar_este_directorio
			buscar_subdirectorios
			cd ..

		fi

	done
}

###########################

echo "Procesando el directorio actual $PWD..."

procesar_este_directorio
buscar_subdirectorios

echo "Proceso de colocar acentos terminado."

