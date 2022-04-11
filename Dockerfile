################################################################################
#                                  BASE IMAGE                                  #
################################################################################

# Use Gradle image to run Gradle commands
FROM gradle:7.4.2-jdk17 AS base

# Set default docker build args
ARG APP_DIR=/home/gradle/galaxy-raiders

# Change default workdir
WORKDIR ${APP_DIR}

# Set environment variable to disable Gradle Daemon
ENV GRADLE_OPTS=-Dorg.gradle.daemon=false

# Give the ownership of workdir to the Gradle user
RUN chown -R gradle:gradle .

# Change to non-priviled user (PID 1000)
USER gradle

# NOTE: This repo has gradle wrappers to ensure developers use the same version
# of gradle in every environment. However, using gradle's docker image already
# guarantees that. Therefore, commands below can use the `gradle` CLI directly.

# Copy source code to workdir
COPY --chown=gradle:gradle . .

# NOTE: Gradle download dependencies only when they are necessary, which slows
# tasks the first time they execute. Running them here will allow docker to
# cache these dependencies to speed up containers that execute gradle tasks.

# Force gradle to download dependencies to build, lint and test code
RUN gradle --no-build-cache clean

################################################################################
#                                BUILDER IMAGE                                 #
################################################################################

# Use base image to build the project
FROM base AS builder

# Use gradle wrapper to generate an uber JAR
RUN gradle --no-daemon shadowJar

################################################################################
#                               PRODUCTION IMAGE                               #
################################################################################

# Use Eclipse Temurin image to run the project
FROM eclipse-temurin:17-jdk AS runner

# Reuse default docker build args
ARG APP_DIR=/home/gradle/galaxy-raiders

# Copy runtime from image
COPY --from=builder ${APP_DIR}/app/build/libs/galaxy-raiders.jar \
                    /bin/runner/galaxy-raiders.jar

# Run uber JAR to start application
CMD ["java", "-jar", "galaxy-raiders.jar"]
