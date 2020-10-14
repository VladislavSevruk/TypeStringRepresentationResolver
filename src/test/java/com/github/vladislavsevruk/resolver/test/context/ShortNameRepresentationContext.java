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
package com.github.vladislavsevruk.resolver.test.context;

import com.github.vladislavsevruk.resolver.context.ResolvingContext;
import com.github.vladislavsevruk.resolver.resolver.picker.StringRepresentationResolverPicker;
import com.github.vladislavsevruk.resolver.resolver.picker.TypeResolverPicker;
import com.github.vladislavsevruk.resolver.resolver.storage.ShortNameRepresentationResolverStorage;
import com.github.vladislavsevruk.resolver.resolver.storage.TypeResolverStorage;
import com.github.vladislavsevruk.resolver.type.mapper.StringRepresentationVariableMapper;
import com.github.vladislavsevruk.resolver.type.mapper.TypeVariableMapper;
import com.github.vladislavsevruk.resolver.type.storage.MappedVariableHierarchyStorage;
import com.github.vladislavsevruk.resolver.type.storage.StringRepresentationMappedVariableHierarchyStorage;

public final class ShortNameRepresentationContext implements ResolvingContext<String> {

    private final MappedVariableHierarchyStorage<String> mappedVariableHierarchyStorage;
    private final TypeResolverPicker<String> typeResolverPicker;
    private final TypeResolverStorage<String> typeResolverStorage;
    private final TypeVariableMapper<String> typeVariableMapper;

    public ShortNameRepresentationContext() {
        this.mappedVariableHierarchyStorage = new StringRepresentationMappedVariableHierarchyStorage(this);
        this.typeResolverPicker = new StringRepresentationResolverPicker(this);
        this.typeResolverStorage = new ShortNameRepresentationResolverStorage(this);
        this.typeVariableMapper = new StringRepresentationVariableMapper(this);
    }

    @Override
    public MappedVariableHierarchyStorage<String> getMappedVariableHierarchyStorage() {
        return mappedVariableHierarchyStorage;
    }

    @Override
    public TypeResolverPicker<String> getTypeResolverPicker() {
        return typeResolverPicker;
    }

    @Override
    public TypeResolverStorage<String> getTypeResolverStorage() {
        return typeResolverStorage;
    }

    @Override
    public TypeVariableMapper<String> getTypeVariableMapper() {
        return typeVariableMapper;
    }
}
