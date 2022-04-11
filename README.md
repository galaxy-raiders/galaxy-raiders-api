# Galaxy Raiders

Template for the project of the course [MAC0218 - Programming Techniques II][1]
at University of São Paulo (@USP).

# Development

Galaxy Raiders is built in [Kotlin][2], a modern programming language for
the JVM (Java Virtual Machine) supporting object-oriented programming and
functional programming. It uses [Gradle][3] as the official build tool.

To develop Galaxy Raiders, please install and use [docker][4].

> NOTE: This repo has gradle wrappers to ensure developers use the same version
> of gradle in every environment. However, using gradle's docker image already
> guarantees that. Therefore, commands below can use the `gradle` CLI directly.

## Compilation

To compile all classes in the project, run:
```bash
docker container run --rm --user gradle --volume "$PWD":/home/gradle/app --workdir /home/gradle/app gradle:7.4.2-jdk17 gradle --no-daemon clean assemble
```

To generate a runnable JAR of the project, run:
```bash
docker container run --rm --user gradle --volume "$PWD":/home/gradle/app --workdir /home/gradle/app gradle:7.4.2-jdk17 gradle --no-daemon clean jar
```

## Execution

To execute the project in development mode (with live reload), run:
```bash
docker container run --rm --user gradle --volume "$PWD":/home/gradle/app --workdir /home/gradle/app gradle:7.4.2-jdk17 gradle --continuous run
```

To execute the project in production mode, generate a runnable JAR, then run:
```bash
docker container run --rm --user gradle --volume "$PWD":/home/gradle/app --workdir /home/gradle/app gradle:7.4.2-jdk17 java -jar ./app/build/libs/app.jar
```

## Other tasks

To find available gradle tasks, run:
```bash
docker container run --rm --user gradle --volume "$PWD":/home/gradle/app --workdir /home/gradle/app gradle:7.4.2-jdk17 gradle tasks
```

To execute any task, run:
```bash
docker container run --rm --user gradle --volume "$PWD":/home/gradle/app --workdir /home/gradle/app gradle:7.4.2-jdk17 gradle {task}
```

[1]: https://uspdigital.usp.br/jupiterweb/obterDisciplina?sgldis=MAC0218
[2]: https://gradle.org
[3]: https://kotlinlang.org
[4]: https://docs.docker.com
