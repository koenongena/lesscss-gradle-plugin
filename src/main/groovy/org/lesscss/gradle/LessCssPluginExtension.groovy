package org.lesscss.gradle

/** 
 * User: ko
 * Date: 24/04/12
 * Time: 10:33 
 */
class LessCssPluginExtension {
    String sourceDirectory
    String outputDirectory
    String[] includes = ["**/*.less"]
    def excludes = []
    boolean force = false
    File lessJs
    def compress = false
    def encoding = 'UTF-8'

}
