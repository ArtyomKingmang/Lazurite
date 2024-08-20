package com.kingmang.lazurite.compiler.processors

import com.kingmang.lazurite.parser.impl.LexerImplementation
import com.kingmang.lazurite.parser.impl.ParserImplementation
import com.kingmang.lazurite.parser.preprocessor.Preprocessor
import ru.DmN.pht.processor.utils.computeString
import com.kingmang.lazurite.compiler.processor.ctx.lzrRuntime
import com.kingmang.lazurite.compiler.processor.ctx.lzrUsingOrNull
import com.kingmang.lazurite.compiler.processor.ctx.withLzrUsing
import com.kingmang.lazurite.compiler.utils.convertor.ConvertContext
import com.kingmang.lazurite.compiler.utils.convertor.Convertor
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.node.INodeInfo

class NRIncLzr(val runtime: Boolean?) : INodeProcessor<INodesList> {
    override fun process(node: INodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        process(node.info, processor.computeString(node.nodes[0], ctx), runtime, processor, ctx, valMode)

    companion object {
        fun process(info: INodeInfo, source: String, runtime: Boolean?, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
            if (ctx.lzrUsingOrNull?.any { it == source } == true)
                return null
            val context = ctx.withLzrUsing(source)
            runtime?.let { context.lzrRuntime = it }
            val stream = context.module.getModuleFile(source)
            val code = String(stream.readBytes())
            val preprocessed = Preprocessor.preprocess(code)
            val lexer = LexerImplementation(preprocessed)
            val tokens = lexer.tokenize()
            val parser = ParserImplementation(tokens, source)
            val stmt = parser.parse()
            val converted = Convertor.convert(stmt, info, ConvertContext(processor, context))
            val processed = processor.process(converted, context, valMode)
            return processed
        }
    }
}