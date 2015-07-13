package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import arboles.Arbol;

public class Main {
	
	public static int PASADAS;
	public static int NIVELES_DE_ARBOL;
	public static int CANTIDAD_DE_ARBOL;
	public static int CANTIDAD_A_MUTAR;
	public static String RUTA_ARCHIVO;
	public static String NOMBRE_ARCHIVO;
	public static String RUTA_LOGS;
	
	private static Double[] x;
	private static Double[] y;
	static Arbol[] arrayArboles = new Arbol[1000];
		
	public static void main(String[] args) throws CloneNotSupportedException, FileNotFoundException {
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		
		Writer writer = null;
		
		Operacion operacion = new Operacion();
		
		int mejorPasada = 0;
		Double mejorPearson = (double) 0;
		
		try {
			cargarPropiedades();
			
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream( Main.RUTA_LOGS + "log-" + dateFormat.format(date) +".log"), "utf-8")
			);
			
			writer.write("Bienvenido a Programacion genetica!\r\n");
			
			//Generacion de arboles
			Main.arrayArboles = operacion.generarArboles();
			
			//Lectura de Archivo
			parsearFile();			
			
			//Tests
//			Test.testCalculadora();
//			Test.testPearson();
//			Test.testMutar();
//			System.exit(1);
			
			//Grafico del Archivo
			Grafico.dibujar( "Programacion Genetica", "Regresion - File ingresado.", Main.x, Main.y );
			
			for( int pasadas = 0 ; pasadas < PASADAS ; pasadas++ ){
				
				System.out.println("GENERACION: " + pasadas);
				writer.write("GENERACION: " + pasadas + "\r\n");
				
				//Calcular Pearson para cada Arbol
				for (Arbol a : Main.arrayArboles) {
					double resultado = operacion.getPearsonCorrelation(Main.x, Main.y, a);
					a.setPearson( Double.isNaN(resultado) || Double.isInfinite(resultado) ? 0 : resultado );
				}
				
				// Algoritmo de Ordenamiento por Burbuja
				operacion.ordenarResultados(Main.arrayArboles);
				
				// Muestro los resultados
				for (Arbol a : Main.arrayArboles) {
					writer.write("RESULTADO: " + a.getPearson() + " - CALCULO: " + a.toString() + "\r\n");
				}
				
				// Verifico en que pasada obtuve el mejor Pearson
				if ( mejorPearson < Main.arrayArboles[Main.arrayArboles.length - 1].getPearson() ){
					mejorPearson = Main.arrayArboles[Main.arrayArboles.length - 1].getPearson();
					mejorPasada = pasadas;
					System.out.println("NUEVA FORMULA MAS OPTIMA: " + Main.arrayArboles[Main.arrayArboles.length - 1].toString() +
					"\r\n - PEARSON: " + Main.arrayArboles[Main.arrayArboles.length - 1].getPearson());
				}
				
				// Muto los mejores arboles y refabrico arboles para las poblaciones fallidas
				operacion.mutarResultados(Main.arrayArboles);
				
				writer.write("***********************************************************\r\n");
			}		
			
			String fin = "FIN DEL PROCESO!!\r\nLA FORMULA MAS OPTIMA ES: " + Main.arrayArboles[Main.arrayArboles.length - 1].toString() +
					"\r\nDADO QUE SU CORRELACION DE PEARSON ES: " + Main.arrayArboles[Main.arrayArboles.length - 1].getPearson() +
					"\r\n\r\nDICHA CORRELACION FUE OBTENIDA EN LA PASADA: " + mejorPasada;
			System.out.println(fin);
			writer.write(fin);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		   try {
			   writer.close();
		   } catch (Exception ex) {}
		}
	}

	public static void parsearFile() {
		 
		String csvFile = Main.RUTA_ARCHIVO + Main.NOMBRE_ARCHIVO;
		BufferedReader br = null;
		String linea = "";
		String cvsSplitBy = "\\|";
		ArrayList<String[]> entradas = new ArrayList<String[]>();
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((linea = br.readLine()) != null) {
				String[] funcion = linea.split(cvsSplitBy);
				entradas.add(funcion);
				x.add(Double.valueOf(funcion[0]));
				y.add(Double.valueOf(funcion[1]));
			}
			
			Main.x = new Double[x.size()];
			Main.y = new Double[y.size()];
			x.toArray(Main.x);
			y.toArray(Main.y);
			
//			for (String[] funcion : entradas) {
//				System.out.println("[X= " + funcion[0] + " , Y=" + funcion[1] + "]");
//			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void cargarPropiedades() throws IOException {
		Properties propiedades = new Properties();
	    InputStream entrada = null;
	    
	    try{
	    	entrada = new FileInputStream("../configuracion.properties");
	    }catch( FileNotFoundException e ){
	    	entrada = new FileInputStream("configuracion.properties");
	    }
		propiedades.load(entrada);
		
		Main.PASADAS = Integer.parseInt(propiedades.getProperty("PASADAS"));
		Main.NIVELES_DE_ARBOL = Integer.parseInt(propiedades.getProperty("NIVELES_DE_ARBOL"));
		Main.CANTIDAD_DE_ARBOL = Integer.parseInt(propiedades.getProperty("CANTIDAD_DE_ARBOL"));
		Main.CANTIDAD_A_MUTAR = Integer.parseInt(propiedades.getProperty("CANTIDAD_A_MUTAR"));
		Main.RUTA_ARCHIVO = propiedades.getProperty("RUTA_ARCHIVO");
		Main.NOMBRE_ARCHIVO = propiedades.getProperty("NOMBRE_ARCHIVO");
		Main.RUTA_LOGS = propiedades.getProperty("RUTA_LOGS");
	}
	
	public static Double[] getX() {
		return x;
	}

	public static void setX(Double[] x) {
		Main.x = x;
	}

	public static Double[] getY() {
		return y;
	}

	public static void setY(Double[] y) {
		Main.y = y;
	}

	public Arbol[] getArrayArboles() {
		return arrayArboles;
	}

	public void setArrayArboles(Arbol[] arrayArboles) {
		this.arrayArboles = arrayArboles;
	}
}
