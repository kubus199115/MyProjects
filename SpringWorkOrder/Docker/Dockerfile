FROM ubuntu:18.04
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update
RUN apt-get -y install apt-utils
RUN apt-get -y install openjdk-8-jdk
RUN apt-get -y install mysql-server
COPY . /home
RUN chmod +x /home/sc.sh
CMD [ "sh", "/home/sc.sh" ]
