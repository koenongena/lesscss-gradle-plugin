package org.lesscss.gradle.tasks

import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.internal.file.FileResolver
import org.lesscss.LessCompiler
import org.gradle.api.GradleException
import org.lesscss.LessSource
import org.gradle.api.Project
import org.gradle.api.tasks.util.PatternSet
import org.gradle.api.file.FileTree

/**
 * Created with IntelliJ IDEA.
 * User: koen
 * Date: 13/06/12
 * Time: 21:22
 */
class CompileLessCss extends AbstractTask {

    FileResolver fileResolver
    PatternSet patternSet

    CompileLessCss() {
        fileResolver = getServices().get(FileResolver.class);
        patternSet = new PatternSet();

    }

    @TaskAction
    protected void compile() {
        def settings = project.convention.plugins.lesscss;

        LessCompiler lessCompiler = new LessCompiler()
        lessCompiler.setCompress(settings.compress);
        lessCompiler.setEncoding(settings.encoding);

        if (settings.lessJs) {
            File f = new File(settings.lessJs, project.projectDir)
            lessCompiler.lessJs = f.toURL()
        }

        patternSet.setIncludes(settings.includes)
        patternSet.setExcludes(settings.excludes)

        FileTree files = fileResolver.resolveFilesAsTree(settings.sourceDirectory).matching(patternSet)
        for (File file : files) {
            def outputDirectory = new File(settings.outputDirectory, project.buildDir)
            File output = new File(outputDirectory, file.name.replace(".less", ".css"));
            if (!output.getParentFile().exists() && !output.getParentFile().mkdirs()) {
                throw new GradleException("Cannot create output directory " + output.getParentFile());
            }

            LessSource lessSource = new LessSource(file);
            lessCompiler.compile(lessSource, output, settings.force);
        }
    }
}
