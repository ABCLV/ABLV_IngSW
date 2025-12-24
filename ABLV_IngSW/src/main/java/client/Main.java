package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import applicazione.Concorrente;
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