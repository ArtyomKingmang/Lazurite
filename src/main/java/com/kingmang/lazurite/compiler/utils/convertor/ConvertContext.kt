package com.kingmang.lazurite.compiler.utils.convertor

import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.ctx.IContextCollection
import ru.DmN.siberia.utils.ctx.IContextKey

class ConvertContext(
    val processor: Processor,
    val processingContext: ProcessingContext,
) : IContextCollection<ConvertContext> {
    override val contexts: MutableMap<IContextKey, Any?>
        get() = processingContext.contexts

    override fun with(key: IContextKey, ctx: Any?): ConvertContext =
        ConvertContext(processor, processingContext.with(key, ctx))
}