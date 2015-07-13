package test;

import main.Main;
import main.Operacion;
import arboles.Arbol;

public class Test {

	public static void testCalculadora(){
		
		System.out.println("***********************************************************");
		System.out.println("************************* -TEST- **************************");
		System.out.println("***********************************************************");
		
		Operacion operacion = new Operacion();
		
		//(* (SEN (null)(X))(/ (X)(0.6148129444949525)))
		
		Arbol a = new Arbol();
		Arbol i = new Arbol();
		Arbol d = new Arbol();
		
		a.setValor("*");
		
		i.setValor("SEN");
		i.setIzq(new Arbol());
		i.getIzq().setValor(null);
		i.setDer(new Arbol());
		i.getDer().setValor("X");
		
		d.setValor("/");
		d.setIzq(new Arbol());
		d.getIzq().setValor("X");
		d.setDer(new Arbol());
		d.getDer().setValor("0.6148129444949525");
		
		a.setIzq(i);
		a.setDer(d);
		
		System.out.println( a.toString() );
		System.out.println("Resultado: " + operacion.resolver(0.4, a) );
	}
	
	public static void testPearson(){
		
		System.out.println("***********************************************************");
		System.out.println("************************* -TEST- **************************");
		System.out.println("***********************************************************");
		
		Operacion operacion = new Operacion();
		
		//(* (SEN (null)(X))(/ (X)(0.6148129444949525)))
		
		Arbol a = new Arbol();
		Arbol i = new Arbol();
		Arbol d = new Arbol();
		
		a.setValor("*");
		
		i.setValor("SEN");
		i.setIzq(new Arbol());
		i.getIzq().setValor(null);
		i.setDer(new Arbol());
		i.getDer().setValor("X");
		
		d.setValor("/");
		d.setIzq(new Arbol());
		d.getIzq().setValor("X");
		d.setDer(new Arbol());
		d.getDer().setValor("0.6148129444949525");
		
		a.setIzq(i);
		a.setDer(d);
		
		System.out.println( a.toString() );
		System.out.println("Resultado: " + operacion.getPearsonCorrelation(Main.getX(), Main.getY(), a));
	}
	
	public static void testMutar() throws CloneNotSupportedException{
		
		System.out.println("***********************************************************");
		System.out.println("************************* -TEST- **************************");
		System.out.println("***********************************************************");
		
		Operacion o = new Operacion();
		Arbol a = o.generarArbol(0, Main.NIVELES_DE_ARBOL);
		Arbol b = (a).clone();
		o.mutar( b, Main.NIVELES_DE_ARBOL-1 );
		
		System.out.println("A: " + a.toString());
		System.out.println("B: " + b.toString());
		System.out.println("RESOLUCION B: " + o.getPearsonCorrelation(Main.getX(), Main.getY(), b));
	}

}
