INSERT INTO GARA (
    Codice,
    NumProva,
    Tecnica,
    CriterioPunti,
    MinPersone,
    MaxPersone,
    TipoGara,
    Data,
    CampoGara,
    Arbitro
)
VALUES (
    'GARA_TEST_014',   -- Codice
    1,                 -- NumProva
    'Spinning',        -- Tecnica
    'Somma',           -- CriterioPunti
    1,                 -- MinPersone
    10,                -- MaxPersone
    'Regionale',       -- TipoGara
    '2025-01-10',      -- Data
    'C1001',  -- CampoGara (assicurati esista o crea un record dummy)
    'AR001'  -- Arbitro inesistente → viola FK
);
