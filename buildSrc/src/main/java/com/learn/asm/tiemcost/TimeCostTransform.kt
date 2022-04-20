package com.learn.asm.tiemcost

import com.learn.asm.BaseTransform
import com.learn.asm.transform.FileProcess
import com.learn.asm.transform.convertToFileProcess

class TimeCostTransform : BaseTransform(), FileProcess by TimeCostProcess.convertToFileProcess()