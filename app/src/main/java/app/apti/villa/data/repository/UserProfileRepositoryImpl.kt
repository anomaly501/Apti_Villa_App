package app.apti.villa.data.repository

import app.apti.villa.data.local.dao.UserProfileDao
import app.apti.villa.data.local.entity.UserProfileEntity
import app.apti.villa.domain.model.UserProfile
import app.apti.villa.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
) : UserProfileRepository {

    override fun getUserProfile(): Flow<UserProfile?> {
        return userProfileDao.getProfile().map { it?.toDomain() }
    }

    override suspend fun getUserProfileOnce(): UserProfile? {
        return userProfileDao.getProfileOnce()?.toDomain()
    }

    override suspend fun upsertUserProfile(profile: UserProfile): Long {
        userProfileDao.upsert(profile.toEntity())
        return profile.id
    }

    override suspend fun updateDailyGoals(questions: Int, minutes: Int) {
        val current = userProfileDao.getProfileOnce()
        val now = System.currentTimeMillis()
        if (current != null) {
            userProfileDao.update(
                current.copy(
                    dailyGoalQuestions = questions,
                    dailyGoalMinutes = minutes,
                    updatedAt = now,
                )
            )
        } else {
            userProfileDao.upsert(
                UserProfileEntity(
                    id = 1,
                    dailyGoalQuestions = questions,
                    dailyGoalMinutes = minutes,
                    createdAt = now,
                    updatedAt = now,
                )
            )
        }
    }

    private fun UserProfileEntity.toDomain() = UserProfile(
        id = id,
        name = name,
        preferredDifficulty = preferredDifficulty,
        dailyGoalQuestions = dailyGoalQuestions,
        dailyGoalMinutes = dailyGoalMinutes,
        prepStartDate = prepStartDate,
        targetExamDate = targetExamDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun UserProfile.toEntity() = UserProfileEntity(
        id = id,
        name = name,
        preferredDifficulty = preferredDifficulty,
        dailyGoalQuestions = dailyGoalQuestions,
        dailyGoalMinutes = dailyGoalMinutes,
        prepStartDate = prepStartDate,
        targetExamDate = targetExamDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
