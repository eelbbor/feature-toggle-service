package com.featuretoggle.domain.dto;

import org.junit.BeforeClass;
import org.reflections.Reflections;
import org.testng.annotations.Test;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.lang.reflect.Constructor;
import java.util.Set;

import static org.testng.Assert.assertTrue;

@Test
public class DtoIoConfigurationTest {
    private Set<Class<?>> rootElements;

    @BeforeClass
    public void setUpClass() {
        Reflections reflections = new Reflections("com.featuretoggle.domain");
        rootElements = reflections.getTypesAnnotatedWith(XmlRootElement.class);
        assertTrue(rootElements.size() > 0);
    }

    public void xmlRootElementsMustHaveDefaultConstructor() {
        for (Class<?> element : rootElements) {
            Constructor<?>[] constructors = element.getDeclaredConstructors();
            boolean hasDefaultConstructor = false;
            for (Constructor<?> constructor : constructors) {
                hasDefaultConstructor = constructor.getParameterCount() == 0 && element.getName().equals(constructor.getName());
                if (hasDefaultConstructor) {
                    break;
                }
            }
            assertTrue(hasDefaultConstructor, element.getName() + " must declare a default constructor for JSON IO with MOXy");
        }
    }

    public void xmlRootElementsMustSetXmlTypeNameToEmptyString() {
        String errMsg = " must declare the XmlType name to be an empty string";
        for (Class<?> element : rootElements) {
            XmlType[] annotationsByType = element.getAnnotationsByType(XmlType.class);
            assertTrue(annotationsByType.length > 0 && annotationsByType[0].name().isEmpty(), element.getName() + errMsg);
        }
    }
}