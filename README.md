# Simple Banking System

A simple immitation of the bank system. It is based on Java and built with Intellij IDEA.


## Building Simple Banking System

Source code is available from https://github.com/kairoslav/simple-banking-system by either cloning or downloading a zip file into `<SOURCE_HOME>`.
* Using IntelliJ IDEA
  #### Opening source code for build
  Using IntelliJ IDEA **File | Open**, select the `<SOURCE_HOME>/banking` directory.

  #### Building configuration
  JDK version 1.8 or newer is required for building and developing.

  #### Import Gradle project
  For the first time after cloning or downloading, choose **Event Log** from the main menu, click **Import Gradle project**.
  Check **Use auto-import**, specify **Gradle JVM** and click **OK**.

  #### Running Simple Banking System
  To run application built from source, choose `./simple-banking-system/src/main/java/banking/Main.java`, right click on this file,     choose **Run \'Main.main()\'**. 
  
  You must also specify 2 command line arguments `-fileName db.sqlite3`.


* Using Gradle
  #### Opening source code for build
  Navigate to `<SOURCE_HOME>/banking` directory.

  #### Build and test
  Run `./gradlew build` or run without test `./gradlew build -x test`.

  #### Running Simple Banking System
  Run `java -jar build/libs/simple-banking-system.jar -fileName db.sqlite3`
