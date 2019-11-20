FROM anapsix/alpine-java:8
WORKDIR /
ADD target/JkeInterest-0.0.1-SNAPSHOT.jar jke-interest.jar
RUN sh -c 'touch /jke-interest.jar'
EXPOSE 9032
ENTRYPOINT ["java","-jar","/jke-interest.jar"]
