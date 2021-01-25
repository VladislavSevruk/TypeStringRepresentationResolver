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
package com.github.vladislavsevruk.resolver.context;

import com.github.vladislavsevruk.resolver.resolver.picker.StringRepresentationResolverPicker;
import com.github.vladislavsevruk.resolver.resolver.picker.TypeResolverPicker;
import com.github.vladislavsevruk.resolver.resolver.storage.FullNameResolverStorage;
import com.github.vladislavsevruk.resolver.resolver.storage.TypeResolverStorage;
import com.github.vladislavsevruk.resolver.type.mapper.StringRepresentationVariableMapper;
import com.github.vladislavsevruk.resolver.type.mapper.TypeVariableMapper;
import com.github.vladislavsevruk.resolver.type.storage.MappedVariableHierarchyStorage;
import com.github.vladislavsevruk.resolver.type.storage.StringRepresentationMappedVariableHierarchyStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringRepresentationResolvingContextTest {

    @Mock
    private MappedVariableHierarchyStorage<String> mappedVariableHierarchyStorage;
    @Mock
    private TypeResolverPicker<String> typeResolverPicker;
    @Mock
    private TypeResolverStorage<String> typeResolverStorage;
    @Mock
    private TypeVariableMapper<String> typeVariableMapper;

    @Test
    void customMappedVariableHierarchyStorageFactoryMethodReturnsNullTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(context -> null, null,
                null, null);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }

    @Test
    void customMappedVariableHierarchyStorageTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(
                context -> mappedVariableHierarchyStorage, null, null, null);
        Assertions.assertEquals(mappedVariableHierarchyStorage, resolvingContext.getMappedVariableHierarchyStorage());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }

    @Test
    void customModulesFactoryMethodReturnNullTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(context -> null,
                context -> null, context -> null, context -> null);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }

    @Test
    void customModulesTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(
                context -> mappedVariableHierarchyStorage, context -> typeResolverPicker,
                context -> typeResolverStorage, context -> typeVariableMapper);
        Assertions.assertEquals(mappedVariableHierarchyStorage, resolvingContext.getMappedVariableHierarchyStorage());
        Assertions.assertEquals(typeResolverPicker, resolvingContext.getTypeResolverPicker());
        Assertions.assertEquals(typeResolverStorage, resolvingContext.getTypeResolverStorage());
        Assertions.assertEquals(typeVariableMapper, resolvingContext.getTypeVariableMapper());
    }

    @Test
    void customTypeResolverPickerFactoryMethodReturnsNullTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(null, context -> null,
                null, null);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }

    @Test
    void customTypeResolverPickerTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(null,
                context -> typeResolverPicker, null, null);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(typeResolverPicker, resolvingContext.getTypeResolverPicker());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }

    @Test
    void customTypeResolverStorageFactoryMethodReturnsNullTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(null, null,
                context -> null, null);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }

    @Test
    void customTypeResolverStorageTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(null, null,
                context -> typeResolverStorage, null);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(typeResolverStorage, resolvingContext.getTypeResolverStorage());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }

    @Test
    void customTypeVariableMapperFactoryMethodReturnsNullTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(null, null, null,
                context -> null);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }

    @Test
    void customTypeVariableMapperTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(null, null, null,
                context -> typeVariableMapper);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(typeVariableMapper, resolvingContext.getTypeVariableMapper());
    }

    @Test
    void defaultModulesTest() {
        ResolvingContext<String> resolvingContext = new StringRepresentationResolvingContext(null, null, null, null);
        Assertions.assertEquals(StringRepresentationMappedVariableHierarchyStorage.class,
                resolvingContext.getMappedVariableHierarchyStorage().getClass());
        Assertions.assertEquals(StringRepresentationResolverPicker.class,
                resolvingContext.getTypeResolverPicker().getClass());
        Assertions.assertEquals(FullNameResolverStorage.class, resolvingContext.getTypeResolverStorage().getClass());
        Assertions.assertEquals(StringRepresentationVariableMapper.class,
                resolvingContext.getTypeVariableMapper().getClass());
    }
}
