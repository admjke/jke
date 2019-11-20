FROM anapsix/alpine-java:8
WORKDIR /
ADD build/libs/jkeapi-0.0.1-SNAPSHOT.jar jke_api.jar
RUN sh -c 'touch /jke_api.jar'
EXPOSE 80
ENTRYPOINT ["java","-jar","/jke_api.jar"]
