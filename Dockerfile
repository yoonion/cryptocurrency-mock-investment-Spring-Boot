FROM bellsoft/liberica-openjdk-alpine:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
# 도커 컨테이너를 시작할 때 실행할 명령어
ENTRYPOINT ["java","-jar","/app.jar"]