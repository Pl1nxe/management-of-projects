package ru.mop.app

import org.springframework.stereotype.Service
import ru.mop.entity.Project
import java.time.LocalDateTime

@Service
open class TaskService {

    /**
     * If the variables are not `null` then the function returns `true` when
     * `project.startDate <= startDate < endDate <= project.endDate`.
     *
     * If any variable is `null`, then the part of the expression, that uses it, takes the value `true`.
     *
     * If the project is `null`, the function returns `false`.
     *
     * @param startDate task start date and time
     * @param endDate task end date and time
     * @param project the project to which the task belongs (the method uses the `startDate` and `endDate` of the `project`)
    */
    fun isTimeRangeCorrect(startDate: LocalDateTime?, endDate: LocalDateTime?, project: Project?): Boolean {
        if (project == null)
            return false
        return !((startDate != null && endDate != null
                    && !startDate.isBefore(endDate))
                ||
//  New code
                (startDate != null && project.endDate != null
                        && !startDate.isBefore(project.endDate))
                ||
                (project.startDate != null && endDate != null
                        && !project.startDate!!.isBefore(endDate))
                ||
                (project.startDate != null && project.endDate != null
                        && !project.startDate!!.isBefore(project.endDate))
                ||
// End new code
                (project.startDate != null && startDate != null
                        && project.startDate?.isAfter(startDate) == true)
                ||
                (project.endDate != null && endDate != null
                        && project.endDate?.isBefore(endDate) == true))
    }

    fun isNameCorrect(name: String?): Boolean {
        if (name != null) {
            if (name.isEmpty() || name.length > 100)
                return false
        } else
            return false
        return true
    }

    fun isDescriptionCorrect(description: String?): Boolean {
        return !(description != null && description.length > 500)
    }
}