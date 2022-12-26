package ru.mop.editor

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.mockito.Mockito.mock
import ru.mop.app.ProjectService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ProjectServiceTest {

    companion object {

        private var projectService: ProjectService? = null

        @JvmStatic
        @BeforeAll
        fun init() {
            projectService = mock(ProjectService::class.java)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            projectService = null
        }
    }

    @Test
    fun isTimeRangeCorrectTest() {
        val earlyDate = LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.MIN)
        val lateDate = LocalDateTime.of(LocalDate.of(2000, 1, 2), LocalTime.MIN)

        assertEquals(projectService!!.isTimeRangeCorrect(null, null), true)
        assertEquals(projectService!!.isTimeRangeCorrect(null, earlyDate), true)
        assertEquals(projectService!!.isTimeRangeCorrect(earlyDate, null), true)
        assertEquals(projectService!!.isTimeRangeCorrect(earlyDate, lateDate), true)
        assertEquals(projectService!!.isTimeRangeCorrect(earlyDate, earlyDate), false)
        assertEquals(projectService!!.isTimeRangeCorrect(lateDate, earlyDate), false)
    }

    @Test
    fun isNameCorrectTest() {
        assertEquals(projectService!!.isNameCorrect(null), false)
        var name = ""
        assertEquals(projectService!!.isNameCorrect(name), false)
        for (i in 1..50) name += " "
        assertEquals(projectService!!.isNameCorrect(name), true)
        for (i in 1..50) name += " "
        assertEquals(projectService!!.isNameCorrect(name), true)
        name += " "
        assertEquals(projectService!!.isNameCorrect(name), false)
    }
}