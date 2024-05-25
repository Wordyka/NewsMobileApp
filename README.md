# News Mobile App

This repository contains the code for a News Application for Android. The app displays news articles based on different categories such as Technology, Sports, Science, Health, Business, and Entertainment. The app fetches news from the [News API](https://newsapi.org/), utilizing Retrofit for network requests, JSON for data parsing, and Picasso for image loading.

## Features

- Display news articles based on categories.
- Fetch news data using News API.
- Load images using Picasso.
- Parse JSON data using Retrofit and GsonConverter.

## Getting Started

### Prerequisites

- Android Studio
- Minimum SDK version 26
- Target SDK version 34
- Gradle version 8.6
- JDK version 17

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Wordyka/NewsMobileApp.git
    ```
2. Open the project in Android Studio.
3. Configure the project with the following settings:

   ```groovy
   android {
       namespace = "com.example.mandirinews"
       compileSdk = 34

       defaultConfig {
           applicationId = "com.example.mandirinews"
           minSdk = 26
           targetSdk = 34
           versionCode = 1
           versionName = "1.0"

           testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
   }

### Depedencies
Add the following dependencies in your build.gradle file:
```
// Picasso for image loading
implementation("com.squareup.picasso:picasso:2.8")

// Retrofit for network requests
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

### API Integration
To fetch news data, use the following URLs with the News API:
- Base URL for Retrofit:
```
"https://newsapi.org/"
```
- For category-specific news:
```
"https://newsapi.org/v2/top-headlines/?country=us&category="+category+"&apiKey=3202f7abcee84c0a9ff08eeb07ab7977"
```
- For general news:
```
"https://newsapi.org/v2/top-headlines/?country=us&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=3202f7abcee84c0a9ff08eeb07ab7977"
```
