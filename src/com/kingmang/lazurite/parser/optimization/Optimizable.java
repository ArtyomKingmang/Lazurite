package com.kingmang.lazurite.parser.optimization;

import com.kingmang.lazurite.parser.ast.Node;

public interface Optimizable {

    Node optimize(Node node);

    int optimizationsCount();

    String summaryInfo();
}
