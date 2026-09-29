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
package com.handcraftedbits.support.api.immutables;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Objects")
class ObjectsTest {
     private static final List<String> VALUE = List.of("value");

     @DisplayName("equals()")
     @Nested
     class Equals {
          @DisplayName("should return true when the objects are equal")
          @Test
          void testValuesEqual () {
               assertThat(Objects.equals(VALUE, new ArrayList<>(VALUE))).isTrue();
          }

          @DisplayName("should return false when the objects are not equal")
          @Test
          void testValuesNotEqual () {
               assertThat(Objects.equals(VALUE, List.of("other"))).isFalse();
          }

          @DisplayName("should return true only when both objects are null")
          @SuppressWarnings("ConstantValue")
          @Test
          void testValuesNull () {
               assertThat(Objects.equals(null, null)).isTrue();
               assertThat(Objects.equals(VALUE, null)).isFalse();
               assertThat(Objects.equals(null, VALUE)).isFalse();
          }
     }

     @DisplayName("hashCode()")
     @Nested
     class HashCode {
          @DisplayName("should return the object's hash code when the object is not null")
          @Test
          void testValid () {
               assertThat(Objects.hashCode(VALUE)).isEqualTo(VALUE.hashCode());
          }

          @DisplayName("should return zero when the object is null")
          @Test
          void testValueNull () {
               assertThat(Objects.hashCode(null)).isZero();
          }
     }

     @DisplayName("requireNonNull()")
     @Nested
     class RequireNonNull {
          @DisplayName("should return the object without checking the message when the object is not null")
          @Test
          void testMessageNull () {
               assertThat(Objects.requireNonNull(VALUE, null)).isSameAs(VALUE);
          }

          @DisplayName("should return the object when it is not null")
          @Test
          void testValid () {
               assertThat(Objects.requireNonNull(VALUE, "value")).isSameAs(VALUE);
          }

          @DisplayName("should throw an exception when the object is null")
          @Test
          void testValueNull () {
               assertThatThrownBy(() -> Objects.requireNonNull(null, "value")).isInstanceOfSatisfying(IllegalArgumentException.class,
                    e -> assertThat(e.getMessage()).isEqualTo("value must not be null"));
          }

          @DisplayName("should throw an exception when the object is null and the message is null or blank")
          @Test
          void testValueNullMessageInvalid () {
               for (final String message : new String[] { null, "", " " }) {
                    assertThatThrownBy(() -> Objects.requireNonNull(null, message)).isInstanceOfSatisfying(IllegalArgumentException.class,
                         e -> assertThat(e.getMessage()).isEqualTo("name must not be null or blank"));
               }
          }
     }
}
