package org.lesscss.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.lesscss.gradle.support.TestProject

/**
 * Created with IntelliJ IDEA.
 * User: koen
 * Date: 17/05/12
 * Time: 21:00
 */

class AbstractIntegrationTest {

    def projectsDir = new File(System.getProperty('integTest.projects'))

      def project(path) {
        def projectDir = new File(projectsDir, path)
        def project = ProjectBuilder.builder().withProjectDir(projectDir).build()
        return new TestProject(project: project)
      }
}
