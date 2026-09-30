package app.apti.villa.domain.repository

import app.apti.villa.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<UserProfile?>
    suspend fun getUserProfileOnce(): UserProfile?
    suspend fun upsertUserProfile(profile: UserProfile): Long
    suspend fun updateDailyGoals(questions: Int, minutes: Int)
}
