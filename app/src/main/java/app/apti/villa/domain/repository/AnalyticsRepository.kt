package app.apti.villa.domain.repository

import app.apti.villa.domain.model.AnalyticsDashboardData
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {
    fun getAnalyticsDashboard(): Flow<AnalyticsDashboardData>
}
