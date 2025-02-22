/* SpringUtil.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Thu Jun  1 13:53:53     2006, Created by henrichen
}}IS_NOTE

Copyright (C) 2006 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.spring;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;

import javax.servlet.ServletContext;

/**
 * SpringUtil, a Spring utility.
 *
 * @author henrichen
 * @since 1.0
 */
public class SpringUtil {
    /**
     * Get the spring application context.
     */
    public static ApplicationContext getApplicationContext() {
        final Execution exec = Executions.getCurrent();
        if (exec == null) {
            throw new UiException("SpringUtil can be called only under ZK environment!");
        }

        return WebApplicationContextUtils.getRequiredWebApplicationContext((ServletContext) exec.getDesktop().getWebApp().getNativeContext());
    }

    /**
     * Get the spring bean by the specified name.
     */
    public static Object getBean(String name) {
        try {
            return getApplicationContext().getBean(name);
        } catch (final NoSuchBeanDefinitionException ex) {
            // ignore
            return null;
        }
    }

    /**
     * Get the spring bean by the specified name and class.
     */
    public static <T> T getBean(String name, Class<T> cls) {
        try {
            return getApplicationContext().getBean(name, cls);
        } catch (final NoSuchBeanDefinitionException ex) {
            // ignore
            return null;
        }
    }
}
