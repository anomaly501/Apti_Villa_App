# Test Plan — AptiVilla

> **Version:** 1.0 · Phase 0 · Last updated: 2026-09-29

---

## 1. Testing Strategy Overview

| Layer | Framework | Purpose | Run with |
|---|---|---|---|
| **Unit tests** | JUnit5, MockK, Turbine | Business logic, engines, validators, formatters | `./gradlew testDebugUnitTest` |
| **Architecture tests** | Konsist | Layer boundaries, network isolation | `./gradlew testDebugUnitTest` (included) |
| **Instrumented tests** | JUnit4, Room `MigrationTestHelper`, Compose UI tests | DAOs, FTS, migrations, DB transactions, process-death resume | `./gradlew connectedDebugAndroidTest` |
| **Lint** | Android Lint | Code quality, accessibility, deprecation | `./gradlew lintDebug` |

**CI-style gate:** Every phase must pass `./gradlew assembleDebug testDebugUnitTest lintDebug` before being declared done.

---

## 2. Unit Tests by Module

### 2.1 Import Pipeline (`domain.importer`)

| Test Class | Covers | Phase |
|---|---|---|
| `JsonParserTest` | JSON syntax errors, valid parsing, line/column reporting | 2 |
| `ContentValidatorTest` | Every issue code (E_* and W_*) with dedicated fixtures | 2 |
| `ContentNormalizerTest` | Whitespace trimming, timestamp parsing, answer resolution (all 6 steps), content_hash computation, difficulty normalization | 2 |
| `AnswerResolverTest` | Exact match, trimmed match, case-insensitive, letter mapping, ambiguous, no match | 2 |
| `DeduplicatorTest` | Content hash match, source+external_id match, no match | 2 |
| `ImportPlanBuilderTest` | Valid-only filter, stats computation, issue aggregation | 2 |
| `TimestampParserTest` | HH:MM:SS, MM:SS, edge cases (00:00:00, 99:59:59, invalid) | 2 |

**Fixture files:** Every fixture in IMPORT_SPEC.md §9 must have a corresponding test exercising it.

### 2.2 Test/Practice State Machine (`domain.engine.test`)

| Test Class | Covers | Phase |
|---|---|---|
| `TestSessionTest` | All state transitions: NOT_STARTED → IN_PROGRESS, PAUSED, SUBMITTED, ABANDONED | 4 |
| `TimerPolicyTest` | Monotonic time accumulation, pause/resume, background handling, process death recovery | 4 |
| `ScorerTest` | Score = correct × marks_correct − wrong × negative_marks; edge cases (0 questions, all correct, all wrong, with negative marking) | 4 |
| `QuestionStatusTest` | UNSEEN → SEEN → ANSWERED → SKIPPED → MARKED → ANSWERED_MARKED transitions | 4 |
| `AutoSubmitTest` | Timer expiry triggers auto-submit with correct state | 4 |

### 2.3 Hint Engine (`domain.engine.hint`)

| Test Class | Covers | Phase |
|---|---|---|
| `HintLadderTest` | Sequential opening (can't skip), hint_count tracking, solved_at_hint_level computation | 3 |
| `HintUsageRecorderTest` | Recording opened_at, ms_since_first_seen per hint | 3 |
| `RevealedStateTest` | revealed=1 if "Show answer" used without correct submission | 3 |

### 2.4 Performance Engine (`domain.engine.performance`)

| Test Class | Covers | Phase |
|---|---|---|
| `AccuracyCalculatorTest` | Accuracy formula, empty set (0/0 → "—"), single question, mixed | 6 |
| `FirstAttemptAccuracyTest` | Excludes retries, excludes unanswered | 6 |
| `TimeStatsTest` | Average, median, outlier exclusion (>10 min), empty set | 6 |
| `HintDependencyTest` | Distribution: no-hint, H1, H2, H3+, could-not-solve | 6 |
| `WeakTopicClassifierTest` | n < 10 → "Not enough data"; n ≥ 10 + accuracy < 60% → weak; ≥ 80% → strong; in between → developing | 6 |
| `WeakTopicRankerTest` | Ranking by (60% − accuracy) × weight; tie-break by recency | 6 |
| `StreakCalculatorTest` | Consecutive days, gap handling, today with 0 attempts, DST transitions, timezone changes | 6 |
| `ScoreCalculatorTest` | With/without negative marking, per-test marking scheme | 6 |
| `TopicMetricsTest` | All per-topic aggregations | 6 |

### 2.5 Revision Engine (`domain.engine.revision`)

| Test Class | Covers | Phase |
|---|---|---|
| `LeitnerSchedulerTest` | Box progression (0→1→2→3→4→5), correct without hints → advance, correct with ≥2 hints → same box, wrong → box 0, interval calculation [1,3,7,14,30,60] days | 5 |
| `RevisionQueueBuilderTest` | Due today computation, weak topics section, previous mistakes section, ordering | 5 |
| `MistakeTrackerTest` | Creation on wrong answer, wrong_count increment, consecutive_correct tracking, RESOLVED on 2 consecutive correct, REMOVED on manual remove | 5 |
| `RevisionReasonTest` | WRONG, DIFFICULT, MULTI_HINT, BOOKMARKED, MANUAL reason creation | 5 |

### 2.6 AI Context Builder (`ai.context`)

| Test Class | Covers | Phase |
|---|---|---|
| `ContextBuilderTest` | Correct payload assembly, field inclusion | 9 |
| `AnswerGateTest` | Before submission: no correct answer, no solution steps, no tricks. After submission: all included. After "Show answer": all included | 9 |
| `TokenBudgetTest` | Truncation of oldest messages, budget limits | 9 |
| `ContextContentTest` | Never includes profile, never includes other questions, only opened hints included | 9 |

### 2.7 Backup (`domain.backup`)

| Test Class | Covers | Phase |
|---|---|---|
| `BackupSerializerTest` | Round-trip: export → import produces identical data (for every entity type) | 8 |
| `ManifestTest` | format_version, app_version, db_schema_version, per-file checksums | 8 |
| `MergeImportTest` | UUID matching, newer updated_at wins, no duplicates created | 8 |
| `ReplaceImportTest` | Complete data replacement after confirmation | 8 |
| `BackupExclusionTest` | API keys never present in export, AI conversations toggleable | 8 |

### 2.8 Architecture Tests (Konsist)

| Test | Assertion | Phase |
|---|---|---|
| `DomainPurityTest` | `domain.*` classes do not import Android SDK, Room, OkHttp, Hilt | 1 |
| `NetworkIsolationTest` | Only `ai.*` and `ExternalLinkLauncher` import `okhttp3.*`, `java.net.*`, `javax.net.*` | 1 |
| `DataLayerIsolationTest` | `data.local.*` does not import `ai.*`, `feature.*`, OkHttp | 1 |
| `FeatureIsolationTest` | `feature.*` (except `feature.ai`) does not import `ai.*`, Room entities, OkHttp | 1 |

---

## 3. Instrumented Tests

### 3.1 Database (Room)

| Test Class | Covers | Phase |
|---|---|---|
| `QuestionDaoTest` | CRUD, FTS sync, content_hash lookup, source/external_id uniqueness | 2 |
| `TestDaoTest` | CRUD, test_question junction, cascade deletes | 2 |
| `AttemptDaoTest` | CRUD, answer pre-creation, status updates, time tracking | 4 |
| `AnswerDaoTest` | Submission recording, hint usage recording, status transitions | 3 |
| `MistakeDaoTest` | Creation, wrong_count increment, consecutive_correct, status changes | 5 |
| `RevisionDaoTest` | Item creation, box updates, due_at queries, suspend/unsuspend | 5 |
| `TagDaoTest` | Seeded tags exist, question_tag CRUD, saved_trick CRUD | 1 |
| `TaxonomyDaoTest` | Subject/topic/sub_topic CRUD, upsert by name, cascades | 1 |
| `SearchDaoTest` | FTS queries: English text, Hindi/Devanagari text, multi-table union, ranking | 7 |
| `StudyDayDaoTest` | Upsert, streak queries | 6 |
| `AiDaoTest` | Conversation/message CRUD, cascade deletes | 9 |

### 3.2 FTS (Full-Text Search)

| Test | Assertion | Phase |
|---|---|---|
| `FtsEnglishTest` | Search "percentage" finds questions with "Percentage", "PERCENTAGE", "percentage calculation" | 7 |
| `FtsDevanagariTest` | Search "प्रतिशत" finds questions with "प्रतिशत", "प्रतिशत गणना" | 7 |
| `FtsPerformanceTest` | 5,000 questions searched in < 100 ms | 7 |
| `FtsSyncTest` | Insert/update/delete a question → FTS table is updated | 7 |

### 3.3 Migrations

| Test | Assertion | Phase |
|---|---|---|
| `MigrationTest_1_to_N` | For every schema version step: data is preserved, new columns/tables exist, no data loss | Every phase that modifies schema |

### 3.4 Transactional Integrity

| Test | Assertion | Phase |
|---|---|---|
| `ImportTransactionTest` | Import 100 questions → inject failure at question 50 → DB has 0 new rows (rollback) | 2 |
| `SubmitTransactionTest` | Submit answer → mistake + revision_item + study_day all created atomically | 5 |

### 3.5 Process Death / Resume

| Test | Assertion | Phase |
|---|---|---|
| `PracticeResumeTest` | Kill app mid-practice → relaunch → exact question, selections, marks, per-question time restored | 3 |
| `ExamResumeTest` | Kill app mid-exam → relaunch → same state, timer resumes with correct elapsed | 4 |

---

## 4. Compose UI Tests

| Test | Scenario | Phase |
|---|---|---|
| `FirstRunImportTest` | Fresh app → tap "Import your first test" → select file → validation → preview → import → test appears in list | 2 |
| `PracticeFlowTest` | Start practice → see question → open hints sequentially → submit wrong → try again → submit correct → see approach/tricks/steps | 3 |
| `ExamFlowTest` | Start exam → answer questions → use palette → mark/skip → submit → see results → enter review mode | 4 |
| `ExamResumeUiTest` | Start exam → kill process → relaunch → "Resume test" banner → tap → continue from exact state | 4 |
| `MistakeRetryTest` | Mistake book → tap question → retry → correct → mistake status updated | 5 |
| `BackupRoundTripTest` | Export complete backup → wipe data → import backup → verify restored state | 8 |

---

## 5. Performance Budgets

| Metric | Budget | Measured On | Phase |
|---|---|---|---|
| Import 2,000 questions | < 3 seconds | Low-end emulator (2 GB RAM, 2 cores) | 2 |
| FTS search over 5,000 questions | < 100 ms | Low-end emulator | 7 |
| Cold start | < 2 seconds | Low-end emulator | 10 |
| Release APK size | < 25 MB | Release build with R8 | 10 |
| Question navigation | No jank (< 16 ms frames) | Low-end emulator | 3 |
| List scrolling (Paging) | No jank | Low-end emulator | 3 |

---

## 6. Accessibility Tests

| Check | Criteria | Phase |
|---|---|---|
| Touch targets | All interactive elements ≥ 48dp | 10 |
| TalkBack | All interactive elements have content descriptions | 10 |
| Font scaling | UI scales correctly up to 200% font size | 10 |
| Color independence | Correct/incorrect never indicated by color alone (always icon + text) | 3 |
| Contrast | All text meets WCAG AA contrast ratios (4.5:1 normal, 3:1 large) | 10 |

---

## 7. Security Tests

| Test | Assertion | Phase |
|---|---|---|
| `KeystoreStoreTest` | Store key → retrieve key → keys match | 9 |
| `KeystoreDeleteTest` | Store key → delete key → retrieve returns null | 9 |
| `KeystoreInvalidationTest` | Simulate key invalidation → app detects and asks for re-entry (no crash) | 9 |
| `BackupNoKeysTest` | Export backup → inspect ZIP → no API key material in any file | 8 |
| `LogRedactionTest` | HTTP logging redacts Authorization, x-api-key, key= headers | 9 |
| `ReleaseNoBodyLogTest` | Release build has no HTTP body logging enabled | 10 |
| `RoomNoKeysTest` | Query all Room tables → no API key plaintext found anywhere | 9 |
| `DataStoreNoKeysTest` | Inspect DataStore → no API key plaintext | 9 |

---

## 8. Offline Tests

| Test | Assertion | Phase |
|---|---|---|
| `FullOfflineFlowTest` | With network unavailable: import → practice → test → results → analytics → search → backup — all work | 10 |
| `AiOfflineTest` | AI button shows "No internet" state; no crash | 9 |
| `LinkOfflineTest` | "Watch Explanation" shows "Requires internet" state; no crash | 3 |

---

## 9. Test Data Strategy

| Data Set | Location | Purpose |
|---|---|---|
| Import fixtures (JSON) | `src/test/resources/import_fixtures/` | Golden files for validator/normalizer tests |
| Large fixture (2,000 Qs) | Generated at test time by `TestDataGenerator` | Performance budget testing (not committed to repo) |
| Seed data (exams, tags) | `src/main/` (prepopulated DB or callback) | Production seed data |
| Debug fixtures | Debug build type only, fixture loader | Manual testing during development |

---

## 10. Test Naming Convention

```
fun `GIVEN precondition WHEN action THEN expected result`()
```

Examples:
```kotlin
fun `GIVEN valid json WHEN parsed THEN returns success with all questions`()
fun `GIVEN exam in progress WHEN timer expires THEN auto submits with correct score`()
fun `GIVEN 8 attempts in topic WHEN classifying THEN returns not enough data`()
```
