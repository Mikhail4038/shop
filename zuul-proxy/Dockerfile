FROM openjdk:8-jdk-alpine
COPY /target/zuul-proxy.jar zuul-proxy.jar
ENTRYPOINT ["java","-jar","zuul-proxy.jar"]