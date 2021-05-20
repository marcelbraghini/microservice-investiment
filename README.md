# microservice-wallet project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Can be used with
[messageDisplay](https://github.com/marcelbraghini/messageDisplay)

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `microservice-wallet-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/microservice-wallet-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/microservice-wallet-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

# RESTEasy JAX-RS

<p>A Hello World RESTEasy resource</p>

Guide: https://quarkus.io/guides/rest-json

# Mongodb

database: investiment

Collection: walletBase

```
{
    "coinAcronym" : "BTC",
    "quantity" : NumberDecimal("0.00128507"),
    "type": "CRYPTOCURRENCY"
}
{
    "coinAcronym" : "ETH",
    "quantity" : NumberDecimal("0.01818041"),
    "type": "CRYPTOCURRENCY"
}
```

Collection: wallet

```
{
    "coins" : [ 
        {
            "coinAcronym" : "BTC",
            "fraction" : NumberDecimal("0.00128507"),
            "price" : NumberDecimal("321999.00"),
            "totalValue" : NumberDecimal("413.79")
        }, 
        {
            "coinAcronym" : "ETH",
            "fraction" : NumberDecimal("0.01818041"),
            "price" : NumberDecimal("9799.99"),
            "totalValue" : NumberDecimal("178.17")
        }
    ],
    "totalValue" : NumberDecimal("591.96"),
    "type" : "CRYPTOCURRENCY"
}
```

# Integrations

- BrasilBitcoin

# JaCoCo plugin

```shell script
./mvnw package
```

- To see the code coverage using the jacoco you can access it through your html
/target/site/jacoco/index.html
