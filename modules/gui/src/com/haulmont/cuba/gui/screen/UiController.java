/*
 * Copyright (c) 2008-2018 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.cuba.gui.screen;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is a "UiController" (e.g. a screen controller or fragment controller).
 * Annotated class must by a direct or indirect subclass of either {@link Screen} or {@link ScreenFragment}.
 *
 * @see Screen
 * @see ScreenFragment
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UiController {
    String ID_ATTRIBUTE = "id";
    String VALUE_ATTRIBUTE = "value";

    @AliasFor(ID_ATTRIBUTE)
    String value() default "";

    @AliasFor(VALUE_ATTRIBUTE)
    String id() default "";
}