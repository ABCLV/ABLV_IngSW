Weekly SCRUM #5 (28/01) o  
Sprint backlog (dal 28/01 al 04/02) 
===== 
Note:  
Dato che è stato deciso di eseguire degli weekly scrum invece che daily scrum (a causa di impegni/non presenza di tutti sempre) e la lunghezza degli sprint di 1 settimana (così da non farne troppo pochi e rischiare di non finire il progetto), i documenti di sprint backlog e weekly scrum coincidono in quanto già di natura molto simili.


Partecipanti

* Celeste \- Product Owner, Dummy client, Developer  
* Vitali Michele \- SCRUM Master, Admin della repository su github  
* Algeri Nicola \- Developer, Admin della repository su github  
* Bertoli Marco \- Developer, UML Designer

1\. Obiettivi della settimana

* Implementazione codice (A, B, L ,V): 
    - Iscrizioni gare (tutto il blocco di issues relativi).
    - Gestione nuove proposte di amministratori/società.
* Sistemare il Project Plan, nello specifico:
    - Definire il principio di progettazione utilizzato. (A)
    - Definire coesione e accoppiamento del progetto. (A)
    - Definire le metriche usate per "valutare" il SW. (V)
    - Specificare i test utilizzato per le prime fasi di sviluppo. (L)

2\. Lavoro completato (Done)

* Il progetto è rimasto per lo più in stand-by in quanto è stato portato a termine un refactoring di circa tutto il progetto in quanto era stato accumulato molto debito tecnico e veniva violato il principio MVC e si avevano cicli nei grafi delle dipendenze.

3\. Lavoro in corso (In Progress)

* Implementazione codice (A, B, L ,V): 
    - Iscrizioni gare (tutto il blocco di issues relativi).
    - Gestione nuove proposte di amministratori/società.
    - Homepages.

4\. Impedimenti attuali (Impediments)

* Debito tecnico accumulato molto elevato (ora si spera sia quasi azzerato...).

5\. Decisioni prese nella settimana

* Scelto di eseguire il refactoring.
* Scelto di migrare da MVC pure ad un MVC più evoluto e stratificato, in particolare abbiamo aggiunto un livello "Service" che serve ad evitare controller troppo grandi, prendendo così in carico la logica di business e la comunicazione con il database (perciò model nel nostro progetto sarà un layer a sè stante, che definisce solo le entità...).

 6\. Note dello Scrum Master

* Da ora in poi seguire le direttive del progetto
* Stare attenti a non violare ancora il principio MVC così da non dover rimodificare ancora tutto dopo.