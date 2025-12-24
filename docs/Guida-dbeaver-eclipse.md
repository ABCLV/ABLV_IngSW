# Guida all'installazione e utilizzo di DBeaver in Eclipse

## 1. Introduzione

DBeaver è un potente client SQL multipiattaforma che permette di
interagire con diversi tipi di database. Questa guida illustra come
installarlo come plugin su Eclipse e come iniziare a utilizzarlo.

------------------------------------------------------------------------

## 2. Installazione di DBeaver come plugin su Eclipse

### 2.1 Requisiti

-   Eclipse installato (versioni recenti consigliate)
-   Connessione Internet attiva

### 2.2 Procedura di installazione

1.  Apri **Eclipse**.
2.  Vai nel menu: **Help \> Install New Software...**
3.  Nella finestra che appare, clicca su **Add...**
4.  Inserisci come Nome: `DBeaver`
5.  Inserisci come URL di aggiornamento:\
    `https://dbeaver.io/update/latest/`
6.  Premi **OK**.
7.  Attendi il caricamento dei componenti disponibili.
8.  Seleziona il plugin **DBeaver**.
9.  Clicca su **Next** e segui le istruzioni fino alla conferma dei
    termini di licenza.
10. Dopo l'installazione, Eclipse chiederà di essere riavviato.
    Conferma.

------------------------------------------------------------------------

## 3. Avviare DBeaver in Eclipse

Dopo il riavvio: 1. Vai su **Window \> Perspective \> Open Perspective
\> Other...** 2. Cerca e seleziona **DBeaver**. 3. Eclipse passerà alla
prospettiva dedicata al database.

------------------------------------------------------------------------

## 4. Configurare una connessione database

### 4.1 Creare una nuova connessione

1.  Clicca su **Database Navigator**.
2.  Premi **New Connection**.
3.  Scegli il tipo di database (solo SQLite)
4.  Inserisci hostname, porta, nome database, username e password.
5.  Se i driver non sono presenti, DBeaver ti chiederà di scaricarli
    automaticamente.
6.  Clicca su **Test Connection** per verificare la riuscita della
    connessione.
7.  Premi **Finish**.

------------------------------------------------------------------------

## 5. Funzionalità principali

### 5.1 Eseguire query SQL

-   Vai su un database \> tasto destro \> **SQL Editor \> New SQL
    Script**
-   Scrivi la tua query
-   Esegui con **Ctrl + Enter**

### 5.2 Navigare nelle tabelle

-   Espandi la connessione nel **Database Navigator**
-   Apri tabelle, viste, procedure, funzioni
-   Con doppio clic su una tabella puoi vedere i dati, la struttura e le
    statistiche

### 5.3 Modificare dati

-   Apri una tabella
-   Passa alla vista **Data**
-   Modifica le celle direttamente
-   Conferma con **Ctrl + S**
-   Evitare modifiche dall'interfaccia in quanto non vengono rispettati i vincoli chiave esterna (li gestiamo solo su codice, è importante ricordarsi di utilizzare la classe apposita per le connessioni al db, il controllo viene attivato qui)

### 5.4 Esportare dati

-   Tasto destro su tabella \> **Export Data**
-   Scegli formato (CSV, SQL, JSON, Excel, ecc.)
-   Completa la procedura guidata

------------------------------------------------------------------------

## 6. Suggerimenti utili

-   Usa il pannello **Properties** per informazioni avanzate sugli
    oggetti del database.
-   Puoi personalizzare colori e temi da **Preferences \> General \>
    Appearance**.
-   I driver scaricati vengono gestiti automaticamente da DBeaver, ma
    puoi aggiornarli da **Database \> Driver Manager**.

------------------------------------------------------------------------
