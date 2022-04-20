package com.learn.asm.tiemcost

import com.learn.asm.transform.BytesProcess
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

object TimeCostProcess  : BytesProcess {

    override fun read(bytes: ByteArray): ByteArray {
        val classReader = ClassReader(bytes)
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val injectClassVisitor = TimeCostClassVisitor(classWriter)
        classReader.accept(injectClassVisitor, 0)

        return classWriter.toByteArray()
    }
}