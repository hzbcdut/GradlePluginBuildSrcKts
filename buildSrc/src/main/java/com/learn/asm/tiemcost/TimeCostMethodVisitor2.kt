package com.learn.asm.tiemcost

import org.objectweb.asm.*
import org.objectweb.asm.commons.AdviceAdapter

class TimeCostMethodVisitor2(
    api: Int,
    mv: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?,
) : AdviceAdapter(api, mv, access, name, descriptor), Opcodes {

    override fun onMethodEnter() {
        super.onMethodEnter()
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
    }

}