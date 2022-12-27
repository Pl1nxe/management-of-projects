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

        assertEquals(true, projectService!!.isTimeRangeCorrect(null, null))
        assertEquals( true, projectService!!.isTimeRangeCorrect(null, earlyDate))
        assertEquals(true, projectService!!.isTimeRangeCorrect(earlyDate, null))
        assertEquals(true, projectService!!.isTimeRangeCorrect(earlyDate, lateDate))
        assertEquals(false, projectService!!.isTimeRangeCorrect(earlyDate, earlyDate))
        assertEquals(false, projectService!!.isTimeRangeCorrect(lateDate, earlyDate))
    }

    @Test
    fun isNameCorrectTest() {
        assertEquals(false, projectService!!.isNameCorrect(null))
        var name = ""
        assertEquals(false, projectService!!.isNameCorrect(name))
        for (i in 1..50) name += " "
        assertEquals(true, projectService!!.isNameCorrect(name))
        for (i in 1..50) name += " "
        assertEquals(true, projectService!!.isNameCorrect(name))
        name += " "
        assertEquals(false, projectService!!.isNameCorrect(name))
    }
}