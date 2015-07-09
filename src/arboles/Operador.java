package arboles;

public interface Operador {

	public static int CANTIDAD_OPERADORES = 7;
		
	public static String SUMA = "+";
	public static String RESTA = "-";
	public static String MULTIPLICACION = "*";
	public static String DIVISION = "/";
	public static String COSENO = "COS";
	public static String SENO = "SEN";
	public static String TANGENTE = "TG";
	
	public static String X = "X";
	public static String Y = "Y";
	public static String IGUAL = "=";
	public static String NUL = null;
	
	public String[] OPERADORES_DOBLES = {SUMA, RESTA, MULTIPLICACION, DIVISION};
	
	public String[] OPERADORES_SIMPLES = {SUMA, RESTA, MULTIPLICACION, DIVISION, COSENO, SENO, TANGENTE}; 
	
	public String[] TERMINALES = {X, IGUAL, NUL};
	
}
