[![Build Status](https://travis-ci.org/VladislavSevruk/StringRepresentationTypeResolver.svg?branch=develop)](https://travis-ci.com/VladislavSevruk/StringRepresentationTypeResolver)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_StringRepresentationTypeResolver&metric=alert_status)](https://sonarcloud.io/dashboard?id=VladislavSevruk_StringRepresentationTypeResolver)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_StringRepresentationTypeResolver&metric=coverage)](https://sonarcloud.io/component_measures?id=VladislavSevruk_StringRepresentationTypeResolver&metric=coverage)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/type-string-representation-resolver/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/type-string-representation-resolver)

# Java Type String Representation Resolver
This utility library helps to determine string representation for generics, arrays, wildcards and other complex types on runtime. 
Receiving type for generics can be a problem because of [Java doesn't store information about generic type parameters 
on runtime](https://docs.oracle.com/javase/tutorial/java/generics/erasure.html) so this library helps to solve this problem.

## Table of contents
* [Getting started](#getting-started)
  * [Maven](#maven)
  * [Gradle](#gradle)
* [Main entities](#main-entities)
  * [TypeMeta](#typemeta)
  * [TypeProvider](#typeprovider)
  * [FieldTypeResolver](#fieldtyperesolver)
  * [ExecutableTypeResolver](#executabletyperesolver)
* [Usage](#usage)
  * [Determine field type](#determine-field-type)
  * [Determine method argument and return types](#determine-method-argument-and-return-types)
* [Switching to short names](#switching-to-short-names)
* [License](#license)

## Getting started
To add library to your project perform next steps:

### Maven
Add the following dependency to your pom.xml:
```xml
<dependency>
      <groupId>com.github.vladislavsevruk</groupId>
      <artifactId>type-string-representation-resolver</artifactId>
      <version>1.0.0</version>
</dependency>
```
### Gradle
Add the following dependency to your build.gradle:
```groovy
implementation 'com.github.vladislavsevruk:type-string-representation-resolver:1.0.0'
```

## Main entities
### TypeMeta
[TypeMeta](https://github.com/VladislavSevruk/TypeResolver/blob/develop/src/main/java/com/github/vladislavsevruk/resolver/type/TypeMeta.java) 
represents metadata about type 
parameter(s) for generics and arrays.  
Examples of resulted __TypeMeta__ structure for different cases:
  - __List&lt;? extends Number&gt;__:
  ```javascript
  { type: List, wildcard: false, genericTypes: [
      { type: Number, wildcard: true, genericTypes:[] }
  ] }
  ```
  - __long[]__:
  ```javascript
  { type: long[], wildcard: false, genericTypes: [
      { type: long, wildcard: false, genericTypes:[] }
  ] }
  ```
  - __Map&lt;String, Integer&gt;[]__:
  ```javascript
  { type: Map[], wildcard: false, genericTypes: [
      { type: Map, wildcard: false, genericTypes: [
          { type: String, wildcard: false, genericTypes:[] },
          { type: Integer, wildcard: false, genericTypes:[] }
      ] }
  ] }
  ```

### TypeProvider
[TypeProvider](https://github.com/VladislavSevruk/TypeResolver/blob/develop/src/main/java/com/github/vladislavsevruk/resolver/type/TypeProvider.java) provides easy to use method 
for generating __TypeMeta__ for generics.
  - With __TypeProvider__:
  ```kotlin
  TypeMeta<?> typeMeta = new TypeProvider<Map<String, List<Integer>>>() {}.getTypeMeta();
  ```
  - Without __TypeProvider__:
  ```kotlin
  TypeMeta<?> innerTypeMeta1 = new TypeMeta<>(String.class);
  TypeMeta<?> deepInnerTypeMeta = new TypeMeta<>(Integer.class);
  TypeMeta<?>[] deepInnerTypeMetas = new TypeMeta<?>[] { deepInnerTypeMeta };
  TypeMeta<?> innerTypeMeta2 = new TypeMeta<>(List.class, deepInnerTypeMetas);
  TypeMeta<?>[] innerTypeMetas = new TypeMeta<?>[] { innerTypeMeta1, innerTypeMeta2 };
  TypeMeta<Map> typeMeta = new TypeMeta<>(Map.class, innerTypeMetas);
  ```

### FieldTypeResolver
[FieldTypeResolver](https://github.com/VladislavSevruk/TypeResolver/blob/develop/src/main/java/com/github/vladislavsevruk/resolver/resolver/field/FieldTypeResolver.java) 
can be used to determine string representation for field of provided class. Library has 
[default implementation](src/main/java/com/github/vladislavsevruk/resolver/resolver/field/FieldStringRepresentationResolver.java) 
of this interface.

### ExecutableTypeResolver
[ExecutableTypeResolver](https://github.com/VladislavSevruk/TypeResolver/blob/develop/src/main/java/com/github/vladislavsevruk/resolver/resolver/executable/ExecutableTypeResolver.java) 
can be used to determine string representation for return and argument types of provided method. Library has 
[default implementation](src/main/java/com/github/vladislavsevruk/resolver/resolver/executable/ExecutableStringRepresentationResolver.java) 
of this interface.

## Usage
### Determine field type
Let's assume that we have following generic class:
```java
public class Cake<T> {
    private List<String> ingredients;
    private T filling;
}
```

and we need to determine string representation its fields type. We can use 
[FieldStringRepresentationResolver](src/main/java/com/github/vladislavsevruk/resolver/resolver/field/FieldStringRepresentationResolver.java)
for this purpose:
```kotlin
FieldTypeResolver<String> fieldTypeResolver = new FieldStringRepresentationResolver();
// get class field to determine its type
Field fieldToResolve = Cake.class.getDeclaredField("ingredients");
String fieldRepresentation = fieldTypeResolver.resolveField(Cake.class, fieldToResolve);
```

Resulted __String__ will be: ``java.util.List<java.lang.String>``

If we need to determine type of field that use generic parameter(s) we may use subclass of 
[TypeProvider](https://github.com/VladislavSevruk/TypeResolver/blob/develop/src/main/java/com/github/vladislavsevruk/resolver/type/TypeProvider.java):
```kotlin
FieldTypeResolver<String> fieldTypeResolver = new FieldStringRepresentationResolver();
// get class field to determine its type
Field fieldToResolve = Cake.class.getDeclaredField("filling");
// create type provider with generic class where field declared
// we use String as Cake's type parameter for this example
TypeProvider<?> typeProvider = new TypeProvider<Cake<String>>() {};
String fieldRepresentation = fieldTypeResolver.resolveField(typeProvider, fieldToResolve);
```

And as a result __String__ will be: ``java.lang.String``

### Determine method argument and return types
Let's assume that our generic class have following methods:
```java
public class Cake<T> {
    ...
    public List<String> getIngredients() {
        ...
    }

    public void setFilling(T filling) {
        ...
    }
}
```

To determine their argument or return types we can use 
[ExecutableStringRepresentationResolver](src/main/java/com/github/vladislavsevruk/resolver/resolver/executable/ExecutableStringRepresentationResolver.java):
```kotlin
ExecutableTypeResolver<String> executableTypeResolver = new ExecutableStringRepresentationResolver();
// get method to determine its return and argument types
Method methodToResolve = Cake.class.getDeclaredMethod("getIngredients");
String methodReturnTypeRepresentation = executableTypeResolver.getReturnType(Cake.class, methodToResolve);
List<String> methodArgumentsRepresentationList = executableTypeResolver
        .getParameterTypes(Cake.class, methodToResolve);
```

Resulted __String__-s will be:
  - Return type (_methodReturnTypeRepresentation_): ``java.util.List<java.lang.String>``
  - Argument types (_methodArgumentsRepresentationList_): ``[]``

If we need to determine types of method that uses generic parameters we may use subclass of 
[TypeProvider](https://github.com/VladislavSevruk/TypeResolver/blob/develop/src/main/java/com/github/vladislavsevruk/resolver/type/TypeProvider.java):
```kotlin
ExecutableTypeResolver<String> executableTypeResolver = new ExecutableTypeResolverImpl();
// get method to determine its return and argument types
Method methodToResolve = Cake.class.getDeclaredMethod("setFilling", Object.class);
// create type provider with generic class where field declared
// we use String as Cake's type parameter for this example
TypeProvider<?> typeProvider = new TypeProvider<Cake<String>>() {};
String methodReturnTypeRepresentation = executableTypeResolver.getReturnType(typeProvider, methodToResolve);
List<String> methodArgumentsRepresentationList = executableTypeResolver
        .getParameterTypes(typeProvider, methodToResolve);
```

And as a result __String__-s will be:
  - Return type (_methodReturnTypeRepresentation_): ``void``
  - Argument types (_methodArgumentsRepresentationList_): ``java.lang.String``

## Switching to short names
By default resolvers generate use full names (with package name - ``java.util.List``) for resolved classes but you can 
configure them to use short names (only class name itself - ``List``) by replacing one of library modules:
```kotlin
StringRepresentationResolvingModuleFactory
        .replaceTypeResolverStorage(ShortNameRepresentationResolverStorage::new);
```

## License
This project is licensed under the MIT License, you can read the full text [here](LICENSE).
