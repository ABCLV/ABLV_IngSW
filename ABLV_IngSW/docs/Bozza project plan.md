# **Project Plan**

*Gruppo: ABLV*  
 *Progetto: Gare di pesca nella bergamasca*  
 *Versione: 1.0 – Data: **28/11/2025***  
\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **1\. Introduzione**

### **1.1 Scopo del documento**

Questo documento rappresenta il Project Plan del gruppo ABLV per il progetto “Gare di pesca della bergamasca”.  
In particolare verrà descritto come e cosa verrà sviluppato dal nostro team ed è utile per chi volesse farsi una prima idea di come vengono gestiti i vari compiti nella nostra organizzazione.  
Nota per il team: Tutti devono leggere e comprendere questo Project Plan, chiunque avesse domande o non avesse capito bene qualcosa apra pure una discussion su github dove si provvederà a spiegare al meglio i concetti.

### **1.2 Scopo del progetto**

Il software che vogliamo sviluppare tornerà utile a chiunque sia interessato alla scena della pesca bergamasca, dagli appassionati che vogliono avere tutte le statistiche a chi vuole solo dare un’occhiata per curiosità.  
In particolare però si concentra sulla gestione a livello organizzativo delle gare/campionati da parte di concorrenti, società e amministratori delle prove puntando a raggruppare tutte le informazioni utili in un unico posto in modo efficiente e ordinato.

### **1.3 Contesto e descrizione generale**

Il progetto consiste nello sviluppo di un sistema software per la gestione completa delle gare agonistiche di pesca nella provincia di Bergamo.  
 Il sistema deve supportare sia gare singole sia campionati composti da più prove, gestendo l’intero ciclo di vita della competizione: creazione dell’evento, gestione dei concorrenti e delle società, organizzazione dei turni e dei gruppi, raccolta dei risultati e generazione delle classifiche finali.  
L’applicazione prevede funzionalità differenziate in base al ruolo dell’utente (concorrente, società, arbitro, amministratore) e permette di effettuare operazioni come iscrizioni alle gare, assegnazione dei gruppi, registrazione delle catture, calcolo delle penalità, rinvio di una gara, pubblicazione delle classifiche e consultazione dello storico delle edizioni passate.  
Il progetto sarà sviluppato come applicazione desktop utilizzando Java per la logica applicativa e un framework Model-View-Controller per la gestione del flusso; la persistenza dei dati invece sarà gestita tramite database relazionale (es. MySQL o PostgreSQL).  
 Il sistema dovrà garantire sicurezza dei dati, autenticazione differenziata per ruolo e una interfaccia chiara e accessibile.  
Tra i principali vincoli si includono:

* rispetto delle regole specifiche del dominio (penalità, gruppi, turni, limiti max/min iscritti);  
* gestione dei rinvii delle gare in base a criteri ambientali o organizzativi;  
* obbligo di mantenere uno storico consultabile delle gare e dei concorrenti;  
* necessità di operare in un ambiente multi–utente con ruoli diversi e privilegi distinti.


### 

### **1.4 Stakeholder, team e responsabilità**

* **Product Owner:** Locatelli Celeste  
* **Scrum Master:** Vitali Michele  
* **Sviluppatori:** Locatelli Celeste, Vitali Michele, Algeri Nicola, Bertoli Marco  
* **UML Designer:** Bertoli Marco  
* **Repository Admin:** Vitali Michele, Algeri Nicola

### **1.5 Glossario e riferimenti**

Per il glossario e i riferimenti guardare il Piano della Gestione della Configurazione su github.  
\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **2\. Modello di processo**

Come modello di processo per lo sviluppo è stato scelto di usare una metodologia Agile (in particolare SCRUM) integrando anche Kanban Board direttamente su github.  
Le scelte prese sono le seguenti:

* Decisione di usare SCRUM in quanto basandoci su quello studiato a lezione ci sembrava il più appropriato e il migliore dal punto di vista della gestione del team e del codice.  
* La durata degli sprint è di 1 settimana, alla fine della quale avverrà una release del software.  
  E’ stato scelto così perchè dato il tempo limitato di sviluppo ci sembrava più ragionevole produrre più spesso codice a piccoli passi e rilasci (così da non rendere l’esperienza utente troppo difficile in una volta sola)  
* Ogni settimana, idealmente al mercoledì, avverrà un weekly scrum, questo perchè tra vari impegni e difficoltà nell’incontrarsi giornalmente ci sembrava ragionevole estendere il periodo analizzato da giornaliero a settimanale.  
  Di conseguenza visto che lo sprint backlog sarebbe un documento molto simile al weekly scrum per ora si è deciso di farli coincidere in un unico file.  
* La revisione del codice avverrà ogni volta che qualcuno voglia integrare le sue modifiche nel codice sorgente, in particolare ogni volta serviranno che altri 2 membri del team approvino i cambiamenti “in sospeso”.  
  Successivamente ogni volta che il codice viene approvato un workflow automatizzato su github controllerà che il codice rispetti le convenzioni Java ed eventualmente indicherà i punti dove esse non vengono seguite, così da semplificare, migliorare e velocizzare eventuali modifiche al codice.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **3\. Organizzazione del progetto**

### **3.1 Struttura del team**

E’ stato deciso, in base ai ruoli ovviamente, che ogni partecipante farà:

* **Product Owne**r: sceglierà e controllerà sia a livello di codice che concettualmente che vengano implementate le funzionalità richieste e in modo corretto indicando eventuali migliorie alle stesse (viste ovviamente dal lato user).  
* **Scrum Master**: verificherà che nel team i compiti vengano svolti in modo organizzato e conforme al Piano della Gestione della Configurazione. Inoltre si impegnerà ogni settimana a redigere il weekly scrum e quindi gestire, assegnare e ottenere feedback sugli obiettivi e compiti del team.


### **3.2 Comunicazione interna**

E’ stato deciso in presenza di tutto il team che i canali e metodi di comunicazione saranno i seguenti:

* 1 volta a settimana (idealmente il mercoledì) ci sarà una riunione per discutere della settimana passata (utile soprattutto ad indicare eventuali difficoltà e migliorie nell’organizzazione/flusso di lavoro) e per compilare in presenza di tutti il weekly scrum e quindi, dato che è stato scelto di far coincidere i due documenti in uno, anche lo sprint backlog.  
* Come canali di comunicazione invece sono stati scelti principalmente il gruppo whatsapp del gruppo anche se è fortemente consigliato preferire le *discussions* su github in quanto più facili da reperire ed è più difficile perdere eventuali messaggi importanti.  
* Le decisioni che verranno prese durante la settimana da chiunque verranno indicate tutte nel weekly scrum della stessa.

### **3.3 Relazioni esterne**

Al momento non è stata programmata nessuna relazione con terzi, solamente la condivisione per revisione del progetto con il professor Angelo Gargantini.  
\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **4\. Standard, linee guida e procedure**

Per il nostro progetto è stato deciso di seguire i seguenti standard e linee guida:

* **Convenzioni**: sia per la parte di implementazione che documentazione è stato deciso di seguire le convenzioni java comunemente usate da tutti i programmatori (reperibili qui [https://www.oracle.com/docs/tech/java/codeconventions.pdf](https://www.oracle.com/docs/tech/java/codeconventions.pdf)), mentre per la parte di progettazione UML sono state usate le convenzioni e sintassi studiate a lezione.  
* **Regole di utilizzo Github**: vedi il Piano della Gestione della Configurazione.  
* **Formati dei documenti**: per semplicità nel modificare i documenti dopo il loro caricamento è stato deciso nel weekly scrum \#2 che si useranno solo i file con estensione .md (preferiti) o .org in quanto possono essere facilmente modificati direttamente su github online.  
* **Funzionalità**: per richiedere l’implementazione di nuove funzionalità o correzioni alle stesse si deve aprire un issue con un nome descrittivo di ciò che si vuole/non va bene.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **5\. Gestione degli obiettivi e priorità**

Il nostro team si pone come obiettivi principali quello di offrire ai nostri utenti un esperienza semplice e veloce adatta a tutti; in particolare ci impegniamo ad offrire un prodotto di ottima qualità, affidabilità e correttezza nell’offrire le informazioni richieste.  
Per seguire ciò abbiamo stilato una lista MOSCOW dei requisiti con le loro priorità consultabile a questo link: *link\_al\_file\_moscow*.  
Mentre per accettare funzionalità è stato deciso che prima di tutto deve essere proposta e discussa al weekly scrum e poi, solo dopo un’approvazione da parte del team, si deve aprire l’issue come spiegato al punto 5; nel corso della sua implementazione o al termine si esegue una PR su github e almeno 2 persone del team devono approvare il codice scritto (si raccomanda ai componenti del team di controllare in modo rigoroso le implementazioni e non approvare “ad occhi chiusi”).

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **6\. Analisi dei rischi**

Da definire bene…

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **7\. Personale**

| Componente | Ruolo | Competenze | Disponibilità | Durata coinvolgimento |
| :---: | :---: | :---: | :---: | :---: |
| Locatelli Celeste | Product Owner, Dummy Client, Sviluppatore |  | Giornaliera | Tutta la durata del progetto |
| Vitali Michele | Scrum Master, Sviluppatore, Repository Admin | Organizzazione del team, sviluppo Java | Giornaliera | Tutta la durata del progetto |
| Algeri Nicola | Sviluppatore, Repository Admin |  | Giornaliera | Tutta la durata del progetto |
| Bertoli Marco | Sviluppatore, UML Designer |  | Giornaliera | Tutta la durata del progetto |

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **8\. Metodi e tecniche**

### **8.1 Ingegneria dei requisiti** 

Per consultare le user stories, use cases e i vari diagrammi UML controllare la cartella apposita nella repository (*link\_alla\_cartella\_docs*).

### **8.2 Progettazione**

E’ stato scelto di creare tutti i diagrammi UML visti a lezione, ecco qui la lista: 

* Use Case Diagram  
* Class Diagram  
* State Machine Diagram, sul ciclo di vita di una gara di pesca  
* Sequence Diagram, sullo scambio di messaggi tra componenti per l’iscrizione ad una gara  
* Communication Diagram  
* Timing Diagram  
* Activity Diagram  
* Componenti Diagram  
* Package Diagram


Mentre sul lato dell’implementazione i design patterns utilizzati sono i seguenti:  
*lista dei pattern utilizzati*

### **8.3 Implementazione**

Per l’implementazione del nostro codice è stato usato unicamente il linguaggio Java.

### **8.4 Test**

Per la fase di testing verranno create delle classi apposite per eseguire Unit Testing (sfruttando Junit) così da individuare al più presto eventuali errori nella codifica delle funzionalità e correggerli al più presto.  
Inoltre per controllare la struttura del nostro codice come per esempio dipendenze, astrazione, duplicazione, … verranno usati dei tool visti alle lezioni di tutorato che sono:

* **Stan4J**: tool utile per ottenere informazioni su dipendenze tra i vari moduli/package, eventuali tangle nel nostro progetto, pollution, astrazione e instabilità, coesione, accoppiamento e complessità del nostro progetto.  
* **PMD**: utile per individuare eventuali copia/incolla nel codice che potrebbero portare ad errori.  
* **SonarQube**: utile per individuare duplicazioni nel codice che possono venire astratte o comunque modificate per migliorare la manutenibilità del nostro progetto.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **9\. Garanzia di qualità (Quality Assurance)**

Per assicurare ai nostri utenti una garanzia di qualità ottimale del nostro progetto si seguiranno queste regole:

* **Code review**: per approvare modifiche/implementazione di medio/alto impatto è obbligatorio eseguire PR che per essere integrate nel codice principale devono venire approvate da almeno 2 persone nel team (così come specificato prima, si ricorda al personale di controllare veramente il codice e non approvare modifiche senza guardarle).  
* **Analisi statica**: come specificato al punto precedente verranno usati dei tool per l'analisi statica del codice (Stan4J, PMD e SonarQube).  
* **Verifica dei requisiti**: ogni settimana il Product Owner insieme allo Scrum Master  
  durante il weekly scrum discutono se il team sta rispettando i requisiti prestabiliti e programma i prossimi passi (a livello organizzativo, poi verrà ovviamente discusso tutto con il team).  
* **Rispetto degli standard**: ogni cosa che viene fatta deve seguire le linee guida e standard indicati al punto apposito ed inoltre per il codice è stato creato un workflow automatico di Github per verificare il rispetto delle convenzioni java che indica se ci sono punti in cui non sono state seguite.  
* **Metrica della qualità**: come metrica per controllare la qualità del nostro progetto verranno ovviamente usate tutte quelle fornite dai tool di Analisi Statica precedentemente citati *(ed in più quelle studiate a lezione? ce ne sono altre?*).

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **10\. Pacchetti di lavoro (Work Breakdown Structure –** 

## **WBS)**

Il nostro progetto si divide in questi macro-pacchetti di lavoro:

* **Analisi dei requisiti**: svolta in prima parte dal Product Owner che compila un file dove annota tutti i requisiti necessari e cosa dovrà fare la nostra applicazione.  
* **Progettazione UML**: inizialmente svolta da un po’ tutto il team (così da velocizzare l’avanzamento) causa assenza di altri compiti significativi, ma che poi verrà portata avanti principalmente dal nostro UML Designer con l’eventuale aiuto di altri qualora abbiano terminato i propri compiti o ritengano opportuno (previo accordo con il team) finire prima un altro compito.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **11\. Risorse**

### **11.1 Hardware**

(Pc, prestazioni minime, server, eventuali dispositivi.)  
*Non so cosa scrivere??? Che risorse HW abbiamo ahaha*

### **11.2 Software**

Le risorse software principali del nostro team sono:

* **Eclipse**: IDE utilizzato per lo sviluppo di tutto il nostro progetto.  
* **Papyrus**: tool integrato in eclipse per lo sviluppo di alcuni diagrammi UML.  
* **PlantUML**: altro tool per lo sviluppo di alcuni diagrammi UML (preferito in corso d’opera a Papyrus in quanto quest’ultimo molte volte da’ problemi e si blocca).  
* **Markdown**: formato di file (preferito in corso d’opera ai pdf) per la documentazione e stesura di documenti in linguaggio naturale.  
* **Tool di Analisi Statica**: come Stan4J, PMD e SonarQube (vedi punto apposito).  
* **Librerie**: *useremo delle librerie strane immagino (soprattutto per la parte grafica…*)  
* **Testing**: useremo principalmente JUnit.  
* **Strumenti di build e package managing**: useremo Maven (infatti il nostro progetto sarà un progetto Maven in eclipse).

### **11.3 Strumenti di comunicazione**

Gli strumenti e canali di comunicazione preferiti per discutere e proporre sono:

* **Whatsapp**: gruppo principale dove si discutono problemi “informali” del progetto.  
* **Github discussions**: canale preferito al primo per problemi o proposte riguardanti direttamente il progetto e la sua documentazione/organizzazione/implementazione.  
* **Github Kanban Board**: usata per condividere gli step da completare per l’avanzamento del progetto e l’assegnazione dei singoli agli individui.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## 

## **12\. Budget**

*Dovrei mettere un budget stimato dei mesi/uomo come visto a lezione? stimo anche risorse e licenze ooo?*  
\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **13\. Gestione dei cambiamenti**

Ad ogni cambiamento che sarà necessario seguire per le volontà utente o adeguarsi agli standard e linee guida è stato deciso di seguire il seguente protocollo:

1. Prima di tutto, se è un cambiamento volontario (quindi non fondamentale ma solo per scelta tecnica), proporlo al weekly scrum. Se invece fosse un cambiamento involontario (ovvero non programmato e neanche controllato ma ottimale per il progetto) farlo notare al weekly scrum.  
2. In entrambi i casi citati al punto precedente è poi fondamentale discuterne con il team al primo weekly scrum disponibile così da effettuare eventuali cambi di rotta nello sviluppo il prima possibile senza rischiare di poi dover cambiare gran parte del lavoro già svolto.   
   In particolare si deve consultare:   
* **Rischi**, che possono derivare dall’adozione del cambiamento.  
* **Modifiche**, che si dovrebbero apportare al progetto per mettersi in linea con il cambiamento.  
* **Alternative**, che si possono adottare invece del cambiamento in discussione (SEMPRE utili, così da avere una visione totale delle possibiità di avanzamento del progetto).  
3. Dopo aver discusso con il team il Product Owner e lo Scrum Master danno un’ultima occhiata alla proposta di cambiamento così da delucidare ancora eventuali concetti non chiari o ambigui.  
   Se il consulto avrà esito positivo si passa al prossimo punto del protocollo, se invece avrà esito negativo o non chiaro, allora si può tornare al punto precedente di stesura del cambiamento o rifiutare la proposta.  
4. Se il cambiamento viene accettato lo Scrum Master deve modificare il backlog, correggere gli issue nella repository Github e ricordarsi di annotare le decisioni prese nel weekly scrum.  
5. Come ultimo passaggio poi il team si deve impegnare a rivedere tutti i documenti e/o implementazioni ritenute “toccate” dal cambiamento.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **14\. Consegna**

La consegna del progetto finale avverrà in data dell’esposizione del progetto all’esame orale tramite, oltre ovviamente alla repository Github, una presentazione PowerPoint del progetto, una panoramica dei dati dei vari tool di analisi statica e una simulazione della nostra applicazione.  
Inoltre ci impegniamo ogni settimana a rilasciare una nuova versione del progetto in cui le modifiche sostanziali potrebbero essere solo di codice, solo di documentazione (si prevede molto raro una volta iniziata l’implementazione, ma per ora possibile con le versioni 0.X) oppure entrambi.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_