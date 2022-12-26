package ru.mop.app

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
open class ProjectService {

    fun isTimeRangeCorrect(startDate: LocalDateTime?, endDate: LocalDateTime?): Boolean {
        return !(startDate != null && endDate != null && !startDate.isBefore(endDate))
    }

    fun isNameCorrect(name: String?): Boolean {
        if (name != null) {
            if (name.isEmpty() || name.length > 100)
                return false
        } else
            return false
        return true
    }
}