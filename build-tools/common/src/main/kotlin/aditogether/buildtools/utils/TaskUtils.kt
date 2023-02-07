package aditogether.buildtools.utils

import org.gradle.api.Task
import org.gradle.api.tasks.TaskContainer

fun TaskContainer.configureTaskNamed(taskName: String, configure: (Task) -> Unit) {
    configureEach { task ->
        if (task.name == taskName) {
            configure(task)
        }
    }
}