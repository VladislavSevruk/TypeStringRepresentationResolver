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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

/**
 * Implementation of <code>ResolvingContext</code> for string representation of generic types.
 *
 * @see ResolvingContext
 */
@Log4j2
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
final class StringRepresentationResolvingContext implements ResolvingContext<String> {

    MappedVariableHierarchyStorage<String> mappedVariableHierarchyStorage;
    TypeResolverPicker<String> typeResolverPicker;
    TypeResolverStorage<String> typeResolverStorage;
    TypeVariableMapper<String> typeVariableMapper;

    /**
     * Creates new instance using received modules or default implementations for nulls.
     *
     * @param mappedVariableHierarchyStorageFactoryMethod factory method for <code>MappedVariableHierarchyStorageImpl</code>
     *                                                    module implementation.
     * @param typeResolverPickerFactoryMethod             factory method for <code>TypeResolverPicker</code> module
     *                                                    implementation.
     * @param typeResolverStorageFactoryMethod            factory method for <code>TypeResolverStorage</code> module
     *                                                    implementation.
     * @param typeVariableMapperFactoryMethod             factory method for <code>TypeVariableMapper</code> module
     *                                                    implementation.
     */
    StringRepresentationResolvingContext(
            ResolvingModuleFactoryMethod<String, MappedVariableHierarchyStorage<String>> mappedVariableHierarchyStorageFactoryMethod,
            ResolvingModuleFactoryMethod<String, TypeResolverPicker<String>> typeResolverPickerFactoryMethod,
            ResolvingModuleFactoryMethod<String, TypeResolverStorage<String>> typeResolverStorageFactoryMethod,
            ResolvingModuleFactoryMethod<String, TypeVariableMapper<String>> typeVariableMapperFactoryMethod) {
        this.mappedVariableHierarchyStorage = orDefault(mappedVariableHierarchyStorageFactoryMethod,
                StringRepresentationMappedVariableHierarchyStorage::new);
        log.debug(() -> String.format("Using '%s' as mapped variable hierarchy storage.",
                mappedVariableHierarchyStorage.getClass().getName()));
        this.typeResolverPicker = orDefault(typeResolverPickerFactoryMethod, StringRepresentationResolverPicker::new);
        log.debug(() -> String.format("Using '%s' as type resolver picker.", typeResolverPicker.getClass().getName()));
        this.typeResolverStorage = orDefault(typeResolverStorageFactoryMethod, FullNameResolverStorage::new);
        log.debug(
                () -> String.format("Using '%s' as type resolver storage.", typeResolverStorage.getClass().getName()));
        this.typeVariableMapper = orDefault(typeVariableMapperFactoryMethod, StringRepresentationVariableMapper::new);
        log.debug(() -> String.format("Using '%s' as type variable mapper.", typeVariableMapper.getClass().getName()));
    }

    private <T> T orDefault(ResolvingModuleFactoryMethod<String, T> factoryMethod,
            ResolvingModuleFactoryMethod<String, T> defaultFactoryMethod) {
        if (factoryMethod != null) {
            T module = factoryMethod.get(this);
            if (module != null) {
                return module;
            }
        }
        return defaultFactoryMethod.get(this);
    }
}
