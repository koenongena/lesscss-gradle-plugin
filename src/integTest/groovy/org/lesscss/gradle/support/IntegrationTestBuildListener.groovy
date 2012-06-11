package org.lesscss.gradle.support

import org.gradle.BuildAdapter
import org.gradle.api.invocation.Gradle
import org.gradle.initialization.ClassLoaderRegistry

class IntegrationTestBuildListener extends BuildAdapter {
    @Override
    void projectsLoaded(final Gradle gradle) {
        gradle.rootProject.services.get(ClassLoaderRegistry.class).rootClassLoader.allowPackage("org.lesscss.gradle")
    }
}