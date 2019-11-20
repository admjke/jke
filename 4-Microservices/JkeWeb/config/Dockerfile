FROM anapsix/alpine-java:8
WORKDIR /
ADD build/libs/jkeweb-0.0.1-SNAPSHOT.jar jke-web.jar
RUN sh -c 'touch /jke-web.jar'
EXPOSE 9030
ENTRYPOINT ["java","-jar","/jke-web.jar"]