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
package com.github.vladislavsevruk.resolver.resolver.storage;

import com.github.vladislavsevruk.resolver.context.ResolvingContext;
import com.github.vladislavsevruk.resolver.context.StringRepresentationResolvingContextManager;
import com.github.vladislavsevruk.resolver.resolver.annotated.AnnotatedTypeBaseResolver;
import com.github.vladislavsevruk.resolver.resolver.annotated.AnnotatedTypeResolver;
import com.github.vladislavsevruk.resolver.resolver.annotated.array.AnnotatedArrayStringRepresentationResolver;
import com.github.vladislavsevruk.resolver.resolver.annotated.parameterized.ShortNameAnnotatedParameterizedTypeStringRepresentationResolver;
import com.github.vladislavsevruk.resolver.resolver.picker.TypeResolverPicker;
import com.github.vladislavsevruk.resolver.resolver.simple.TypeResolver;
import com.github.vladislavsevruk.resolver.resolver.simple.clazz.ShortNameClassStringRepresentationResolver;
import com.github.vladislavsevruk.resolver.resolver.simple.generic.GenericArrayStringRepresentationResolver;
import com.github.vladislavsevruk.resolver.resolver.simple.parameterized.ShortNameParameterizedStringRepresentationResolver;
import com.github.vladislavsevruk.resolver.resolver.simple.variable.TypeVariableStringRepresentationResolver;
import com.github.vladislavsevruk.resolver.resolver.simple.wildcard.WildcardStringRepresentationResolver;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of <code>TypeResolverStorage</code> for short name representation of generic types.
 *
 * @see TypeResolverStorage
 */
@Getter
public final class ShortNameRepresentationResolverStorage implements TypeResolverStorage<String> {

    private final List<AnnotatedTypeResolver<String>> annotatedTypeResolvers;
    private final List<TypeResolver<String>> typeResolvers;

    public ShortNameRepresentationResolverStorage() {
        this(StringRepresentationResolvingContextManager.getContext());
    }

    public ShortNameRepresentationResolverStorage(ResolvingContext<String> resolvingContext) {
        annotatedTypeResolvers = createAnnotatedTypeResolversList(resolvingContext.getTypeResolverPicker());
        typeResolvers = createTypeResolversList(resolvingContext.getTypeResolverPicker());
    }

    private List<AnnotatedTypeResolver<String>> createAnnotatedTypeResolversList(
            TypeResolverPicker<String> typeResolverPicker) {
        return Arrays.asList(new AnnotatedArrayStringRepresentationResolver(typeResolverPicker),
                new ShortNameAnnotatedParameterizedTypeStringRepresentationResolver(typeResolverPicker),
                new AnnotatedTypeBaseResolver<>(typeResolverPicker));
    }

    private List<TypeResolver<String>> createTypeResolversList(TypeResolverPicker<String> typeResolverPicker) {
        return Arrays.asList(new ShortNameClassStringRepresentationResolver(typeResolverPicker),
                new GenericArrayStringRepresentationResolver(typeResolverPicker),
                new ShortNameParameterizedStringRepresentationResolver(typeResolverPicker),
                new TypeVariableStringRepresentationResolver(),
                new WildcardStringRepresentationResolver(typeResolverPicker));
    }
}
