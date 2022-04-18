package com.learn.asm.transform

import com.learn.asm.isValidClassName
import com.learn.asm.writeToZip
import java.io.File
import java.util.jar.JarFile

interface BytesProcess {

    fun read(bytes: ByteArray):ByteArray

    fun process(bytes: ByteArray): ByteArray? {
        return read(bytes)
    }
}

fun BytesProcess.convertToFileProcess(): FileProcess {
    return object : FileProcess {
        override fun processDirectory(outputDirFile: File) {
            outputDirFile.walkTopDown().filter { it.name.isValidClassName }.forEach { singleFile ->
                val readBytes = singleFile.readBytes()
                val processBytes = process(readBytes) ?: return@forEach
                singleFile.writeBytes(processBytes)
            }
        }

        override fun processJar(outputJarFile: File) {
            val outputJar = JarFile(outputJarFile)
            outputJar.entries().asSequence()
                .filter { it.name.isValidClassName }
                .forEach { jarEntry ->
                    val ins = outputJar.getInputStream(jarEntry)
                    val readBytes = ins.readBytes()
                    ins.close()
                    val processBytes = process(readBytes) ?: return@forEach
                    outputJar.close()
                    outputJarFile.writeToZip({ zipEntry -> zipEntry.name == jarEntry.name }
                    ) { processBytes }
                    return
                }
            outputJar.close()
        }
    }
}