#/usr/bin/env bash

vagrant=/vagrant
install=$vagrant/install
client=$vagrant/client
server=$vagrant/server

print_help () {
  echo "#################################################"
  echo "                     HELP                        "
  echo "#################################################"
  echo "Connect to the VM: 'vagrant ssh'"
  echo "Shutdown the VM: 'vagrant halt'"
  echo "Launch the VM: 'vagrant up'"
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
mkdir $client
mkdir $server

# Update system
apt-get update
apt-get upgrade -y

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

# Install js packages
cd $client
yarn global add create-react-app
yarn add -D enzyme @types/enzyme react-addons-test-utils
yarn add -S redux react-redux @types/react-redux

print_help