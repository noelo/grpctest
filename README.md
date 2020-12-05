# grpctest

#Run the server
```
mvn compile exec:java -Dexec.mainClass="io.coastwatch.impl.ShipSightedServer" 
.
.
.
.
Server started, listening on 32000
```

#Run the client
```
mvn compile exec:java -Dexec.mainClass="io.coastwatch.impl.ShipSightedClient"
.
.
.
Running the ShipSightedClient
Server : shipsighted shipType: "tinny"
location: "Clon"

Client : Munition received : Do something with it munitionType: "munition name"
```

