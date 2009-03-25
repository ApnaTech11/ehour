/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * eHour is sponsored by TE-CON  - http://www.te-con.nl/
 */

package net.rrm.ehour.ui.timesheet.export.excel.part;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.rrm.ehour.report.reports.ReportData;
import net.rrm.ehour.report.reports.element.FlatReportElement;
import net.rrm.ehour.report.reports.element.ReportElement;
import net.rrm.ehour.ui.common.report.ExcelWorkbook;
import net.rrm.ehour.ui.common.report.Report;
import net.rrm.ehour.ui.common.report.ExcelWorkbook.CellStyle;
import net.rrm.ehour.ui.common.util.PoiUtil;
import net.rrm.ehour.util.DateUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * Created on Mar 25, 2009, 6:35:04 AM
 * @author Thies Edeling (thies@te-con.nl) 
 *
 */
public class ExportReportBody extends AbstractExportReportPart
{
	public ExportReportBody(int cellMargin, HSSFSheet sheet, Report report, ExcelWorkbook workbook)
	{
		super(cellMargin, sheet, report, workbook);
	}
	
	@Override
	public int createPart(int rowNumber)
	{
		Map<Date, List<FlatReportElement>> dateMap = getElementsAsDateMap(getReport());
		List<Date> dateSequence = DateUtil.createDateSequence(getReport().getReportRange(), getConfig());
		
		rowNumber = createRowForDateSequence(rowNumber, dateMap, dateSequence);
		
		return rowNumber;
	}

	private int createRowForDateSequence(int rowNumber, Map<Date, List<FlatReportElement>> dateMap, List<Date> dateSequence)
	{
		for (Date date : dateSequence)
		{
			List<FlatReportElement> flatList = dateMap.get(date);
			
			boolean borderCells = isFirstDayOfWeek(date);
			
			if (!CollectionUtils.isEmpty(flatList))
			{
				rowNumber = addColumnsToRow(date, flatList, rowNumber, borderCells);
			}
			else
			{
				rowNumber = addEmptyRow(rowNumber, date);
			}
		}
		return rowNumber;
	}
	
	private boolean isFirstDayOfWeek(Date date)
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.DAY_OF_WEEK) == getConfig().getFirstDayOfWeek();
		
	}

	private int addEmptyRow(int rowNumber, Date date)
	{
		HSSFRow row = getSheet().createRow(rowNumber++);
		createDateCell(date, row);
		return rowNumber;
	}
	
	private int addColumnsToRow(Date date, List<FlatReportElement> elements, int rowNumber, boolean borderCells)
	{
		boolean addedForDate = false;
		
		for (FlatReportElement flatReportElement : elements)
		{
			HSSFRow row = getSheet().createRow(rowNumber++);
			
			if (flatReportElement.getTotalHours() != null && flatReportElement.getTotalHours().doubleValue() > 0.0)
			{
				createDateCell(date, row);
				createProjectCell(flatReportElement.getProjectName(), row);
				createHoursCell(flatReportElement.getTotalHours(), row);
				
				addedForDate = true;
			}
		}
		
		if (!addedForDate)
		{
			HSSFRow row = getSheet().createRow(rowNumber++);
			createDateCell(date, row);	
		}
		
		return rowNumber;
		
	}

	private void createHoursCell(Number hours, HSSFRow row)
	{
		PoiUtil.createCell(row, getCellMargin() + 6 ,hours.floatValue(), CellStyle.VALUE_DIGIT, getWorkbook());
	}

	
	private void createProjectCell(String project, HSSFRow row)
	{
		PoiUtil.createCell(row, getCellMargin(), project, getWorkbook());
	}

	
	private void createDateCell(Date date, HSSFRow row)
	{
		PoiUtil.createCell(row, getCellMargin() + 2 , getFormatter().format(date), CellStyle.DATE_NORMAL, getWorkbook());
	}
	
	/**
	 * Return a map with the key being the report's date and a list of a report elements for that date as the value
	 * @param report
	 * @return
	 */
	private Map<Date, List<FlatReportElement>> getElementsAsDateMap(Report report)
	{
		Map<Date, List<FlatReportElement>> flatMap = new TreeMap<Date, List<FlatReportElement>>();
		
		ReportData reportData = report.getReportData();
		
		for (ReportElement reportElement : reportData.getReportElements())
		{
			FlatReportElement flat = (FlatReportElement)reportElement;
		
			Date date = DateUtil.nullifyTime(flat.getDayDate());
			
			List<FlatReportElement> dateElements;
			
			if (flatMap.containsKey(date))
			{
				dateElements = flatMap.get(date);
			}
			else
			{
				dateElements = new ArrayList<FlatReportElement>();
			}
			
			dateElements.add(flat);
			
			flatMap.put(date, dateElements);
		}
		
		return flatMap;
	}	
}