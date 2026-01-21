# Specifica Operazioni

## CONCORRENTE
- **iscriviGara(codice: String): boolean** – chiama `g.nuovaIsscrizione`
- **annullaIscrizione(codice: String): boolean**
- **visualizzaProfilo(): void**
- **carriera(): void**
- **modificaProfilo(): void** – aggrega i vari setter
- **iscrizioni(): void**
- **aggiungiRecensione(codice: String, rec: Recensione): boolean**

## ARBITRO
- **avviaGara(codice: String): void** – imposta gara in corso
- **rinuncia(codice: String): void** – chiama `g.trovaArbitro()`
- **segnaAssenze(concorrenti: List<Concorrente>): void**
- **generaGruppi(concorrenti: List<Concorrente>): void**
- **segnaPunto(concorrente: Concorrente): void** – +1 punto
- **segnaPunto(concorrente: Concorrente, punti: Integer): void**
- **annullaConcorrente(concorrente: Concorrente, motivo: String): void**
- **penalizzaConcorrente(concorrente: Concorrente, quantita: Integer, motivo: String): void** – chiama `p.penalità`
- **salvaPunti(idSett: String, numTurno: Integer): void** – chiama `g.classificaTurno`

## SOCIETÀ
- **iscrizioneGaraGruppi(concorrenti: List<Concorrente>, codiceGara: String): boolean**
- **iscrizioneGaraSingolo(concorrente: Concorrente, codiceGara: String): boolean**
- **getProfilo(): String**
- **aggiungiSocio(concorrente: Concorrente): void**
- **abbandonoSocio(concorrente: Concorrente): void**
- **modificaProfilo(): void**
- **nuovaGara(gara: Gara): void** – in attesa di conferma amministratore
- **regolamento(): String**

## GARA
- **cambiaStato(stato: StatoGara): void** – confermata / in attesa / rinviata / annullata
- **cambiaStatoSvolgimento(stato: StatoSvolgimento): void** – corso / intervallo / finita
- **squalifica(concorrente: Concorrente, motivo: String): void**
- **preparaTurni(concorrenti: List<Concorrente>)** – gestisce assenze
- **preparazioneGara(gruppi: List<List<Concorrente>>): void** – chiama `assegnaTurno`
- **classificaTurno(turno: Turno, punteggi: List<Punteggio>): void**
- **trovaArbitro(): void**
- **nuovaIscrizione(cf: String, out numIscritti: Integer): boolean**
- **iscrizioneMultipla(concorrentiPresenti: List<Concorrente>): boolean**
- **verificaData(): boolean**
- **verificaMax(): boolean**
- **verificaMin(): boolean**
- **getClassifica(): List<Punteggio>**

## CAMPIONATO
- **getClassificaTotale(): List<Punteggio>**
- **classificaProva(numProva: Integer): List<Punteggio>**

## AMMINISTRATORE
- **nuovaGara(gara: Gara): void** – stessa logica società (interfaccia)
- **confermaProposta(numGara): void**
- **negaProposta(numGara, motivo: String)**
- **notificaSocieta(): void**
- **notificaComune(): void**
- **regolamento(): String**

## TURNO
- **calcolaClassifica(punteggi: List<Punteggio>): void**
- **getClassifica(): List<Punteggio>**

## PUNTEGGIO
- **mostraPenalita(): String**
- **setPunti(totPunti: Integer): void**
- **getPunti(): Integer**
- **squalifica(): void**
- **penalità(quantità: Integer, motivo: String, out numPunti: Integer): void**

## CONTRATTO
- **rinnova(dataFine: Date, premio: String): void**

## CAMPOGARA
- **getCaratteristiche(): String**
- **setCaratteristiche(): void**

## SETTORE
- **setCaratteristiche(): void**
- **getCaratteristiche(): String**

