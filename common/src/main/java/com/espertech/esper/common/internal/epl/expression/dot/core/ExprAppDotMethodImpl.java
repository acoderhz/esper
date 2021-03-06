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
package com.espertech.esper.common.internal.epl.expression.dot.core;

import com.espertech.esper.common.internal.epl.expression.core.*;
import com.espertech.esper.common.internal.epl.join.analyze.FilterExprAnalyzerAffector;
import com.espertech.esper.common.internal.epl.join.analyze.FilterExprAnalyzerAffectorProvider;
import com.espertech.esper.common.internal.filterspec.FilterSpecCompilerAdvIndexDesc;
import com.espertech.esper.common.internal.filterspec.FilterSpecCompilerAdvIndexDescProvider;
import com.espertech.esper.common.internal.settings.SettingsApplicationDotMethod;

import java.io.StringWriter;

public class ExprAppDotMethodImpl extends ExprNodeBase implements FilterSpecCompilerAdvIndexDescProvider, FilterExprAnalyzerAffectorProvider {

    private final SettingsApplicationDotMethod desc;

    public ExprAppDotMethodImpl(SettingsApplicationDotMethod desc) {
        this.desc = desc;
    }

    public ExprNode validate(ExprValidationContext validationContext) throws ExprValidationException {
        desc.validate(validationContext);
        return null;
    }

    public SettingsApplicationDotMethod getDesc() {
        return desc;
    }

    public FilterSpecCompilerAdvIndexDesc getFilterSpecDesc() {
        return desc.getFilterSpecCompilerAdvIndexDesc();
    }

    public FilterExprAnalyzerAffector getAffector(boolean isOuterJoin) {
        return isOuterJoin ? null : desc.getFilterExprAnalyzerAffector();
    }

    public ExprForge getForge() {
        return desc.getForge();
    }

    public ExprEvaluator getExprEvaluator() {
        return desc.getForge().getExprEvaluator();
    }

    public Class getEvaluationType() {
        return desc.getForge().getEvaluationType();
    }

    public ExprPrecedenceEnum getPrecedence() {
        return ExprPrecedenceEnum.UNARY;
    }

    public void toPrecedenceFreeEPL(StringWriter writer, ExprNodeRenderableFlags flags) {
        writer.append(desc.getLhsName());
        writer.append("(");
        ExprNodeUtilityPrint.toExpressionStringMinPrecedenceAsList(desc.getLhs(), writer);
        writer.append(").");
        writer.append(desc.getDotMethodName());
        writer.append("(");
        writer.append(desc.getRhsName());
        writer.append("(");
        ExprNodeUtilityPrint.toExpressionStringMinPrecedenceAsList(desc.getRhs(), writer);
        writer.append("))");
    }

    public boolean isConstantResult() {
        return false;
    }

    public boolean equalsNode(ExprNode node, boolean ignoreStreamPrefix) {
        if (!(node instanceof ExprAppDotMethodImpl)) {
            return false;
        }
        ExprAppDotMethodImpl other = (ExprAppDotMethodImpl) node;
        if (!desc.getLhsName().equals(other.getDesc().getLhsName())) return false;
        if (!desc.getDotMethodName().equals(other.getDesc().getDotMethodName())) return false;
        if (!desc.getRhsName().equals(other.getDesc().getRhsName())) return false;
        if (!ExprNodeUtilityCompare.deepEquals(desc.getLhs(), other.getDesc().getLhs(), false)) return false;
        return ExprNodeUtilityCompare.deepEquals(desc.getRhs(), other.getDesc().getRhs(), false);
    }
}
