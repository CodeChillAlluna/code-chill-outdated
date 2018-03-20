#!/bin/bash

# Installation de Docker
sudo su -
echo -e "[Unit]
Description=Docker Application Container Engine
Documentation=https://docs.docker.com
After=network-online.target docker.socket firewalld.service
Wants=network-online.target
Requires=docker.socket\n
[Service]
Type=notify
# the default is not to use systemd for cgroups because the delegate issues still
# exists and systemd currently does not support the cgroup feature set required
# for containers run by docker
ExecStart=/usr/bin/dockerd -H=tcp://0.0.0.0:2375
ExecReload=/bin/kill -s HUP \$MAINPID
LimitNOFILE=1048576
# Having non-zero Limit*s causes performance problems due to accounting overhead
# in the kernel. We recommend using cgroups to do container-local accounting.
LimitNPROC=infinity
LimitCORE=infinity
# Uncomment TasksMax if your systemd version supports it.
# Only systemd 226 and above support this version.
TasksMax=infinity
TimeoutStartSec=0
# set delegate yes so that systemd does not reset the cgroups of docker containers
Delegate=yes
# kill only the docker process, not all processes in the cgroup
KillMode=process
# restart the docker process if it exits prematurely
Restart=on-failure
StartLimitBurst=3
StartLimitInterval=60s\n
[Install]
WantedBy=multi-user.target" > /lib/systemd/system/docker.service
systemctl daemon-reload                                                                            
systemctl restart docker
export DOCKER_HOST=tcp://localhost:2375
if ! grep -qF "DOCKER_HOST=tcp://localhost:2375" /etc/environment
then
        echo "DOCKER_HOST=tcp://localhost:2375" >> /etc/environment
fi
source /etc/environment
systemctl restart docker
docker pull ubuntu
docker run --name code-chill -dti ubuntu /bin/bash