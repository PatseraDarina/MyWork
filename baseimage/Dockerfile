FROM ubuntu:latest
MAINTAINER Artem Palahno <Artem_Palahno@epam.com>

COPY script/docker-entrypoint.sh script/scr/docker-entrypoint.sh
COPY go/main.go usr/bin/main.go
RUN apt-get update && apt-get install -y vim nano zsh curl git sudo
RUN sudo apt-get update
RUN sudo apt-get install -y git
RUN  useradd admin && echo "admin:admin" | chpasswd && adduser admin sudo
USER admin
ENTRYPOINT script/scr/docker-entrypoint.sh
