package org.lesscss.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * User: Koen Ongena
 * Date: 24/04/12
 * Time: 12:39 
 */
class LessCssPluginTest extends Specification {

    def "compileLessCss task is correctly added to project"() {
        given:
        Project project = ProjectBuilder.builder().build()

        when:
        project.apply plugin: 'lesscss'

        then:
        project.tasks["compileLessCss"] != null
        project.tasks["compileLessCss"].execute()
    }
}
