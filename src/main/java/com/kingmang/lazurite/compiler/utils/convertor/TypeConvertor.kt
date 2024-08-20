package com.kingmang.lazurite.compiler.utils.convertor

import com.kingmang.lazurite.core.Types
import ru.DmN.pht.processor.ctx.getType
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object TypeConvertor {
    fun convert(type: Int, processor: Processor, ctx: ProcessingContext): VirtualType =
        when (type) {
            Types.OBJECT   -> VTDynamic
            Types.NUMBER   -> VirtualType.DOUBLE
            Types.STRING   -> ctx.getType("String", processor, ctx)
            Types.ARRAY    -> ctx.getType("[dynamic", processor, ctx)
            Types.MAP      -> ctx.getType("Map", processor, ctx)
            Types.FUNCTION -> ctx.getType("Function", processor, ctx)
            Types.CLASS    -> ctx.getType("Class", processor, ctx)
            else -> throw UnsupportedOperationException("$type")
        }
}