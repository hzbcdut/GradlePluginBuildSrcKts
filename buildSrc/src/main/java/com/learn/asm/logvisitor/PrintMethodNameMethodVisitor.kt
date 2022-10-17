package com.learn.asm.logvisitor

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class PrintMethodNameMethodVisitor (
    api: Int,
    mv: MethodVisitor,
    access: Int,
    name: String?,
    val descriptor: String?,
    private val className:String = "",
    private val signature:String?
) : AdviceAdapter(api, mv, access, name, descriptor), Opcodes {


    override fun onMethodEnter() {
        super.onMethodEnter()
        visitLdcInsn("debug_asm")
        visitLdcInsn("--->类名=$className  方法名=$name   descriptor=$descriptor")
        invokeStatic(Type.getType("Landroid/util/Log;"), Method("d", "(Ljava/lang/String;Ljava/lang/String;)I"))
        pop()
    }
}