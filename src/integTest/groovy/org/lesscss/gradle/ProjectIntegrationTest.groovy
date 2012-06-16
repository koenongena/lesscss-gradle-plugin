/*
* Copyright 2012 Koen Ongena
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.lesscss.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.lesscss.gradle.support.TestProject
import spock.lang.Specification
import spock.lang.Unroll

/**
 *
 * @author Koen Ongena
 *
 */
class ProjectIntegrationTest extends Specification {

    def projectsDir = new File(System.getProperty('integTest.projects'))

    @Unroll("Project '#projectName' didn't produce the expected results ")
    def "verify content of produced css"() {
        given:
        def p = project(projectName)

        when:
        p.runTasks LessCssPlugin.COMPILE_TASK_NAME

        then:
        assertSameContentAsExpectedCssFile(p, pathOfResultingCss)

        where:
        projectName     | pathOfResultingCss
        'basic'         | 'build/less/test.css'
        'custom-lessjs' | 'build/custom-lessjs-testing/css/test.css'
    }

    @Unroll("Project '#projectName' didn't produce the expected results ")
    def "verify inclusion and exclusion of files"() {
        given:
        def p = project(projectName)

        when:
        p.runTasks LessCssPlugin.COMPILE_TASK_NAME

        then:
        includedFiles.each() { includedFile ->
            fileExists(p, includedFile)
        }
        excludedFiles.each {excludedFile ->
            fileDoesntExist(p, excludedFile)
        }

        where:
        projectName | includedFiles              | excludedFiles
        'excludes'  | ['build/less/test.css']    | ['build/less/exclude.css']
        'includes'  | ['build/less/include.css'] | ['build/less/test.css']
    }

    def "methods in file A are invoked by file B"(){
        given:
        def p = project("methodfile")

        when:
        p.runTasks LessCssPlugin.COMPILE_TASK_NAME

        then:
        assertSameContentAsExpectedCssFile(p, "build/less/test.css")
        '' == p.file('build/less/methods.css').getText()
    }



    private void fileDoesntExist(TestProject p, String fileName) {
        assert !p.file(fileName).exists()
    }

    def project(path) {
        def projectDir = new File(projectsDir, path)
        def project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        return new TestProject(project: project)
    }

    private void assertSameContentAsExpectedCssFile(TestProject p, String resultFilePath) {
        fileExists(p, resultFilePath)
        def expectedCss = p.file('expected.css').getText()
        assert p.file(resultFilePath).getText().trim() == expectedCss.trim()
    }

    private void fileExists(TestProject p, String resultFilePath) {
        assert p.file(resultFilePath).isFile()
    }
}