/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.compiler.client.option;

import com.espertech.esper.common.client.util.NameAccessModifier;

/**
 * Implement this interface to provide or override the access modifier for a script.
 */
public interface AccessModifierScriptOption {
    /**
     * Returns the access modifier for the script
     *
     * @param env information about the statement
     * @return modifier
     */
    NameAccessModifier getValue(AccessModifierScriptContext env);
}
