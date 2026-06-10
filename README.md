# WebView Sample Android App

A simple Android application built with Kotlin that demonstrates WebView functionality with network monitoring and performance logging.

## Features

- **Single URL Default**: Starts with one URL field for simple single URL loading
- **Dynamic URL Input**: Add/remove URL text fields dynamically (up to 10 URLs total)
- **Scrollable Interface**: Scrollable layout when number of URLs exceeds screen height
- **Multi-URL Comparison**: Compare multiple URLs simultaneously with side-by-side WebViews
- **URL Validation**: Validates and formats URLs before loading
- **Progress Indicators**: Individual progress bars for each WebView
- **Real-time UI Display**: Live display of load time, render time, and total time in the interface
- **Network Detection**: Detects and logs current network type (Wi-Fi, Mobile Data, etc.)
- **Performance Monitoring**: Measures and logs both page load times and UI rendering times in milliseconds
- **Comprehensive Logging**: Detailed performance analysis with rankings and statistics
- **Material Design**: Modern UI using Material Design components
- **Responsive Layout**: Adaptive layout that works with any number of URLs

## Technical Specifications

- **Language**: Kotlin
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Architecture**: Standard Android Activities with ViewBinding
- **Dependencies**: AndroidX, Material Design Components

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/webviewsample/
│   │   ├── HomeActivity.kt          # Main screen with URL input
│   │   ├── WebViewActivity.kt       # WebView screen with monitoring
│   │   └── NetworkUtils.kt          # Network type detection utility
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_home.xml    # Home screen layout
│   │   │   └── activity_webview.xml # WebView screen layout
│   │   └── values/
│   │       ├── strings.xml          # String resources
│   │       ├── colors.xml           # Color definitions
│   │       └── themes.xml           # App themes
│   └── AndroidManifest.xml          # App configuration and permissions
```

## Key Features Implementation

### 1. URL Input and Validation
- Text input with Material Design TextInputLayout
- Automatic protocol addition (https://) if missing
- URL format validation using Android's Uri class

### 2. WebView Configuration
- JavaScript enabled for dynamic content
- DOM storage and database storage enabled
- Zoom controls and user agent customization
- Proper lifecycle management with cache clearing

### 3. Network Monitoring
- Real-time network type detection (Wi-Fi, Mobile Data, Ethernet)
- Compatible with both modern (API 23+) and legacy Android versions
- Network availability checking

### 4. Performance Logging
- Precise load time measurement using System.currentTimeMillis()
- UI rendering time measurement (from page load completion to 100% progress)
- Comprehensive logging with Log.d for debugging
- Network type correlation with both load and render times
- Separate performance benchmarks for load vs render times

## Enhanced Logging Output

The app now provides comprehensive logging with multi-URL performance analysis:

```
D/WebViewActivity: ============================================================
D/WebViewActivity: 📊 NETWORK PERFORMANCE ANALYSIS
D/WebViewActivity: ============================================================
D/WebViewActivity: 📶 Current Network Type: Wi-Fi
D/WebViewActivity: 🌐 Network Available: true
D/WebViewActivity: ============================================================

D/WebViewActivity: Loading 3 URLs simultaneously
D/WebViewActivity: Loading URL 1: https://www.google.com
D/WebViewActivity: Loading URL 2: https://www.github.com
D/WebViewActivity: Loading URL 3: https://www.stackoverflow.com

D/WebViewActivity: ✅ WEBVIEW 1 PAGE LOADED: 850ms
D/WebViewActivity: 🎨 WEBVIEW 1 RENDERING STARTED
D/WebViewActivity: 🎨 WEBVIEW 1 RENDERING COMPLETED: 200ms
D/WebViewActivity: 📊 WEBVIEW 1 TOTAL TIME: Load(850ms) + Render(200ms) = 1050ms

D/WebViewActivity: ✅ WEBVIEW 2 PAGE LOADED: 1200ms
D/WebViewActivity: 🎨 WEBVIEW 2 RENDERING STARTED
D/WebViewActivity: 🎨 WEBVIEW 2 RENDERING COMPLETED: 300ms
D/WebViewActivity: 📊 WEBVIEW 2 TOTAL TIME: Load(1200ms) + Render(300ms) = 1500ms

D/WebViewActivity: ✅ WEBVIEW 3 PAGE LOADED: 1500ms
D/WebViewActivity: 🎨 WEBVIEW 3 RENDERING STARTED
D/WebViewActivity: 🎨 WEBVIEW 3 RENDERING COMPLETED: 400ms
D/WebViewActivity: 📊 WEBVIEW 3 TOTAL TIME: Load(1500ms) + Render(400ms) = 1900ms

D/WebViewActivity: ========================================================================
D/WebViewActivity: 🏆 COMPREHENSIVE URL COMPARISON RESULTS
D/WebViewActivity: ========================================================================
D/WebViewActivity: 📊 Total URLs Compared: 3
D/WebViewActivity: 📶 Network Type: Wi-Fi
D/WebViewActivity: ========================================================================
D/WebViewActivity: 📈 PERFORMANCE RANKING:
D/WebViewActivity: 🥇 FASTEST - WebView 1: 1050ms total
D/WebViewActivity:    URL: https://www.google.com
D/WebViewActivity:    📥 Load: 850ms | 🎨 Render: 200ms
D/WebViewActivity:    📥 Load Performance: EXCELLENT (< 1s)
D/WebViewActivity:    🎨 Render Performance: EXCELLENT (< 1s)
D/WebViewActivity: 
D/WebViewActivity: 🥈 SECOND - WebView 2: 1500ms total
D/WebViewActivity:    URL: https://www.github.com
D/WebViewActivity:    📥 Load: 1200ms | 🎨 Render: 300ms
D/WebViewActivity:    📥 Load Performance: GOOD (1-3s)
D/WebViewActivity:    🎨 Render Performance: EXCELLENT (< 1s)
D/WebViewActivity: 
D/WebViewActivity: 🥉 THIRD - WebView 3: 1900ms total
D/WebViewActivity:    URL: https://www.stackoverflow.com
D/WebViewActivity:    📥 Load: 1500ms | 🎨 Render: 400ms
D/WebViewActivity:    📥 Load Performance: GOOD (1-3s)
D/WebViewActivity:    🎨 Render Performance: EXCELLENT (< 1s)
D/WebViewActivity: 
D/WebViewActivity: 📊 LOAD TIME STATISTICS:
D/WebViewActivity: ⚡ Fastest Load: 850ms
D/WebViewActivity: 🐌 Slowest Load: 1500ms
D/WebViewActivity: 📊 Average Load: 1183.3ms
D/WebViewActivity: 📈 Load Difference: 650ms
D/WebViewActivity: 
D/WebViewActivity: 🎨 RENDER TIME STATISTICS:
D/WebViewActivity: ⚡ Fastest Render: 200ms
D/WebViewActivity: 🐌 Slowest Render: 400ms
D/WebViewActivity: 📊 Average Render: 300.0ms
D/WebViewActivity: 📈 Render Difference: 200ms
D/WebViewActivity: 
D/WebViewActivity: 📊 TOTAL TIME STATISTICS:
D/WebViewActivity: ⚡ Fastest Total: 1050ms
D/WebViewActivity: 🐌 Slowest Total: 1900ms
D/WebViewActivity: 📊 Average Total: 1483.3ms
D/WebViewActivity: 📈 Total Difference: 850ms
D/WebViewActivity: ========================================================================
D/WebViewActivity: 🎯 COMPARISON COMPLETE - All 3 URLs loaded and rendered on Wi-Fi
D/WebViewActivity: ========================================================================
```

### Performance Benchmarks by Network Type:

- **Wi-Fi**: Excellent < 1s, Good 1-3s, Average 3-5s, Slow > 5s
- **Mobile Data**: Excellent < 2s, Good 2-5s, Average 5-8s, Slow > 8s  
- **Ethernet**: Excellent < 0.5s, Good 0.5-1.5s, Average 1.5-3s, Slow > 3s

## Permissions

The app requires the following permissions:
- `INTERNET`: For WebView to load web content
- `ACCESS_NETWORK_STATE`: For network type detection

## Building and Running

1. Open the project in Android Studio
2. Sync Gradle files
3. Build and run on an Android device or emulator (API 24+)

## Usage

1. **Launch the app** - You'll see one default URL field
2. **Add more URLs** - Tap "Add URL" button to add more text fields (up to 10 total)
3. **Remove URLs** - Tap the "×" button on any URL field to remove it (minimum 1 required)
4. **Enter URLs** - Fill in the URL fields (e.g., "google.com", "github.com", "stackoverflow.com")
5. **Load/Compare** - Tap "Load URL" button to load single URL or multiple URLs simultaneously
6. **View Results** - Each WebView shows its load time, render time, and total time in the UI, plus comprehensive logs in Android Studio Logcat

### Dynamic Features:
- **Scrollable Interface**: When you have many URLs, the interface becomes scrollable
- **Real-time Progress**: Each WebView has its own progress indicator
- **Live Performance Display**: See load time, render time, and total time directly in the UI
- **Performance Ranking**: URLs are ranked by total time (fastest to slowest)
- **Statistics**: View average, fastest, slowest load times, render times, and total times

## Customization

- Modify `strings.xml` to change UI text
- Update `colors.xml` and `themes.xml` for different styling
- Extend `NetworkUtils.kt` for additional network monitoring features
- Add more WebView settings in `WebViewActivity.kt` as needed

## Requirements Met

✅ Two screens with navigation  
✅ URL input with validation  
✅ WebView with JavaScript enabled  
✅ ProgressBar during loading  
✅ Load time measurement and logging  
✅ Network type detection and logging  
✅ Material Design components  
✅ ViewBinding implementation  
✅ Proper WebView lifecycle management  
✅ Internet permission in manifest  
✅ Kotlin language throughout  
✅ Min SDK 24, Target SDK 34
