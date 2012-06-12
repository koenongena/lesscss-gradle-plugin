package org.lesscss.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.lesscss.LessCompiler
import org.gradle.api.GradleException
import org.lesscss.LessSource

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


        project.task(COMPILE_TASK_NAME) << {
            LessCompiler lessCompiler = new LessCompiler()
            lessCompiler.setCompress(settings.compress);
            lessCompiler.setEncoding(settings.encoding);

            if (settings.lessJs) {
                File f = new File(settings.lessJs, it.project.projectDir)
                lessCompiler.lessJs = f.toURL()
            }

            File[] files = findInputFiles(it.project, settings.sourceDirectory, settings.includes)
            for (File file : files) {
                def outputDirectory = new File(settings.outputDirectory, it.project.buildDir)
                File output = new File(outputDirectory, file.name.replace(".less", ".css"));
                if (!output.getParentFile().exists() && !output.getParentFile().mkdirs()) {
                    throw new GradleException("Cannot create output directory " + output.getParentFile());
                }

                LessSource lessSource = new LessSource(file);
                lessCompiler.compile(lessSource, output, settings.force);
            }
        }
    }

    def findInputFiles(Project project, String directoryName, String[] filePatterns) {

        Set fileFound = new HashSet<File>()
        def directory = new File(directoryName, project.projectDir)
        if (directory.isDirectory()) {
            directory.eachFileRecurse({ it ->
                for (String filePattern : filePatterns) {
                    //TODO use includes
                    def pattern = ~/\.less/
                    if (pattern.matcher(it.name).find()) { fileFound << it }
                }
            })
        }
        return fileFound
    }
}
