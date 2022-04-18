package com.learn.asm

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin :Plugin<Project> {
    override fun apply(project: Project) {
        println("MyPlugin 自定义插件 ====>  apply")
        val appExtension = project.extensions.findByType(AppExtension::class.java)
        appExtension?.registerTransform(MyTransform())
    }
}