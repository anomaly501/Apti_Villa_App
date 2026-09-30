# Decisions Log — AptiVilla

> Living document. Every architectural, design, or UX decision is recorded here.
> Format: `D{N}` — Decision, Alternatives considered, Reason chosen.

---

## Binding Decisions from Spec (D1–D17)

### D1 — Navigation: 5 Bottom Tabs
**Decision:** 5 bottom tabs: Home, Practice, Tests, Review, Analytics. *Review* has segmented tabs for Mistakes / Revision / Bookmarks. Profile, Inventory, Settings, Search, AI History are reached from the Home top bar.
**Alternatives:** 7 tabs (too many for Material bottom bar, max 5).
**Reason:** Material 3 guideline compliance; keeps bottom bar usable on small screens.

### D2 — Practice vs Exam Mode
**Decision:** Practice = untimed (count-up stopwatch), immediate feedback, hints/tricks/solution available. Exam = countdown timer, no hints during test, feedback at submit, then Review mode with everything visible. Mistake retries and revision sessions use Practice mode.
**Alternatives:** Single mode with optional timer.
**Reason:** Matches real exam vs study workflows. Students need both.

### D3 — correct_answer is Option Text
**Decision:** Normalizer resolves `correct_answer` to `question_option.is_correct`. Resolution order: exact → trimmed → case-insensitive → letter/number position mapping (with warning). Ambiguous or no match = error.
**Alternatives:** Require option index instead.
**Reason:** Most JSON sources in the wild use text, not index. The resolution pipeline handles edge cases explicitly.

### D4 — JSON Has No Exam/Subject/Difficulty
**Decision:** `source_metadata.topic`/`sub_topic` and per-question `category` map to Topic/SubTopic. Subject and Exam are chosen by user in Import Preview (optional, can be "Unassigned"/"Custom"). Optional JSON fields honored if present.
**Alternatives:** Require subject/exam in JSON.
**Reason:** Most user-created JSON won't have these. Making them optional and assignable in the UI keeps import friction low.

### D5 — Taxonomy vs Exam Relationship
**Decision:** Taxonomy is global: Subject → Topic → SubTopic. Exam is a tag on Test/Attempt, not a parent of Topic. Analytics "Exam → Subject → Topic" is produced by joining attempts→tests→exam. Exams are data rows (seeded + extensible), never enum-hardcoded.
**Alternatives:** Exam → Subject → Topic hierarchy.
**Reason:** "Percentage" is the same topic for SSC and Banking. A global taxonomy avoids duplicating content.

### D6 — Malformed Questions: All-or-Nothing Default
**Decision:** Default: any ERROR blocks import. Optional: "Import valid questions only (skip N)" which shows exactly what's skipped and saves an import report on the Source.
**Alternatives:** Always skip invalid; always block on any error.
**Reason:** Safety-first default prevents surprising data loss, but the escape hatch respects user control.

### D7 — hint_count vs hints.length
**Decision:** `hint_count` is optional; if present, must equal `hints.length` (else ERROR). Hint numbers must be contiguous 1..N. Same for `solution_steps`.
**Alternatives:** Ignore hint_count; auto-fix numbering.
**Reason:** Strict validation catches upstream data bugs. Silent auto-fixing hides problems.

### D8 — "Try Again" in Practice Mode
**Decision:** Student may submit multiple times per question. Each submission is recorded. First submission drives first-attempt accuracy; eventual correctness drives solved state.
**Alternatives:** Single submission only.
**Reason:** Learning-focused: students should be able to retry after seeing hints without penalty to their "solved" status.

### D9 — Weak Topic Minimum Sample
**Decision:** Never label weak/strong below n=10 attempted questions in that topic (configurable constant). Below that: "Not enough data (n/10)".
**Alternatives:** Lower threshold (5); no threshold.
**Reason:** Small samples produce misleading labels. 10 is a reasonable minimum for a percentage-based judgment.

### D10 — Accuracy vs Negative Marking
**Decision:** Accuracy = correct / attempted (skipped/unseen excluded). Score = correct × marks_correct − wrong × negative_marks (per-test, default 1/0).
**Alternatives:** Include skipped in denominator; no negative marking.
**Reason:** Accuracy as a skill measure excludes strategy (skipping). Score reflects exam-realistic marking.

### D11 — Backup Format
**Decision:** Logical export (ZIP of NDJSON + manifest), not raw SQLite copy. Survives schema migrations, is mergeable across devices.
**Alternatives:** Raw SQLite file copy; JSON only.
**Reason:** SQLite copy breaks on schema changes. NDJSON is streaming-friendly, merge-friendly, and human-inspectable.

### D12 — IDs Across Devices
**Decision:** Exportable entities carry a stable UUID (UUIDv4, TEXT, unique) plus local Long PK, and created_at/updated_at (epoch ms).
**Alternatives:** Use only Long PK; use only UUID.
**Reason:** UUID enables merge-import and future sync. Local Long PK keeps Room efficient (integer PK = fast joins). Both are needed.

### D13 — AI Answer Leak Prevention
**Decision:** Before the student submits at least once (or taps "Show answer"), the AI context omits correct answer and solution steps.
**Alternatives:** Always include answer; never include answer.
**Reason:** Prevents AI from revealing the answer before the student tries. After first attempt, including the answer enables meaningful explanation.

### D14 — Rich Content in MVP
**Decision:** MVP: plain Unicode text + light formatting (line breaks, `**bold**`). No LaTeX/images in MVP (V2). Unsupported structures are ERRORs, not silently dropped.
**Alternatives:** Support LaTeX from day 1.
**Reason:** LaTeX rendering adds significant complexity and dependency weight. Most aptitude content works with plain text. V2 adds it when the core is solid.

### D15 — PerformanceSnapshot Entity
**Decision:** Not in MVP. Compute from indexed attempt data via SQL; add caching only if benchmarks demand it.
**Alternatives:** Pre-computed cache table.
**Reason:** YAGNI. SQL on indexed data should be fast enough. Add caching only with evidence.

### D16 — Modularization
**Decision:** Single Gradle module `:app`, package-by-feature, boundaries enforced by architecture tests. Split later only if needed.
**Alternatives:** Multi-module from start.
**Reason:** Multi-module adds build complexity. For a single-developer project, architecture tests provide sufficient boundary enforcement.

### D17 — Question Types
**Decision:** MVP supports single-correct MCQ (2–6 options) only. Other formats → "unsupported structure" error naming the question.
**Alternatives:** Support multiple-correct, true/false, fill-in-blank from start.
**Reason:** MCQ covers 90%+ of competitive exam content. Other types add UI/engine complexity. V2 adds them.

---

## New Decisions (Phase 0)

### D18 — No UUID on Taxonomy Tables (subject, topic, sub_topic)
**Decision:** Taxonomy tables (`subject`, `topic`, `sub_topic`) do not carry `uuid` or `updated_at` fields. They are matched by name during import and backup merge.
**Alternatives:** Add UUID to all tables.
**Reason:** Taxonomy is a shared namespace — "Percentage" should be the same topic regardless of which device created it. Name-based matching is simpler and avoids UUID conflicts. If a future sync layer needs stable IDs, a migration can add them.

### D19 — Exam Timer Behavior on Background
**Decision:** Backgrounding the app pauses the exam timer (same as practice mode).
**Alternatives:** Keep counting in background (like a real exam).
**Reason:** On mobile, backgrounding is often involuntary (phone call, notification). Penalizing students for interruptions is hostile UX. Real exam simulation can be a future "strict mode" toggle.

### D20 — subject_id Nullable on Topic
**Decision:** `topic.subject_id` is nullable with `ON DELETE SET NULL`. A topic with `subject_id = NULL` is "unassigned" and valid.
**Alternatives:** Require subject for every topic; create a "General" subject.
**Reason:** Users may import content without specifying a subject. Forcing assignment adds friction. The Import Preview allows optional subject assignment.

### D21 — source.content_hash Scope
**Decision:** `source.content_hash` is the SHA-256 of the **entire JSON input** (not individual questions). Used to detect exact reimport of the same file.
**Alternatives:** Hash only metadata; don't hash at all.
**Reason:** Detects "same file imported twice" cheaply. Individual question hashes (on `question.content_hash`) handle per-question dedup.

### D22 — Timestamp Fields: created_at and updated_at
**Decision:** `created_at` is set once at creation. `updated_at` is set at creation and on every update. Both stored as epoch milliseconds (UTC) as INTEGER.
**Alternatives:** ISO 8601 strings; seconds instead of milliseconds.
**Reason:** Milliseconds match `System.currentTimeMillis()` and Kotlin's `Clock.System.now().toEpochMilliseconds()`. INTEGER is faster to compare and sort than TEXT in SQLite.

### D23 — Test.source_id ON DELETE behavior
**Decision:** `test.source_id` uses `ON DELETE SET NULL`. Deleting a source does not delete its tests.
**Alternatives:** CASCADE (delete test with source).
**Reason:** A user might want to delete the source metadata but keep the test and its questions. Questions have CASCADE on source (they are meaningless without provenance), but tests have independent value.

### D24 — Question Deletion vs Source Deletion
**Decision:** `question.source_id` uses `ON DELETE CASCADE`. Deleting a source deletes all its questions (and cascaded children: options, hints, tricks, steps, FTS entries).
**Alternatives:** SET NULL on question.source_id.
**Reason:** Questions are meaningless without their source provenance. Deleting a source is "undo import." Orphaned questions would lack metadata and create confusion. Cascading is the clean approach. The UI confirms this clearly.

### D25 — answer.selected_option_id ON DELETE behavior
**Decision:** `answer.selected_option_id` uses `ON DELETE SET NULL`. If the option entity is deleted (via source cascade), the answer record remains but loses the specific option reference.
**Alternatives:** CASCADE (delete answer with option).
**Reason:** Attempt history has independent value for analytics even if the original question is later deleted. SET NULL preserves the historical record.

### D26 — App Name: AptiVilla
**Decision:** Final app name is **AptiVilla**. `app_name` string resource = "AptiVilla".
**Alternatives:** AptiPrep (working title).
**Reason:** User's choice.

### D27 — applicationId: app.apti.villa
**Decision:** `applicationId = "app.apti.villa"`. Package: `app.apti.villa`.
**Alternatives:** `app.aptitude.prep` (original placeholder).
**Reason:** Aligned with final app name.

### D28 — SDK Versions: minSdk 24 / compileSdk 35 / targetSdk 35
**Decision:** `minSdk = 24` (Android 7.0), `compileSdk = 35`, `targetSdk = 35` (Android 15 — latest stable SDK platform installed).
**Alternatives:** compileSdk 36 (Android 16 platform not installed; user's phone runs Android 16 but API 35 apps run fine on it).
**Reason:** minSdk 24 covers ~97% of Indian devices. compileSdk/targetSdk 35 is the latest stable platform available. Will update to 36 when the platform is installed.
