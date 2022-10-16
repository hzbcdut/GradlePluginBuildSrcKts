package com.learn.asm.tiemcost

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class TimeCostMethodVisitor2(
    api: Int,
    mv: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?,
) : AdviceAdapter(api, mv, access, name, descriptor), Opcodes {

    override fun onMethodEnter() {
        super.onMethodEnter()
        invokeStatic(Type.getType("Ljava/lang/System;"), Method("currentTimeMillis", "()J"))
//        val startTime = newLocal(Type.LONG_TYPE)
//        storeLocal(startTime)
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
    }

}