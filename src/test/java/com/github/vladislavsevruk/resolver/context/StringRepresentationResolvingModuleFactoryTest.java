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

import com.github.vladislavsevruk.resolver.resolver.picker.TypeResolverPicker;
import com.github.vladislavsevruk.resolver.resolver.storage.TypeResolverStorage;
import com.github.vladislavsevruk.resolver.type.mapper.TypeVariableMapper;
import com.github.vladislavsevruk.resolver.type.storage.MappedVariableHierarchyStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringRepresentationResolvingModuleFactoryTest {

    private static boolean initialAutoRefreshContext;

    @Mock
    private MappedVariableHierarchyStorage<String> mappedVariableHierarchyStorage;
    @Mock
    private TypeResolverPicker<String> typeResolverPicker;
    @Mock
    private TypeResolverStorage<String> typeResolverStorage;
    @Mock
    private TypeVariableMapper<String> typeVariableMapper;

    @BeforeAll
    static void disableContextRefresh() {
        initialAutoRefreshContext = TypeMetaResolvingContextManager.isAutoRefreshContext();
        TypeMetaResolvingContextManager.disableContextAutoRefresh();
    }

    @AfterAll
    static void setInitialAutoContextRefresh() {
        if (initialAutoRefreshContext) {
            TypeMetaResolvingContextManager.enableContextAutoRefresh();
        }
    }

    @Test
    void defaultModulesTest() {
        Assertions.assertNull(StringRepresentationResolvingModuleFactory.mappedVariableHierarchyStorage());
        Assertions.assertNull(StringRepresentationResolvingModuleFactory.typeResolverPicker());
        Assertions.assertNull(StringRepresentationResolvingModuleFactory.typeResolverStorage());
        Assertions.assertNull(StringRepresentationResolvingModuleFactory.typeVariableMapper());
    }

    @Test
    void replaceTypeConverterStorageTest() {
        ResolvingModuleFactoryMethod<String, MappedVariableHierarchyStorage<String>> factoryMethod
                = context -> mappedVariableHierarchyStorage;
        StringRepresentationResolvingModuleFactory.replaceMappedVariableHierarchyStorage(factoryMethod);
        Assertions.assertEquals(factoryMethod, StringRepresentationResolvingModuleFactory.mappedVariableHierarchyStorage());
    }

    @Test
    void replaceTypeResolverPickerTest() {
        ResolvingModuleFactoryMethod<String, TypeResolverPicker<String>> factoryMethod
                = context -> typeResolverPicker;
        StringRepresentationResolvingModuleFactory.replaceTypeResolverPicker(factoryMethod);
        Assertions.assertEquals(factoryMethod, StringRepresentationResolvingModuleFactory.typeResolverPicker());
    }

    @Test
    void replaceTypeResolverStorageTest() {
        ResolvingModuleFactoryMethod<String, TypeResolverStorage<String>> factoryMethod
                = context -> typeResolverStorage;
        StringRepresentationResolvingModuleFactory.replaceTypeResolverStorage(factoryMethod);
        Assertions.assertEquals(factoryMethod, StringRepresentationResolvingModuleFactory.typeResolverStorage());
    }

    @Test
    void replaceTypeVariableMapperTest() {
        ResolvingModuleFactoryMethod<String, TypeVariableMapper<String>> factoryMethod
                = context -> typeVariableMapper;
        StringRepresentationResolvingModuleFactory.replaceTypeVariableMapper(factoryMethod);
        Assertions.assertEquals(factoryMethod, StringRepresentationResolvingModuleFactory.typeVariableMapper());
    }
}
