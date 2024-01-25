# void

## What is the Void

A place where the focus is on what's happening right now and removing everything else.

## How To Get Started

### Requirements

1. Postgresql
2. Java 21
3. Maven
4. NPM

### Interesting Tools

1. pgAdmin 4 - for Postgresql


### Building The Project

You should think about this as two steps. There is a frontend and there is a backend.
We need to compile everything for the frontend first, then we need to package those frontend
components into the jar file where the backend resources will live. Our final product is a 
single package with all of our components for the frontend and the api.

```npm run-script build```

This command will compile our frontend components.

```mvn clean package```

This command will compile our backend components.

```npm run-script build;mvn clean package```

You could do this and combine these steps into one.

Set SPRING_ACTIVE_PROFILE to "local" in your environment variables