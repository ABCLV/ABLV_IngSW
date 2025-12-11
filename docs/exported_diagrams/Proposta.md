PROPOSTA: GESTIONE GARE DI PESCA BERGAMASCA

Progettare un sistema in grado di gestire le gare agonistiche di pesca in bergamasca.
Ci sono diversi tipi di gare: la gara singola oppure i campionati (2 o più prove in giorni diversi).
Ogni gara è identificata da un codice, le prove nei campionati hanno lo stesso codice.
Le gare si svolgono in un campo gara (tratto di fiume/torrente dove è possibile organizzare gare).
I campi gara si possono dividere prima in zone (solitamente solo 1) e poi in settori.
Ogni gara ha una società organizzatrice.
Ogni gara prevede una tecnica di pesca specifica (sarà un’enumerazione) che devono rispettare i pescatori.
Una gara si può dividere in turni.
Ogni turno ha una certa durata.
Attenzione, non confondere turno con settore, una gara si può fare su un solo settore ed avere più turni ad esempio.
Alle gare partecipano i concorrenti. Ogni concorrente appartiene ad una società di pesca e può avere sponsor.

Le classifiche sia dei turni che delle prove dei campionati sono basate sulle penalità, le penalità vengono assegnate ai concorrenti in base ad un criterio (numero pesci pescati, peso totale pescato o chi fa la cattura più pesante).
In caso di pari punteggio (secondo il criterio della gara), si assegna mezza penalità ai concorrenti arrivati in parità (esempio 1°, 2°, 3.5°, 3.5°, 4)
I pescatori di una gara possono essere divisi in gruppi (se la gara si svolge in più settori) ciascun gruppo gareggia su un determinato settore. I pescatori gareggiano nello stesso gruppo in tutti i turni della giornata. I gruppi si spostano nei vari settori (della stessa zona) nei vari turni
Al termine della giornata si calcolano le classifiche di ogni turno per ogni gruppo, si sommano le penalità e si ottiene la classifica della gara/prova.

Si vuole gestire lo storico delle gare, alcune gare infatti soprattutto i campionati sono appuntamenti annuali.

Le gare hanno un massimo numero di concorrenti che possono partecipare, hanno anche un minimo, se non si raggiunge il minimo la gara viene annullata (Gestire attraverso variabile stato in gara)

il sistema deve fornire una pagina di autenticazione per:
amministratori: sono gli organizzatori delle gare, hanno il potere di inserire nuove competizioni, assegnare arbitri
concorrenti: possono solo consultare il sw per vedere le classifiche
società: account delle società, possono iscrivere i loro concorrenti alle gare. 
arbitro: deve poter accedere alla gara per conteggiare le catture. 



(esempio di una prova di campionato, con 4 turni e 1 zona)

(esempio di classifica finale campionato (dello stesso di sopra))


(Un altro esempio: c'è un campionato di 4 prove, in ciascuna prova ci sono 3 turni. Il campo gara della prima prova è diviso in 3 tratti. Un concorrente gareggia in un tratto (che cambia nei vari turni) insieme ad altri pescatori avversari formando un gruppo. Ad ogni turno si calcolano le classifiche per ogni gruppo (che hanno gareggiato in settori diversi, 3 gruppi per 3 settori). Avremo allora 3 classifiche per ogni turno. A fine giornata si sommano le classifiche di ciascun turno per ciascun gruppo (i gruppi non cambiano nei vari turni) e si uniscono ottenendo la classifica generale della gara, a fine campionato si sommano i punteggi di ogni gara e si ottiene la classifica finale del campionato) 


Possibile complicazione esercizio, ci sono anche gare di coppia e gare di squadra (della società), il sistema di punteggi e classifiche rimane lo stesso (bisogna solo sommare). Persiste la regola che un concorrente pesca solo in settori di una zona. 
Ci possono essere più squadre iscritte della stessa società, in questo caso presentano un codice progressivo diverso (B C D…)


l’arbitro può compilare i punteggi in 2 modalità: nelle gare senza rilascio vengono conteggiate alla fine per ogni partecipante. Nelle gare con rilascio il sistema deve permettere di segnalare catture in tempo live.


![Testo alternativo](esempio1.PNG)
![Testo alternativo](esempio2.PNG)
![Testo alternativo](esempio3.PNG)



classificazione Kano 
A - attraente
M - must -be
U - unilaterale
R - rovescio
I - indifferente
B - bho, non sappiamo







Specifica requisiti 
P.s: i requisiti dell’utente esterno devono essere garantiti anche per tutti gli altri attori


Sistema

A: Il sistema invia una notifica a tutti gli iscritti il giorno prima della gare

M: fornisce un’interfaccia diversa di autenticazione per ogni attore

A: fornisce all’arbitro un sistema casuale che associa i concorrenti (non assenti) ai gruppi (se ci sono) di una gara

I: fornisce un regolamento a società/amministratori per la definizione delle gare, come disporre turni su zone e settori

M: fornisce un’interfaccia semplice per le varie ricerche

M: gestisce lo storico delle gare

I: ci sarà una pagina in cui si ringraziano i creatori del software

M: non accetta più iscrizioni quando si è raggiunto il numero massimo di concorrenti

M: annulla la gara prevista quando non si è raggiunto il numero minimo di iscrizioni

A: cartina con i campi gara interattiva

I: indica le condizioni metereologiche previste per una gara

M: permette di gestire pagamenti, iscrizione alla gara

R: il sistema consiglia agli utenti gare simili (pubblicità)

M: a fine gara verranno inviati a tutti i partecipanti la classifica finale in formato pdf, nel pdf saranno presenti anche i nomi degli sponsor della gara

M: gestisce rimborsi in caso di iscrizione annullata/ gara sospesa


Utente esterno

M: deve poter consultare i risultati delle gare

M: deve poter visionare le gare future

M: deve poter controllare i campi gara e verificare le gare su di essi fatte e che si faranno

M: deve poter accedere ad uno specifico concorrente per visionare la sua carriera


Concorrente

M: l’utente deve potersi registrare al sistema come concorrente

M: il concorrente non si può iscrivere ad una gara se non è in una società

M: un concorrente non si può iscrivere personalmente ad una gara di squadra (lo fa la società (vedi sotto))

M: il concorrente può indicare un solo sponsor

M: il concorrente può cambiare società d’appartenenza (le modifiche vengono appartate dalle società interessate)

R: può valutare con una recensione l’organizzazione della gara

R: può valutare l’operato dell’arbitro



Arbitro

M: l’arbitro deve potersi registrare al sistema

M: l’arbitro deve poter registrare i risultati in differita  (al termine della gara)

A: oppure l’applicazione permette all’arbitro di segnare in diretta le catture (i punteggi)

M: l’arbitro può decidere di rinviare una gara per condizioni non adeguate alla pesca, in questo caso deve poter modificare la data della gara

M: l’arbitro può penalizzare un concorrente in caso di irregolarità

M: l’arbitro può annullare la gara di un concorrente

I: l’arbitro può dichiarare un suo sostituto in caso di necessità

M: l’arbitro deve poter pubblicare i risultati di una gara/prova

M: l’arbitro crea tramite sorteggio i gruppi della gara

M: in caso di assenza da parte di un concorrente, questo verrà squalificato per la prova/gara. Nel caso in cui la gara divide i concorrenti in gruppi, non c’è problema, i gruppi vengono creati equamente il giorno stesso della gara


Società

M: la società deve potersi registrare al sistema

M: la società può iscrivere uno o più dei suoi concorrenti ad una gara
(ps: i concorrenti devono essere iscritti alla piattaforma)

M: nelle gare a squadra, un concorrente si può iscrivere solo con altri membri della sua società, l’iscrizione in questo caso è fatto esclusivamente dalla società

M: può proporre all’amministratore nuove gare con proprie regole




Amministratore

M: deve poter annunciare una nuova gara, con tutti i suoi dati

M: può accettare/negare la proposta di gare da parte delle società

M: non può inserire/accettare gare che si trovano nello stesso campo gara con una distanza temporale di una settimana

M: deve occuparsi della comunicazione al territorio della gara (con email al comune), e predisporre cartelli nello spot in questione nei giorni precedenti la gara

