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

if ! grep -qF "cd "$vagrant /home/ubuntu/.bashrc
then
        echo "cd "$vagrant >> /home/ubuntu/.bashrc
fi

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

# Installation de Docker
sudo apt-get install -y docker.io

# Install js packages
cd $client
sudo yarn global add create-react-app
sudo yarn global add serve

# Fix error with shared folder and npm modules
# https://medium.com/@dtinth/isolating-node-modules-in-vagrant-9e646067b36
mkdir $HOME_DIR/vagrant_node_modules
sudo chown -R vagrant:vagrant $HOME_DIR/vagrant_node_modules
mkdir $client/node_modules
sudo mount --bind $HOME_DIR/vagrant_node_modules $client/node_modules

# Install client dependencies
sudo yarn install

print_help
