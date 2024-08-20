package com.kingmang.lazurite.compiler.utils.node

import com.kingmang.lazurite.compiler.utils.node.NodeTypes.Type.*
import ru.DmN.siberia.utils.node.INodeType

enum class NodeTypes : INodeType {
    // i
    INC_LZR("inc-lzr", PARSED),
    INC_LZR_RT("inc-lzr-rt", PARSED),
    // l
    LZR_AGET("lzr-aget", PARSED),
    LZR_ASET("lzr-aset", PARSED),
    LZR_CALL("lzr-call", PARSED),
    LZR_ICALL("lzr-icall", PARSED),
    LZR_CAST("lzr-cast", PARSED),
    LZR_CAST_("lzr-cast", PROCESSED),
    LZR_CTOR("lzr-ctor", PARSED),
    LZR_DEFN("lzr-defn", PARSED),
    LZR_FGET("lzr-fget", PARSED),
    LZR_FSET("lzr-fset", PARSED),
    LZR_GET_VAR("lzr-get-var", PARSED),
    LZR_USE("lzr-use", PARSED),
    LZR_VAR("lzr-var", PARSED),

    // @
    ANN_LZR_ENTRY("@lzr-entry", PARSED);

    override val operation: String
    override val processable: Boolean
    override val compilable: Boolean

    constructor(operation: String, processable: Boolean, compilable: Boolean) {
        this.operation = operation
        this.processable = processable
        this.compilable = compilable
    }

    constructor(operation: String, type: Type) {
        this.operation = operation
        when (type) {
            NONE -> {
                this.processable = false
                this.compilable = false
            }
            PARSED -> {
                this.processable = true
                this.compilable = false
            }
            PROCESSED -> {
                this.processable = false
                this.compilable = true
            }
            ALL -> {
                this.processable = true
                this.compilable = true
            }
        }
    }

    enum class Type {
        NONE,
        ALL,
        PARSED,
        PROCESSED
    }
}