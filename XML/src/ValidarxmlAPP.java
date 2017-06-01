import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ValidarxmlAPP {
	int contadorcerradas = 0;
	int contadorabiertas = 0;
	public static void main(String[] args) {
		Scanner sc;
		SAXBuilder builder = new SAXBuilder();
		File xml = new File("C:/Users/Migue/Desktop/tutoriales.xml");
		boolean atributo = false;
	
		int tipo;
	    if(xml.exists()){
			try {
				Document document = (Document) builder.build(xml);		
				System.out.println("El archivo es valido");
				
			} catch (JDOMException e) {
				System.out.println("El archivo no es valido por los siguientes motivos:");
				try {
					sc = new Scanner(xml);
						while(sc.hasNext()){
							String linea = sc.nextLine(); 
							System.out.println(linea);
								if(linea.equals("﻿")){
									System.out.println("Su documento esta vacio");
								}else{
								tipo = detectarTipo(linea);
									switch(tipo){
									case 1:
										break;
									case 2:
										break;
									case 3:
										break;
									case 4:
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
	}else System.out.println("Su archivo no existe");
	    
	}


	private static int detectarTipo(String linea) {
		int primera = linea.indexOf("<");
		int primeraseg = linea.indexOf("<", primera);
		int primerados = linea.indexOf("</");
		int segunda = linea.indexOf(">");
		int segundaseg = linea.indexOf(">", segunda);
		int segundados = linea.indexOf("/>");
		int tipo =0 ;
		if(primera> 0 && segunda > 0 && segundaseg >0 && primeraseg >0 && segunda<primeraseg && segunda<segundaseg && primeraseg < segundaseg && primera<segunda){
			/*con apertura y cierre diferentes en una misma linea*/
				tipo = 4;		
		}
		else{
			if(primera >0 && segunda >0 && primera<segunda){
				/*Con apertura y cierre*/
				/*De aqui tienen que salir 3 tipos cerrada, abierta abierta y cerrada en la misma*/
				if(primerados > 0){
					/*Etiqueta que cierra*/
					tipo = 2;
				}
				if(segundados>0){
					/*Etiqueta abierta y cerrada*/
					tipo = 3;
				}else{
					/*Etiqueta abierta*/
					tipo = 1;
				}
			}else{
				System.out.println("Etiqueta mal formada");
			}
			
		}
		return tipo;
	}

	
	private static boolean detectarAtributos(String linea) {
		boolean atributo = false;
		
		String subatrib = linea.substring(0, linea.indexOf(">"));
		if(linea.indexOf(" ") > 0){/*Atributos*/
		String[] atributos=	subatrib.split(" ");
		
		
		
		if(atributos.length != 0){
			for(int i =1 ; i<atributos.length;i++){
				int j =atributos[i].indexOf("=");
				int j2 = atributos[i].indexOf("=");
				int v= atributos[i].indexOf('"');
				int x= atributos[i].indexOf('"',v + 1);
				int f= atributos[i].indexOf('"',x+1);
				
				if(j>0 && v >0 && x>0 && f<0){
					
				}else{
					System.out.println("atributo mal formado");
						return false;
				}
				atributo = true;
			}
			
			for(int i=1; i<atributos.length; i++){
				String atrib = atributos[i].substring(0, atributos[i].indexOf("="));
				System.out.println(atrib);
				int rep =atributos[i].indexOf(atrib);
			}
		}
		
		}else{
			atributo = false;
		}
		
		return atributo;
		
		
	}

}
