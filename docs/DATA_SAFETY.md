# Google Play Store Data Safety & Privacy Form Guide

This document provides exact responses for the Google Play Console **Data Safety Form** for **AptiVilla** (`app.apti.villa`).

---

## 1. Overview & Core Answers

| Question | Answer | Rationale |
|:---|:---|:---|
| **Does your app collect or share any user data?** | **No** (by default / for core app) | The app functions 100% locally with an offline SQLite database. No SDKs (Firebase, Crashlytics, Google Analytics, Ads) are included in the codebase. |
| **Is all user data collected by your app encrypted in transit?** | **Yes** | Any network connection (optional, user-initiated BYOK AI calls) uses TLS 1.3 HTTPS directly to the user's chosen provider. |
| **Do you provide a way for users to request that their data be deleted?** | **Yes** | Under `Settings -> Data Ownership & Reset`, the user can clear attempt logs, reset mistakes, or perform a full local Factory Reset. |

---

## 2. Optional AI Assistant (BYOK) Data Disclosure

If Google Play reviewers question the presence of the `android.permission.INTERNET` permission:

- **Purpose of Internet Permission:** Strictly for optional, user-initiated AI tutoring requests using the user's personal API key (Bring-Your-Own-Key).
- **Direct-to-Provider:** Requests are transmitted directly from the device to OpenAI, Anthropic, or Google Gemini. AptiVilla maintains **no backend server**, operates **no proxy**, and **never logs or collects prompts**.
- **Data Transmitted:** Only the specific question, student's selected answer, and student-unlocked hints. Never personal profile data, device IDs, or questions from other topics.
- **Key Storage:** API keys are encrypted at rest with hardware-backed Android KeyStore AES-256-GCM and are excluded from backups.

---

## 3. Play Store Category & Declarations

- **App Category:** Education / Exam Preparation
- **Target Audience:** Everyone (13+ / Competitive Exam Aspirants)
- **Ads:** No Ads
- **In-App Purchases:** No In-App Purchases
- **Location Permission:** Not requested
- **Contacts / SMS / Storage:** Scoped Storage Access Framework (SAF) only for `.aptbackup` and `.json` imports/exports.
