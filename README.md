# AccessNews - Accessible RSS News Reader

A modern, accessibility-first Android news reader application built with cutting-edge technologies and following best practices.

---

## Overview

AccessNews is a professional Android application that demonstrates modern Android development practices with a strong focus on accessibility and inclusive design. Built entirely with Kotlin and Jetpack Compose, it showcases advanced architectural patterns and state-of-the-art Android development tools.

**Platform:** Android
**Language:** Kotlin 100%
**Min SDK:** 24 (Android 7.0)
**Target SDK:** 36
**Architecture:** MVI + Clean Architecture

---

## Key Highlights

**Accessibility-First Design**
Built from the ground up with accessibility as a core feature, not an afterthought

**Modern Stack**
Koin, Ktor, SQLDelight, Jetpack Compose

**MVI Architecture**
Predictable state management with unidirectional data flow

**Comprehensive Testing**
Unit tests with Kotest, MockK, and Turbine

**Clean Architecture**
Clear separation of concerns across layers

---

## Features

### Core Functionality

**RSS/Atom Feed Reader**
Support for multiple feed formats

**Bookmarks**
Save articles for later reading

**Offline Reading**
Local caching with SQLDelight

**Search**
Find articles and feeds quickly

**Multiple Categories**
Organize feeds by topic

### Accessibility Features (WCAG 2.1 Level AA Compliant)

**Visual Accessibility**
- Full TalkBack and screen reader support
- High contrast themes for better visibility
- Dynamic text scaling from 80% to 200%
- Semantic labels and descriptions for all UI elements

**Motor Accessibility**
- Minimum 48dp touch targets on all interactive elements
- Full keyboard navigation support
- Switch Access compatible
- Alternative input methods support

**Cognitive Accessibility**
- Simple and clear navigation patterns
- Distraction-free reading mode
- Consistent UI patterns throughout the app
- Clear focus indicators for navigation

---

## Tech Stack

### Architecture & Patterns

**MVI (Model-View-Intent)**
Unidirectional data flow

**Clean Architecture**
Domain, Data, Presentation layers

**Repository Pattern**
Abstract data sources

**Use Cases**
Encapsulated business logic

### Core Technologies

| Category | Technology | Purpose |
|----------|-----------|---------|
| **Language** | Kotlin 100% | Modern, type-safe language |
| **UI Framework** | Jetpack Compose | Declarative UI |
| **Dependency Injection** | Koin 3.5+ | Lightweight DI |
| **Networking** | Ktor Client | Modern async HTTP client |
| **Database** | SQLDelight | Type-safe SQL |
| **Async** | Coroutines + Flow | Structured concurrency |
| **Architecture** | MVI | Predictable state management |
| **Image Loading** | Coil | Compose-first image loading |
| **Material Design** | Material 3 | Latest design system |

### Testing Stack

| Type | Technology |
|------|-----------|
| **Unit Testing** | Kotest, JUnit |
| **Mocking** | MockK |
| **Flow Testing** | Turbine |
| **DI Testing** | Koin Test |
| **UI Testing** | Compose Test |
| **Coroutines** | Coroutines Test |

---

## Project Structure

```
app/
├── core/
│   ├── mvi/                    # MVI base classes
│   │   ├── ViewState.kt
│   │   ├── ViewIntent.kt
│   │   ├── ViewEffect.kt
│   │   └── BaseViewModel.kt
│   ├── common/                 # Common utilities
│   │   ├── Resource.kt
│   │   ├── Constants.kt
│   │   └── Extensions.kt
│   ├── accessibility/          # Accessibility utilities
│   └── designsystem/          # Reusable UI components
│
├── data/                       # Data layer
│   ├── local/                  # Local data sources
│   │   ├── DatabaseDriverFactory.kt
│   │   └── LocalDataSource.kt
│   ├── remote/                 # Remote data sources
│   │   ├── KtorClientFactory.kt
│   │   ├── RssParser.kt
│   │   ├── RemoteDataSource.kt
│   │   └── dto/                # Data transfer objects
│   ├── mapper/                 # Data mappers
│   └── repository/             # Repository implementations
│
├── domain/                     # Domain layer
│   ├── model/                  # Domain models
│   │   ├── Article.kt
│   │   └── Feed.kt
│   └── repository/             # Repository interfaces
│       ├── ArticleRepository.kt
│       └── FeedRepository.kt
│
├── presentation/               # Presentation layer
│   ├── feeds/                  # Feeds screen
│   ├── articles/               # Articles list screen
│   ├── reader/                 # Article reader screen
│   ├── settings/               # Settings screen
│   └── navigation/             # Navigation setup
│
└── di/                         # Dependency injection
    └── AppModule.kt
```

---

## Architecture

### Clean Architecture Layers

```
┌─────────────────────────────────────┐
│      Presentation Layer             │
│  (Composables, ViewModels, MVI)     │
└───────────┬─────────────────────────┘
            │
┌───────────▼─────────────────────────┐
│       Domain Layer                  │
│  (Models, Repository Interfaces)    │
└───────────┬─────────────────────────┘
            │
┌───────────▼─────────────────────────┐
│        Data Layer                   │
│ (Repositories, DataSources, DTOs)   │
└─────────────────────────────────────┘
```

### MVI Pattern Flow

```
User Action → Intent → ViewModel → State Update → UI Render
                ↓
           Side Effect → One-time Event
```

---

## Getting Started

### Prerequisites

Android Studio Hedgehog | 2023.1.1 or higher

JDK 11 or higher

Android SDK API 24+

### Installation

**1. Clone the repository**
```bash
git clone https://github.com/miguelmialdea/accessnews.git
cd accessnews
```

**2. Open in Android Studio**

**3. Sync Gradle files**

**4. Run the app**

### Build Commands

```bash
# Debug build
gradlew assembleDebug

# Release build
gradlew assembleRelease

# Run tests
gradlew test

# Run connected tests
gradlew connectedAndroidTest
```

---

## Key Differences from ShopFlow

| Aspect | ShopFlow | AccessNews |
|--------|----------|------------|
| **DI** | Hilt/Dagger | **Koin** |
| **Networking** | Retrofit | **Ktor** |
| **Database** | Room | **SQLDelight** |
| **Architecture** | MVVM | **MVI** |
| **Focus** | E-commerce | **Accessibility** |
| **Testing** | JUnit + Mockito | **Kotest + MockK + Turbine** |

---

## Accessibility Implementation

### Semantic Descriptions

```kotlin
Text(
    text = article.title,
    modifier = Modifier.semantics {
        contentDescription = "Article title: ${article.title}"
        role = Role.Button
    }
)
```

### Touch Target Sizes

All interactive elements have a minimum size of 48dp × 48dp.

### Dynamic Text Scaling

Supports system font scaling up to 200%.

### Screen Reader Support

Full TalkBack compatibility with custom content descriptions and navigation order.

---

## Testing

### Unit Tests

```bash
# Run all unit tests
gradlew test

# Run specific test
gradlew test --tests "FeedViewModelTest"
```

### Example Test with Kotest

```kotlin
class FeedRepositoryTest : StringSpec({
    "should return subscribed feeds" {
        // Given
        val repository = FeedRepositoryImpl(localDataSource, remoteDataSource)

        // When
        val result = repository.getSubscribedFeeds().first()

        // Then
        result shouldBe Resource.Success(listOf(feed1, feed2))
    }
})
```

---

## Performance Optimizations

**LazyColumn** for efficient scrolling

**SQLDelight** for optimized database queries

**Coroutines** for non-blocking operations

**Flow** for reactive data streams

**Coil** for image caching and loading

---

## Future Enhancements

The following features are planned for future releases:

- User authentication and personalization
- Feed discovery and recommendations
- Article sharing capabilities
- Reading statistics and analytics
- Home screen widget support
- Podcast feed support
- Enhanced dark mode theming
- Multiple language support
- OPML import/export functionality
- Cross-device synchronization

---

## Development

### Building the Project

```bash
# Clone the repository
git clone https://github.com/MiguelMialdeaDev/AccessNews.git
cd AccessNews

# Build debug APK
./gradlew assembleDebug

# Run tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Project Requirements

Android Studio Hedgehog (2023.1.1) or higher

JDK 11 or higher

Android SDK 36

Gradle 8.0+

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## Author

**Miguel Ángel Mialdea**

Android Developer with 5+ years of experience specializing in Kotlin, Jetpack Compose, and accessibility-focused application development. Experienced in banking and FinTech solutions with Openbank/Santander Group.

---

## Acknowledgments

This project was built using excellent open-source tools and libraries:

- Android Jetpack and Compose teams for the modern UI toolkit
- Material Design team for accessibility guidelines and components
- Koin community for the lightweight dependency injection framework
- SQLDelight team for type-safe SQL database access
- Ktor team for the modern HTTP client
- The Android developer community for continuous support and resources
