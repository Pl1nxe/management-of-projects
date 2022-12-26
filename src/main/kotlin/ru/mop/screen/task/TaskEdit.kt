package ru.mop.screen.task

import io.jmix.core.Messages
import io.jmix.ui.Notifications
import io.jmix.ui.screen.*
import org.springframework.beans.factory.annotation.Autowired
import ru.mop.app.TaskService
import ru.mop.entity.Task

@UiController("Task_.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
class TaskEdit : StandardEditor<Task>() {

    @Autowired
    private var taskService: TaskService? = null

    @Autowired
    private var notifications: Notifications? = null

    @Autowired
    private val messages: Messages? = null

    @Subscribe
    private fun onBeforeCommitChanges(event: BeforeCommitChangesEvent) {
        if (editedEntity.startDate == null)
            editedEntity.startDate = editedEntity.project?.startDate
        if (editedEntity.endDate == null)
            editedEntity.endDate = editedEntity.project?.endDate
        if (!taskService!!.isNameCorrect(editedEntity.name) || !taskService!!.isDescriptionCorrect(editedEntity.description))
            showIncorrectNameSizeExceptionWarning(event)
        if (!taskService!!.isTimeRangeCorrect(editedEntity.startDate, editedEntity.endDate, editedEntity.project))
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

    private fun showIncorrectNameSizeExceptionWarning(event: BeforeCommitChangesEvent) {
        notifications!!.create()
            .withCaption(messages!!.getMessage("ru.mop.screen.task/exception.IncorrectNameSizeExceptionHeader"))
            .withDescription(messages.getMessage("ru.mop.screen.task/exception.IncorrectNameSizeException"))
            .withType(Notifications.NotificationType.WARNING)
            .show()
        event.preventCommit()
    }
}