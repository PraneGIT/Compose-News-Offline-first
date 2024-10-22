
# Compose News App

Compose News App is an offline-first news application built using Jetpack Compose, Kotlin, and Room. It fetches top headlines from the News API and allows users to view, search, and store news articles locally. The app ensures seamless user experience with offline access and the ability to retry fetching news when the network is available.

## Demo


https://github.com/user-attachments/assets/6975580b-3cad-46df-94e9-ea964aa2ba33


## Features




- Offline-first access to news articles
- Local database storage using Room
- Local news search functionality
- Retry option for fetching news when offline
- Modern UI with Jetpack Compose
- Dependency injection using Hilt

## Tech Stack

- **Kotlin**
- **Jetpack Compose** for UI
- **Room** for offline data storage
- **Hilt** for dependency injection
- **NewsAPI** for fetching top headlines

## How it works

- The app checks for network availability using a utility function.
- If the network is available, it fetches news articles from the API and stores them locally using Room.
- If the network is not available, the app loads cached articles from the local database.
- A Snackbar notification appears when the user is offline, with a retry button to attempt fetching news again.

## Prerequisites

- An active API key from [NewsAPI](https://newsapi.org/).
- Android Studio with Kotlin support.

