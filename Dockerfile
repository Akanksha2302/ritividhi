FROM docker.io/library/amazoncorretto:17-alpine3.17
RUN apk add --update curl && rm -rf /var/cache/apk/*
ADD target/ritividhi*.jar ritividhi.jar
ADD src/main/resources/ritividhi-gcp-key.json ritividhi-gcp-key.json
ENV GOOGLE_APPLICATION_CREDENTIALS=/ritividhi-gcp-key.json
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ritividhi.jar"]