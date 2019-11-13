package net.hulyka.spacexviewer.domain

import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import net.hulyka.spacexviewer.api.model.LaunchData
import net.hulyka.spacexviewer.api.model.LaunchData.Links
import net.hulyka.spacexviewer.repository.LaunchRepository
import org.hamcrest.core.Is.`is`
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime.now
import org.threeten.bp.temporal.TemporalAdjusters

class LoadLaunchesForLastYearUseCaseTest {

    lateinit var usecase: LoadLaunchesForLastYearUseCase

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var launchRepository: LaunchRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        usecase = LoadLaunchesForLastYearUseCase(launchRepository)
    }

    @Test
    fun `ensure that params for repository call are correct`() {
        runBlocking {
            val start =
                LocalDate.now().minusYears(1)
                    .with(TemporalAdjusters.firstDayOfMonth()).toString()
            val end = LocalDate.now().minusMonths(1)
                .with(TemporalAdjusters.lastDayOfMonth()).toString()

            whenever(launchRepository.getPastLaunches(anyString(), anyString()))
                .thenReturn(listOf())

            usecase(this, Unit)
            verifyBlocking(launchRepository) {
                this.getPastLaunches(start, end)
            }

        }
    }

    @Test
    fun `check success result`() {
        runBlocking {
            val item1 = LaunchData(null, 123, now(), Links(), "apollo13")
            val item2 = LaunchData(null, 123, now(), Links(), "apollo14")
            val item3 = LaunchData(null, 123, now().minusMonths(3), Links(), "apollo14")
            val item4 = LaunchData(null, 123, now().minusMonths(4), Links(), "apollo14")
            whenever(launchRepository.getPastLaunches(anyString(), anyString()))
                .thenReturn(listOf(item1, item2, item3, item4))

            usecase(this, Unit) {
                it.result({ }, { Assert.assertThat(it.count(), `is`(3)) })
            }
        }
    }

    @Test
    fun `check failure result`() {
        runBlocking {
            val errorMsg = "Failed to load data"
            whenever(launchRepository.getPastLaunches(anyString(), anyString()))
                .thenThrow(IllegalStateException(errorMsg))

            usecase(this, Unit) {
                it.result({ assertEquals(errorMsg, it.exception.localizedMessage) }, {})
            }
        }
    }


}