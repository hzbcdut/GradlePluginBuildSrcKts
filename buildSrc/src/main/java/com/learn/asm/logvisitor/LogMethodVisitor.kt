package com.learn.asm.logvisitor

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class LogMethodVisitor(
    api: Int,
    mv: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?,
) : AdviceAdapter(api, mv, access, name, descriptor), Opcodes {

    override fun onMethodEnter() {
        super.onMethodEnter()
        visitLdcInsn("debug_asm")
        newInstance(Type.getType("Ljava/lang/Throwable;"))
        dup()

//        INVOKESPECIAL java/lang/Throwable.<init> ()V
        invokeConstructor(Type.getType("Ljava/lang/Throwable;"), Method("<init>", "()V"))
//        INVOKESTATIC android/util/Log.getStackTraceString (Ljava/lang/Throwable;)Ljava/lang/String;
        invokeStatic(Type.getType("Landroid/util/Log;"), Method("getStackTraceString", "(Ljava/lang/Throwable;)Ljava/lang/String;"))
        invokeStatic(Type.getType("Landroid/util/Log;"), Method("d", "(Ljava/lang/String;Ljava/lang/String;)I"))
        pop()
    }
}