# Game Launcher - Fullstack Developer Assignment

This repository hosts two main projects:

1. **Game Launcher Backend** (Java Spring Boot): An API service to initialize and manage game sessions.
2. **Backoffice** (Node.js): A back-office application for viewing game sessions launched on the current day.

## Project Overview

The objective of this solution is to create a **Game Launcher API** and a **Backoffice** dashboard.

### Game Launcher API (Spring Boot)

This backend service enables users to launch a game session by:
1. Generating a unique session ID (UUID) for each game launch.
2. Storing session information in a MySQL database.
3. Redirecting users to the game URL, passing the session UUID and language parameter.

**Endpoint**: `GET http://localhost:8080/play/{game-code}`
- Generates and stores a new game session.
- Redirects to the game URL with session data and language settings.

You can pass the language as a query parameter. If no language is provided, the default value will be **`en`**.

#### Game Code Validation
To simulate scenarios where a **game code is not found**, certain validation rules are applied:
- The game code must be an alphanumeric string with a length of **7 characters**, as specified in the configuration file (`application.yml`). This allows for testing valid and invalid codes without needing a game repository.

### Backoffice Dashboard (Node.js)

A Node.js application with server-side rendering (SSR) to display a list of game sessions launched on the current day. This application fetches data from the MySQL database and renders it on an HTML page, offering an overview of daily game sessions.

**Endpoint**: `http://localhost:3000/`
- Displays a table of all game sessions created today, including:
  - Game code
  - Session ID
  - Time of creation

---

## Features

### Game Launcher API
- **Endpoint**: `GET /play/{game-code}`
  - Generates and stores a new game session.
  - Redirects to the game URL with session data and language settings.

### Backoffice Dashboard
- Displays a table of all game sessions created today, including:
  - Game code
  - Session ID
  - Time of creation

---

### Folder Details

1. **`/gamelauncher`**
   - Contains the Java-based Game Launcher API, built with Spring Boot.
   - Configurations and endpoints for managing game sessions are located here.

2. **`/game-launcher-backoffice`**
   - Contains the Node.js application for the back-office dashboard.
   - Renders data in an HTML view using EJS templates.

---

## Setup and Deployment

### Prerequisites
- Docker & Docker Compose

### Installation

1. **Start the Application with Docker Compose**

   Run the following command to start all services:

   ```bash
   docker-compose up
