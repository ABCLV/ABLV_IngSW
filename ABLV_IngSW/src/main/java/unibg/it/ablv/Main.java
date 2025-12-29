package unibg.it.ablv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import applicazione.CampoGara;
import applicazione.Concorrente;
import applicazione.Gara;
import applicazione.Settore;
import database.Consultazioni;

public class Main {
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== MENU PRINCIPALE =====");
            System.out.println("1) Login");
            System.out.println("2) Ricerche");
            System.out.println("2) Ringraziamenti");
            System.out.println("n per uscire");
            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n":
                    System.out.println("uscita...");
                    return;

                
                case "2":
                	ricerche();

                default:
                    System.out.println("comando non riconosciuto");
            }
        }
    }
	
	
	public static void ricerche() {
		
		while (true) {
            System.out.println("\n===== MENU RICERCHE =====");
            System.out.println("Cosa ti interessa?");
            System.out.println("1) gare");
            System.out.println("2) concorrenti");
            System.out.println("3) campo gara");
            System.out.println("n per uscire");

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n": return;

                case "1":
                    
                    break;
                case "2":
                	ricercaConcorrenti();
                    break;

                case "3":
                    ricercaSpot();
                    break;

                default:
                	System.out.println("comando non riconosciuto");
            }
        }
	}
	
	public static void ricercaSpot() {
		List<CampoGara> spot = new ArrayList<CampoGara>();
		spot = Consultazioni.getCampoGara();
		
		System.out.println(spot.toString());
		
		
		while (true) {
            System.out.println("\n===== MENU CAMPOGARA =====");
            System.out.println("Cosa ti interessa?");
            System.out.println("1) esplora settori");
            System.out.println("2) vedi gare");
            System.out.println("n per uscire");

            String scelta = input.nextLine().trim().toLowerCase();
            String dato;
            CampoGara c;
            switch (scelta) {
                case "n": return;

                case "1":
                	System.out.println("inserisci id campo gara da esplorare: ");
                	dato = input.nextLine().trim();
                	c = Consultazioni.trovaCampoGara(dato);
                	System.out.println(c.toString());
                	List<Settore> settori = new ArrayList<Settore>();
                	settori = Consultazioni.esploraSettori(c);
                    System.out.println(settori.toString());
                    break;

                case "2":
                	System.out.println("inserisci id campo gara di cui vuoi vedere le gare organizzate: ");
                	dato = input.nextLine().trim();
                	c = Consultazioni.trovaCampoGara(dato);
                	System.out.println(c.toString());
                	List<Gara> gare = new ArrayList<Gara>();
                	gare = Consultazioni.esploraGare(c);
                    System.out.println(gare.toString());
                    break;

                case "3":
                   
                    break;

                default:
                	System.out.println("comando non riconosciuto");
            }
        }
		
		
	}
	
	
	public static void ricercaConcorrenti() {
		List<Concorrente> concorrenti = new ArrayList<Concorrente>();
		concorrenti = Consultazioni.getConcorrenti();
		
		System.out.println(concorrenti.toString());		
		
	}
	
	
	
	
	
	
}