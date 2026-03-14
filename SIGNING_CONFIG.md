# ✅ Automatic APK Signing Configuration

**Date:** 2026-03-14
**Status:** ✅ **CONFIGURED**

---

## 📋 Configuration Summary

Automatic signing has been configured for your NovelDokusha APK builds using your existing keystore.

---

## 🔐 Keystore Information

**File:** `HarzBaiQ.jks`
**Location:** `/home/ubuntu/myNovelDokusha/HarzBaiQ.jks`
**Key Alias:** `harzbaiq`

---

## 📁 Configuration Files

### 1. `local.properties`

```properties
# Lokasi file .jks atau .keystore
storeFile=/home/ubuntu/myNovelDokusha/HarzBaiQ.jks

# Password untuk keystore dan key alias
storePassword=abualif2001
keyAlias=harzbaiq
keyPassword=abualif2001
```

### 2. `app/build.gradle.kts`

Signing configuration automatically loads from `local.properties`:

```kotlin
signingConfigs {
    if (hasDefaultSigningConfigData) create("default") {
        storeFile = rootProject.file(defaultSigningConfigData.getProperty("storeFile"))
        storePassword = defaultSigningConfigData.getProperty("storePassword")
        keyAlias = defaultSigningConfigData.getProperty("keyAlias")
        keyPassword = defaultSigningConfigData.getProperty("keyPassword")
    }
}

buildTypes {
    named("debug") {
        signingConfig = signingConfigs.asMap["default"] ?: signingConfigs.getByName("debug")
    }

    named("release") {
        signingConfig = signingConfigs.asMap["default"] ?: signingConfigs.getByName("debug")
    }
}
```

---

## 🚀 How to Build

### Debug Build (Automatically signed with your keystore)

```bash
./gradlew assembleFullDebug
```

**Output:** `app/build/outputs/apk/full/debug/NovelDokusha_v2.3.4-full-debug.apk`

### Release Build (Automatically signed with your keystore)

```bash
./gradlew assembleFullRelease
```

**Output:** `app/build/outputs/apk/full/release/NovelDokusha_v2.3.4-full-release.apk`

### Build All Flavors

```bash
./gradlew assembleRelease
```

This will create:
- `NovelDokusha_v2.3.4-full-release.apk`
- `NovelDokusha_v2.3.4-foss-release.apk`

---

## ✅ Verification

### Check if APK is Signed

```bash
# Verify signature
jarsigner -verify -verbose -certs app/build/outputs/apk/full/release/NovelDokusha_v2.3.4-full-release.apk

# Should show:
# s = signature was verified
# m = entry is listed in manifest
# k = at least one certificate was found in keystore
# jar verified.
```

### Check Keystore Info

```bash
keytool -list -v -keystore /home/ubuntu/myNovelDokusha/HarzBaiQ.jks -alias harzbaiq
```

---

## 🔒 Security Best Practices

### ⚠️ IMPORTANT: Protect Your Keystore

1. **NEVER commit `local.properties` to Git**
   - Already in `.gitignore` ✅

2. **Backup your keystore file**
   - Store in multiple secure locations
   - If you lose this file, you CANNOT update your app on Play Store

3. **Use strong passwords**
   - Consider changing from default passwords

4. **Limit access**
   - Only trusted developers should have access

---

## 🛠️ Troubleshooting

### Build fails with "Keystore file does not exist"

**Solution:** Check the path in `local.properties` is correct:
```properties
storeFile=/absolute/path/to/HarzBaiQ.jks
```

### Build fails with "Wrong password"

**Solution:** Verify passwords in `local.properties`:
```properties
storePassword=your_password
keyPassword=your_password
```

### APK not signed

**Solution:** Make sure `local.properties` exists in project root:
```bash
ls -la local.properties
```

---

## 📊 Build Output Locations

| Build Type | Output Path |
|------------|-------------|
| **Debug** | `app/build/outputs/apk/full/debug/` |
| **Release** | `app/build/outputs/apk/full/release/` |
| **FOSS Debug** | `app/build/outputs/apk/foss/debug/` |
| **FOSS Release** | `app/build/outputs/apk/foss/release/` |

---

## 🎯 Next Steps

### For Google Play Store Release

1. **Build signed release APK:**
   ```bash
   ./gradlew assembleFullRelease
   ```

2. **Verify signature:**
   ```bash
   jarsigner -verify app/build/outputs/apk/full/release/NovelDokusha_v2.3.4-full-release.apk
   ```

3. **Upload to Play Console**

### For Testing on Device

1. **Install directly:**
   ```bash
   ./gradlew installFullDebug
   ```

2. **Or manually install APK:**
   ```bash
   adb install app/build/outputs/apk/full/debug/NovelDokusha_v2.3.4-full-debug.apk
   ```

---

## 📝 Summary

✅ **Keystore configured:** `HarzBaiQ.jks`
✅ **Automatic signing:** Enabled for all builds
✅ **Debug builds:** Signed with your keystore
✅ **Release builds:** Signed with your keystore
✅ **Build verified:** `BUILD SUCCESSFUL`

---

**Generated:** 2026-03-14
**Build Status:** ✅ SUCCESSFUL
**Signing Status:** ✅ CONFIGURED
