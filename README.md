# Table of content
1. [Code-Chill](https://codechillalluna.github.io/code-chill/#code-chill)
	* [What is Code&Chill](https://codechillalluna.github.io/code-chill/#what-is-codechill-)
	* [Why Code&Chill](https://codechillalluna.github.io/code-chill/#why-codechill-)
	* [How can i use Code&Chill](https://codechillalluna.github.io/code-chill/#how-can-i-use-codechill-)
	* [Want to know more about Code&Chill ?](https://codechillalluna.github.io/code-chill/#want-to-know-more-about-codechill-)
2. [Features](https://codechillalluna.github.io/code-chill/#features)
	* [User Management](https://codechillalluna.github.io/code-chill/#user-management)
	* [Docker Management](https://codechillalluna.github.io/code-chill/#docker-management)
	* [Language Choice](https://codechillalluna.github.io/code-chill/#language-choice)
	* [Basic Terminal](https://codechillalluna.github.io/code-chill/#basic-terminal)
	* [Advanced Terminal](https://codechillalluna.github.io/code-chill/#advanced-terminal)
	* [Personnal server](https://codechillalluna.github.io/code-chill/#personnal-server)
3. [Docs](https://codechillalluna.github.io/code-chill/#docs)
4. [Installation guide](https://codechillalluna.github.io/code-chill/#installation-guide)

# Code-Chill

[![Travis branch](https://img.shields.io/travis/CodeChillAlluna/code-chill/master.svg?style=flat-square)](https://travis-ci.org/CodeChillAlluna/code-chill)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://github.com/CodeChillAlluna/code-chill/blob/master/LICENSE)
[![Maintainability](https://api.codeclimate.com/v1/badges/b61e96a6f14db189b5b1/maintainability)](https://codeclimate.com/github/CodeChillAlluna/code-chill/maintainability)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3373b12b915d4be68943182e1c2ff979)](https://www.codacy.com/app/Lulu300/code-chill?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=CodeChillAlluna/code-chill&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/CodeChillAlluna/code-chill/badge.svg?branch=master)](https://coveralls.io/github/CodeChillAlluna/code-chill?branch=master)


## What is Code&Chill ?
Code&Chill is an Online Development Environment.
It is an application allowing anyone to program and execute code in a web browser.
You can chose between hosting the application in your computer and hosting in an external machine.

## Why Code&Chill ?
We wanted to create something useful and worth for any programmer, from beginner to expert. We took advantage of our Master degree project to create something big and clean.

## How can I use Code&Chill ?
You can use Code&Chill on both computer and mobile devices.

## Want to know more about Code&Chill ?
You can click <a href="https://github.com/CodeChillAlluna/code-chill">here</a> to see more about Code&Chill.

# Features
## User Management 
<span style="color:grey;">Code&Chill allows you to manage the profile of your users. You can create a profile, delete it or update it. You have access to additionnal features such as forgotten password.</span>
## Docker Management
<span style="color:grey;">Our solution permits users to have a complete management interface for their dockers, they are able to link the docker to their profile, create or delete one with a terminal delivered with it.</span>
## Docker importation & exportation
<span style="color:grey;">Our users can export dockers they created in the platform. They can also import their own dockerfile if they want to use their personnal work environment. It is possible to import your personal .bashrc file to increase your user experience.</span>
## Language Choice
<span style="color:grey;">Code&Chill enables users to chose their own programmation language depending on their needs. You can choose C, C++, Java or any other language like Haskell. You can either add a language in command line or with a graphic interface.</span>
## Basic Terminal 
Our basic terminal is emulated by javascript and allows you to execute and send basic command line to a server.
## Advanced Terminal
<span style="color:grey;">The advanced terminal functions exactly like the one you can use on a linux system and provide features like autocompletion or tabulation to automaticaly end a command line.</span>
## Personnal server
<span style="color:grey;">Our users can use a personnal cloud server allowing them to use a server of their choice.</span>
## Fee Server
<span style="color:grey;">If you want us to manage your server, we can offer you a fee server and link it to your account. Note that this is optionnal and you do not have to pay.</span>

# Docs
Available later.

# Installation guide
* You need <a href="http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html">Java 8+</a> and <a href="https://nodejs.org/en/">NodeJS 9+</a>.
* You can install Code&Chill <a href="https://github.com/CodeChillAlluna/code-chill/releases">here</a>.
* Go to your spring repository : `cd ./spring`
* Run the command : `java -jar spring-0.1.0.jar`
* And then : `npm install serve`
* To run the server : `serve -s client` 
* You can go to http://localhost:5000/ on your browser to access to your terminal.