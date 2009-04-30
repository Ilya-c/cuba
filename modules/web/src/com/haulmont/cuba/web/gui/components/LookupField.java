/*
 * Copyright (c) 2008 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.

 * Author: Dmitry Abramov
 * Created: 22.12.2008 18:12:13
 * $Id$
 */
package com.haulmont.cuba.web.gui.components;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.data.Datasource;
import com.itmill.toolkit.ui.AbstractSelect;
import com.itmill.toolkit.ui.Select;
import com.itmill.toolkit.data.Property;

public class LookupField
    extends
        AbstractOptionsField<Select>
    implements
        com.haulmont.cuba.gui.components.LookupField, Component.Wrapper
{
    private String nullName;

    public LookupField() {
        this.component = new Select() {
            @Override
            public void setPropertyDataSource(Property newDataSource) {
                super.setPropertyDataSource(new PropertyAdapter(newDataSource) {
                    public Object getValue() {
                        final Object o = itemProperty.getValue();
                        return getKeyFromValue(o);
                    }

                    public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
                        final Object v = getValueFromKey(newValue);
                        itemProperty.setValue(v);
                    }
                });
            }
        };
        attachListener(component);
        component.setImmediate(true);
        component.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
    }

    protected Object getKeyFromValue(Object value) {
        if (value instanceof Enum) {
            return value;
        } else {
            // TODO (abramov) need to be changed
            return (value instanceof Entity) ? ((Entity) value).getId() : value;
        }
    }

    protected <T> T getValueFromKey(Object key) {
        if (key == null) return null;
        if (key instanceof Enum) { return (T)key; }

        Object v;
        if (optionsDatasource != null) {
            if (Datasource.State.INVALID.equals(optionsDatasource.getState())) {
                optionsDatasource.refresh();
            }
            v = (T) optionsDatasource.getItem(key);
        } else {
            v = key;
        }

        return (T) v;
    }

    @Override
    public void setRequired(boolean required) {
        super.setRequired(required);
        component.setNullSelectionAllowed(!required);
    }

    @Override
    public <T> T getValue() {
        final Object key = super.getValue();
        return (T)getValueFromKey(key);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(getKeyFromValue(value));
    }

    public String getNullName() {
        return nullName;
    }

    public void setNullName(String nullName) {
        this.nullName = nullName;
//        component.setNullSelectionItemId(nullName);
//        component.requestRepaint();
    }
}