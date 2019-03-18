package org.jbpm.spring.boot.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.core.env.PropertyResolver;

/**
 * Created by simon on 3/18/2019.
 */
public class DecryptRelaxPropertyResolver extends RelaxedPropertyResolver {

    public DecryptRelaxPropertyResolver(PropertyResolver resolver) {
        super(resolver);
    }

    public DecryptRelaxPropertyResolver(PropertyResolver resolver, String prefix) {
        super(resolver, prefix);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return super.getProperty(key, targetType, defaultValue);
    }
}