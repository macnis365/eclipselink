/*******************************************************************************
 * Copyright (c) 1998, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 *     05/16/2008-1.0M8 Guy Pelletier 
 *       - 218084: Implement metadata merging functionality between mapping files
 ******************************************************************************/  
package org.eclipse.persistence.internal.jpa.metadata.converters;

import java.lang.annotation.Annotation;

import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.DirectAccessor;
import org.eclipse.persistence.internal.jpa.metadata.accessors.objects.MetadataAccessibleObject;
import org.eclipse.persistence.mappings.DatabaseMapping;

/**
 * INTERNAL:
 * Object to hold onto a custom converter metadata.
 * 
 * @author Guy Pelletier
 * @since TopLink 11g
 */
public class ConverterMetadata extends AbstractConverterMetadata {
    private String m_className;
    
    /**
     * INTERNAL:
     * Used for OX mapping.
     */
    public ConverterMetadata() {
        super("<converter>");
    }
    
    /**
     * INTERNAL:
     */
    public ConverterMetadata(Annotation converter, MetadataAccessibleObject accessibleObject) {
        super(converter, accessibleObject);

        m_className = ((Class) MetadataHelper.invokeMethod("converterClass", converter)).getName();        
    }
    
    /**
     * INTERNAL:
     */
    @Override
    public boolean equals(Object objectToCompare) {
        if (objectToCompare instanceof ConverterMetadata) {
            ConverterMetadata converter = (ConverterMetadata) objectToCompare;
            
            if (! valuesMatch(getName(), converter.getName())) {
                return false;
            }
            
            return valuesMatch(m_className, converter.getClassName());
        }
        
        return false;
    }
    
    /**
     * INTERNAL:
     * Used for OX mapping.
     */
    public String getClassName() {
        return m_className;
    }
    
    /**
     * INTERNAL:
     */
    @Override
    public void initXMLObject(MetadataAccessibleObject accessibleObject) {
        super.initXMLObject(accessibleObject);
        
        m_className = initXMLClassName(m_className).getName();
    }
    
    /**
     * INTERNAL:
     * Process this converter for the given mapping.
     */
    public void process(DatabaseMapping mapping, DirectAccessor accessor) {
        accessor.setConverterClassName(mapping, getClassName());
    }
    
    /**
     * INTERNAL:
     * Used for OX mapping.
     */
    public void setClassName(String className) {
        m_className = className;
    }
}
