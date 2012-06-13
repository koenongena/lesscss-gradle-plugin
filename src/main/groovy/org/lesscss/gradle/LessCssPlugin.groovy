package org.lesscss.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.lesscss.LessCompiler
import org.gradle.api.GradleException
import org.lesscss.LessSource
import org.lesscss.gradle.tasks.CompileLessCss

/**
 * User: ko
 * Date: 24/04/12
 * Time: 10:32 
 */
public class LessCssPlugin implements Plugin<Project> {

    public static final String COMPILE_TASK_NAME = 'compileLessCss'
    private logger

    void apply(Project project) {
        logger = project.logger

        def settings = new LessCssPluginConvention()
        project.convention.plugins.lesscss = settings

        project.tasks.add(COMPILE_TASK_NAME, CompileLessCss.class)
    }


}
