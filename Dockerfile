FROM node:22-alpine AS frontend
WORKDIR /build
COPY package*.json ./
RUN npm ci --ignore-scripts
COPY index.html vite.config.js ./
COPY public ./public
COPY src ./src
ENV VITE_API_URL=/api
RUN npm run build

FROM maven:3.9-eclipse-temurin-17 AS backend
WORKDIR /build
COPY backend-plato-para-todos/pom.xml ./pom.xml
COPY backend-plato-para-todos/src ./src
COPY --from=frontend /build/dist ./src/main/resources/static
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=backend /build/target/backend-plato-para-todos-0.0.1-SNAPSHOT.jar ./app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=70.0 -Dfile.encoding=UTF-8"
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
