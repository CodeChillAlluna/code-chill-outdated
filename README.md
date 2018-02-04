# code-chill

[![Travis branch](https://img.shields.io/travis/CodeChillAlluna/code-chill/master.svg?style=flat-square)](https://travis-ci.org/CodeChillAlluna/code-chill)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://github.com/CodeChillAlluna/code-chill/blob/master/LICENSE)
[![Maintainability](https://api.codeclimate.com/v1/badges/b61e96a6f14db189b5b1/maintainability)](https://codeclimate.com/github/CodeChillAlluna/code-chill/maintainability)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3373b12b915d4be68943182e1c2ff979)](https://www.codacy.com/app/Lulu300/code-chill?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=CodeChillAlluna/code-chill&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/CodeChillAlluna/code-chill/badge.svg?branch=master)](https://coveralls.io/github/CodeChillAlluna/code-chill?branch=master)

Master project : Online development environment

# Installation guide
## Environment installation
### Requirements
* **Vagrant** is needed, you can download it here : 
https://www.vagrantup.com/downloads.html
* **VirtualBox** needs to be installed. You can download it here : https://www.virtualbox.org/wiki/Downloads
### Download
Choose either of the solutions:
- Cloning the repository : `git clone https://github.com/CodeChillAlluna/code-chill.git`
- Downloading the repository : `https://github.com/CodeChillAlluna/code-chill/archive/master.zip`

### Setup vagrant
1. Move to the project directory : `cd code-chill`
2. Run the command : `vagrant up`.
2. Wait for Vagrant to download all dependencies.
3. Run `vagrant ssh` to connect to the virtual machine.
4. You should be connected to the VM, in `/vagrant` where the project is located.
### Common commands
- Connect to the VM: `vagrant ssh`
- Shutdown the VM: `vagrant halt`
- Launch the VM: `vagrant up`
- Reload the VM: `vagrant reload`
- Delete the VM: `vagrant destroy`
- Verify packages are up to date: `vagrant provision`

## Usage
## Usage
### Build Spring
To build Spring ,  you will have to use the following command lines : 

1. cd src/spring : will place you in the source dictionnary.
2. sh mvnw clean package : will compile java files to create a runnable jar package.
3. cd target : will place you in the build dictionnary of the project.
4. java -jar spring-0.0.1-SNAPSHOT.jar : will launch the Spring project, depending on what you are using the version might not always be "0.0.1".
### Build React
To build React  ,  you will have to use the following command lines : 
1. cd src/client : will place you in the source dictionnary.
2. sudo yarn install : will install if they are missing and update the dependencies of the project.
2. yarn build : will build the project.
3. serve -s build : will launch the project properly.
