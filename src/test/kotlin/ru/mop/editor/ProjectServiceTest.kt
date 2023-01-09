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
        private var earlyDate: LocalDateTime? = null
        private var lateDate: LocalDateTime? = null

        @JvmStatic
        @BeforeAll
        fun init() {
            earlyDate = LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.MIN)
            lateDate = LocalDateTime.of(LocalDate.of(2000, 1, 2), LocalTime.MIN)
            projectService = mock(ProjectService::class.java)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            projectService = null
            earlyDate = null
            lateDate = null
        }
    }

    @Test
    fun testVariantsWhenSomeDateIsNull() {
        assertEquals(true, projectService!!.isTimeRangeCorrect(null, null))
        assertEquals(true, projectService!!.isTimeRangeCorrect(null, earlyDate))
        assertEquals(true, projectService!!.isTimeRangeCorrect(earlyDate, null))
    }

    @Test
    fun testInvalidVariantsOfDateCombinations() {
        assertEquals(false, projectService!!.isTimeRangeCorrect(earlyDate, earlyDate))
        assertEquals(false, projectService!!.isTimeRangeCorrect(lateDate, earlyDate))
    }

    @Test
    fun testValidVariantsOfDateCombinations() {
        assertEquals(true, projectService!!.isTimeRangeCorrect(earlyDate, lateDate))
    }

    @Test
    fun testVariantWhenNameIsNull() {
        assertEquals(false, projectService!!.isNameCorrect(null))
    }

    @Test
    fun testVariantWhenNameIsEmpty() {
        assertEquals(false, projectService!!.isNameCorrect(""))
    }

    @Test
    fun testNameLengthForIntermediateValues() {
        var name = ""
        for (i in 1..50) name += "a"
        assertEquals(true, projectService!!.isNameCorrect(name))
    }

    @Test
    fun testNameLengthForBoundaryValues() {
        var name = ""
        for (i in 1..100) name += "a"
        assertEquals(true, projectService!!.isNameCorrect(name))
        name += "a"
        assertEquals(false, projectService!!.isNameCorrect(name))
    }
}