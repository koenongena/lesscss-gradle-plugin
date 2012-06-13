package org.lesscss.gradle

/** 
 * User: ko
 * Date: 24/04/12
 * Time: 10:33 
 */
class LessCssPluginConvention {
    String sourceDirectory = "src/main/less"
    String outputDirectory = "less"
    Iterable<String> includes = ["**/*.less"]
    Iterable<String> excludes = []
    boolean force = false
    String lessJs
    def compress = false
    def encoding = 'UTF-8'

    def lesscss(Closure closure){
        closure.delegate = this
        closure()
    }
}
