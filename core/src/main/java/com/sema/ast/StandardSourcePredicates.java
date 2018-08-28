package com.sema.ast;

import java.util.function.Predicate;

public class StandardSourcePredicates {
    public static final Predicate<String> RUBY = (fileName) -> fileName.endsWith(".rb");
}
