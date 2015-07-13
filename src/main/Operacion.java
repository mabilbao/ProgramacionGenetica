package main;

import java.util.ArrayList;
import java.util.Random;

import arboles.Arbol;
import arboles.Operador;

public class Operacion {

	public Arbol[] generarArboles(){
		
		Arbol[] arrayArboles = new Arbol[Main.CANTIDAD_DE_ARBOL];
	
		for (int i = 0 ; i < arrayArboles.length ; i++) {
			arrayArboles[i] = generarArbol(0, Main.NIVELES_DE_ARBOL);
		}
		
		return arrayArboles;
	}
	
	public Arbol generarArbol(int numero, int niveles){
		Arbol a = new Arbol();
		return this.generarArbol(a, 0, niveles);
	}
	
	private Arbol generarArbol(Arbol a, int numero, int niveles){
		numero++;
		
		if ( numero < niveles ){
			a.setValor( obtenerOperadorDoble() );
			a.setIzq( this.generarArbol(new Arbol(), numero, niveles ) );
			a.setDer( this.generarArbol(new Arbol(), numero, niveles ) );
			
			if ( a.getIzq().getValor() == null ^ a.getDer().getValor() == null ){
				a.setValor( obtenerOperadorSimple() );
			}
			
			if ( a.getIzq().getValor() == "X" && a.getDer().getValor() == "X" ){
				a.getIzq().setValor( obtenerTerminal( false ) );
			}
			
		}else{
			a.setValor( obtenerTerminal( true ) );
			a.setIzq( null );
			a.setDer( null );
		}
		return a;
	}
	
	private String obtenerOperadorDoble(){
		Random randomGenerator = new Random();
		int numero;
		
		numero = randomGenerator.nextInt(Operador.OPERADORES_DOBLES.length);
		return Operador.OPERADORES_DOBLES[numero];
	}

	private String obtenerOperadorSimple(){
		Random randomGenerator = new Random();
		int numero;
		
		numero = randomGenerator.nextInt(Operador.OPERADORES_SIMPLES.length);
		return Operador.OPERADORES_SIMPLES[numero];
	}
	
	private String obtenerTerminal( boolean conX ){
		Random randomGenerator = new Random();

//		int numero;
//		numero = randomGenerator.nextInt(Operador.TERMINALES.length);
//		if ( Operador.TERMINALES[numero] == "=" ){
//			return String.valueOf(randomGenerator.nextDouble());
//		}else{
//			return Operador.TERMINALES[numero];
//		}
		
		Double numero = randomGenerator.nextDouble();
		if ( conX ) {
			if ( numero < 0.21 ){
				return Operador.X;
			}else if ( numero > 0.8 ){
				return Operador.NUL;
			}else{
				return String.valueOf(randomGenerator.nextDouble());
			}
		}else{
			if ( numero > 0.7 ){
				return Operador.NUL;
			}else{
				return String.valueOf(randomGenerator.nextDouble());
			}
		}
	}

	public void ordenarResultados( Arbol[] arrayArboles ){
		for (int j = 0 ; j < arrayArboles.length ; j++) { 
			for (int k = 0 ; k < arrayArboles.length-1 ; k++) { 
				if( arrayArboles[k].getPearson() > arrayArboles[k+1].getPearson() ){ 
					Arbol mayor = arrayArboles[k]; 
					arrayArboles[k] = arrayArboles[k+1]; 
					arrayArboles[k+1] = mayor; 
				}
			}
		}
	}
	
	public void mutarResultados( Arbol[] arrayArboles ) throws CloneNotSupportedException{
		int i = 0;
		for (int j = 0 ; j < Main.arrayArboles.length ; j++) {
			if ( i <= Main.CANTIDAD_A_MUTAR ){
				i++;
				Arbol b = (arrayArboles[(arrayArboles.length - 1) - i]).clone();
				arrayArboles[j] = null;
				mutar( b, (Main.NIVELES_DE_ARBOL-1) );
				b.setPearson((double) 0);
				arrayArboles[j] = b;
			}
		}
	}
	
	public Arbol mutar( Arbol a, int niveles ){
		Random randomGenerator = new Random();
		Double random = randomGenerator.nextDouble();
		Arbol b = generarArbol(0, niveles);
		
//		System.out.println("Mutacion: " + b.toString());
		if ( random < 0.5 ){
			a.setIzq( b );
		}else{
			a.setDer( b );
		}
	
		return a;
	}
	
    public double getPearsonCorrelation(Double[] scoresX, Double[] scores2, Arbol funcion){
    	double media1 = 0;
    	double media2 = 0;
    	double desviacion1 = 0;
    	double desviacion2 = 0;
    	double covarianza = 0;
    	double resultado = 0;
    	int cantidad = scoresX.length;
    	
    	Double[] scores1 = new Double[scoresX.length];
        ArrayList<Double> arrayScore = new ArrayList<Double>();
        
    	for ( int i=0 ; i<cantidad ; i++ ){
    		double tendencia = resolver(scoresX[i], funcion);
    		arrayScore.add(tendencia);
    		media1 += tendencia;
    		media2 += scores2[i];
    	}
    	
        arrayScore.toArray(scores1);
        media1 = media1/cantidad;
        media2 = media2/cantidad;
        
        for ( int i=0 ; i<cantidad ; i++ ){
        	covarianza += (scores1[i]-media1) * (scores2[i]-media2);
        	desviacion1 += Math.pow((scores1[i]-media1),2);
        	desviacion2 += Math.pow((scores2[i]-media2),2);
        }
        
        covarianza = covarianza/(cantidad-1);
        desviacion1 = Math.sqrt(desviacion1/(cantidad-1));
        desviacion2 = Math.sqrt(desviacion2/(cantidad-1));
        
//      System.out.println(media1);
//    	System.out.println(media2);	
//    	System.out.println(covarianza);
//    	System.out.println(desviacion1);
//    	System.out.println(desviacion2);	
        
        resultado = covarianza/(desviacion1*desviacion2);
        
        return Math.pow(resultado, 2);
    }
    
    public double resolver( Double x, Arbol funcion){
    	
    	Double a, b;
    	
    	if ( funcion.getIzq().isTerminal() && funcion.getDer().isTerminal() ){
    		
    		if (funcion.getIzq().getValor() == "X"){
    			a = x;
    		}else{    			
    			a = funcion.getIzq().getValor() == null ? null : Double.valueOf(funcion.getIzq().getValor()) ;
    		}
    		
    		if (funcion.getDer().getValor() == "X"){
    			b = x;
    		}else{
    			b = funcion.getDer().getValor() == null ? null : Double.valueOf(funcion.getDer().getValor());
    		}
    		
    		return calcular( a, b, funcion.getValor());
    	
    	} else if ( funcion.getIzq().isTerminal()){
    		
    		if (funcion.getIzq().getValor() == "X"){
    			a = x;
    		}else{
    			a = funcion.getIzq().getValor() == null ? null : Double.valueOf(funcion.getIzq().getValor());
    		}
    		
    		return calcular( a, 
				resolver( x, funcion.getDer()), 
				funcion.getValor());
    	
    	} else if ( funcion.getDer().isTerminal()){
    		
    		if (funcion.getDer().getValor() == "X"){
    			b = x;
    		}else{
    			b = funcion.getDer().getValor() == null ? null : Double.valueOf(funcion.getDer().getValor());
    		}
    		
    		return calcular( resolver( x, funcion.getIzq()), 
				b, 
				funcion.getValor());
    	
    	} else {
    		return calcular( resolver( x, funcion.getIzq()),
    				resolver( x, funcion.getDer()), 
    				funcion.getValor());
    	}
    }
        
    public static double calcular (Double a, Double b, String funcion){
    			      
		if (funcion == Operador.SUMA){
			if ( a == null && b == null){
				return 0;
			}else if ( a == null ){
				return (b);
			}else if ( b == null ){
				return (a);
			}else{
				return (a+b);
			}
		}
		else if (funcion == Operador.RESTA){
			if ( a == null && b == null){
				return 0;
			}else if ( a == null ){
				return (b);
			}else if ( b == null ){
				return (a);
			}else{
				return (a-b);
			}
		}
		else if (funcion == Operador.MULTIPLICACION){
			if ( a == null && b == null){
				return 0;
			}else if ( a == null ){
				return (b);
			}else if ( b == null ){
				return (a);
			}else{
				return (a*b);
			}
		}
		else if (funcion == Operador.DIVISION){
			if ( a == null && b == null){
				return 0;
			}else if ( a == null ){
				return (b);
			}else if ( b == null ){
				return (a);
			}else{
				return (a/b);
			}
		}
		else if (funcion == Operador.COSENO){
			if ( a != null ){
				return (Math.cos(a));
			}else if ( b != null ){
				return (Math.cos(b));
			}else{
				return 0;
			}
		}
		else if (funcion == Operador.SENO){
			if ( a != null ){
				return (Math.sin(a));
			}else if ( b != null ){
				return (Math.sin(b));
			}else{
				return 0;
			}
		}
		else if (funcion == Operador.TANGENTE){
			if ( a != null ){
				return (Math.tan(a));
			}else if ( b != null ){
				return (Math.tan(b));
			}else{
				return 0;
			}
		}
		return 0;
    }
}
