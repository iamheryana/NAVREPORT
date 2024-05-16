package solusi.hapis.webui.util;


import java.util.Arrays;
import java.util.List;

import solusi.hapis.util.Codec;

/**
 * @author <a href="dbbottle@gmail.com">hermanto</a>
 * @Date 12 Mar 12
 * ==================================================================
 * Copyright (c) 2012  All rights reserved.
 * ==================================================================
 */

public class EnumConverter<T extends Enum> {
    private final Class<T> className;

    public EnumConverter(Class<T> className) {
        this.className = className;
    }

    public <T extends Enum> List<T> getEnumToList() {
        return (List<T>) Arrays.asList(this.className.getEnumConstants());
    }

}
