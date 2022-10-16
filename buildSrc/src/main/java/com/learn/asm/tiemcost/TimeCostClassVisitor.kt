package com.learn.asm.tiemcost

import com.learn.asm.pluginLog
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TimeCostClassVisitor(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM7, classVisitor) {

    private var className: String = ""
    private var superName: String = ""

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
        this.superName = superName ?: ""
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        pluginLog(" TimeCostClassVisitor ==> visitAnnotation()  in class: $className --- find: $desc")
        return super.visitAnnotation(desc, visible)
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        pluginLog(" TimeCostClassVisitor ==> visitMethod()  name = $name   descriptor =  $descriptor in class: $className")
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return TimeCostMethodVisitor2(api, methodVisitor, access, name,descriptor)
    }
}