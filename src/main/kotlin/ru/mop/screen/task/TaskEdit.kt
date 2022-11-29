package ru.mop.screen.task

import io.jmix.core.Messages
import io.jmix.ui.Notifications
import io.jmix.ui.screen.*
import org.springframework.beans.factory.annotation.Autowired
import ru.mop.entity.Task

@UiController("Task_.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
class TaskEdit : StandardEditor<Task>() {

    @Autowired
    private var notifications: Notifications? = null

    @Autowired
    private val messages: Messages? = null

    @Subscribe
    fun onBeforeCommitChanges(event: BeforeCommitChangesEvent) {
        if (
                    (editedEntity.startDate != null && editedEntity.endDate != null
                    && editedEntity.startDate?.isBefore(editedEntity.endDate) == false)
            ||
            (
                    (editedEntity.project?.startDate != null && editedEntity.startDate != null
                    && editedEntity.project?.startDate?.isAfter(editedEntity.startDate) == true)
                    ||
                    (editedEntity.project?.endDate != null && editedEntity.endDate != null
                    && editedEntity.project?.endDate?.isBefore(editedEntity.endDate) == true)
            )
        )
            showIncorrectTimeRangeExceptionWarning(event)
    }

    private fun showIncorrectTimeRangeExceptionWarning(event: BeforeCommitChangesEvent) {
        notifications!!.create()
            .withCaption(messages!!.getMessage("ru.mop.screen.task/exception.IncorrectTimeRangeExceptionHeader"))
            .withDescription(messages.getMessage("ru.mop.screen.task/exception.IncorrectTimeRangeException"))
            .withType(Notifications.NotificationType.WARNING)
            .show()
        event.preventCommit()
    }
}