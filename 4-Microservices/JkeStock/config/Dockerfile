FROM anapsix/alpine-java:8
WORKDIR /
ADD target/JkeStock-0.0.1-SNAPSHOT.jar jke-stock.jar
RUN sh -c 'touch /jke-stock.jar'
EXPOSE 9033
ENTRYPOINT ["java","-jar","/jke-stock.jar"]
