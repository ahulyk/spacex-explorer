package net.hulyka.spacexviewer.domain

import net.hulyka.spacexviewer.api.model.LaunchData
import net.hulyka.spacexviewer.domain.base.Failure
import net.hulyka.spacexviewer.domain.base.Result
import net.hulyka.spacexviewer.repository.LaunchRepository
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
import javax.inject.Inject


class LoadLaunchesForLastYearUseCase @Inject constructor(private val launchRepository: LaunchRepository) :
    BaseAsyncUseCase<Map<String, Int>, Unit>() {

    private val yearMountFormat = DateTimeFormatter.ofPattern("MMM-yy")

    override suspend fun run(params: Unit): Result<Failure, Map<String, Int>> {
        val start = LocalDate.now()
            .minusYears(1)
            .with(TemporalAdjusters.firstDayOfMonth()).toString()
        val end = LocalDate.now()
            .minusMonths(1)
            .with(TemporalAdjusters.lastDayOfMonth()).toString()
        val result = launchRepository.getPastLaunches(start, end)
            .groupBy {
                it.launchDate.format(yearMountFormat)
            }
            .mapValues { it.value.count() }
        return Result.Success(result)
    }


}