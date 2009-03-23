/**
 * Created on Sep 28, 2007
 * Created by Thies Edeling
 * Created by Thies Edeling
 * Copyright (C) 2007 TE-CON, All Rights Reserved.
 *
 * This Software is copyright TE-CON 2007. This Software is not open source by definition. The source of the Software is available for educational purposes.
 * TE-CON holds all the ownership rights on the Software.
 * TE-CON freely grants the right to use the Software. Any reproduction or modification of this Software, whether for commercial use or open source,
 * is subject to obtaining the prior express authorization of TE-CON.
 * 
 * thies@te-con.nl
 * TE-CON
 * Legmeerstraat 4-2h, 1058ND, AMSTERDAM, The Netherlands
 *
 */

package net.rrm.ehour.ui.report.panel.aggregate;

import net.rrm.ehour.ui.common.report.AbstractExcelReport;
import net.rrm.ehour.ui.common.report.ReportConfig;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

public class EmployeeReportExcel extends AbstractExcelReport
{
	private static final long serialVersionUID = 1L;
	
	public EmployeeReportExcel()
	{
		super(ReportConfig.AGGREGATE_EMPLOYEE);
	}	
	
	@Override
	protected IModel getExcelReportName()
	{
		return new ResourceModel("report.title.employee");
	}

	@Override
	protected IModel getHeaderReportName()
	{
		return new ResourceModel("report.title.employee");
	}

	public static String getId()
	{
		return "employeeReportExcel";
	}
}
