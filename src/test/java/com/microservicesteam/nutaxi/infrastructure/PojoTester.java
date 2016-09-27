package com.microservicesteam.nutaxi.infrastructure;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.EqualsMethodTester;
import org.meanbean.test.HashCodeMethodTester;

public abstract class PojoTester {

    protected final BeanTester beanTester = new BeanTester();

    protected final HashCodeMethodTester hashCodeMethodTester = new HashCodeMethodTester();

    protected final EqualsMethodTester equalsMethodTester = new EqualsMethodTester();

    protected abstract Class<?> getPojoClass();

    @Test
    public void test() {
        beanTester.testBean(getPojoClass());

        hashCodeMethodTester.testHashCodeMethod(getPojoClass());

        equalsMethodTester.testEqualsMethod(getPojoClass());
    }

}
