package com.learn.asm

import com.learn.asm.transform.FileProcess
import com.learn.asm.transform.InjectProcess
import com.learn.asm.transform.convertToFileProcess


class MyTransform :BaseTransform(),FileProcess by InjectProcess.convertToFileProcess()