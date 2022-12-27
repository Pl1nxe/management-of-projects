package ru.mop.editor
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import ru.mop.app.TaskService
import ru.mop.entity.Project
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TaskServiceTest {

    companion object {
        private var taskService: TaskService? = null
        private var dates: Array<LocalDateTime>? = null
        private var project: Project? = null

        @JvmStatic
        @BeforeAll
        fun init() {
            taskService = mock(TaskService::class.java)
            project = mock(Project::class.java)
            dates = arrayOf(
                LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.MIN),
                LocalDateTime.of(LocalDate.of(2000, 1, 2), LocalTime.MIN),
                LocalDateTime.of(LocalDate.of(2000, 1, 3), LocalTime.MIN),
                LocalDateTime.of(LocalDate.of(2000, 1, 4), LocalTime.MIN))
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            taskService = null
            dates = null
            project = null
        }

        @JvmStatic
        fun getVariantsWhenProjectIsNull(): Array<Array<Any?>> =
            arrayOf(
                arrayOf(false, null, null),
                arrayOf(false, null, dates!![0]),
                arrayOf(false, dates!![0], null),
                arrayOf(false, dates!![0], dates!![0]),
                arrayOf(false, dates!![1], dates!![0]),
                arrayOf(false, dates!![0], dates!![1]))

        @JvmStatic
        fun getVariantsWhenDatesOfProjectAreNull(): Array<Array<Any?>> =
            arrayOf(
                arrayOf(true, null, null),
                arrayOf(true, null, dates!![0]),
                arrayOf(true, dates!![0], null),
                arrayOf(false, dates!![0], dates!![0]),
                arrayOf(false, dates!![1], dates!![0]),
                arrayOf(true, dates!![0], dates!![1]))

        @JvmStatic
        fun getVariantsWhenProjectStartIsNotNull(): Array<Array<Any?>> =
            arrayOf(
                // st == null || et == null
                arrayOf(true, null, null),
                arrayOf(false, null, dates!![0]),
                arrayOf(false, null, dates!![1]),
                arrayOf(true, null, dates!![2]),
                arrayOf(false, dates!![0], null),
                arrayOf(true, dates!![1], null),
                arrayOf(true, dates!![2], null),
                // st < sp
                arrayOf(false, dates!![0], dates!![0]),
                arrayOf(false, dates!![0], dates!![1]),
                arrayOf(false, dates!![0], dates!![2]),
                // st == sp
                arrayOf(false, dates!![1], dates!![0]),
                arrayOf(false, dates!![1], dates!![1]),
                arrayOf(true, dates!![1], dates!![2]),
                // st > sp
                arrayOf(false, dates!![2], dates!![0]),
                arrayOf(false, dates!![2], dates!![1]),
                arrayOf(false, dates!![2], dates!![2]),
                arrayOf(true, dates!![2], dates!![3]))

        @JvmStatic
        fun getVariantsWhenProjectEndIsNotNull(): Array<Array<Any?>> =
            arrayOf(
                // st == null || et == null
                arrayOf(true, null, null),
                arrayOf(true, null, dates!![1]),
                arrayOf(true, null, dates!![2]),
                arrayOf(false, null, dates!![3]),
                arrayOf(true, dates!![1], null),
                arrayOf(false, dates!![2], null),
                arrayOf(false, dates!![3], null),
                // et < ep
                arrayOf(true, dates!![0], dates!![1]),
                arrayOf(false, dates!![1], dates!![1]),
                arrayOf(false, dates!![2], dates!![1]),
                arrayOf(false, dates!![3], dates!![1]),
                // et == ep
                arrayOf(true, dates!![1], dates!![2]),
                arrayOf(false, dates!![2], dates!![2]),
                arrayOf(false, dates!![3], dates!![2]),
                // et > ep
                arrayOf(false, dates!![1], dates!![3]),
                arrayOf(false, dates!![2], dates!![3]),
                arrayOf(false, dates!![3], dates!![3]))

        @JvmStatic
        fun getVariantsWhenDatesOfProjectAreNotNull(): Array<Array<Any?>> =
            arrayOf(
                // st == null || et == null
                arrayOf(true, null, null),
                arrayOf(false, null, dates!![0]),
                arrayOf(false, null, dates!![1]),
                arrayOf(true, null, dates!![2]),
                arrayOf(false, null, dates!![3]),
                arrayOf(false, dates!![0], null),
                arrayOf(true, dates!![1], null),
                arrayOf(false, dates!![2], null),
                arrayOf(false, dates!![3], null),
                // st < sp
                arrayOf(false, dates!![0], dates!![0]),
                arrayOf(false, dates!![0], dates!![1]),
                arrayOf(false, dates!![0], dates!![2]),
                arrayOf(false, dates!![0], dates!![3]),
                // st == sp
                arrayOf(false, dates!![1], dates!![0]),
                arrayOf(false, dates!![1], dates!![1]),
                arrayOf(true, dates!![1], dates!![2]),
                arrayOf(false, dates!![1], dates!![3]),
                // st > sp, st == ep
                arrayOf(false, dates!![2], dates!![0]),
                arrayOf(false, dates!![2], dates!![1]),
                arrayOf(false, dates!![2], dates!![2]),
                arrayOf(false, dates!![2], dates!![3]),
                // st > ep
                arrayOf(false, dates!![3], dates!![0]),
                arrayOf(false, dates!![3], dates!![1]),
                arrayOf(false, dates!![3], dates!![2]),
                arrayOf(false, dates!![3], dates!![3]))

        @JvmStatic
        fun getComplexVariantsWhenDatesOfProjectAreNotNull(): Array<Array<Any?>> =
            arrayOf(
                // p < t
                arrayOf(false, dates!![2], dates!![3], dates!![0], dates!![1]),
                arrayOf(false, dates!![2], dates!![2], dates!![0], dates!![1]),
                arrayOf(false, dates!![3], dates!![2], dates!![0], dates!![1]),
                // t < p
                arrayOf(false, dates!![0], dates!![1], dates!![2], dates!![3]),
                arrayOf(false, dates!![1], dates!![1], dates!![2], dates!![3]),
                arrayOf(false, dates!![1], dates!![0], dates!![2], dates!![3]),
                // st < sp < et < ep
                arrayOf(false, dates!![0], dates!![2], dates!![1], dates!![3]),
                // sp < st < ep < et
                arrayOf(false, dates!![1], dates!![3], dates!![0], dates!![2]),
                // st == sp && sp < et < ep
                arrayOf(true, dates!![0], dates!![1], dates!![0], dates!![2]),
                // et == ep && sp < st < ep
                arrayOf(true, dates!![1], dates!![2], dates!![0], dates!![2]),
                // sp < st < et < ep
                arrayOf(true, dates!![1], dates!![2], dates!![0], dates!![3]))
    }

    @Test
    fun testVariantsWhenStartProjectIsAfterEndProject() {
        val list = dates!!.toMutableList<LocalDateTime?>()
        list.add(null)
        for (a1 in 0..4)
            for (a2 in 0..4)
                for (a3 in 0..3)
                    for (a4 in a3..3) {
                        `when`(project!!.startDate).thenReturn(list[a4])
                        `when`(project!!.endDate).thenReturn(list[a3])
                        assertEquals(false, taskService!!.isTimeRangeCorrect(list[a1], list[a2], project))
                    }
    }

    @ParameterizedTest
    @MethodSource("getVariantsWhenProjectIsNull")
    fun testVariantsWhenProjectIsNull(expected: Boolean, startDate: LocalDateTime?, endDate: LocalDateTime?) {
        assertEquals(expected, taskService!!.isTimeRangeCorrect(startDate, endDate, null))
    }

    @ParameterizedTest
    @MethodSource("getVariantsWhenDatesOfProjectAreNull")
    fun testVariantsWhenDatesOfProjectAreNull(expected: Boolean, startDate: LocalDateTime?, endDate: LocalDateTime?) {
        `when`(project!!.startDate).thenReturn(null)
        `when`(project!!.endDate).thenReturn(null)
        assertEquals(expected, taskService!!.isTimeRangeCorrect(startDate, endDate, project))
    }

    @ParameterizedTest
    @MethodSource("getVariantsWhenProjectStartIsNotNull")
    fun testVariantsWhenProjectStartIsNotNull(expected: Boolean, startDate: LocalDateTime?, endDate: LocalDateTime?) {
        `when`(project!!.startDate).thenReturn(dates!![1])
        `when`(project!!.endDate).thenReturn(null)
        assertEquals(expected, taskService!!.isTimeRangeCorrect(startDate, endDate, project))
    }

    @ParameterizedTest
    @MethodSource("getVariantsWhenProjectEndIsNotNull")
    fun testVariantsWhenProjectEndIsNotNull(expected: Boolean, startDate: LocalDateTime?, endDate: LocalDateTime?) {
        `when`(project!!.startDate).thenReturn(null)
        `when`(project!!.endDate).thenReturn(dates!![2])
        assertEquals(expected, taskService!!.isTimeRangeCorrect(startDate, endDate, project))
    }

    @ParameterizedTest
    @MethodSource("getVariantsWhenDatesOfProjectAreNotNull")
    fun testVariantsWhenDatesOfProjectAreNotNull(expected: Boolean, startDate: LocalDateTime?, endDate: LocalDateTime?) {
        `when`(project!!.startDate).thenReturn(dates!![1])
        `when`(project!!.endDate).thenReturn(dates!![2])
        assertEquals(expected, taskService!!.isTimeRangeCorrect(startDate, endDate, project))
    }

    @ParameterizedTest
    @MethodSource("getComplexVariantsWhenDatesOfProjectAreNotNull")
    fun testComplexVariantsWhenDatesOfProjectAreNotNull(expected: Boolean,
                                                        startDate: LocalDateTime?,
                                                        endDate: LocalDateTime?,
                                                        projectStartDate: LocalDateTime?,
                                                        projectEndDate: LocalDateTime?) {
        `when`(project!!.startDate).thenReturn(projectStartDate)
        `when`(project!!.endDate).thenReturn(projectEndDate)
        assertEquals(expected, taskService!!.isTimeRangeCorrect(startDate, endDate, project))
    }

    @Test
    fun isNameCorrectTest() {
        assertEquals(false, taskService!!.isNameCorrect(null))
        var name = ""
        assertEquals(false, taskService!!.isNameCorrect(name))
        for (i in 1..50) name += "a"
        assertEquals(true, taskService!!.isNameCorrect(name))
        for (i in 1..50) name += "a"
        assertEquals(true, taskService!!.isNameCorrect(name))
        name += "a"
        assertEquals(false, taskService!!.isNameCorrect(name))
    }

    @Test
    fun isDescriptionCorrectTest() {
        assertEquals(true, taskService!!.isDescriptionCorrect(null))
        var description = ""
        assertEquals(true, taskService!!.isDescriptionCorrect(description))
        for (i in 1..250) description += "a"
        assertEquals(true, taskService!!.isDescriptionCorrect(description))
        for (i in 1..250) description += "a"
        assertEquals(true, taskService!!.isDescriptionCorrect(description))
        description += "a"
        assertEquals(false, taskService!!.isDescriptionCorrect(description))
    }
}