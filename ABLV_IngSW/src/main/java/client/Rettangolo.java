package client;

public class Rettangolo {
	private int base;
	private int altezza;
	public Rettangolo(int b, int a){
	base=b;
	altezza=a;
	}
	public int getBase(){
	return base;
	}
	public int getAltezza(){
	return altezza;
	}
	public int getArea(){
	return altezza+base;
	}

}
