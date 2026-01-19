# **Project Plan**

*Gruppo: ABLV*  
 *Progetto: Gare di pesca nella bergamasca*  
 *Versione: 1.1 – Data: **1/12/2025***  
\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **1\. Introduzione**

### **1.1 Scopo del documento**

Questo documento rappresenta il Project Plan del gruppo ABLV per il progetto “Gare di pesca della bergamasca”.  
In particolare verrà descritto come e cosa verrà sviluppato dal nostro team ed è utile per chi volesse farsi una prima idea di come vengono gestiti i vari compiti nella nostra organizzazione.  
Nota per il team: TUTTI devono leggere e comprendere questo Project Plan, chiunque avesse domande o non avesse capito bene qualcosa apra pure una discussion su github dove si provvederà a spiegare al meglio i concetti.

### **1.2 Scopo del progetto**

Il software che vogliamo sviluppare tornerà utile a chiunque sia interessato alla scena della pesca bergamasca, dagli appassionati che vogliono avere tutte le statistiche a chi vuole solo dare un’occhiata per curiosità.  
In particolare però si concentra sulla gestione a livello organizzativo delle gare/campionati da parte di concorrenti, società e amministratori delle prove puntando a raggruppare tutte le informazioni utili in un unico posto in modo efficiente e ordinato.

### **1.3 Contesto e descrizione generale**

Il progetto consiste nello sviluppo di un sistema software per la gestione completa delle gare agonistiche di pesca nella provincia di Bergamo.  
 Il sistema deve supportare sia gare singole sia campionati composti da più prove, gestendo l’intero ciclo di vita della competizione: creazione dell’evento, gestione dei concorrenti e delle società, organizzazione dei turni e dei gruppi, raccolta dei risultati e generazione delle classifiche finali.  
L’applicazione prevede funzionalità differenziate in base al ruolo dell’utente (concorrente, società, arbitro, amministratore) e permette di effettuare operazioni come iscrizioni alle gare, assegnazione dei gruppi, registrazione delle catture, calcolo delle penalità, rinvio di una gara, pubblicazione delle classifiche e consultazione dello storico delle edizioni passate.  
Il progetto è basato su un'architettura a 3-tier che separa chiaramente le componenti di presentazione, logica di business e accesso ai dati, garantendo una gestione efficiente del flusso delle richieste e delle risposte.  
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

Per il glossario e i riferimenti guardare il [Piano della Gestione della Configurazione](<./Piano Gestione della Configurazione.md>) su github.  
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

E’ stato deciso che:

* **Product Owne**r: sceglierà e controllerà sia a livello di codice che concettualmente che vengano implementate le funzionalità richieste e in modo corretto indicando eventuali migliorie alle stesse (viste ovviamente dal lato user).  
* **Scrum Master**: verificherà che nel team i compiti vengano svolti in modo organizzato e conforme al Piano della Gestione della Configurazione. Inoltre si impegnerà ogni settimana a redigere il weekly scrum e quindi gestire, assegnare e ottenere feedback sugli obiettivi e compiti del team.


### **3.2 Comunicazione interna**

E’ stato deciso in presenza di tutto il team che i canali e metodi di comunicazione saranno i seguenti:

* 1 volta a settimana (idealmente il mercoledì) ci sarà una riunione per discutere della settimana passata (utile soprattutto ad indicare eventuali difficoltà e migliorie nell’organizzazione/flusso di lavoro) e per compilare in presenza di tutti il weekly scrum e quindi, dato che è stato scelto di far coincidere i due documenti in uno, anche lo sprint backlog.  
* Come canali di comunicazione invece sono stati scelti principalmente il gruppo whatsapp del gruppo anche se è fortemente consigliato preferire le [discussions](https://github.com/ABCLV/ABLV_IngSW/discussions) su github in quanto più facili da reperire ed è più difficile perdere eventuali messaggi importanti.  
* Le decisioni che verranno prese durante la settimana da chiunque verranno indicate tutte nel weekly scrum della stessa.

### **3.3 Relazioni esterne**

Al momento non è stata programmata nessuna relazione con terzi, solamente la condivisione per revisione del progetto con il professor Angelo Gargantini e la professoressa Silvia Bonfanti.  
\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **4\. Standard, linee guida e procedure**

Per il nostro progetto è stato deciso di seguire i seguenti standard e linee guida:

* **Convenzioni**: sia per la parte di implementazione che documentazione è stato deciso di seguire le convenzioni java comunemente usate da tutti i programmatori (reperibili qui [convenzioni java](https://www.oracle.com/docs/tech/java/codeconventions.pdf)), mentre per la parte di progettazione UML sono state usate le convenzioni e sintassi studiate a lezione.  
* **Regole di utilizzo Github**: vedi il Piano della Gestione della Configurazione.  
* **Formati dei documenti**: per semplicità nel modificare i documenti dopo il loro caricamento è stato deciso nel [Weekly Scrum #2](./scrumsebacklogs/weekly_scrum_26_novembre.md) che si useranno solo i file con estensione .md (preferiti) o .org in quanto possono essere facilmente modificati direttamente su github online.  
* **Funzionalità**: per richiedere l’implementazione di nuove funzionalità o correzioni alle stesse si deve aprire un issue con un nome descrittivo di ciò che si vuole/non va bene.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **5\. Gestione degli obiettivi e priorità**

Il nostro team si pone come obiettivi principali quello di offrire ai nostri utenti un esperienza semplice e veloce adatta a tutti; in particolare ci impegniamo a fornire un prodotto di ottima qualità, affidabilità e correttezza nel recuperare le informazioni richieste.  
Per seguire ciò abbiamo stilato una classificazione Kano dei requisiti con le loro priorità consultabile [qui](<./Proposta.md>) (in fondo al file).
Mentre per accettare funzionalità è stato deciso che prima di tutto deve essere proposta e discussa al weekly scrum e poi, solo dopo un’approvazione da parte del team, si deve aprire l’issue come spiegato al [punto 4](#4-standard-linee-guida-e-procedure); nel corso della sua implementazione o al termine si esegue una PR su github e almeno 2 persone del team devono approvare il codice scritto (si raccomanda ai componenti del team di controllare in modo rigoroso le implementazioni e non approvare “ad occhi chiusi”).

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **6\. Analisi dei rischi**

Lo scopo di questa sezione è individuare i principali rischi del progetto, valutarne la probabilità e l’impatto e definire le azioni da intraprendere per ridurli o gestirli in modo efficace.

* **Probabilità**: Bassa (B), Media (M), Alta (A)
* **Impatto**: Bassa (B), Media (M), Alta (A)

|                  Rischio                   |                                                                         Descrizione                                                                          | Probabilità | Impatto |                                                                                                      Strategia per diminuire il rischio                                                                                                      |
|:------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------:|:-------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|       Scarsa disponibilità del team        |                    Impegni universitari o personali riducono il tempo dedicabile al progetto, causando ritardi nelle attività pianificate                    |     M       |    A    |                           Pianificare attività realistiche per ogni sprint; usare il weekly scrum per riallineare il carico; ridurre il numero di task paralleli e concentrarsi prima sulle funzionalità Must Have                           |
|      Ritardi nella progettazione UML       |       I diagrammi UML (in particolare quelli più complessi) richiedono più tempo del previsto, rallentando l’avvio o l’evoluzione dell’implementazione       | M |    M    |                  Dare priorità ai diagrammi fondamentali (Use Case, Class, Sequence); coinvolgere altri membri a supporto dell’UML Designer quando hanno terminato i propri task; rivedere i diagrammi a piccoli incrementi                  |
|      Errori nelle regole del dominio       |              Implementazione non corretta delle regole (penalità, gruppi, turni, limiti iscritti, rinvii), con rischio di classifiche sbagliate              | M |    A    |                          Coinvolgere il Product Owner (pescatore/esperto dominio) in fase di analisi e verifica; scrivere test di unità sui casi “critici”; rivedere insieme i casi d’uso relativi alle classifiche                          |
|    Conflitti o uso errato di Git/GitHub    |                           Merge conflict frequenti, commit non chiari o modifiche sul branch sbagliato che rallentano lo sviluppo                            | M |    B    |                   Definire regole chiare di branching (es. `dev` + branch per feature); usare sempre Pull Request con almeno 2 review; discutere nel weekly scrum eventuali problemi ricorrenti di gestione del repository                   |
| Mancato rispetto delle scadenze intermedie | Alcuni sprint non portano a una release funzionante o alcune funzionalità rimangono a metà, accumulando debito tecnico e ritardi vicini alla consegna finale | M |    A    |                                Limitare il numero di funzionalità per sprint e concentrarsi su incrementi piccoli ma completi; il Product Owner e lo Scrum Master adeguano il backlog durante il weekly scrum                                |
|  Problemi hardware / ambiente di sviluppo  |         Malfunzionamenti del PC personale, IDE non configurato correttamente, problemi con librerie o versioni di Java che bloccano uno sviluppatore         | B |    M    |                                     Usare strumenti e versioni il più possibile uniformi nel team; in caso di problemi, affiancare temporaneamente un altro membro per non bloccare un’attività critica                                      |

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **7\. Personale**

| Componente |                      Ruolo                       |                      Competenze                      | Disponibilità | Durata coinvolgimento |
| :---: |:------------------------------------------------:|:----------------------------------------------------:| :---: | :---: |
| Locatelli Celeste |    Product Owner, Dummy Client, Sviluppatore     | Pescatore(quindi esperto nel settore), sviluppo java | Giornaliera | Tutta la durata del progetto |
| Vitali Michele |   Scrum Master, Sviluppatore, Repository Admin   |        Organizzazione del team, sviluppo Java        | Giornaliera | Tutta la durata del progetto |
| Algeri Nicola |          Sviluppatore, Repository Admin          |             Sviluppo Java, Manutenzione github              | Giornaliera | Tutta la durata del progetto |
| Bertoli Marco |           Sviluppatore, UML Designer |       Sviluppo java, plantUML/Papyrus designer       | Giornaliera | Tutta la durata del progetto |

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **8\. Metodi e tecniche**

### **8.1 Ingegneria dei requisiti** 

Per consultare le user stories, use cases e i vari diagrammi UML controllare la [cartella dei diagrammi](./exported_diagrams) nella repository
### **8.2 Progettazione**

**Principi di progettazione adottati**

Il progetto adotta principi di progettazione orientati alla gestione della complessità, come previsto dall’ingegneria del software.
In particolare vengono applicati:

* scomposizione del sistema in componenti (presentazione, dominio applicativo, accesso ai dati); 
* Separation of Concerns, per isolare responsabilità diverse;
* Single Responsibility Principle (SRP), per aumentare coesione e manutenibilità.

Le decisioni architetturali iniziali sono state successivamente raffinate in modo iterativo, in accordo con il processo Agile adottato.

**Coesione e accoppiamento**

La progettazione mira a massimizzare la coesione e minimizzare l’accoppiamento, che sono attributi interni di qualità del software.

* L’alta coesione è ottenuta raggruppando classi con responsabilità omogenee all’interno degli stessi package.
* Il basso accoppiamento è reso possibile dalla limitazione delle dipendenze tra livelli architetturali e impedendo che la GUI introduca dipendenze tecnologiche nel dominio applicativo.

Queste scelte migliorano la manutenibilità e supportano l’evoluzione del sistema nel tempo.


E’ stato scelto di creare tutti i diagrammi UML visti a lezione, ecco qui la lista: 

* Use Case Diagram  
* Class Diagram  
* State Machine Diagram, sul ciclo di vita di una gara di pesca  
* Sequence Diagram, sullo scambio di messaggi tra componenti per l’iscrizione ad una gara  
* Communication Diagram, sulle iscrizione alle gare
* Timing Diagram, sul rinvio/annullamento delle gare 
* Activity Diagram, sulle iscrizione alle gare da parte degli utenti
* Component Diagram  
* Package Diagram


Mentre sul lato dell’implementazione i design patterns utilizzati sono i seguenti:
* Abstraction-Occurrence pattern
* Wrapper / Adapter per JavaFX (FX Wrapper pattern)
* Delegation pattern (se necessario)

**Uso del Wrapper per la GUI**

Per mantenere la coerenza tra modello concettuale e implementazione, la GUI non utilizza classi di dominio modificate o replicate.
La visualizzazione delle gare avviene tramite la classe `GaraFxWrapper`, che contiene al suo interno un’istanza della classe di dominio `Gara` ed espone le proprietà necessarie al binding JavaFX.

Questo approccio consente di preservare l’integrità del dominio applicativo, ridurre l’accoppiamento tra modello e interfaccia grafica e migliorare la manutenibilità del sistema.


### **8.3 Implementazione**

Per l’implementazione del nostro codice è stato usato unicamente il linguaggio Java. Le query sql, come consigliato a lezione, non sono state scritte il SQL puro tramite stringhe bensì tramite il DSL type safe fornito da JOOQ, che permette di costruire le query in modo strutturato e sicuro a livello di compilazione evitando errori sintattici, injection e problemi tipici delle stringhe SQL scritte a mano

### **8.4 Test**

Per la fase di testing verranno create delle classi apposite per eseguire Unit Testing (sfruttando Junit) così da individuare al più presto eventuali errori nella codifica delle funzionalità e correggerli al più presto.  
Inoltre per controllare la struttura del nostro codice come per esempio dipendenze, astrazione, duplicazione, … verranno usati dei tool visti alle lezioni di tutorato che sono:

* **Stan4J**: tool utile per ottenere informazioni su dipendenze tra i vari moduli/package, eventuali tangle nel nostro progetto, pollution, astrazione e instabilità, coesione, accoppiamento e complessità del nostro progetto.  
* **PMD**: utile per individuare eventuali copia/incolla nel codice che potrebbero portare ad errori.  
* **SonarQube**: utile per individuare duplicazioni nel codice che possono venire astratte o comunque modificate per migliorare la manutenibilità del nostro progetto.

**Test nelle prime fasi di sviluppo** 

Nelle prime fasi di sviluppo sono stati introdotti unit test preliminari su classi semplici e isolate, con l’obiettivo di verificare l’infrastruttura di testing e applicare il principio secondo cui gli errori vanno individuati il prima possibile.

Questa attività è coerente con l’approccio Agile adottato (SCRUM), che prevede l’introduzione dei test fin dalle prime iterazioni per ottenere feedback rapido, individuare tempestivamente gli errori e supportare l’evoluzione incrementale del software.


\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **9\. Garanzia di qualità (Quality Assurance)**

Per garantire che gli utenti che utilizzeranno il nostro software abbiamo la miglior esperienza possibile abbiamo scelto di concentrarci principalmente sulla qualità del prodotto, che è più rilevante per un lavoro didattico e di dimensioni ridotte.
Per farlo si seguiranno queste regole:

* **Code review**: per approvare modifiche/implementazione di medio/alto impatto è obbligatorio eseguire PR che per essere integrate nel codice principale devono venire approvate da almeno 2 persone nel team (così come specificato prima, si ricorda al personale di controllare veramente il codice e non approvare modifiche senza guardarle).  
* **Analisi statica**: come specificato al [punto 8.4](#84-test) verranno usati dei tool per l'analisi statica del codice (Stan4J, PMD e SonarQube).  
* **Verifica dei requisiti**: ogni settimana il Product Owner insieme allo Scrum Master durante il weekly scrum discutono se il team sta rispettando i requisiti prestabiliti e programma i prossimi passi (a livello organizzativo, poi verrà ovviamente discusso tutto con il team).  
* **Rispetto degli standard**: ogni cosa che viene fatta deve seguire le linee guida e standard indicati al [punto 4](#4-standard-linee-guida-e-procedure) ed inoltre per il codice è stato creato un workflow automatico di Github per verificare il rispetto delle convenzioni java che indica se ci sono punti in cui non sono state seguite.  
* **Metrica della qualità**: come metriche per controllare la qualità del nostro progetto verranno prima di tutto usate tutte quelle fornite dai tool di Analisi Statica precedentemente citati.
Le metriche adottate misurano attributi interni di qualità del software.
In particolare vengono considerate:
    * numero di duplicazioni nel codice; 
    * complessità ciclomatica di classi e metodi; 
    * numero di code smells;
    * numero di bug e vulnerabilità; 
    * accoppiamento e dipendenze tra package.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **10\. Pacchetti di lavoro (Work Breakdown Structure)**
Il nostro progetto si divide in questi macro-pacchetti di lavoro:

* **Analisi dei requisiti**: svolta in prima parte dal Product Owner che compila un file dove annota tutti i requisiti necessari e cosa dovrà fare la nostra applicazione.  
* **Progettazione UML**: inizialmente svolta da un po’ tutto il team (così da velocizzare l’avanzamento) causa assenza di altri compiti significativi, ma che poi verrà portata avanti principalmente dal nostro UML Designer con l’eventuale aiuto di altri qualora abbiano terminato i propri compiti o ritengano opportuno (previo accordo con il team) finire prima un altro compito.
* **Sviluppo**, fatto da tutto il team (product owner compreso)
* **Rilascio**, ogni mercoledì prima del nuovo weekly scrum 

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **11\. Risorse**

### **11.1 Hardware**

PC con prestazioni minime 

### **11.2 Software**

Le risorse software principali del nostro team sono:

* **Eclipse**: IDE utilizzato per lo sviluppo di tutto il nostro progetto.  
* **Papyrus**: tool integrato in eclipse per lo sviluppo di alcuni diagrammi UML.  
* **PlantUML**: altro tool per lo sviluppo di alcuni diagrammi UML (preferito in corso d’opera a Papyrus in quanto quest’ultimo molte volte da’ problemi e si blocca).  
* **Markdown**: formato di file (preferito in corso d’opera ai pdf) per la documentazione e stesura di documenti in linguaggio naturale.  
* **Tool di Analisi Statica**: come Stan4J, PMD e SonarQube (vedi punto apposito).  
* **Librerie**: JavaFX  per la grafica, per il DB java.sql e org.jooq
* **Testing**: useremo principalmente JUnit.  
* **Strumenti di build e package managing**: useremo Maven (infatti il nostro progetto sarà un progetto Maven in eclipse).

### **11.3 Strumenti di comunicazione**

Gli strumenti e canali di comunicazione preferiti per discutere e proporre sono:

* **Whatsapp**: gruppo principale dove si discutono problemi “informali” del progetto.  
* **Github discussions**: canale preferito al primo per problemi o proposte riguardanti direttamente il progetto e la sua documentazione, organizzazione e implementazione.  
* **Github Kanban Board**: usata per condividere gli step da completare per l’avanzamento del progetto e l’assegnazione dei singoli agli individui.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## 

## **12\. Budget**

Avendo iniziato a lavorare al progetto ad inizio ottobre e considerando la fine verso la meta di gennaio, si prevede circa 100 giorni di lavoro per 4 uomini = 400 Giorni, supponendo circa 1h di lavoro al giorno medio per ognuno:

TOTALE: 400 ore di sviluppo

Il lavoro è svolto interamente a scopo didattico, per questo motivo non sono previsti costi economici.

Costo orario = 0€/h.  
Costo **totale**: 400h * 0€/h = 0€.

Ma ipotizzando uno stipendio medio di uno sviluppatore software in Italia (circa 15€/h).

Costo orario = 15€/h.  
Costo **per sviluppatore** = 100h * 15€/h = 1.500€.  
Costo **totale** = 1.500€ * 4 = 6.000€. (Solo per rientrare con gli stipendi!)

Inoltre consideriamo la metrica di sforzo mese/uomo.
Solitamente si assume 1 mese/uomo = 160h (20gg * 8h).  

Mese/uomo **totali** = 400h /160h = 2,5 mese/uomo.  
Mese/uomo **per persona** = 100h /160h = 0,625 mese/uomo.  

Questo significa che se venisse sviluppato da un singolo (come lavoro a tempo pieno, ovvero 40h settimanali) il progetto impiegherebbe 2,5 mesi per essere completato.
Mentre se venisse sviluppato dal nostro team a tempo pieno impiegheremmo solo 0,625 mesi ovvero circa 18-20gg lavorativi.  
A causa però di altri impegni e materie da portare avanti abbiamo deciso di fare una media di 1h al giorno e spalmare il lavoro su circa 100 giorni.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **13\. Gestione dei cambiamenti**

Ad ogni cambiamento che sarà necessario seguire per le volontà utente o adeguarsi agli standard e linee guida è stato deciso di seguire il seguente protocollo:

1. Prima di tutto, se è un cambiamento volontario (quindi non fondamentale ma solo per scelta tecnica), proporlo al weekly scrum. Se invece fosse un cambiamento involontario (ovvero non programmato e neanche controllato ma ottimale per il progetto) farlo notare appena ci si rende conto.  
2. In entrambi i casi citati al punto precedente è fondamentale discuterne con il team al primo weekly scrum disponibile così da effettuare eventuali cambi di rotta nello sviluppo il prima possibile senza rischiare di poi dover cambiare gran parte del lavoro già svolto.   
   In particolare si deve consultare:   
* **Rischi**, che possono derivare dall’adozione del cambiamento.  
* **Modifiche**, che si dovrebbero apportare al progetto per mettersi in linea con il cambiamento.  
* **Alternative**, che si possono adottare invece del cambiamento in discussione (**SEMPRE** utili, così da avere una visione totale delle possibiità di avanzamento del progetto).  
3. Dopo aver discusso con il team il Product Owner e lo Scrum Master danno un’ultima occhiata alla proposta di cambiamento così da delucidare ancora eventuali concetti non chiari o ambigui.  
   Se il consulto avrà esito positivo si passa al prossimo punto del protocollo, se invece avrà esito negativo o non chiaro, allora si può tornare al punto precedente di stesura del cambiamento o rifiutare la proposta.  
4. Se il cambiamento viene accettato lo Scrum Master deve modificare il backlog, correggere gli issue nella repository Github e ricordarsi di annotare le decisioni prese nel weekly scrum.  
5. Come ultimo passaggio poi il team si deve impegnare a rivedere tutti i documenti e/o implementazioni ritenute “toccate” dal cambiamento.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **14\. Manutenzione del software**

### **14.1 Obiettivi della manutenzione**

La manutenzione del software serve per garantire che il software da noi progettato rimanga affidabile anche dopo che avremo rilasciato le varie versioni incrementali dello sviluppo.

Siccome abbiamo adottato il metodo agile SCRUM con cadenza settimanale la manutenzione avrà un ruolo fondamentale e riguarderà tutte le parti del progetto accompagnandolo per tutta la durata del ciclo di vita del software.
In particolare, la manutenzione mira a:
- correggere eventuali difetti dopo i rilasci;
- adattare il sistema a cambiamenti dell'ambiente o delle tecnologie utilizzate;
- supportare l'evoluzione dei requisiti del dominio (regole delle gare, classifiche, ruoli);
- migliorare la qualità interna del codice e ridurre il debito tecnico.

### **14.2 Tipologie di manutenzione**

Nel progetto distinguiamo i seguenti tipi di manutenzione:

**Manutenzione correttiva:**

Finalizzata alla correzione degli errori individuali nel sistema, come:
- calcolo errato delle penalità
- problemi nella generazione delle classifiche 
- malfunzionamenti nell'iscrizione alle gare o nella gestione dei gruppi

Questi interventi vengono gestiti tramite gli issue di GitHub e successivamente classificate e discusse durante i weekly scrum.

**Manutenzione Adattiva:**

Serve ad adattare il sistema a cambiamenti dell’ambiente, come:
- aggiornamenti di librerie (Java, JavaFX, JOOQ);
- modifiche al DBMS o alla configurazione dell’ambiente di sviluppo;
- variazioni nei vincoli tecnologici.

Tali modifiche vengono pianificate come task di sprint e sottoposte alle normali procedure di revisione.

**Manutenzione perfettiva:**

Riguarda l’introduzione di nuove funzionalità o il miglioramento di quelle esistenti, come:
- nuove modalità di visualizzazione delle classifiche;
- miglioramento dell’esperienza utente;
- supporto a nuove tipologie di gare.

**Manutenzione preventiva:**

Ha l’obiettivo di migliorare la manutenibilità futura del sistema, riducendo il rischio di errori e facilitando l’evoluzione del software.

Include attività come:
- refactoring del codice;
- riduzione della duplicazione;
- miglioramento dell’astrazione e della modularità;
- aggiornamento della documentazione tecnica.

### **14.3 Processo di manutenzione**

Le attività di manutenzione seguono le caratteristiche del modello SCRUM adottato:

1. Segnalazione del problema o richiesta di modifica tramite issue su GitHub con descrizione chiara e motivata.
2. Valutazione dell’impatto: vengono valutati
    * impatto sui requisiti;
    * impatto sull’architettura;
    * rischi introdotti dalla modifica.
3. Pianificazione: la manutenzione viene inserita nel backlog e pianificata in uno sprint.
4. Implementazione e revisione: le modifiche vengono sviluppate su branch dedicati e integrate solo tramite Pull Request con almeno due approvazioni.
5. Aggiornamento della documentazione: ogni modifica rilevante comporta l’aggiornamento dei documenti interessati (Project Plan, UML, requisiti).

### **14.4 Ruoli e responsabilità nella manutenzione**

Le attività di manutenzione coinvolgono l’intero team di progetto secondo i ruoli definiti nel Project Plan:

* **Product Owner**
     * valuta e prioritizza le richieste di manutenzione;
     * verifica la coerenza delle modifiche con i requisiti funzionali e di dominio.
* **Scrum Master**
     * garantisce il rispetto del processo SCRUM durante le attività di manutenzione;
     * supporta la pianificazione delle attività all’interno degli sprint.
* **Sviluppatori**
     * implementano le modifiche e correggono i difetti;
     * svolgono attività di refactoring e aggiornano la documentazione tecnica;
     * collaborano alla revisione del codice e ai test.
* **Repository Admin**
     * supervisionano l’integrazione del codice nel repository GitHub;
     * gestiscono branch, Pull Request e regole di versionamento.

### **14.5 Manutenzione e gestione della configurazione**

La manutenzione è strettamente collegata alla Gestione della Configurazione.

Ogni intervento di manutenzione:
* è tracciato tramite issue e commit;
* produce una nuova versione del software;
* mantiene lo storico delle modifiche grazie a Git e GitHub.

Questo approccio garantisce tracciabilità, riproducibilità e controllo delle versioni, riducendo il rischio di regressioni.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

## **15\. Consegna**

La consegna del progetto finale avverrà 5 giorni prima della data dell’esposizione del progetto all’esame orale tramite, oltre ovviamente alla repository Github, una presentazione PowerPoint del progetto, una panoramica dei dati dei vari tool di analisi statica e una simulazione della nostra applicazione.  
Inoltre ci impegniamo ogni settimana a rilasciare una nuova versione del progetto.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_
