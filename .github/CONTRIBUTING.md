# Linee guida per contribuire

Grazie per aver dedicato tempo a migliorare il progetto!  
Prima di aprire issue o pull request, leggi il [Piano di Gestione della Configurazione](../ABLV_IngSW/docs/Piano%20Gestione%20della%20Configurazione.pdf) dove sono definiti branching model, naming convention e processo di review.

## Ambiente di sviluppo
- Eclipse (ultima versione)
- Java 17
- GitHub Desktop o Git da terminale
- Papyrus / PlantUML per i diagrammi

## Flusso rapido
1. aggiorna il tuo branch `dev` locale
2. crea un branch con prefisso `feature/`, `bugfix/` o `hotfix/`
3. committa con messaggio chiaro
4. apri PR verso `dev`, assegna 2 revisori
5. dopo l'approvazione il branch verrà eliminato automaticamente

## Norme sul codice
- rispetta la convenzione di nomi già in uso
- ogni nuovo metodo deve avere JavaDoc essenziale
- prima di committare esegui un pull per ridurre conflitti
- non spostare/renominare package senza previo accordo nel gruppo

## Test
- tutte le nuove feature devono includere test JUnit
- esegui `mvn test` locale prima della PR
- la CI GitHub eseguirà nuovamente i test: se falliscono la PR viene bloccata

## Documentazione
- aggiorna il file corrispondente in `/docs` per ogni modifica rilevante

## Dove chiedere aiuto
- apri una [Discussion](https://github.com/ABCLV/ABLV_IngSW/discussions) per domande generali
- usa le issue per bug o feature nuove, etichettandole correttamente (`bug`, `help-wanted`)

Grazie per la collaborazione!  
Il team ABLV
