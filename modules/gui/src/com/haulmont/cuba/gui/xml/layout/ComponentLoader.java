/*
 * Copyright (c) 2008-2016 Haulmont.
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
 *
 */
package com.haulmont.cuba.gui.xml.layout;

import com.haulmont.cuba.core.global.BeanLocator;
import com.haulmont.cuba.core.global.MessageTools;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.data.DsContext;
import com.haulmont.cuba.gui.model.ScreenData;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import org.dom4j.Element;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Base interface for loaders which create components by XML definitions.
 */
public interface ComponentLoader<T extends Component> {

    interface Context {
        /**
         * @return message pack of the context, should be used with {@link MessageTools#loadString(String)} in loaders
         */
        String getMessagesPack();
    }

    interface ComponentContext extends Context {
        ScreenOptions getOptions();

        ScreenData getScreenData();

        @Nullable
        ComponentContext getParent();

        Frame getFrame();

        String getFullFrameId();

        String getCurrentFrameId();

        void addPostInitTask(PostInitTask task);
        void executePostInitTasks();

        void addInjectTask(InjectTask task);
        void executeInjectTasks();

        void addInitTask(InitTask task);
        void executeInitTasks();

        /* old API */

        Map<String, Object> getParams();
        DsContext getDsContext();
        Map<String, String> getAliasesMap();
    }

    interface CompositeComponentContext extends Context {
        Class<? extends Component> getComponentClass();

        String getDescriptorPath();
    }

    /**
     * PostInitTasks are used to perform deferred initialization of visual components.
     */
    interface PostInitTask {
        /**
         * This method will be invoked after window initialization.
         *
         * @param context loader context
         * @param window  top-most window
         */
        void execute(ComponentContext context, Frame window);
    }

    /**
     * For internal use only.
     */
    interface InjectTask {
        /**
         * This method will be invoked after window components loading before window initialization.
         *
         * @param context top-most loader context
         * @param window top-most window
         */
        void execute(ComponentContext context, Frame window);
    }

    /**
     * For internal use only.
     */
    interface InitTask {
        /**
         * This method will be invoked after window components loading before window initialization.
         *
         * @param context loader context
         * @param window top-most window
         */
        void execute(ComponentContext context, Frame window);
    }

    Context getContext();
    void setContext(Context context);

    UiComponents getFactory();
    void setFactory(UiComponents factory);

    LayoutLoaderConfig getLayoutLoaderConfig();
    void setLayoutLoaderConfig(LayoutLoaderConfig config);

    Element getElement(Element element);
    void setElement(Element element);

    void setBeanLocator(BeanLocator beanLocator);

    /**
     * Creates result component by XML-element and loads its Id. Also creates all nested components.
     *
     * @see #getResultComponent()
     */
    void createComponent();

    /**
     * Loads component properties by XML definition.
     *
     * @see #getElement(Element)
     */
    void loadComponent();

    /**
     * Returns previously created instance of component.
     *
     * @see #createComponent()
     */
    T getResultComponent();
}