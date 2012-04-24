package org.lesscss.gradle

import org.junit.Test
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import static org.junit.Assert.*
/**
 * User: ko
 * Date: 24/04/12
 * Time: 12:39 
 */
class LessCssPluginTest {

    @Test
    public void greeterPluginAddsGreetingTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'lesscss'

        assertNotNull(project.tasks[LessCssPlugin.COMPILE_TASK_NAME] != null)

        project.tasks[LessCssPlugin.COMPILE_TASK_NAME].execute()
    }
}
