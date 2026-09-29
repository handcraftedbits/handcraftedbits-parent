/*
 * Copyright (C) 2015-2026 HandcraftedBits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.handcraftedbits.support.internal.codegen.immutables;

import java.util.Objects;
import java.util.Set;

import org.immutables.extgenerator.GeneratedImportsModifier;

// Immutables extension that replaces java.util.Objects with a custom HandcraftedBits implementation which throws IllegalArgumentException instead of
// NullPointerException when calling requireNonNull().
public final class ObjectsImportRewriter implements GeneratedImportsModifier {
     @Override
     public void modify (final String packageOfGeneratedFile, final Set<String> imports) {
          if (imports.removeIf(importName -> importName.equals(Objects.class.getName()))) {
               imports.add(com.handcraftedbits.support.api.immutables.Objects.class.getName());
          }
     }
}
