name: Richiesta funzionalità
description: Proponi una nuova idea per il gestore gare
title: "[FEATURE] "
labels: ["enhancement"]
body:
  - type: markdown
    attributes:
      value: "Grazie per il suggerimento!"

  - type: textarea
    id: problema
    attributes:
      label: Problema attuale
      description: "Quale fastidio incontri oggi?"
      placeholder: "Devo compilare due volte l'iscrizione se cambio settore..."
    validations:
      required: true

  - type: textarea
    id: soluzione
    attributes:
      label: Soluzione desiderata
      description: "Cosa vorresti che il sistema facesse?"
    validations:
      required: true

  - type: textarea
    id: alternative
    attributes:
      label: Alternative considerate
      description: "Hai valutato altri modi per risolvere il problema?"

  - type: textarea
    id: extra
    attributes:
      label: Ulteriori dettagli
      description: "Screenshot, mock-up, link di riferimento..."
