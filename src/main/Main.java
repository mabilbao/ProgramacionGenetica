package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import arboles.Arbol;

public class Main {
	
	public static int PASADAS = 2000;
	public static int NIVELES_DE_ARBOL = 3;
	public static int CANTIDAD_DE_ARBOL = 100;
	public static int CANTIDAD_A_MUTAR = 25;
	public static String ARCHIVO = "dolar-venta.csv";
	
	private static Double[] x;
	private static Double[] y;
	static Arbol[] arrayArboles = new Arbol[1000];
		
	public static void main(String[] args) throws CloneNotSupportedException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		
		Writer writer = null;
		
		Operacion operacion = new Operacion();
		
		int mejorPasada = 0;
		Double mejorPearson = (double) 0;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("logs/log-" + dateFormat.format(date) +".log"), "utf-8")
			);
			
			writer.write("Bienvenido a Programacion genetica!\r\n");
			
			//Generacion de arboles
			Main.arrayArboles = operacion.generarArboles();
			
			//Lectura de Archivo
			parsearFile();			
			
			//Grafico del Archivo
			Grafico.dibujar( "Programacion Genetica", "Regresion - File ingresado.", Main.x, Main.y );
			
			for( int pasadas = 0 ; pasadas < PASADAS ; pasadas++ ){
				System.out.println("PASADA: " + pasadas);
				writer.write("PASADA: " + pasadas + "\r\n");
				
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
		 
		String csvFile = Main.ARCHIVO;
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
			
			/*for (String[] funcion : entradas) {
				System.out.println("[X= " + funcion[0] + " , Y=" + funcion[1] + "]");
			}*/
	 
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

	public static void testPearson(){
		
		System.out.println("***********************************************************");
		System.out.println("***********************************************************");
		System.out.println("***********************************************************");
		
		Operacion operacion = new Operacion();
		
		//(* (/ (0.9568404858476679)(null))(+ (0.8612044761646538)(0.02273167680519772)))
		
		Arbol a = new Arbol();
		Arbol i = new Arbol();
		Arbol d = new Arbol();
		
		a.setValor("*");
		
		i.setValor("/");
		i.setIzq(new Arbol());
		i.getIzq().setValor("0.9568404858476679");
		i.setDer(new Arbol());
		i.getDer().setValor(null);
		
		d.setValor("+");
		d.setIzq(new Arbol());
		d.getIzq().setValor("0.8612044761646538");
		d.setDer(new Arbol());
		d.getDer().setValor("0.02273167680519772");
		
		a.setIzq(i);
		a.setDer(d);
		
		System.out.println( a.toString() );
		System.out.println("Resultado: " + operacion.getPearsonCorrelation(Main.x, Main.y, a));
	}
	
	public static void testMutar() throws CloneNotSupportedException{
		
		Operacion o = new Operacion();
		Arbol a = o.generarArbol(0, Main.NIVELES_DE_ARBOL);
		Arbol b = (a).clone();
		o.mutar( b, Main.NIVELES_DE_ARBOL-1 );
		
		System.out.println("A: " + a.toString());
		System.out.println("B: " + b.toString());
		System.out.println("RESOLUCION B: " + o.getPearsonCorrelation(Main.x, Main.y, b));
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
