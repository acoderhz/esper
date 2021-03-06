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
package com.espertech.esper.runtime.internal.kernel.thread;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.internal.context.util.EPStatementAgentInstanceHandle;
import com.espertech.esper.runtime.internal.kernel.stage.EPStageEventServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Route execution work unit.
 */
public class RouteUnitMultipleStaged implements RouteUnitRunnable {
    private static final Logger log = LoggerFactory.getLogger(RouteUnitMultipleStaged.class);

    private final EPStageEventServiceImpl epRuntime;
    private final EventBean theEvent;
    private Object callbackList;
    private EPStatementAgentInstanceHandle handle;
    private final long filterVersion;

    /**
     * Ctor.
     *
     * @param epRuntime     runtime to process
     * @param callbackList  callback list
     * @param theEvent      event to pass
     * @param handle        statement handle
     * @param filterVersion version of filter
     */
    public RouteUnitMultipleStaged(EPStageEventServiceImpl epRuntime, Object callbackList, EventBean theEvent, EPStatementAgentInstanceHandle handle, long filterVersion) {
        this.epRuntime = epRuntime;
        this.callbackList = callbackList;
        this.theEvent = theEvent;
        this.handle = handle;
        this.filterVersion = filterVersion;
    }

    public void run() {
        try {
            epRuntime.processStatementFilterMultiple(handle, callbackList, theEvent, filterVersion, 0);

            epRuntime.dispatch();

            epRuntime.processThreadWorkQueue();
        } catch (RuntimeException e) {
            log.error("Unexpected error processing multiple route execution: " + e.getMessage(), e);
        }
    }

}
