# The MovieDB App

The MovieDB App is a feature-rich movie streaming application that allows users to explore popular
movies, browse by genres, and view detailed information about each movie. The app is built with a
scalable architecture, follows best practices, and leverages modern Android technologies to deliver
a seamless experience.

---

## Features

- **Dynamic Carousel**: Displays the top five popular movies.
- **Genre-Based Browsing**: Movies categorized by genres for easy navigation.
- **Detailed Views**: Access comprehensive details about any selected movie.
- **Pagination**: Efficiently fetches and displays data in chunks to ensure performance.

---

## Architecture Overview

The project adheres to the **MVVM** (Model-View-ViewModel) architecture pattern, designed for
scalability and maintainability:

### 1. **Presentation Layer**

- **ViewModels** manage UI-related data and handle user interactions.
- Observes `StateFlow` for reactive UI updates.

### 2. **Domain Layer**

- **UseCases** encapsulate business logic and mediate between the Presentation and Data layers.
- Promotes separation of concerns.

### 3. **Data Layer**

- **Repositories** serve as single sources of truth by abstracting data sources (remote API and
  local database).
- Utilizes Retrofit for API calls and Room for local storage.

### 4. **Dependency Injection**

- Managed by **Hilt**, ensuring easy dependency management and testing.

---

## Build and Run Instructions

### Prerequisites

1. **Android Studio**: Latest stable version.
2. **JDK**: Version 11 or higher.
3. **Gradle**: Included with Android Studio.

### Steps

1. Clone the repository:
   ```bash
   git clone git@github.com:matiascestoni/themoviedb.git
   cd moviedb-app
   ```

2. Open the project in Android Studio.
3. Sync Gradle and resolve dependencies.
4. Build the project and run on an emulator or physical device:
    - **Run**: Click the green play button in Android Studio.

---

## Testing

### Unit Tests

- Run all unit tests:
  ```bash
  ./gradlew test
  ```

### Instrumentation Tests

- Run all Android instrumentation tests:
  ```bash
  ./gradlew connectedAndroidTest
  ```

- Ensure a connected device or emulator is running before executing the above command.

---

## Libraries and Tools

- **Hilt**: For dependency injection.
- **Retrofit**: For network requests.
- **Room**: For local database management.
- **Coroutines**: For asynchronous programming.
- **StateFlow**: For reactive UI state management.

---

## Scalability

This project is designed to scale efficiently:

- Modular layers allow for the addition of new features (e.g., search functionality, user
  authentication).
- Decoupled components enable easy testing and maintenance.
- Optimized data handling supports growing datasets with pagination.

---

## Contribution Guidelines

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a feature branch.
3. Commit your changes with descriptive messages.
4. Submit a pull request.

---