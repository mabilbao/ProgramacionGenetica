package arboles;

public class Arbol implements Cloneable{
	
	private String valor;
	private Arbol izq;
	private Arbol der;
	private Double pearson;

	public Boolean isTerminal(){
		if ( izq == null && der == null){
			return true;
		}else{
			return false;
		}
	}
	
	public String toString(){
		return preorden( this, "");
	}
	
	private String preorden(Arbol a, String textoArbol) {
		textoArbol += "(";
		if ( !a.isTerminal() ) {
			textoArbol = textoArbol + a.getValor() + " ";
			textoArbol = preorden(a.getIzq(), textoArbol);
			textoArbol = preorden(a.getDer(), textoArbol);
		}else{
			textoArbol = textoArbol + a.getValor() + "";
		}
		textoArbol += ")";
		return textoArbol;
	}
	
    @Override
    public Arbol clone() throws CloneNotSupportedException {
        return (Arbol) super.clone();
    }
	
	public String getValor() {
		return valor;
	}
	public void setValor(String value) {
		this.valor = value;
	}
	public Arbol getIzq() {
		return izq;
	}
	public void setIzq(Arbol izq) {
		this.izq = izq;
	}
	public Arbol getDer() {
		return der;
	}
	public void setDer(Arbol der) {
		this.der = der;
	}
	public Double getPearson() {
		return pearson;
	}
	public void setPearson(Double pearson) {
		this.pearson = pearson;
	}
}
