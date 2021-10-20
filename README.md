# Devops-Challenge Project

[![CircleCI](https://circleci.com/gh/tuxpower/devops-challenge/tree/develop.svg?style=svg)](https://circleci.com/gh/tuxpower/devops-challenge/tree/develop)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
mvn compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/dev/.

## Preflight checklist

Checking known OWASP vulnerabilities using:
```shell script
mvn verify -Dcheckstyle.skip -DskipUTs -DskipPTs -DskipFTs
```

Verifying dependencies updates using:
```shell script
mvn versions:display-dependency-updates
```

Checking plugin updates using:
```shell script
mvn versions:display-plugin-updates
```

Verifying checkstyle using:
```shell script
mvn validate
```

### Unit tests

Run Unit tests using:
```shell script
mvn test -DskipDCs -Dcheckstyle.skip -DskipFTs -DskipPTs
```

### Functional and Integrations tests

Run Functional and Integrations using:
```shell script
mvn verify  -DskipDCs -Dcheckstyle.skip -DskipUTs -DskipPTs
```

## Building a Docker image

You can build a Docker image using: 
```shell script
mvn clean package -Dquarkus.container-image.build=true
```
It builds a Docker image and tag as `jgaspar/devops-test:1.0.0

## Running the application

### Prerequisites

This application requires a PostgreSQL database server.
Do you need a ready-to-use PostgreSQL server to try out the API?
```shell sript
docker run -d --restart always --name postgres \
-e POSTGRES_DB=devops_test \
-e POSTGRES_USER=devops \
-e POSTGRES_PASSWORD=password \
--network host postgres:10.6
```

You can then run the application using:
```shell script
docker run -d --rm --network host jgaspar/devops-test:1.0.0
```

## Access the API

You can test the API via a user-friendly user interface named Swagger UI.
Swagger UI is a great tool permitting to visualize and interact with the API. The UI is automatically generated from the application OpenAPI specification.
Once the application is started, you can go to http://localhost:8080/swagger-ui and play with the API.

## Deploy the API in Kubernetes

By adding these dependencies, we enable the generation of Kubernetes manifests each time we perform a build while also enabling the build of a container image using Jib. For example, following the execution of ./mvnw package, you will notice amongst the other files that are created, two files named _kubernetes.json_ and _kubernetes.yml_ in the target/kubernetes/ directory.

The generated manifest can be applied to the cluster from the project root using kubectl:
```shell script
kubectl apply -f target/kubernetes/kubernetes.yml
```

This assumes that a _.kube/config_ is available in your user directory that points to the target Kubernetes cluster. In other words the extension will use whatever cluster kubectl uses.
