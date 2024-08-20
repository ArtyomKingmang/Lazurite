package com.kingmang.lazurite.compiler.compilers

import com.kingmang.lazurite.compiler.ast.NodeLzrCast
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCLzrCast : INodeCompiler<NodeLzrCast> {
    override fun compile(node: NodeLzrCast, compiler: Compiler, ctx: CompilationContext) =
        compiler.compile(node.value, ctx)

    override fun compileVal(node: NodeLzrCast, compiler: Compiler, ctx: CompilationContext): Variable =
        compiler.compileVal(node.value, ctx)
}