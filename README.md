

# 🚀 Reelly: Android Project Skeleton (Kotlin, Jetpack Compose, MVVM)

![MVVM Architecture](https://github.com/user-attachments/assets/f01ea363-b6ac-46e3-90f3-60915d08b05f)

## ✨ Overview


This project sets up a modern Android stack using **Kotlin, Jetpack Compose, and the MVVM architectural pattern**, ensuring you're ready to showcase your problem-solving skills in a contemporary environment.

-----

## 🛠️ Key Technologies & Architecture

This skeleton project comes pre-configured with the following modern Android technologies and architectural choices:

* **Kotlin:** The primary programming language for concise and expressive Android development.
* **Jetpack Compose:** Google's modern toolkit for building native Android UI. The project provides basic UI composables for handling various data states.
* **MVVM (Model-View-ViewModel):** A robust architectural pattern that separates concerns, making the codebase testable and maintainable.
* **Retrofit:** A type-safe HTTP client for Android and Java, pre-configured for network requests.
* **Coil:** A fast, lightweight image loading library for Android, built with Kotlin Coroutines.
* **StateFlow:** Part of Kotlin Coroutines, used for reactive state management within the MVVM architecture.
* **Dependency Injection (DI):** Managed via an `appmodule` for easy provision of dependencies like API services and repositories.

-----

## 📁 Project Structure

The project is structured with clear separation of concerns, following best practices for modularity in Android development:

* `data/api/`: Contains the `SubtitlesApi` interface, a placeholder for defining your network endpoints. It currently points to a fake endpoint and should be replaced with the actual API endpoint for your task.
* `data/model/`: Houses data classes that represent your network responses or domain entities. A sample `User` data class is provided as a placeholder.
* `data/repository/`: Contains repository interfaces and implementations, abstracting data sources (like network or local database).
* `di/appmodule/`: Manages dependency injection setup for the application.
* `ui/base/`: Contains base classes or common utilities for UI components.
* `ui/components/`: Reusable Jetpack Compose UI elements.
* `ui/screens/`: Holds the main composable screens (e.g., for the list and detail views).
* `ui/viewmodel/`: Contains the ViewModels that expose data to the UI and handle UI logic.

-----

## 🚦 Pre-configured UI States

To accelerate your UI development, basic **Jetpack Compose UI components** are provided to handle common data fetching states. These components offer a starting point for:

* **Initial State:** What the screen looks like before any data is loaded.
* **Loading State:** Showing a progress indicator while data is being fetched.
* **Error State:** Displaying an error message if the network request fails.
* **Success State:** Ready for you to implement the actual data display (e.g., your list of items).
* **Retry State:** A mechanism to re-attempt data fetching after an error.

These foundational UI elements mean you can jump straight into binding your fetched data to the UI.

-----
## Detailed project stucture
```
.
├── AndroidManifest.xml
├── java
│   └── com
│       └── gauravbajaj
│           └── reelly
│               ├── MainActivity.kt
│               ├── MyApplication.kt
│               ├── data
│               │   ├── api
│               │   │   └── SubtitlesApi.kt
│               │   ├── model
│               │   │   └── Subtitle.kt
│               │   └── repository
│               │       └── SubtitlesRepository.kt
│               ├── di
│               │   ├── AppModule.kt
│               │   └── Qualifiers.kt
│               ├── navigation
│               │   ├── Navigation.kt
│               │   └── Screens.kt
│               ├── ui
│               │   ├── base
│               │   │   ├── ScreenContent.kt
│               │   │   └── UIState.kt
│               │   ├── components
│               │   │   ├── ErrorMessage.kt
│               │   │   └── LoadingIndicator.kt
│               │   ├── screens
│               │   │   ├── VideoGalleryPickerScreen.kt
│               │   ├── theme
│               │   │   ├── Color.kt
│               │   │   ├── Theme.kt
│               │   │   └── Type.kt
│               │   └── viewmodel
│               │       └── VideoGalleryPickerViewModel.kt
│               └── utils
├── print.txt
└── res
    ├── drawable
    │   ├── ic_launcher_background.xml
    │   └── ic_launcher_foreground.xml
    ├── mipmap-anydpi-v26
    │   ├── ic_launcher.xml
    │   └── ic_launcher_round.xml
    ├── mipmap-hdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-mdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-xhdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-xxhdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-xxxhdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── raw
    │   └── users.json
    ├── values
    │   ├── colors.xml
    │   ├── strings.xml
    │   └── themes.xml
    └── xml
        ├── backup_rules.xml
        └── data_extraction_rules.xml
```

---

## Screenshots

Taken from Google Pixel 9 Pro

<p float="left">

<img width="300" alt="Screenshot_20250717_075816" src="https://github.com/user-attachments/assets/35aab9d0-72ce-438f-b466-f4a6da392269" />

<img width="300" alt="Screenshot_20250717_075840" src="https://github.com/user-attachments/assets/c7dbeb88-96ce-45df-9adc-6e8f1dccd0a8" />


<img width="300" alt="Screenshot_20250717_084305" src="https://github.com/user-attachments/assets/7dd857a8-cc48-4df7-b141-2d711562664e" />

</p>


-----


## 📞 Contact

For any questions or suggestions regarding this skeleton project, feel free to reach out.

* Project Link: [https://github.com/gbajaj/reelly](https://github.com/gbajaj/reelly)

-----
