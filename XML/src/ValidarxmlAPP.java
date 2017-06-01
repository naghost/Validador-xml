import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ValidarxmlAPP {
	
	public static void main(String[] args) {
		
		/*
		 * Este validador de xml funciona correctamente siempre que se tenga encuenta
		 * que el documento xml va a estar bien formado 
		 * y que en cada linea hay un cojunto de 2 etiquetas o solo 1
		 * */
		Scanner sc;
		SAXBuilder builder = new SAXBuilder();
		//ruta donde esta el documento
		File xml = new File("C:/Users/Migue/Desktop/tutoriales.xml");
		boolean atributos;
		int tipo;
		ArrayList <String> abiertas = new ArrayList<String>();
		ArrayList <String> cerradas = new ArrayList<String>();
		
	    if(xml.exists()){
			try {
				//Intentamos abrir el documento XML
				Document document = (Document) builder.build(xml);		
				System.out.println("El archivo es valido");
				//Si esta bien saltara el mensaje
			} catch (JDOMException e) {
				//en caso de que java no pueda construir el archivo xml significa que el documento tiene errores
				//y por ello procedemos a comprobarlo
				System.out.println("El archivo no es valido por los siguientes motivos:");
				try {
					//Empezamos a leer el documento con scanner
					sc = new Scanner(xml);
					//y lo recorremos con un bucle while usando el metodo .hasNext() para comprobar si tiene mas lineas
						while(sc.hasNext()){
							String linea = sc.nextLine(); 
							System.out.println("\n"+linea);
							//guardamos en una variable la linea y la mostramos
								if(linea.equals("ï»¿")){
									//Estrañamente al abrir el archivo siempre me ha salido ese conjunto de simbolo
									//si solo aparece ese conjunto de simbolos mostraremos el siguiente mensaje:
									System.out.println("Su documento esta vacio");
								}else{
									//usamos un metodo para detectar el tipo de etiqueta que puede ser en este caso son 4 tipos
								tipo = detectarTipo(linea);
									switch(tipo){
									case 1:
										/*Etiqueta que abierta*/
										//En una etiqueta abierta comprobaremos que los atributos estan correctamente puestos
										atributos = detectarAtributos(linea);
										abiertas.add(añadirEtiquetaAbierta(linea,atributos));
										break;
									case 2:
										/*Etiqueta que cierra*/
										//en una etiqueta cerrada no necesitamos comprobar nada
										cerradas.add(añadirEtiquetaCerrada(linea));
										break;
									case 3:
										/*Etiqueta abierta y cerrada*/
										//no necesita mayor comprobacion
										detectarAtributos(linea);
										
										break;
									case 4:
										/*con apertura y cierre diferentes en una misma linea*/
										//comrpobamos contenido, atributos y que sean iguales las etiquetas de apertura y cierra
										atributos =	detectarAtributos(linea);
										validarcontenido(linea);
										comrpobarEtiquetas(linea, atributos);
										break;
									}
								}
							}
				
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//salta este mensaje si el archivo no se encuetra
	}else System.out.println("Su archivo no existe");
	 
	    //con este metodo comprobamos que tenemos la misma cantidad de etiquetas abiertas y cerradas
	    comprobarAbiertasCerradas(abiertas, cerradas);
	    
	    System.out.println("\n Si no encuentra el error en este validador de XML puede que tenga una sintaxis incorrecta"+
	    					"\n Por cada linea debe haber una etiqueta o par de etiquetas"+
	    					"\n ¡Le animo a que lo ajuste y intente pasarlo de nuevo!");
	}


	private static void comprobarAbiertasCerradas(ArrayList<String> abiertas, ArrayList<String> cerradas) {
	
		Collections.sort(abiertas);
		Collections.sort(cerradas);

		if(abiertas.equals(cerradas)){
			System.out.println("\nMisma cantidad de abiertas y cerradas");
		}else{
			System.out.println("\nHay etiquetas no abiertas, no cerradas o no existen directamente\n");
			System.out.println("\netiquetas abiertas: "+abiertas);
			System.out.println(" ");
			System.out.println("etiquetas cerradas: "+cerradas);
		}
	}


	private static String añadirEtiquetaCerrada(String linea) {
		
		int a = linea.indexOf("</")+2;
		int b = linea.indexOf(">");
		String etiqueta;
		
		etiqueta = linea.substring(a,b);
		
		return etiqueta;
	}


	private static String añadirEtiquetaAbierta(String linea, boolean atributos) {
		int e = linea.indexOf(" ");
		int a = linea.indexOf("<")+1;
		int b = linea.indexOf(">");
		int c = linea.indexOf("<",b)+2;
		int d = linea.indexOf(">",b+1);
		String etiqueta;
		if(atributos){
			etiqueta = linea.substring(a,e);
			
		}else{
			etiqueta = linea.substring(a, b);
		
		}
		return etiqueta;
	}


	private static void comrpobarEtiquetas(String linea, boolean atributos) {
		int e = linea.indexOf(" ");
		int a = linea.indexOf("<")+1;
		int b = linea.indexOf(">");
		int c = linea.indexOf("<",b)+2;
		int d = linea.indexOf(">",b+1);
		if(atributos){
		
				String etiqueta2 = linea.substring(c, d);
				String etiqueta3 = linea.substring(a,e);
				
				if(!etiqueta2.equals(etiqueta3)){
					System.out.println("Las etiquetas no coinciden");
				}
				
		}
		else{
			
			String etiqueta1 = linea.substring(a, b);
			String etiqueta2 = linea.substring(c, d);
			
			
		
			if(!etiqueta1.equals(etiqueta2)){
				System.out.println("Las etiquetas no coinciden");
			}
			}
		
	}


	private static void validarcontenido(String linea) {
		/*
		 * En este metodo tenemos que intentar que el contenido de las etiquetas esta bien formado
		 * Para ello tenemos que tener encuenta las siguientes cosas
		 * 1.El contenido no puede empezar ni acabar por un espacio
		 * 2.*/
			int a = linea.indexOf(">")+1;
			int b = linea.indexOf("<", a);
		String substring = linea.substring(a,b);
		int c = substring.length();
		
		if(substring.indexOf(" ")==0 || (substring.lastIndexOf(" ")+1)==c){
			System.out.println("Esta linea acaba o empieza en espacio");
		}
	}


	private static int detectarTipo(String linea) {
		int tipo =0 ;

		int primera = linea.indexOf("<");
		int primeraseg = linea.indexOf("<", primera+1);
		int primerados = linea.indexOf("</");
		int segunda = linea.indexOf(">");
		int segundaseg = linea.indexOf(">", segunda+1);
		int segundados = linea.indexOf("/>");
		
		if(primera> 0 && segunda > 0 && segundaseg >0 && primeraseg >0 && segunda<primeraseg && segunda<segundaseg && primeraseg < segundaseg && primera<segunda){
			/*con apertura y cierre diferentes en una misma linea*/
				tipo = 4;		
		}
		else{
			if(primera >=0 && segunda >0 && primera<segunda){
				/*Con apertura y cierre*/
				/*De aqui tienen que salir 3 tipos cerrada, abierta, abierta y cerrada en la misma*/
				if(primerados >= 0&& segunda>0 && primeraseg<0){
					/*Etiqueta que cierra*/
					tipo = 2;
				}else{
					if(segundados>0 && primera > 0){
						/*Etiqueta abierta y cerrada*/
						tipo = 3;
					}else{
					if(primera>=0 && segunda>0 && primera<segunda && primeraseg<0 && segundaseg<0 ){
						/*Etiqueta abierta*/
						tipo = 1;
						}else System.out.println("Etiqueta mal formada");
					}
					}
			}else{
				System.out.println("Etiqueta mal formada");
			}
			
		}
		return tipo;
	}

	
	private static boolean detectarAtributos(String linea) {
		/*
		 * Reglas:
		 * 1. Los atributos estan en una etiqueta abierta
		 * 2. todo atributo tiene esta sintaxis <nombre>="<valor>"
		 * 3. puede tener varios atributos
		 * 4. los atributos estan separados por espacios
		 * 
		 * Pasos:
		 * 1. hacemos una subcadena con la etiqueta
		 * 2. la separamos por espacios 
		 * 3. Con ello generamos un array
		 * 4. comprobamos que cada atributo tiene una sintaxis correcta
		 * 
		 * */
		String aux = linea.substring(linea.indexOf("<"), linea.indexOf(">")+1);	
		String[] auxAtributosArray = linea.split(" ");
		boolean atributo = false;
		char[] arrayChar = aux.toCharArray();

		char a = '\'';
		int contadorComillas = 0;
		int contadorComillasSimples=0;
		int contadorIguales=0;
		for(int i=0; i<arrayChar.length; i++){

			if( arrayChar[i]=='"' ){
				contadorComillas++;
				
			}else{
				if(arrayChar[i]==a){
					contadorComillasSimples++;
				}else{
					if(arrayChar[i] == '='){
						contadorIguales++;
					}
				}
			}
		}
		/*
		 * Contador de comillas y contador de comillas simples tiene que ser numeros pares
		 * contador de iguales tiene que ser la mitad que la cantidad de comillas simples y comilla ssimples
		 * */
		
		
		int repetidos = 0;
		int contador2 = 0;
		
			if(contadorComillas%2 == 0 && contadorComillasSimples%2 == 0){
				
				if(((contadorComillas+contadorComillasSimples)/2)!=contadorIguales){
					System.out.println("Te faltan simbolos de igual");
				}
					else{
						
						if(contadorComillas > 2 || contadorComillasSimples >2  && contadorIguales > 1){
							
							for(int i = 1; i<auxAtributosArray.length ;i++){
						
									String aux1 = auxAtributosArray[i].substring(0, auxAtributosArray[i].indexOf("="));
						
										for (int o = 2;o<auxAtributosArray.length;o++){

											if(aux1.equals(auxAtributosArray[i].substring(0, auxAtributosArray[o].indexOf("=")))){
														repetidos++;
														if(repetidos > auxAtributosArray.length){
															contador2++;
															System.out.println("Tienes atributos: "+contador2+" repetidos");
															}
								
												}
										}
							}
						}
			}

		/*
		 * El siguiente problema es que los atributos NO se pueden repetir en xml
		 * El problema que tiene es que los atributos puede que no tengan ninguna separacion
		 * */		
			
			}
			if(contadorComillas >0 || contadorComillasSimples >0 && contadorIguales>0) atributo =  true;
			return atributo;
	}

}
