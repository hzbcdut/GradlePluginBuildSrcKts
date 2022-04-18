package com.learn.asm.visitor

import com.learn.asm.pluginLog
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


class InjectClassVisitor(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM7, classVisitor) {
    private var className: String = ""
    private var superName: String = ""
    override fun visit(
        version: Int, access: Int, name: String,
        signature: String?, superName: String?, interfaces: Array<String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name ?: ""
        this.superName = superName ?: ""
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        pluginLog(" InjectClassVisitor ==> visitAnnotation()  in class: $className --- find: $desc")
        return super.visitAnnotation(desc, visible)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        pluginLog(" InjectClassVisitor ==> visitMethod()  name = $name   descriptor =  $descriptor in class: $className")

        val cv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (className == "com/example/learnasm/MainActivity") {
            if (name == "onCreate") {
                pluginLog("跑到这里来了吗？")
                //处理onCreate()方法
                return LifecycleMethodVisitor(cv, className, name)
            }
        }
        return cv
    }
}