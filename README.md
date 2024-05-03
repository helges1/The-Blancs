# INF112 Prosject - *Blancs Game (Working Title)*

* Team: *The Blancs* (Gruppe X): *Stian Bekkeheien, Lasse Holt, Balder Benjamin Weidenhiller Hopp-Haugstvedt, Sebastian Helgesen, Michal Zborowski*

* Lenker:
[GitLab](https://git.app.uib.no/Michal.Zborowski/the-blancs)
[Trello](https://trello.com/b/tr1xRmAG/the-blancs)

## Om spillet
*"Romskipet Blancs sitter fast i en annen galakse. Skyt din vei hjem."*


## Kjøring 
- Spillet kjøres i main.java filen som ligger i main/java/main mappen.

- Tester kan kjøres i test mappen vår, og er satt opp med lik struktur som kodemappene våre. 


## Kjente feil
- GameOverScreen fungerer ikke skikkelig enda 
- Et par tester som feiler grunnet endring i kode

## Credits
`src/main/resources/pictures/TheBlancsTextureAtlas.png(enemyLaser)`  
https://opengameart.org/content/lasers-and-beams - Arthor: Rawdanitsu, License (CC0)

https://opengameart.org/content/explosion-set-1-m484-games - Arthor: Master484 , License (CC0)

`src/main/resources/pictures/TheBlancsTextureAtlas.png(playerLaser)`  
https://opengameart.org/content/spaceship-bullet - Arthor: vergil1018 , License (CC0)

`src/main/resources/pictures/TheBlancsTextureAtlas.png(shield)`  
https://opengameart.org/content/shield-effect - Arthor: Bonsaiheldin , License (CC-BY-3.0)

`src/main/resources/pictures/TheBlancsTextureAtlas.png(start-1.png , start-2.png, help-1.png , help-2.png , exit-1.png , exit-2.png , blank-1.png , blank-2.png)`  
https://opengameart.org/content/a-pack-of-games-buttons-2 - Arthor: plemuzic , License (CC0)

`src/main/resources/sound/laser1.mp3`  
https://opengameart.org/content/laser-fire - Arthor: dklon , License (CC-BY-3.0)

`src/main/resources/sound/ExplosionSound.mp3`  
https://opengameart.org/content/big-explosion - Arthor: Blender Foundation , License (CC-BY-3.0) 

`src/main/resources/pictures/TheBlancsTextureAtlas.png(playerShip, basicEnemyShip, strongerEnemyShip), game-icon.png`  
https://kenney.nl/assets/space-shooter-redux - Arthor: Kenny , License (CC0)

`src/main/resources/pictures/TheBlancsTextureAtlas.png(air-blast)`  
https://opengameart.org/content/animated-circular-air-blast-effect - Arthor: Julien Jorge (CC-BY-3.0)

`src/main/resources/music/GameOverMusic.ogg`  https://opengameart.org/content/game-over-iii - Author: Kistol (CC-BY-3.0) 

`src/main/resources/music/throughSpace.ogg`  https://opengameart.org/content/through-space - Author: maxstack (CC-BY-3.0) 

`src/main/resources/music/ville_seppanen-1_g.mp3`  https://opengameart.org/content/space-ambient - Author - Osmic (CC-BY-3.0) 





# INF112 libGDX + Maven template  
Simple skeleton with [libGDX](https://libgdx.com/). 

**Important:** Replace this README with info about *your* project!


# Maven Setup
This project comes with a working Maven `pom.xml` file. You should be able to import it into Eclipse using *File → Import → Maven → Existing Maven Projects* (or *Check out Maven Projects from SCM* to do Git cloning as well). You can also build the project from the command line with `mvn clean compile` and test it with `mvn clean test`.

Pay attention to these folders:
* `src/main/java` – Java source files go here (as usual for Maven) – **IMPORTANT!!** only `.java` files, no data files / assets
* `src/main/resources` – data files go here, for example in an `assets` sub-folder – **IMPORTANT!** put data files here, or they won't get included in the jar file
* `src/test/java` – JUnit tests
* `target/classes` – compiled Java class files

**TODO:** You should probably edit the `pom.xml` and fill in details such as the project `name` and `artifactId`:


```xml

	< !-- FIXME - set group id -->
	<groupId>inf112.skeleton.app</groupId>
	< !-- FIXME - set artifact name -->
	<artifactId>gdx-app</artifactId>
	<version>1.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	< !-- FIXME - set app name -->
	<name>mvn-app</name>
	< !-- FIXME change it to the project's website -->
	<url>http://www.example.com</url>
```

	
## Running
You can run the project with Maven using `mvn exec:java`. Change the main class by modifying the `main.class` setting in `pom.xml`:

```
		<main.class>inf112.skeleton.app.Main</main.class>
```

Running the program should open a window with the text “Hello, world!” and an alligator in the lower left corner.  Clicking inside the window should play a *blip* sound. Exit by pressing *Escape* or closing the window.

You may have to compile first, with `mvn compile` – or in a single step, `mvn compile exec:java`.

## Testing
Run unit tests with `mvn test` – unit test files should have `Test` in the file name, e.g., `ExampleTest.java`. This will also generate a [JaCoCo](https://www.jacoco.org/jacoco) code coverage report, which you can find in [target/site/jacoco/index.html](target/site/jacoco/index.html).

Use `mvn verify` to run integration tests, if you have any. This will do everything up to and including `mvn package`, and then run all the tests with `IT` in the name, e.g., `ExampleIT.java`.

## Jar Files

If you run `mvn package` you get everything bundled up into a `.jar` file + a ‘fat’ Jar file where all the necessary dependencies have been added:

* `target/NAME-VERSION.jar` – your compiled project, packaged in a JAR file
* `target/NAME-VERSION-fat.jar` – your JAR file packaged with dependencies

Run Jar files with, for example, `java -jar target/NAME-VERSION-fat.jar`.


If you have test failures, and *really* need to build a jar anyway, you can skip testing with `mvn -Dmaven.test.skip=true package`.

## Git Setup
If you look at *Settings → Repository* in GitLab, you can protect branches – for example, forbid pushing to the `main` branch so everyone have to use merge requests.




