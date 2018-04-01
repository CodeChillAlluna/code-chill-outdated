#!/bin/bash

# Installation de Docker
export DOCKER_HOST=tcp://localhost:2375
if ! grep -qF "DOCKER_HOST=tcp://localhost:2375" /etc/environment
then
        echo "DOCKER_HOST=tcp://localhost:2375" >> /etc/environment
fi
source /etc/environment
systemctl restart docker

git clone https://github.com/CodeChillAlluna/DockerFiles.git
docker build -f DockerFiles/CodeChill-Ubuntu/DockerFile -t codechill/ubuntu-base .
docker build -f DockerFiles/CodeChill-Ubuntu-User/DockerFile -t codechill/ubuntu-base-user .
rm -R DockerFiles
docker run --name codechill -dti codechill/ubuntu-base-user /bin/bash
