package ru.mop.screen.task

import io.jmix.ui.screen.*
import ru.mop.entity.Task

@UiController("Task_.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
class TaskBrowse : StandardLookup<Task>()