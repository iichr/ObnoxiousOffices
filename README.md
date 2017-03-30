# ObnoxiousOffices

[![Join the chat at https://gitter.im/ObnoxiousOffices/Lobby](https://badges.gitter.im/ObnoxiousOffices/Lobby.svg)](https://gitter.im/ObnoxiousOffices/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

documentition file:
https://docs.google.com/document/d/1tHW-Oq5dcD3EwIc-kVH9BfQ34VwjgWgUhWI00lU-1UY/edit

# Before you run

Please download the following zip , unzip it and add the jars into the libraries 
https://drive.google.com/file/d/0ByPIa9XrePKUSEdxM2UtMkdJVE0/view?usp=sharing

## In order to get music to work

- You need to add **two** more jars to your libraries

- Although they come with the **Slick** package already, they may not be loaded by default.

  Those are:

```
jogg-0.0.7.jar

jorbis-0.0.15.jar
```

Their default location within the slick package is:

```
your_slick_folder/lib
```

In Eclipse:

*Note: This is assuming you have your own user library set up.*

1. Right click your custom made Slick library for the project.
2. Click on **Properties**
3. Click on the **User Libraries…** button 
4. Click on **Add external JARs**
5. Find the above two files and add them.

# Running the game
All commands should be run from the root of the submitted .zip file

Compile: `javac -cp libs/jogg-0.0.7.jar:libs/jorbis-0.0.15.jar:libs/lwjgl_util.jar:libs/lwjgl.jar:libs/slick.jar:src src/game/DevWars.java`

Run client: `java -cp libs/jogg-0.0.7.jar:libs/jorbis-0.0.15.jar:libs/lwjgl_util.jar:libs/lwjgl.jar:libs/slick.jar:src game/DevWars`

Run server: `java -cp libs/jogg-0.0.7.jar:libs/jorbis-0.0.15.jar:libs/lwjgl_util.jar:libs/lwjgl.jar:libs/slick.jar:src game/networking/Server`
