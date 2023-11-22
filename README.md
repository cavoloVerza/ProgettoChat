# ProgettoChat

> Gruppo Cosma, Sestini, Borgi

## Tecnologie utilizzate
  - Java
  - Thread

## Casi d’uso  Client
  - Connessione al Server: avverto il server che mi sono connesso
  - Disconnessione Client: avverto il Server della disconnessione
  - invio e Ricezione dei messaggi


## Casi d’uso  Server
  - Connessione di un Client: stabilisco la connessione col Client
  - Disconnessione di un Client: avverto gli altri Client che uno si è disconnesso
  - Invio in Broadcast: invio a tutti i client
  - Invio mirato: invio ad un determinato client


## Diagramma delle Classi



## Messaggi scambiati tra client e server

```
server --> inserisci il nome 
client --> "inserisce nome" 
server --> spiegazione di come utilizzare la chat
client --> inizio della conversazione
```
