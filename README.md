# Product Explorer

A Kotlin Multiplatform (KMP) application that allows users to browse, search, and filter products using the DummyJSON API. This project demonstrates a shared codebase for business logic and UI across Android and iOS platforms, enhanced with offline support via local caching.

## Summary of Business Requirements
The application addresses the following business needs:
- **Product Discovery**: Users can view a list of products with essential details such as name, brand, price, and ratings.
- **Dynamic Search**: A responsive search feature allows users to find products by keywords with a debounced input mechanism to minimize API calls.
- **Category-Based Filtering**: Users can filter the product list by categories accessible through a side navigation drawer.
- **In-depth Product Information**: Tapping a product reveals a detailed view including a full description, pricing, and availability.
- **Offline Resilience**: The app caches product data and categories locally, allowing users to browse previously fetched content without an active internet connection.
- **Resilient UX**: The app handles network loading states and error scenarios (with retry options) to ensure a smooth user experience.

## Project Architecture Overview
The project follows **Clean Architecture** principles within a **Kotlin Multiplatform** framework, maximizing code reuse while maintaining a clear separation of concerns.

- **Domain Layer (`:shared`)**: Contains the core business logic, including:
    - **Models**: Platform-agnostic data structures like `Product` and `FilterItem`.
    - **Use Cases**: Encapsulated business rules like `GetProductsUseCase`, `SearchProductsUseCase`, and `GetProductCategoriesUseCase`.
    - **Repository Interfaces**: Defined abstractions for data operations.
- **Data Layer (`:shared`)**: Implements the repository interfaces with a **Remote-Mediator** pattern:
    - **Remote Source**: Fetches data from the **DummyJSON API** using **Ktor**.
    - **Local Source**: Persists data using **SQLDelight** for offline access.
    - **Serialization**: Handles JSON parsing with **Kotlinx Serialization**.
- **Presentation Layer (`:shared`)**: 
    - **ViewModel**: A shared `androidx.lifecycle.ViewModel` that manages UI state using `StateFlow`.
    - **UI**: Shared UI components built with **Compose Multiplatform** and **Material 3**.
    - **Navigation**: Managed by the **JetBrains Compose Navigation** library.
- **Dependency Injection**: Orchestrated by **Koin** for seamless multiplatform dependency management.
- **Image Loading**: Handled by **Coil 3** for cross-platform image rendering.

## How to Build and Run

### Prerequisites
- **Android Studio Ladybug** (or newer)
- **Xcode** (required for iOS)
- **JDK 17 or 21** (Project is configured for JVM 21)

### Android
1. Open the project in Android Studio.
2. Ensure the `androidApp` module is selected in the run configuration.
3. Click the **Run** button to deploy to an emulator or connected device.
4. CLI Build: `./gradlew :androidApp:assembleDebug`

### iOS
1. Open the project in Android Studio.
2. Select the `iosApp` run configuration.
3. Choose a target iOS Simulator.
4. Click **Run**.
5. *Alternative*: Open `iosApp/iosApp.xcworkspace` in Xcode and run it from there.

## Trade-offs and Assumptions

### Caching Strategy
- **Partial Persistence**: For `ProductEntity`, only essential fields (ID, title, price, thumbnail, category, brand, description, rating, discount, and stock) are cached. Complex nested objects like `dimensions`, `meta`, and `reviews` are currently not persisted to keep the database schema lean, though they could be added in future iterations.
- **Remote-First**: The app attempts to fetch fresh data from the API first. If the request fails, it seamlessly falls back to the local database, ensuring the UI always displays the most recent cached content.
- **Atomic Operations**: Database insertions are wrapped in `runCatching` blocks within the repository to prevent local storage issues (like disk full) from breaking the core network-driven user flow.

### Architecture & UI
- **Navigation Choice**: JetBrains Compose Navigation was chosen for its direct compatibility with Jetpack Navigation patterns, facilitating a smoother transition for Android developers.
- **Filter Discoverability**: A dedicated "Filter" button in the top bar complements the side drawer to enhance the visibility of category filtering.
- **Multi-Platform Consistency**: The shared UI ensures a unified experience across Android and iOS while utilizing platform-specific drivers (AndroidSqliteDriver and NativeSqliteDriver) for the local database.
