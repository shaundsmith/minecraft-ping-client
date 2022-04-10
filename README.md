# minecraft-ping-client
> A simple client for making requests to a Minecraft server's "ping" endpoint.


## Installation
Maven:
```xml
<dependency>
    <groupId>dev.shaundsmith.minecraft</groupId>
    <artifactId>minecraft-ping-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

Gradle:
```groovy
dependencies {
    implementation 'dev.shaundsmith.minecraft:minecraft-ping-client:1.0.0'
}
```

## Use
### Constructing the client
The client can be constructed simply using the `MinecraftPingClientBuilder`.
The minimum data required to build a `MinecraftPingClient` is the hostname and port of the Minecraft server.

With this information, the builder can construct a `MinecraftPingClient` configured to retrieve the simple ping details of the given server.
(See [Server_List_Ping](https://wiki.vg/Server_List_Ping) for more details about the simple ping endpoint)

```java
MinecraftPingClient client = new MinecraftPingClientBuilder("http://example.com", 8080)
        .build();

MinecraftServerResponse response = client.ping();
```

### Response
The `MinecraftServerResponse` contains some simple pieces of information about the server's name, version, and players.
Minecraft Forge mod loader adds an extra property to the ping responses, so servers running Minecraft Forge will also return
the name of the mod loader and the list of mods that are currently installed on the server.
Unfortunately, Fabric does not provide this information.
```
MinecraftServerResponse
  | serverDescription:: String         # The description of the server
  | serverVersion:: String             # The version of Minecraft that the server is running
  | serverIcon:: String                # The server icon as a data URL
  | players:: Players
      | maximumNumberOfPlayers:: int   # The maximum number of players allowed on the server
      | onlineNumberOfPlayers:: int    # The number of players currently online
      | playerNames:: String[]         # The names of the players that are online - this is just a sample and may not be complete
  | modLoader                          
      | name:: String                  # Name of the mod loader
      | mod:: Mod[]
          | name:: String              # Name of the mod
          | version:: String           # Version of the mod
```

### Advanced Construction:
There are two optional fields that can be provided to the `MinecraftPingClientBuilder`: an object mapper and a timeout value.
* `withObjectMapper(ObjectMapper)` allows an existing Jackson object mapper to be used for parsing the response from the Minecraft server.
* `withTimeout(int)` allows the timeout (in milliseconds) for the `ping()` call to be provided. The default value is 30000.
```java
MinecraftPingClient client = new MinecraftPingClientBuilder("http://example.com", 8080)
        .withObjectMapper(myCustomObjectMapper)
        .withTimeout(5000)
        .build();
```
