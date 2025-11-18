package com.miguelmialdea.accessnews.domain.usecase

import com.miguelmialdea.accessnews.domain.model.Article
import java.util.UUID

/**
 * Use case for getting sample articles to populate the app
 */
class GetSampleArticlesUseCase {

    operator fun invoke(feedId: String): List<Article> {
        val now = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L

        return listOf(
            Article(
                id = UUID.randomUUID().toString(),
                feedId = feedId,
                title = "The Future of Android Development with Jetpack Compose",
                description = "Jetpack Compose is revolutionizing the way we build Android UIs. This article explores the latest features and best practices for modern Android development.",
                content = """
                    Jetpack Compose has transformed Android development by introducing a declarative UI framework that simplifies and accelerates UI development.

                    With Compose, you write less code with powerful tools and intuitive Kotlin APIs. It's fully compatible with your existing code so you can adopt it at your own pace.

                    Key benefits include:
                    - Declarative UI: Describe your UI, and Compose takes care of the rest
                    - Less code: Write less code with more functionality
                    - Intuitive: Just describe your UI, and Compose takes care of the rest
                    - Accelerate Development: Compatible with all existing code so you can adopt when and where you want
                    - Powerful: Create beautiful apps with direct access to the Android platform APIs

                    The future of Android development is here, and it's built with Compose.
                """.trimIndent(),
                url = "https://example.com/compose-future",
                imageUrl = null,
                author = "Android Dev Team",
                publishedAt = now - (2 * oneDay),
                isBookmarked = false,
                isRead = false
            ),
            Article(
                id = UUID.randomUUID().toString(),
                feedId = feedId,
                title = "Building Accessible Android Apps: A Complete Guide",
                description = "Learn how to make your Android apps accessible to everyone. This comprehensive guide covers TalkBack, semantic properties, and WCAG guidelines.",
                content = """
                    Accessibility is not just a feature—it's a fundamental aspect of creating inclusive applications that everyone can use.

                    In this guide, we'll cover:

                    1. TalkBack Integration
                       - Proper content descriptions
                       - Navigation order
                       - Custom actions

                    2. Visual Accessibility
                       - High contrast support
                       - Dynamic text sizing
                       - Color contrast ratios

                    3. Motor Accessibility
                       - Touch target sizes (minimum 48dp)
                       - Keyboard navigation
                       - Switch access support

                    4. WCAG 2.1 Compliance
                       - Level AA requirements
                       - Testing strategies
                       - Automated testing tools

                    Remember: Building accessible apps makes them better for everyone, not just users with disabilities.
                """.trimIndent(),
                url = "https://example.com/accessibility-guide",
                imageUrl = null,
                author = "Sarah Johnson",
                publishedAt = now - (5 * oneDay),
                isBookmarked = false,
                isRead = false
            ),
            Article(
                id = UUID.randomUUID().toString(),
                feedId = feedId,
                title = "Kotlin Coroutines: Mastering Asynchronous Programming",
                description = "Deep dive into Kotlin Coroutines and learn how to write clean, efficient asynchronous code for Android applications.",
                content = """
                    Kotlin Coroutines provide a powerful way to handle asynchronous operations in Android development.

                    What are Coroutines?
                    Coroutines are a Kotlin feature that convert async callbacks into sequential code. They're incredibly lightweight and allow you to write asynchronous code that looks synchronous.

                    Key Concepts:
                    - suspend functions: Functions that can be paused and resumed
                    - Dispatchers: Control which thread executes the coroutine
                    - Structured concurrency: Ensures coroutines are properly managed
                    - Flow: Reactive streams that emit multiple values over time

                    Best Practices:
                    1. Use viewModelScope for ViewModel operations
                    2. Handle exceptions with try-catch or CoroutineExceptionHandler
                    3. Use Flow for reactive data streams
                    4. Cancel coroutines when they're no longer needed
                    5. Use the appropriate dispatcher (Main, IO, Default)

                    Coroutines make asynchronous programming simple, safe, and efficient.
                """.trimIndent(),
                url = "https://example.com/kotlin-coroutines",
                imageUrl = null,
                author = "John Doe",
                publishedAt = now - (7 * oneDay),
                isBookmarked = false,
                isRead = false
            ),
            Article(
                id = UUID.randomUUID().toString(),
                feedId = feedId,
                title = "Clean Architecture in Android: Best Practices",
                description = "Implement Clean Architecture in your Android apps for better testability, maintainability, and scalability.",
                content = """
                    Clean Architecture helps you build Android apps that are testable, maintainable, and scalable by separating concerns into distinct layers.

                    The Three Layers:

                    1. Presentation Layer
                       - UI components (Activities, Fragments, Composables)
                       - ViewModels
                       - UI state management

                    2. Domain Layer
                       - Business logic
                       - Use cases
                       - Domain models
                       - Repository interfaces

                    3. Data Layer
                       - Repository implementations
                       - Data sources (local, remote)
                       - DTOs and mappers

                    Key Principles:
                    - Dependency Rule: Dependencies point inward
                    - Separation of Concerns: Each layer has specific responsibilities
                    - Testability: Easy to test each layer independently
                    - Independence: Framework, UI, and database independent

                    Benefits:
                    - Easier to test
                    - Better organized code
                    - Easier to maintain and scale
                    - Team members can work independently
                """.trimIndent(),
                url = "https://example.com/clean-architecture",
                imageUrl = null,
                author = "Robert C. Martin",
                publishedAt = now - (10 * oneDay),
                isBookmarked = false,
                isRead = false
            ),
            Article(
                id = UUID.randomUUID().toString(),
                feedId = feedId,
                title = "Material Design 3: The Next Evolution of Design",
                description = "Explore Material Design 3 and learn how to implement beautiful, accessible UIs with dynamic color and improved components.",
                content = """
                    Material Design 3 represents the next evolution of Google's design system, bringing dynamic color, improved accessibility, and refined components.

                    What's New in Material 3:

                    1. Dynamic Color
                       - System-generated color schemes from wallpaper
                       - Personalized user experience
                       - Automatic light and dark themes

                    2. Improved Components
                       - Updated visual style
                       - Better accessibility
                       - More customization options

                    3. Enhanced Accessibility
                       - WCAG 2.1 AAA contrast ratios
                       - Improved touch targets
                       - Better screen reader support

                    4. Design Tokens
                       - Consistent theming
                       - Easy customization
                       - Platform flexibility

                    Implementation in Compose:
                    Material 3 is fully integrated with Jetpack Compose, making it easier than ever to build beautiful, accessible Android apps.

                    The combination of Material 3 and Compose provides the best tools for creating modern Android UIs.
                """.trimIndent(),
                url = "https://example.com/material-design-3",
                imageUrl = null,
                author = "Google Design Team",
                publishedAt = now - (1 * oneDay),
                isBookmarked = false,
                isRead = false
            )
        )
    }
}
