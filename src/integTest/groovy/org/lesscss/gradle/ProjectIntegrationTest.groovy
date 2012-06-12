/*
* Copyright 2011 Andrew Oberstar
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

import org.gradle.tooling.BuildLauncher
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.lesscss.gradle.support.TestProject

import static org.junit.Assert.assertTrue

/**
 *
 * @author Koen Ongena
 *
 */
class ProjectIntegrationTest extends AbstractIntegrationTest {

    @Test
    void buildBasic() {
        def p = project('basic')

        p.runTasks LessCssPlugin.COMPILE_TASK_NAME

        def resultFilePath = 'build/less/test.css'

        assertSameContent(p, resultFilePath)
    }

    private void assertSameContent(TestProject p, String resultFilePath) {
        p.fileExists resultFilePath
        def expectedCss = p.file('expected.css').getText()
        p.file(resultFilePath).getText().equals(expectedCss)
    }

    @Test
    void buildCustomLessJs() {
        def p = project('custom-lessjs')

        p.runTasks LessCssPlugin.COMPILE_TASK_NAME

        def resultFile = 'build/custom-lessjs-testing/css/test.css'
        p.fileExists resultFile

        assertSameContent(p, resultFile)
    }

    @Test
    void buildExcludes() {
        def p = project('excludes')

        p.runTasks LessCssPlugin.COMPILE_TASK_NAME

        p.fileExists('build/less/test.css')
        p.fileDoesntExist('build/less/exclude.css')
    }

}