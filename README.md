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

Please also install [pre-commit][5] in your development environment to
automatically format code and to detect bed smells before making new commits.
By default, this integration also uses [docker][4].

## Linting

This project uses [ktlint][6] and [detekt][7] for static code analysis.

If you installed [pre-commit][5], to execute the linters, run:
```bash
pre-commit run --all-files
```

Alternatively, to execute the linters manually, run:
```bash
docker compose --profile dev run --rm linter
```

## Compilation

The compilation of all classes and the generation of a runnable self-contained
JAR is made "behind the scenes" by [docker][4].

To build the development images, run:
```bash
docker compose --profile dev build
```

To build the production images, run:
```bash
docker compose --profile prod build
```

## Tests

All tests in the project are developed using [JUnit 5][8].

To execute all tests (with live reload), run:
```bash
docker compose --profile dev up tester
```

## Execution

To execute the project in development mode (with live reload), run:
```bash
docker compose --profile dev up demo --build
```

To execute the project in production mode, run:
```bash
docker compose --profile prod up game --build
```

## Other tasks

To find available gradle tasks, run:
```bash
docker compose --profile dev run --rm demo gradle --no-daemon tasks
```

To execute any task, run:
```bash
docker compose --profile dev run --rm demo gradle --no-daemon {task}
```

[1]: https://uspdigital.usp.br/jupiterweb/obterDisciplina?sgldis=MAC0218
[2]: https://gradle.org
[3]: https://kotlinlang.org
[4]: https://docs.docker.com
[5]: https://pre-commit.com
[6]: https://github.com/pinterest/ktlint
[7]: https://github.com/detekt/detekt
[8]: https://junit.org/junit5
