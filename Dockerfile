
FROM maven:3.2-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD pom.xml /usr/src/app

RUN mvn package

RUN mvn dependency:resolve

ADD . /usr/src/app

RUN mvn package


#TODO second stage docker build

# Extend vert.x image
FROM vertx/vertx3

COPY --from=0  /usr/src/app/target/hello-verticle-3.4.2.jar /usr/src/app/target/hello-verticle-3.4.2.jar

RUN

RUN ls -lart /usr/src/app/target/

ENV VERTICLE_NAME io.vertx.sample.hello.HelloVerticle
ENV VERTICLE_FILE /usr/src/app/target/hello-verticle-3.4.2.jar

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080

RUN echo $VERTICLE_FILE

RUN ls -lart $VERTICLE_FILE

RUN mkdir -p  $VERTICLE_HOME/

# Copy your verticle to the container
COPY $VERTICLE_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
