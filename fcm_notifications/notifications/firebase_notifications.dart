import 'package:firebase_messaging/firebase_messaging.dart';

/// Initializes Firebase Messaging: requests permission and prints the token.
/// Must be called after Firebase.initializeApp().
Future<void> setupFirebaseMessaging() async {
  final FirebaseMessaging messaging = FirebaseMessaging.instance;

  // Request permissions (mostly relevant for iOS and web)
  final NotificationSettings settings = await messaging.requestPermission(
    alert: true,
    badge: true,
    sound: true,
  );

  if (settings.authorizationStatus == AuthorizationStatus.authorized) {
    print('✅ User granted notification permission');
  } else if (settings.authorizationStatus == AuthorizationStatus.provisional) {
    print('⚠️ User granted provisional permission');
  } else {
    print('❌ User declined or has not accepted permission');
    return;
  }

  // Get the current FCM device token
  final String? token = await messaging.getToken();
  if (token != null) {
    print('📨 FCM Token: $token');
  } else {
    print('⚠️ Failed to obtain FCM token');
  }

  // (Optional) For web builds, include your public VAPID key:
  // final token = await messaging.getToken(vapidKey: "YOUR_PUBLIC_VAPID_KEY");
}

/// Sets up listeners for foreground and background notification interactions.
void requestBgNotifications() {
  // Foreground messages (app is open)
  FirebaseMessaging.onMessage.listen((RemoteMessage message) {
    final notification = message.notification;
    print(
      '📩 Foreground message: ${notification?.title ?? "No title"} | ${notification?.body ?? ""}',
    );
  });

  // When a user taps a notification to open the app
  FirebaseMessaging.onMessageOpenedApp.listen((RemoteMessage message) {
    final notification = message.notification;
    print('🚀 Opened from notification: ${notification?.title ?? "No title"}');
  });
}
