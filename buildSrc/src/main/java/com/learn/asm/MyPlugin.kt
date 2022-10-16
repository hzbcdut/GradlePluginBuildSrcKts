package com.learn.asm

import com.android.build.gradle.AppExtension
import com.learn.asm.logvisitor.LogTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin :Plugin<Project> {
    override fun apply(project: Project) {
        println("MyPlugin 自定义插件 ====>  apply")
        val appExtension = project.extensions.findByType(AppExtension::class.java)
//        这里不知道为啥不能同时注册两个Transform
//  Cannot add task 'transformClassesWithCustomTransformForDebug' as a task with that name already exists.
//        appExtension?.registerTransform(MyTransform())
        appExtension?.registerTransform(LogTransform())
    }

    companion object{
        //当函数运行时间大于threshold阀值时判定为耗时函数，单位ms
        var threshold = 0L
    }
}