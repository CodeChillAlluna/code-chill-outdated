#!/bin/bash

# Installation de Docker
export DOCKER_HOST=tcp://localhost:2375
if ! grep -qF "DOCKER_HOST=tcp://localhost:2375" /etc/environment
then
        echo "DOCKER_HOST=tcp://localhost:2375" >> /etc/environment
fi
source /etc/environment
systemctl restart docker
docker pull ubuntu
docker run --name code-chill -dti ubuntu /bin/bash