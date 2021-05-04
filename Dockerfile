FROM openjdk:16-jdk-alpine
WORKDIR /workspace/bakery-app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY common common
COPY user user
COPY product product
COPY checkout checkout
COPY app app

RUN chmod +x && ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../../app/target/*.jar)

FROM openjdk:16-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/bakery-app/target/dependency
COPY --from=0 ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=0 ${DEPENDENCY}/META-INF/ /app/META-INF
COPY --from=0 ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.natay.ecomm.bakery.BakeryApplication"]