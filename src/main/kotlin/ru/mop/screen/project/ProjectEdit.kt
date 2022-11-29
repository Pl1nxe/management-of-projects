package ru.mop.screen.project

import io.jmix.core.Messages
import io.jmix.ui.Notifications
import io.jmix.ui.screen.*
import org.springframework.beans.factory.annotation.Autowired
import ru.mop.entity.Project

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
class ProjectEdit : StandardEditor<Project>() {

    @Autowired
    private var notifications: Notifications? = null

    @Autowired
    private val messages: Messages? = null

    @Subscribe
    fun onBeforeCommitChanges(event: BeforeCommitChangesEvent) {
        if (editedEntity.startDate != null && editedEntity.endDate != null
            && editedEntity.startDate?.isBefore(editedEntity.endDate) == false) {
            print("\nasd\n")
            showIncorrectTimeRangeExceptionWarning(event)
        }
    }

    private fun showIncorrectTimeRangeExceptionWarning(event: BeforeCommitChangesEvent) {
        notifications!!.create()
            .withCaption(messages!!.getMessage("ru.mop.screen.project/exception.IncorrectTimeRangeExceptionHeader"))
            .withDescription(messages.getMessage("ru.mop.screen.project/exception.IncorrectTimeRangeException"))
            .withType(Notifications.NotificationType.WARNING)
            .show()
        event.preventCommit()
    }
}