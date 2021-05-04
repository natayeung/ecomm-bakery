FROM openjdk:16-jdk-alpine as build
WORKDIR /workspace/bakery-app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY common common
COPY user user
COPY product product
COPY checkout checkout
COPY app app

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../../app/target/*.jar)

FROM openjdk:16-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/bakery-app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF/ /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.natay.ecomm.bakery.BakeryApplication"]