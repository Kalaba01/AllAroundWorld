# AllAroundWorld

![viber_image_2024-06-24_23-42-32-309](https://github.com/Kalaba01/AllAroundWorld/assets/130281220/95b08e91-c0cc-4463-be7b-d1c567923ef1)

## Overview

The All Around World News App is an Android application that provides news articles from various countries. Users can select a country to view news articles specific to that country. The app is built using Jetpack Compose for the UI, and it integrates Text-to-Speech functionality for reading news articles aloud.

## Project Structure
The project is organized into the following structure:

```
com.example.allaroundworld
|
├── navGraph
|   └── navGraph.kt
|
├── network
|   ├── ApiBuilder.kt
|   ├── ApiService.kt
|   ├── Constants.kt
|   └── NewsModel.kt
|
├── repo
|   └── Repo.kt
|
├── screen
|   ├── HomeApp.kt
|   ├── NewsDetailScreen.kt
|   ├── NewsViewModel.kt
|   └── Result.kt
|
├── ui.theme
|   └── AllAroundWorldTheme.kt
|
└── MainActivity.kt
```

## File Breakdown

### MainActivity.kt
MainActivity.kt is the entry point of the application. It sets up the UI using Jetpack Compose and handles the splash screen display.

### HomeApp.kt
HomeApp.kt contains the main screen of the app where users can view and select news articles.

### NewsDetailScreen.kt
NewsDetailScreen.kt displays detailed information about a selected news article.

### NewsViewModel.kt
NewsViewModel.kt manages the state and data for the news articles. It fetches news data and updates the UI accordingly.

### Result.kt
Result.kt defines a sealed class for handling different states of data loading (Success, Loading, Error).

### Repo.kt
Repo.kt handles data fetching from the news API.

### ApiService.kt
ApiService.kt defines the API service interface for network operations.

### ApiBuilder.kt
ApiBuilder.kt sets up the Retrofit instance for network operations.

### Constants.kt
Constants.kt defines constants used in the app, such as the base URL for the news API.

### NewsModel.kt
NewsModel.kt defines the data model for news articles.


## Implementation Details

### Country Selection and News Fetching
In HomeApp.kt, the top app bar contains a menu for selecting different countries. When a country is selected, the NewsViewModel updates the country code and fetches news articles for that country.

### Text-to-Speech
Both HomeApp.kt and NewsDetailScreen.kt implement Text-to-Speech functionality. When the user taps the volume icon, the app reads the news article's description or content aloud.

### Navigation
The app uses Jetpack Compose Navigation to navigate between the home screen and the news detail screen. The navHostController is used to navigate and pass data between these screens.

### Error Handling
The Result sealed class is used to handle different states of data loading, ensuring that the UI can respond appropriately to loading states or errors.


