# Nimbbl Android WebView DIY Sample App

A sample Android app (Kotlin) demonstrating how to integrate Nimbbl's payment flow inside a WebView with proper UPI deep-link handling.

## Features

- Load `https://sonicshop.nimbbl.tech` (or any URL) in a full-screen WebView
- Intercepts UPI deep-links and launches the appropriate UPI app (GPay, PhonePe, Paytm, BHIM, etc.)
- Reliably brings backgrounded UPI apps to the foreground
- Progress indicator during page load
- Back navigation support (navigates WebView history before closing)
- Mixed content support for compatibility with payment pages

## Project Structure

```
app/src/main/
├── java/com/example/webviewsample/
│   ├── HomeActivity.kt       # URL input screen
│   └── WebViewActivity.kt    # WebView + UPI deep-link handling
├── res/layout/
│   ├── activity_home.xml     # Home screen layout
│   └── activity_webview.xml  # WebView screen layout
└── AndroidManifest.xml
```

## How It Works

### 1. URL Input (HomeActivity)
- Single URL field pre-filled with `https://sonicshop.nimbbl.tech`
- Validates the URL before navigating to the WebView screen

### 2. WebView Loading (WebViewActivity)
- JavaScript, DOM storage, and zoom enabled
- Progress bar controlled via `WebChromeClient.onProgressChanged`
- Main-frame errors logged separately from sub-resource errors

### 3. UPI Deep-Link Handling
When the payment page triggers a UPI intent, `shouldOverrideUrlLoading` intercepts it:

**Supported schemes:**
- `upi://` — standard UPI scheme
- `gpay://`, `phonepe://`, `paytm://`, `bhim://`, `amazonpay://`, `mobikwik://`, `freecharge://`, `jupiter://`, `slice://`, `cred://`

**How the launch works:**
```kotlin
val resolveInfo = packageManager.resolveActivity(intent, MATCH_DEFAULT_ONLY)
Intent(ACTION_VIEW, Uri.parse(url)).apply {
    resolveInfo?.activityInfo?.packageName?.let { setPackage(it) }
    // No FLAG_ACTIVITY_NEW_TASK — launching from Activity context
    // ensures the UPI app comes to foreground on both cold and warm starts
}
```

> `setPackage()` targets the correct UPI app without hardcoding the activity component, so the app handles its own intent routing. Omitting `FLAG_ACTIVITY_NEW_TASK` ensures cold-start launches come to foreground correctly.

## Requirements

- Android Studio Hedgehog or later
- Android device/emulator with API 24+ (Android 7.0)
- A UPI app installed on the device for payment flow testing

## Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Tech Stack

| | |
|---|---|
| Language | Kotlin |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 34 (Android 14) |
| UI | Material Design 3, ViewBinding |
| Architecture | Single-activity screens |

## Getting Started

1. Clone the repo
2. Open in Android Studio and sync Gradle
3. Run on a physical device (emulator won't have UPI apps)
4. The default URL `https://sonicshop.nimbbl.tech` loads on launch — tap **Load URL**
