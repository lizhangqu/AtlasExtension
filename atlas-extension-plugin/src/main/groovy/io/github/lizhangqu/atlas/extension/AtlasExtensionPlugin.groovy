package io.github.lizhangqu.atlas.extension

import org.gradle.api.*
import org.gradle.util.GFileUtils


class AtlasExtensionPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            project.android.libraryVariants.each { variant ->
                def libVariantData = variant.variantData
                def variantScope = libVariantData.getScope()
                def variantOutputData = libVariantData.getMainOutput()
                def bundleTask = variantOutputData.packageLibTask
                if (bundleTask) {
                    bundleTask.doFirst {
                        File variantBundleDir = variantScope.getBaseBundleDir()
                        File customPackageIDSrcFile = project.file("customPackageID.txt")
                        File customPackageIdDestFile = new File(variantBundleDir, "customPackageID.txt")
                        GFileUtils.deleteQuietly(customPackageIdDestFile)
                        if (customPackageIDSrcFile.exists()) {
                            GFileUtils.copyFile(customPackageIDSrcFile, customPackageIdDestFile)
                            project.println "copy ${customPackageIDSrcFile} to awb"
                        }
                    }
                }
            }
        }
    }
}
