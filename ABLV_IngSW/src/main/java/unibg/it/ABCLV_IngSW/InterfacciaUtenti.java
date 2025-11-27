package unibg.it.ABCLV_IngSW;

import java.util.Scanner;

public class InterfacciaUtenti {

    // Codici ANSI per colori
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        menuPrincipale(input);
        input.close();
    }

    //----------------------------------------------------------------------
    // MENU PRINCIPALE
    //----------------------------------------------------------------------

    public static void menuPrincipale(Scanner input) {
        while (true) {
            System.out.println(BLUE + "\n===== MENU PRINCIPALE =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Login");
            System.out.println(YELLOW + "3)" + RESET + " Ricerche");
            System.out.println(YELLOW + "4)" + RESET + " Statistiche");
            System.out.println(YELLOW + "5)" + RESET + " Ringraziamenti");
            System.out.println(RED + "n" + RESET + " per uscire");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n":
                    System.out.println(RED + "\n🚪 Uscita dal programma..." + RESET);
                    return;

                case "1": menuLogin(input); break;
                case "3": menuRicerche(input); break;
                case "4": System.out.println(GREEN + "ℹ Programma di esempio creato in Java!" + RESET); break;
                case "5": System.out.println(GREEN + " Grazie!" + RESET); break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }

    //----------------------------------------------------------------------
    // MENU LOGIN
    //----------------------------------------------------------------------

    public static void menuLogin(Scanner input) {
        while (true) {
            System.out.println(BLUE + "\n===== MENU LOGIN =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Accesso concorrente");
            System.out.println(YELLOW + "2)" + RESET + " Accesso societa");
            System.out.println(YELLOW + "3)" + RESET + " Accesso amministratore");
            System.out.println(YELLOW + "4)" + RESET + " Accesso arbitro");
            System.out.println(RED + "n" + RESET + " per tornare indietro");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            if (scelta.equals("n")) return;

            switch (scelta) {
                case "1":
                    chiediCredenziali(input);
                    menuConcorrente(input);
                    break;

                case "2":
                    chiediCredenziali(input);
                    menuSocieta(input);
                    break;

                case "3":
                    chiediCredenziali(input);
                    menuAmministratore(input);
                    break;

                case "4":
                    chiediCredenziali(input);
                    menuArbitro(input);
                    break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }

    private static void chiediCredenziali(Scanner input) {
        System.out.print("username: ");
        input.nextLine();
        System.out.print("password: ");
        input.nextLine();
    }

    //----------------------------------------------------------------------
    // MENU RICERCHE
    //----------------------------------------------------------------------

    public static void menuRicerche(Scanner input) {
        while (true) {
            System.out.println(BLUE + "\n===== MENU RICERCHE =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Carriera concorrente");
            System.out.println(YELLOW + "2)" + RESET + " Visione gara");
            System.out.println(YELLOW + "3)" + RESET + " Cerca campo gara");
            System.out.println(RED + "n" + RESET + " per tornare indietro");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n": return;

                case "1":
                    System.out.println("inserisci dati utente:");
                    break;

                case "2":
                    menuGare(input);
                    break;

                case "3":
                    System.out.println("inserisci dati spot:");
                    break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }

    //----------------------------------------------------------------------
    // MENU CONCORRENTE
    //----------------------------------------------------------------------

    public static void menuConcorrente(Scanner input) {
        while (true) {
            System.out.println(BLUE + "\n===== MENU CONCORRENTE =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Ricerche");
            System.out.println(YELLOW + "2)" + RESET + " Iscriviti");
            System.out.println(YELLOW + "3)" + RESET + " Visualizza proprie iscrizioni");
            System.out.println(YELLOW + "4)" + RESET + " profilo");
            System.out.println(YELLOW + "5)" + RESET + " Recensione gara e arbitri");
            System.out.println(RED + "n" + RESET + " per tornare indietro");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n": return;

                case "1":
                    menuRicerche(input);
                    break;

                case "2":
                    menuGare(input);
                    break;
                case "3":
                    
                    break;
                case "4":
                    
                    break;
                case "5":
                    System.out.println("Quale gara?");
                    System.out.println("Verifica partecipazione...");
                    break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }


    //----------------------------------------------------------------------
    // MENU GARE
    //----------------------------------------------------------------------

    public static void menuGare(Scanner input) {
        while (true) {
            System.out.println(BLUE + "\n===== CERCA GARA =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " codice");
            System.out.println(YELLOW + "2)" + RESET + " campo gara");
            System.out.println(YELLOW + "3)" + RESET + " tecnica");
            System.out.println(YELLOW + "4)" + RESET + " codice (duplicato?)");
            System.out.println(YELLOW + "5)" + RESET + " stato gara");
            System.out.println(YELLOW + "6)" + RESET + " campionati");
            System.out.println(RED + "n" + RESET + " per tornare indietro");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            if (scelta.equals("n")) return;
        }
    }

    //----------------------------------------------------------------------
    // MENU SOCIETA
    //----------------------------------------------------------------------

    public static void menuSocieta(Scanner input) {
        while (true) {
            System.out.println(BLUE + "\n===== MENU SOCIETA =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Regole definizione gare");
            System.out.println(YELLOW + "2)" + RESET + " Ricerche");
            System.out.println(YELLOW + "3)" + RESET + " Gestione soci");
            System.out.println(YELLOW + "4)" + RESET + " Iscrizione socio a gara singola");
            System.out.println(YELLOW + "5)" + RESET + " Iscrizione gruppo di soci");
            System.out.println(YELLOW + "6)" + RESET + " Nuova proposta gara");
            System.out.println(RED + "n" + RESET + " per tornare indietro");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n": return;

                case "1":
                    System.out.println("regolamento...");
                    break;

                case "2":
                    menuRicerche(input);
                    break;

                case "4":
                    System.out.println("inserisci dati socio da iscrivere");
                    System.out.println("indicare gara");
                    break;

                case "5":
                    System.out.println("selezionare gruppo di soci");
                    System.out.println("indicare gara");
                    break;

                case "6":
                    System.out.println("info gara...");
                    System.out.println("in attesa conferma amministratore");
                    break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }

    //----------------------------------------------------------------------
    // MENU ARBITRO
    //----------------------------------------------------------------------

    public static void menuArbitro(Scanner input) {
        while (true) {

            System.out.println(BLUE + "\n===== MENU ARBITRO =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Accesso gara");
            System.out.println(YELLOW + "2)" + RESET + " Cerca gara da arbitrare");
            System.out.println(YELLOW + "3)" + RESET + " Ricerche");
            System.out.println(RED + "n" + RESET + " per tornare indietro");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n": return;

                case "1":
                    System.out.print("verifica codice gara: ");
                    input.nextLine();
                    arbitraGara(input);
                    break;

                case "2":
                    menuGare(input);
                    break;

                case "3":
                    menuRicerche(input);
                    break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }

    //----------------------------------------------------------------------
    // MENU AMMINISTRATORE
    //----------------------------------------------------------------------

    public static void menuAmministratore(Scanner input) {
        while (true) {

            System.out.println(BLUE + "\n===== MENU AMMINISTRATORE =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Regole definizione gare");
            System.out.println(YELLOW + "2)" + RESET + " Ricerche");
            System.out.println(YELLOW + "3)" + RESET + " Proposte gare");
            System.out.println(YELLOW + "4)" + RESET + " Nuova proposta");
            System.out.println(RED + "n" + RESET + " per tornare indietro");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n": return;

                case "1":
                    System.out.println("regolamento...");
                    break;

                case "2":
                    menuRicerche(input);
                    break;

                case "3":
                    System.out.println("visualizza proposte...");
                    break;

                case "4":
                    System.out.println("info proposta...");
                    System.out.println("in attesa che un altro amministratore la confermi");
                    break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }

    //----------------------------------------------------------------------
    // ARBITRA GARA
    //----------------------------------------------------------------------

    public static void arbitraGara(Scanner input) {
        while (true) {

            System.out.println(BLUE + "\n===== GESTIONE GARA =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Modalità gara");
            System.out.println(YELLOW + "2)" + RESET + " Rinvio gara");
            System.out.println(YELLOW + "3)" + RESET + " Rinuncia gara/ richiesta sostituto");
            System.out.println(RED + "n" + RESET + " salva/esci");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n":
                    System.out.println("salvataggio...");
                    return;

                case "1":
                    gestioneGara(input);
                    break;

                case "2":
                    System.out.println("nuova data:");
                    System.out.println("motivo:");
                    break;

                case "3":
                    System.out.println("motivo:");
                    break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }

    //----------------------------------------------------------------------
    // MODALITÀ GARA
    //----------------------------------------------------------------------

    public static void gestioneGara(Scanner input) {
    	
    	System.out.println("presenze concorrenti...");
        System.out.println("creazione gruppi...");
    	
        while (true) {

            

            System.out.println(BLUE + "\n===== GESTIONE LIVE =====" + RESET);
            System.out.println(YELLOW + "1)" + RESET + " Punteggi live");
            System.out.println(YELLOW + "2)" + RESET + " Salva punti (fine gara)");
            System.out.println(YELLOW + "3)" + RESET + " Segna penalità");
            System.out.println(YELLOW + "4)" + RESET + " Annulla gara concorrente");
            System.out.println(RED + "n" + RESET + " salva/esci");
            System.out.print(CYAN + "\n👉 Inserisci un comando: " + RESET);

            String scelta = input.nextLine().trim().toLowerCase();

            switch (scelta) {
                case "n":
                    System.out.println("salvataggio punteggi...");
                    return;

                case "1":
                    while (true) {
                        System.out.print("cattura del concorrente (n per uscire): ");
                        String s = input.nextLine().trim().toLowerCase();
                        if (s.equals("n")) break;
                    }
                    break;

                case "2":
                    System.out.println("salvataggio...");
                    break;

                case "3":
                    System.out.println("concorrente:");
                    System.out.println("penalità:");
                    System.out.println("motivo:");
                    break;

                case "4":
                    System.out.println("concorrente:");
                    System.out.println("motivo:");
                    break;

                default:
                    System.out.println(RED + "❌ Comando non riconosciuto!" + RESET);
            }
        }
    }
}
