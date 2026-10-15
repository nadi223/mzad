# تصميم منصة المزادات – Jetpack Compose

## الملفات
```
ui/theme/AuctionTheme.kt          الألوان، الأشكال، الخطوط، النصوص (معزولة عن بقية المنصات)
ui/model/AuctionUiModels.kt       نماذج الواجهة (الأسعار نصوص جاهزة → لا افتراض لعملة معينة)
ui/components/Basics.kt           الصورة، LIVE، الشارات، الأزرار
ui/components/Chrome.kt           الشريط العلوي، الشريط السفلي مع زر الإضافة، زر الدعم
ui/components/HomeParts.kt        البانر، بانر الإضافة، شبكة الفئات، عنوان القسم
ui/components/Cards.kt            البطاقة المصغّرة، بطاقة البيع المباشر، بطاقة القائمة الكبيرة
ui/screens/AuctionsHomeScreen.kt  الشاشة الرئيسية
ui/screens/AuctionDetailScreen.kt شاشة التفاصيل
ui/screens/AuctionPreviews.kt     معاينات ببيانات تجريبية
```

## الاعتماديات (build.gradle)
```kotlin
implementation(platform("androidx.compose:compose-bom:2024.10.01"))
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.material:material-icons-extended")
implementation("androidx.compose.foundation:foundation")
implementation("io.coil-kt:coil-compose:2.7.0")
```

## الدمج
1. غيّر `com.yourapp.auctions` إلى اسم حزمتك.
2. لفّ شاشات المزادات فقط: `AuctionTheme { AuctionsHomeScreen(state, actions) }`.
3. حوّل بياناتك الحالية إلى `AuctionsHomeUiState` في الـ ViewModel.
   - إخفاء/إظهار فئة أو قسم أو بانر = حذفه من القائمة أو إضافته.
4. الاتجاه: التصميم مكتوب ليظهر كما هو مطلوب في RTL. مرّر `layoutDirection = LayoutDirection.Rtl` إذا لم يكن تطبيقك RTL أصلاً.
5. النصوص: مرّر `AuctionStrings(...)` من `stringResource` ليعمل زر اللغة.
6. الشعار والاسم: مرّر شعارك أنت عبر `logo = { ... }` و`brandName`.
7. أيقونة التواصل (واتساب) في الشريط العلوي: استبدل `contactIcon` بأيقونتك.
