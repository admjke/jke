FROM anapsix/alpine-java:8
WORKDIR /
ADD build/libs/jkemq-0.0.1-SNAPSHOT.jar jke-batch.jar
RUN sh -c 'touch /jke-batch.jar'
EXPOSE 9031
ENTRYPOINT ["java","-jar","/jke-batch.jar"]
