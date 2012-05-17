package org.lesscss.gradle.support

import org.gradle.GradleLauncher
import org.gradle.api.Project
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue
import org.gradle.os.OperatingSystem

/**
 * Created with IntelliJ IDEA.
 * User: koen
 * Date: 17/05/12
 * Time: 21:01
 * To change this template use File | Settings | File Templates.
 */
class TestProject {
  /*@Delegate*/ Project project

  def runTasks(String... tasks) {
    runTasks([:], tasks as List<String>)
  }

  def runTasks(Map<String, Object> args, String... tasks) {
    runTasks(args, tasks as List<String>)
  }

  def runTasks(Map<String, Object> args, List<String> tasks) {
    if (OperatingSystem.current().isWindows()) {
      tasks.remove 'clean'
    }

    def startParameter = project.gradle.startParameter.newBuild()
    startParameter.projectDir = project.projectDir
    startParameter.buildFile = new File(project.projectDir, args.buildScript?:'build.gradle')
    startParameter.taskNames = tasks
    def launcher = GradleLauncher.newInstance(startParameter)
    //launcher.addListener(new MyBuildListener())
    def result = launcher.run()
    result.rethrowFailure()
  }

  def file(path) {
    project.file(path)
  }

  def exec(closure) {
    project.exec(closure)
  }

  def fileExists(path) {
    assertTrue("File ${path} must exist", file(path).isFile())
  }

  def fileDoesntExist(path) {
    if (OperatingSystem.current().isWindows()) {
      return
    }

    assertFalse("File ${path} must not exist", file(path).exists())
  }
}
