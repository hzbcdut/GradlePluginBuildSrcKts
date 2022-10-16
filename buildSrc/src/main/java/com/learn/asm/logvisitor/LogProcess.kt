package com.learn.asm.logvisitor

import com.learn.asm.transform.BytesProcess
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

object LogProcess  : BytesProcess {

    override fun read(bytes: ByteArray): ByteArray {
        val classReader = ClassReader(bytes)
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val injectClassVisitor = LogClassVisitor(classWriter)
        classReader.accept(injectClassVisitor, ClassReader.EXPAND_FRAMES)

        return classWriter.toByteArray()
    }
}