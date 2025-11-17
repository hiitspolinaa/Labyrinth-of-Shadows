# Labyrinth-of-Shadows
A dynamic maze game where players log in, navigate a generated labyrinth, collect treasures and reach the exit. Includes scoring, collision detection, save/load with MySQL, and a GUI with movement controls. Uses OOP with Player, Maze and GameController classes. 
This project is a simple labyrinth game built for the Object-Oriented Programming (2024–2025) course. The player must navigate through a dynamically generated maze, collect treasures, and reach the exit with the highest possible score. The game supports user login, saving/loading progress, and uses a MySQL database to store game data.

Game Description:
 A simple object-oriented Labyrinth Game where the player moves through a dynamically generated maze, collects treasures, and tries to reach the exit with the highest score. The game includes user login, a graphical interface, and MySQL-based save/load functionality.

 Features: 

User login & registration

Dynamic maze generation

Player movement in four directions

Treasure collection that increases score

Win by reaching the exit

Lose by hitting a wall

Save & load game state using MySQL

Simple and intuitive GUI

  Main Components:
Player

Position in the maze

Score and collected treasures

Movement and exit detection

Maze

Dynamic grid with walls, paths, treasures, and exit

Functions to check walls, treasures, and exit location

GameController

Starts a new game

Manages each turn

Ends the game and updates score

Saves and loads the game from the database

GUI:

Visual display of the maze

Movement buttons (⬆️⬇️⬅️➡️)

Player info panel (position, score, treasures)

Save, Load, and Exit buttons

 Database (MySQL)

Used to store:

User accounts

Game state

Maze layout

Treasure data

 
