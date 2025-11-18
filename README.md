# AccessNews - Accessible RSS News Reader

A modern, accessibility-first Android news reader application built with cutting-edge technologies and following best practices.

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![MinSDK](https://img.shields.io/badge/MinSDK-24-orange.svg)
![Architecture](https://img.shields.io/badge/Architecture-MVI-purple.svg)

## Key Highlights

**Accessibility-First Design** - Built from the ground up with accessibility as a core feature, not an afterthought
**Modern Stack** - Koin, Ktor, SQLDelight, Jetpack Compose
**MVI Architecture** - Predictable state management with unidirectional data flow
**Comprehensive Testing** - Unit tests with Kotest, MockK, and Turbine
**Clean Architecture** - Clear separation of concerns across layers

## Features

### Core Functionality
- 📰 **RSS/Atom Feed Reader** - Support for multiple feed formats
- 🔖 **Bookmarks** - Save articles for later reading
- 📱 **Offline Reading** - Local caching with SQLDelight
- 🔍 **Search** - Find articles and feeds quickly
- 📊 **Multiple Categories** - Organize feeds by topic

### Accessibility Features (WCAG 2.1 Level AA Compliant)
- **Visual Accessibility**
  - Full TalkBack/screen reader support
  - High contrast themes
  - Dynamic text scaling (80% - 200%)
  - Semantic labels and descriptions

- **Motor Accessibility**
  - Minimum 48dp touch targets
  - Keyboard navigation support
  - Switch Access compatible
  - Gesture alternatives

- **Cognitive Accessibility**
  - Simple, clear navigation
  - Distraction-free reading mode
  - Consistent UI patterns
  - Clear focus indicators

## Tech Stack

### Architecture & Patterns
- **MVI (Model-View-Intent)** - Unidirectional data flow
- **Clean Architecture** - Domain, Data, Presentation layers
- **Repository Pattern** - Abstract data sources
- **Use Cases** - Encapsulated business logic

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

## Getting Started

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or higher
- JDK 11 or higher
- Android SDK API 24+

### Installation

1. Clone the repository
```bash
git clone https://github.com/miguelmialdea/accessnews.git
cd accessnews
```

2. Open in Android Studio

3. Sync Gradle files

4. Run the app

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

## Key Differences from ShopFlow

| Aspect | ShopFlow | AccessNews |
|--------|----------|------------|
| **DI** | Hilt/Dagger | **Koin** |
| **Networking** | Retrofit | **Ktor** |
| **Database** | Room | **SQLDelight** |
| **Architecture** | MVVM | **MVI** |
| **Focus** | E-commerce | **Accessibility** |
| **Testing** | JUnit + Mockito | **Kotest + MockK + Turbine** |

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

## Performance Optimizations

- **LazyColumn** for efficient scrolling
- **SQLDelight** for optimized database queries
- **Coroutines** for non-blocking operations
- **Flow** for reactive data streams
- **Coil** for image caching and loading

## Roadmap

- [ ] User authentication
- [ ] Feed discovery
- [ ] Article sharing
- [ ] Reading statistics
- [ ] Widget support
- [ ] Podcast support
- [ ] Dark mode
- [ ] Multiple languages
- [ ] Export/Import feeds (OPML)
- [ ] Sync across devices

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License.

## Author

**Miguel Ángel Mialdea**
- Android Developer | 5+ years experience
- Specialized in Kotlin, Jetpack Compose, Accessibility
- Passionate about inclusive design

## Acknowledgments

- Material Design team for accessibility guidelines
- Koin community for excellent DI framework
- SQLDelight team for type-safe SQL
- Ktor team for modern networking

---

**Built with ❤️ for everyone, accessible to all**
#   A c c e s s N e w s  
 