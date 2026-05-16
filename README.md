# University Event Booking System

A Java desktop application for managing university event reservations, built with JavaFX.

## Features

**Admin**
- Create, edit, and delete university events
- View all events in a dashboard

**User**
- Browse available events
- Register and cancel registrations

## Tech Stack

- Java 17
- JavaFX 21
- Maven

## How to Run

**Prerequisites:** Java 17+, Maven

```bash
git clone https://github.com/cn7n/IcsProject.git
cd IcsProject
mvn javafx:run
```

## Login

| Role  | Username | Password |
|-------|----------|----------|
| Admin | admin    | admin    |
| User  | user     | user     |

## Project Structure

```
src/
└── main/
    ├── java/org/example/secondscene/
    │   ├── Main.java               # App entry point
    │   ├── Event.java              # Event model (OOP)
    │   ├── EventStore.java         # Shared data store
    │   ├── Scene1Controller.java   # Login controller
    │   ├── Scene2Controller.java   # Admin controller
    │   └── userController.java     # User controller
    └── resources/org/example/secondscene/
        ├── firstscene.fxml         # Login screen
        ├── AdminScene.fxml         # Admin dashboard
        ├── UserScene.fxml          # User dashboard
        └── styles.css              # UI stylesheet
```
