FROM openjdk:17-jdk-slim

# 패키지 목록 업데이트 및 필수 패키지 설치
# ca-certificates와 curl을 설치하여 SSL 관련 작업을 수행할 수 있도록 함
# update-ca-certificates를 실행하여 최신 CA 인증서를 적용
# 불필요한 패키지 캐시를 삭제하여 이미지 크기를 줄임
RUN apt-get update && \
    apt-get install -y --no-install-recommends ca-certificates curl openssl && \
    update-ca-certificates && \
    rm -rf /var/lib/apt/lists/*

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
# 도커 컨테이너를 시작할 때 실행할 명령어
ENTRYPOINT ["java","-jar","/app.jar"]