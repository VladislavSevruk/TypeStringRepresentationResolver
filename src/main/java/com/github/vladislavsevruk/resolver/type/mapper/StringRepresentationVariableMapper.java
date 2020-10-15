/*
 * MIT License
 *
 * Copyright (c) 2020 Uladzislau Seuruk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.vladislavsevruk.resolver.type.mapper;

import com.github.vladislavsevruk.resolver.context.ResolvingContext;
import com.github.vladislavsevruk.resolver.context.StringRepresentationResolvingContextManager;
import com.github.vladislavsevruk.resolver.type.MappedVariableHierarchy;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.type.TypeVariableMap;

import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.function.IntFunction;

/**
 * Implementation of <code>TypeVariableMapper</code> for matching string representation of superclasses generic types.
 *
 * @see TypeVariableMapper
 */
public final class StringRepresentationVariableMapper extends AbstractTypeVariableMapper<String> {

    public StringRepresentationVariableMapper() {
        this(StringRepresentationResolvingContextManager.getContext());
    }

    public StringRepresentationVariableMapper(ResolvingContext<String> resolvingContext) {
        super(resolvingContext);
    }

    @Override
    protected String[] getActualTypes(TypeMeta<?> typeMeta) {
        return Arrays.stream(typeMeta.getGenericTypes()).map(this::mapParameterType).toArray(getNewArrayFunction());
    }

    @Override
    protected String getDefaultType(TypeVariable<? extends Class<?>> typeVariable) {
        return typeVariable.getName();
    }

    @Override
    protected IntFunction<String[]> getNewArrayFunction() {
        return String[]::new;
    }

    private String mapParameterType(TypeMeta<?> typeMeta) {
        MappedVariableHierarchy<String> hierarchy = context().getMappedVariableHierarchyStorage().get(typeMeta);
        Class<?> genericType = typeMeta.getType();
        TypeVariableMap<String> typeVariableMap = hierarchy.getTypeVariableMap(genericType);
        return context().getTypeResolverPicker().pickTypeResolver(genericType).resolve(typeVariableMap, genericType);
    }
}
