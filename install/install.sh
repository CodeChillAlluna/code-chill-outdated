#!/bin/bash

vagrant=/vagrant
src=/vagrant/src
client=$src/client
server=$src/spring
HOME_DIR=/home/vagrant

print_help () {
  echo "#################################################"
  echo "                     HELP                        "
  echo "#################################################"
  echo "Connect to the VM: 'vagrant ssh'"
  echo "Shutdown the VM: 'vagrant halt'"
  echo "Launch the VM: 'vagrant up'"
  echo "Reload the VM: 'vagrant reload'"
  echo "Delete the VM: 'vagrant destroy'"
  echo "Verify packages are up to date: 'vagrant provision'"
}

export DEBIAN_FRONTEND=noninteractive

# if ! grep -qF "sudo mount --bind $HOME_DIR/vagrant_node_modules $client/node_modules" /home/vagrant/.bashrc
# then
#         echo "sudo mount --bind $HOME_DIR/vagrant_node_modules $client/node_modules" >> /home/vagrant/.bashrc
# fi

if ! grep -qF "cd "$vagrant /home/vagrant/.bashrc
then
        echo "cd "$vagrant >> /home/vagrant/.bashrc
fi

# Create install folder
mkdir -p $client
mkdir -p $server

# Update system
sudo apt-get update
sudo apt-get upgrade -y

# Install dependencies for installation
sudo apt-get install software-properties-common -y
sudo apt-get install curl -y

# Install screen
sudo apt-get install -y screen

# Install git
sudo apt-get install -y git

# Install node
curl -sL https://deb.nodesource.com/setup_9.x | sudo -E bash -
sudo apt-get install -y nodejs

# Install yarn
curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
echo "deb https://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list
sudo apt-get update
sudo apt-get install yarn -y

# Install jdk
sudo apt-get install -y openjdk-8-jdk openjdk-8-jre

# Install build automation
sudo apt-get install -y maven


# Installation de postgresql
sudo apt-get install -y python-dev
sudo apt-get install -y libpq-dev
sudo apt-get install -y postgresql
sudo apt-get install -y postgresql-contrib

# Configuration de la BDD postgresql
DB_USER=code
DB_PWD=chill
DB_NAME=codechill
DB_NAME_TEST=codechill_test

sudo service postgresql restart
sudo su - postgres -c psql <<EOF
CREATE DATABASE $DB_NAME;
CREATE USER $DB_USER WITH PASSWORD '$DB_PWD';

ALTER ROLE $DB_USER SET client_encoding TO 'utf8';
ALTER ROLE $DB_USER SET default_transaction_isolation TO 'read committed';
ALTER ROLE $DB_USER SET timezone TO 'UTC';

GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;
ALTER USER $DB_USER CREATEDB;
EOF

sudo su - postgres -c psql <<EOF
CREATE DATABASE $DB_NAME_TEST;
CREATE USER $DB_USER WITH PASSWORD '$DB_PWD';

ALTER ROLE $DB_USER SET client_encoding TO 'utf8';
ALTER ROLE $DB_USER SET default_transaction_isolation TO 'read committed';
ALTER ROLE $DB_USER SET timezone TO 'UTC';

GRANT ALL PRIVILEGES ON DATABASE $DB_NAME_TEST TO $DB_USER;
ALTER USER $DB_USER CREATEDB;
EOF

# Installation de Docker
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get update
sudo apt-get install docker-ce -y
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

# Install nginx
sudo apt-get install nginx

# Install js packages
cd $client
yarn global add create-react-app
yarn global add serve
yarn global add typescript
yarn global add react-scripts-ts
yarn global add gulp

sudo apt-get install make -y
sudo apt-get install g++ -y

# Fix error with shared folder and npm modules
# https://medium.com/@dtinth/isolating-node-modules-in-vagrant-9e646067b36
mkdir $HOME_DIR/vagrant_node_modules
sudo chown -R vagrant:vagrant $HOME_DIR/vagrant_node_modules
mkdir $client/node_modules
# sudo mount --rbind $HOME_DIR/vagrant_node_modules $client/node_modules

# Install client dependencies
yarn add git+https://github.com/CodeChillAlluna/xterm.js.git --no-bin-links
yarn install --no-bin-links
cd node_modules/xterm
npm install --no-bin-links

print_help
