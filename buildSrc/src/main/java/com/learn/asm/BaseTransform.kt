package com.learn.asm

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.learn.asm.transform.FileProcess

open class BaseTransform :Transform(), FileProcess {
    override fun getName(): String {
        return MyTransform::class.java.canonicalName
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.PROJECT_ONLY
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(transformInvocation: TransformInvocation) {
        pluginLog("自定义Transform  start")
        val outputProvider = transformInvocation.outputProvider

        if (!isIncremental) {
            outputProvider.deleteAll()
        }
        transformInvocation.inputs.forEach { input ->
            input.jarInputs.forEach { jarInput ->
                val outputJarFile = outputProvider.getContentLocation(
                    jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR
                )
                if (isIncremental && jarInput.status == Status.REMOVED) {
                    outputJarFile.deleteRecursively()
                } else {
                    jarInput.file.copyTo(outputJarFile, true)
                }
                if (!isIncremental || jarInput.status != Status.REMOVED) {
                    processJar(outputJarFile)
                }
            }
            input.directoryInputs.forEach { dirInput ->
                val outputDir = outputProvider.getContentLocation(
                    dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY
                )
                outputDir.deleteRecursively()
                dirInput.file.copyRecursively(outputDir, true)
                processDirectory(outputDir)
            }
        }
        onTransformed()
    }
}