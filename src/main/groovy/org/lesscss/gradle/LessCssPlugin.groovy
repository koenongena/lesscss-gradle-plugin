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
class LessCssPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("lesscss", LessCssPluginExtension)


        project.task('compileLessCss') << {
            LessCssPluginExtension settings = project.lesscss;

            LessCompiler lessCompiler = new LessCompiler()
            lessCompiler.setCompress(settings.compress);
            lessCompiler.setEncoding(settings.encoding);

            if (settings.lessJs) {
                lessCompiler.lessJs = settings.lessJs.toURI().toURL()
            }

            File[] files = findFiles(settings.sourceDirectory, settings.includes)
            for (File file : files) {
                File output = new File(settings.outputDirectory, file.name.replace(".less", ".css"));
                if (!output.getParentFile().exists() && !output.getParentFile().mkdirs()) {
                    throw new GradleException("Cannot create output directory " + output.getParentFile());
                }

                LessSource lessSource = new LessSource(file);
                lessCompiler.compile(lessSource, output, settings.force);
            }
        }
    }

    def findFiles(String directoryName, String[] filePatterns) {
        def fileFound = []
        def directory = new File(directoryName)
        if (directory.isDirectory()) {
            directory.eachFileRecurse({
                for (String filePattern : filePatterns) {
                    if (filePattern.matcher(it.name).find()) { fileFound << it }
                }
            })
        }
        return fileFound
    }
}
