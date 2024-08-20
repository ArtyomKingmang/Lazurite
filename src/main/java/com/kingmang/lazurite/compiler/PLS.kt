package com.kingmang.lazurite.compiler

import ru.DmN.pht.module.utils.Module
import ru.DmN.pht.utils.addNP
import ru.DmN.pht.utils.addSMNP
import ru.DmN.pht.utils.addSNP
import ru.DmN.pht.utils.addSNU
import com.kingmang.lazurite.compiler.compilers.NCLzrCast
import com.kingmang.lazurite.compiler.parsers.NPIncLzr
import com.kingmang.lazurite.compiler.parsers.NPLzrGetVar
import com.kingmang.lazurite.compiler.processors.*
import com.kingmang.lazurite.compiler.unparsers.NULzrCast
import com.kingmang.lazurite.compiler.unparsers.NULzrGetVar
import com.kingmang.lazurite.compiler.utils.node.NodeTypes.*
import ru.DmN.siberia.utils.IPlatform.UNIVERSAL

// Pihta Lazurite Sublanguage
object PLS : Module("phtx/pls") {
    private fun initParsers() {
        // i
        addNP("inc-lzr",     NPIncLzr(INC_LZR))
        addNP("inc-lzr-rt",  NPIncLzr(INC_LZR_RT))
        // l
        addSNP(LZR_AGET)
        addSNP(LZR_ASET)
        addSNP(LZR_CALL)
        addSNP(LZR_ICALL)
        addSNP(LZR_CAST)
        addSNP(LZR_FGET)
        addNP("lzr-get-var", NPLzrGetVar)
        addSNP(LZR_VAR)
        addSNP(LZR_USE)

        // @
        addSMNP(ANN_LZR_ENTRY)
    }

    private fun initUnparsers() {
        // i
        addSNU(INC_LZR)
        addSNU(INC_LZR_RT)
        // l
        addSNU(LZR_AGET)
        addSNU(LZR_ASET)
        addSNU(LZR_CALL)
        addSNU(LZR_ICALL)
        add(LZR_CAST_,   NULzrCast)
        addSNU(LZR_FGET)
        add(LZR_GET_VAR, NULzrGetVar)
        addSNU(LZR_USE)
        addSNU(LZR_VAR)

        // @
        addSNU(ANN_LZR_ENTRY)
    }

    private fun initProcessors() {
        // i
        add(INC_LZR,     NRIncLzr(false))
        add(INC_LZR_RT,  NRIncLzr(true))
        // l
        add(LZR_AGET,    NRLzrAGet)
        add(LZR_ASET,    NRLzrASet)
        add(LZR_CALL,    NRLzrCall)
        add(LZR_ICALL,   NRLzrICall)
        add(LZR_CAST,    NRLzrCast)
        add(LZR_CAST_,   NRLzrCastB)
        add(LZR_CTOR,    NRLzrCtor)
        add(LZR_DEFN,    NRLzrDefn)
        add(LZR_FSET,    NRLzrFSet)
        add(LZR_FGET,    NRLzrFGet)
        add(LZR_GET_VAR, NRLzrGetVar)
        add(LZR_USE,     NRLzrUse)
        add(LZR_VAR,     NRLzrVar)

        // @
        add(ANN_LZR_ENTRY, NRAnnLzrEntry)
    }

    private fun initCompilers() {
        // l
        add(UNIVERSAL, LZR_CAST_, NCLzrCast)
    }

    init {
        initParsers()
        initUnparsers()
        initProcessors()
        initCompilers()
    }
}