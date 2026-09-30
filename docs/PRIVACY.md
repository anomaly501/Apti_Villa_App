# Privacy Policy — AptiVilla

> **Version:** 1.0 · Last updated: 2026-09-29

---

## Summary

AptiVilla is a **fully offline** aptitude practice app. It has **no backend, no accounts, no analytics, no ads, and no crash reporting**. All your data lives on your device and never leaves it unless you explicitly choose to send it somewhere.

---

## 1. What Stays on Your Device (Always)

| Data | Storage | Shared? |
|---|---|---|
| Your profile (name, goals, target exams) | Local database | **Never** |
| Imported questions, tests, and content | Local database | **Never** |
| Your practice attempts, answers, and scores | Local database | **Never** |
| Analytics and performance statistics | Computed from local data | **Never** |
| Bookmarks, tags, mistakes, revision queue | Local database | **Never** |
| Search history | Local (in-memory only) | **Never** |
| App settings (theme, goals, preferences) | Local DataStore | **Never** |
| Backup files you export | Saved to your chosen location via Android file picker | Only if you share the file yourself |

---

## 2. What May Leave Your Device (Only When You Choose)

### 2.1 AI Doubt Assistant (Optional)

If you choose to set up AI:

| What is sent | Where | When |
|---|---|---|
| The question text and options | Your chosen AI provider's API endpoint | Only when you tap "Ask AI" |
| Your selected answer (if you've submitted one) | Your chosen AI provider | Only when you tap "Ask AI" |
| Correct answer + solution (only after you've submitted or tapped "Show answer") | Your chosen AI provider | Only when you tap "Ask AI" |
| Hints you've opened | Your chosen AI provider | Only when you tap "Ask AI" |
| Your conversation messages | Your chosen AI provider | Only when you send a message |

**What is NEVER sent to AI:**
- Your profile, name, or personal information
- Your overall performance statistics
- Other questions or their history
- Your full attempt history
- Your bookmarks, tags, or revision data
- Your API key in the request body (sent only as an HTTP header)

**You can preview the exact data** that will be sent before each AI request using the "What will be sent" button.

### 2.2 External Links

| What happens | When |
|---|---|
| YouTube video opens in YouTube app or browser | When you tap "Watch Explanation" on a question with a video timestamp |
| External URLs open in your browser | When you tap any external link in the app |

AptiVilla does not download, embed, or cache any external content.

### 2.3 Backup Export

When you export a backup, the app creates a `.aptbackup` file containing your data in a standard format. This file is saved to a location you choose on your device. The app does not upload it anywhere.

**API keys are NEVER included in backup files.**

---

## 3. API Key Security

| Aspect | Detail |
|---|---|
| Storage | Encrypted using Android Keystore (AES-256-GCM, non-exportable key) |
| Location | Encrypted ciphertext in app-private storage (not in database, not in preferences) |
| Visibility | Masked in UI after entry; never logged |
| Backup | Never included in backup exports |
| Deletion | "Delete API key" wipes both the encrypted ciphertext and the Keystore entry |
| Ownership | **Your API key belongs to you.** AptiVilla stores it only to make API calls on your behalf. |

---

## 4. What AptiVilla Does NOT Do

- ❌ Does **not** collect analytics or usage data
- ❌ Does **not** have crash reporting SDKs
- ❌ Does **not** show advertisements
- ❌ Does **not** require an account or login
- ❌ Does **not** require internet for any core feature
- ❌ Does **not** have a backend server
- ❌ Does **not** track your location
- ❌ Does **not** access your contacts, camera, or microphone
- ❌ Does **not** use Firebase or any Google services SDK
- ❌ Does **not** share data with third parties (except AI providers at your explicit request)

---

## 5. Android System Features

| Feature | Status |
|---|---|
| Auto-backup | **Disabled** (`allowBackup=false` or `data_extraction_rules` excluding sensitive data). The in-app export feature is your backup mechanism. |
| Permissions | Storage access only via SAF (no broad storage permission needed on modern Android). No other special permissions. |
| App data on uninstall | All app data is deleted by Android when you uninstall the app. **Export your data first.** |

---

## 6. Data Ownership

You own all your data. You can:

- **Export** your data at any time as a `.aptbackup` file or as JSON files
- **Import** your data onto another device
- **Delete** specific data (tests, history, mistakes, AI conversations, API keys)
- **Delete all data** with a single action (requires typed confirmation)

---

## 7. AI Provider Policies

When you use the AI feature, your data is processed by the AI provider you choose (e.g., OpenAI, Google, Anthropic, or a custom endpoint). Each provider has its own privacy policy and data handling practices. AptiVilla is not responsible for how AI providers handle data sent to them. Please review your chosen provider's privacy policy.

**Key points:**
- The AI provider may bill you based on your usage (you are using your own API key)
- AI calls require internet connectivity
- You control which provider receives your data
- You can see exactly what will be sent before each request

---

## 8. Data Safety Declaration (Play Store)

For Google Play Store data safety section:

| Declaration | Value |
|---|---|
| Data collected | **None** |
| Data shared | **None** (AI calls are user-initiated to user-chosen endpoints) |
| Data encrypted in transit | Yes (HTTPS for AI calls) |
| Data encrypted at rest | API keys: Yes (Keystore). Database: Android default encryption |
| Users can request data deletion | Yes (in-app controls) |
| Committed to Play Families Policy | N/A |

---

## 9. Contact

For privacy questions about this app, please contact the developer through the Play Store listing.

---

*This privacy policy is also available in-app at Settings → Privacy.*
