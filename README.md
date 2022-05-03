# Recipe keeper console app

[![build](https://github.com/NamraFatima16/recipe-app-console/actions/workflows/build.yml/badge.svg)](https://github.com/NamraFatima16/recipe-app-console/actions/workflows/build.yml)

This is a simple console app written in Kotlin, created as part of _Assignment 3_ for the 2022 Software Development Tools course at the Waterford Insitute of technology. 

## App Description
In a nutshell, the app allows users to store a list of recipies. Users interact with the app by a _menu_ system UI in the console. Users are allowed to 
- Add a recipe 
- Delete a recipe
- Update a recipe
- Retrieve recipe

showcasing simple [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) methods. Additionally, there are a variety of options for the user to search
the stored recipies by certain criterias such as
- total time required 
- what type of meal is it - lunch,dinner or breakfast
- etc.

The recipies can also be persisted to disk in **XML** or **JSON** formats. By default the file to which the recipies are persisted is named `recipies.xml/json`

## Getting started

### Building from source
1. Clone the repository
```bash
git clone https://github.com/NamraFatima16/recipe-app-console.git
cd recipe-app-console
```
2. Execute the build script on a terminal (Command prompt in windows)
```
./gradlew build -x test
```
This will create a build folder in the current directory and you should find a built jar file at `./build/libs/recipe-app-1.0.jar` 

### Prebuilt
You can find a prebuilt jar file from the 1.0 release or from the latest CI workflow which automatically generates a built jar file artifact.

### Running the app
You require java to run the app. The app can be started by executing
```
java -jar recipe-app-1.0.jar
```
this will greet you with the following screen, which is the main menu. 


![Screenshot 2022-05-03 213227](https://user-images.githubusercontent.com/78031248/166561307-f34abac8-39b0-4d3f-bfae-d1e0bd89bb64.png)

The rest of the menu system is pretty self-explanatory
## Authors

- Namra Fatima 
