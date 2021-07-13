FROM openjdk:16-jdk-alpine

ARG JYTHON_VERSION=2.7.0
ARG JYTHON_HOME=/usr/src/jython-$JYTHON_VERSION

ENV JYTHON_VERSION=$JYTHON_VERSION
ENV JYTHON_HOME=$JYTHON_HOME
ENV PATH=$PATH:$JYTHON_HOME/bin

RUN apk update

RUN set -eux && \
    apk add --no-cache bash && \
    apk add --no-cache zip && \
    apk add --no-cache apache-ant


WORKDIR /usr/src

RUN set -eux && \
    apk add --no-cache wget && \
     wget -cO jython-installer.jar "http://search.maven.org/remotecontent?filepath=org/python/jython-installer/$JYTHON_VERSION/jython-installer-$JYTHON_VERSION.jar" && \
     java -jar jython-installer.jar -s -t minimum -d "$JYTHON_HOME" && \
     rm -fr "$JYTHON_HOME"/Docs "$JYTHON_HOME"/Demo "$JYTHON_HOME"/tests && \
     rm -f jython-installer.jar && \
     ln -sfv "$JYTHON_HOME/bin/"* /usr/local/bin/ && \
     wget -cO jfreechart.zip "https://sourceforge.net/projects/jfreechart/files/1.%20JFreeChart/1.0.15/jfreechart-1.0.15.zip/download" && \
     unzip jfreechart.zip && \
     rm -f jfreechart.zip && \
     wget -cO rxtx.zip "http://rxtx.qbang.org/pub/rxtx/rxtx-2.1-7-bins-r2.zip" && \
     unzip rxtx.zip && \
     rm -f rxtx.zip && \
     apk del wget

WORKDIR /usr/src/app
COPY . .

RUN ant compile
RUN ant jar

# Forcing the exit code since there are some errors in the javadoc
RUN ant javadoc; exit 0

WORKDIR /usr/src/app/src/modbuspal/
RUN zip -r /usr/src/app/dist/modbuspal-help.zip help
WORKDIR /usr/src/app/dist
RUN zip -r modbuspal-javadoc.zip javadoc

