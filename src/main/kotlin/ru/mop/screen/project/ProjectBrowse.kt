package ru.mop.screen.project

import io.jmix.ui.screen.*
import ru.mop.entity.Project

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
class ProjectBrowse : StandardLookup<Project>()