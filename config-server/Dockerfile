FROM eclipse-temurin:17 as app-jdk
ENV RELEASE=17

WORKDIR /opt/build
COPY ./target/config-server.jar ./application.jar

RUN java -Djarmode=layertools -jar application.jar extract
RUN $JAVA_HOME/bin/jlink \
         --add-modules `jdeps --ignore-missing-deps -q -recursive --multi-release ${RELEASE} --print-module-deps -cp 'dependencies/BOOT-INF/lib/*':'snapshot-dependencies/BOOT-INF/lib/*' application.jar` \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output jdk

FROM debian:buster-slim

ARG BUILD_PATH=/opt/build
ENV JAVA_HOME=/opt/jdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"

RUN groupadd --gid 1000 app-user \
  && useradd --uid 1000 --gid app-user --shell /bin/bash --create-home app-user

USER app-user:app-user
WORKDIR /opt/workspace

COPY --from=app-jdk $BUILD_PATH/jdk $JAVA_HOME
COPY --from=app-jdk $BUILD_PATH/spring-boot-loader/ ./
COPY --from=app-jdk $BUILD_PATH/dependencies/ ./
COPY --from=app-jdk $BUILD_PATH/snapshot-dependencies/ ./
COPY --from=app-jdk $BUILD_PATH/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]