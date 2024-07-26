package com.kingmang.lazurite.core

import com.kingmang.lazurite.parser.ast.statements.ClassDeclarationStatement
import lombok.Getter
import lombok.NoArgsConstructor
import java.util.concurrent.ConcurrentHashMap

object ClassDeclarations {
    private val declarations: MutableMap<String, ClassDeclarationStatement> = HashMap()

    @Synchronized
    fun clear() =
        declarations.clear()

    @Synchronized
    operator fun set(key: String, classDef: ClassDeclarationStatement) {
        declarations[key] = classDef
    }

    @Synchronized
    operator fun get(key: String): ClassDeclarationStatement? =
        declarations[key]
}
