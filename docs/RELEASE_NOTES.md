# AptiVilla v1.0.0 Release Notes

**Version Code:** 1  
**Version Name:** 1.0.0  
**Target SDK:** 35 (Android 16 Ready)  
**Min SDK:** 24 (Android 7.0 Nougat+)  
**APK Size:** ~2.36 MB (Optimized via R8 Minification and Resource Shrinking)  

---

## Highlights

* **100% Offline Core**: Practice aptitude questions, take timed exams, maintain study streaks, and review mistakes without requiring an active internet connection.
* **Deterministic Content Importer**: Stream-parse and validate custom question JSON files with atomic rollback and automatic 6-step answer key resolution.
* **Progressive Hint Ladder & Exam Scorer**: Multi-stage hints, competitive shortcuts, active-time monotonic tracking, and 5-state test navigation palette.
* **Leitner 5-Box Spaced Repetition**: Dynamic review intervals (1, 3, 7, 14, 30, 60 days) with automatic 2-consecutive-correct mastery resolution.
* **Real Analytics & Weak Topic Engine**: Track accuracy, hint dependency distribution, average solve speed, and subject-level performance with small-sample safeguards ($n \ge 10$).
* **FTS4 Offline Search**: Sub-millisecond indexed search across English and Devanagari Hindi questions, options, topics, and tricks.
* **Full Data Ownership & Portability**: Export complete encrypted/checksummed `.aptbackup` archives or interchange JSON at any time. One-tap local factory reset.
* **Optional BYOK AI Assistant**: Bring your own Google Gemini, OpenAI, Grok, or Claude API key. Direct HTTPS connection with hardware-backed Android KeyStore AES-256-GCM encryption, answer-gating, and zero server logging.
