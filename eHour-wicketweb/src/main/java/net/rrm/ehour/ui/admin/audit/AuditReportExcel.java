/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package net.rrm.ehour.ui.admin.audit;

import net.rrm.ehour.report.criteria.ReportCriteria;
import net.rrm.ehour.ui.common.report.AbstractExcelReport;
import net.rrm.ehour.ui.common.report.AggregatedReportConfig;
import net.rrm.ehour.ui.common.report.Report;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Excel export of audit report
 */

public class AuditReportExcel extends AbstractExcelReport {
    private static final long serialVersionUID = 1597838144702980437L;

    public AuditReportExcel(IModel<ReportCriteria> reportCriteriaModel) {
        super(AggregatedReportConfig.AUDIT_REPORT, reportCriteriaModel);
    }

    @Override
    public String getFilename() {
        return "audit_report.xls";
    }

    @Override
    protected Report createReport(ReportCriteria reportCriteria) {
        return new AuditReport(reportCriteria);
    }

    @Override
    protected IModel<String> getExcelReportName() {
        return new ResourceModel("audit.report.title");
    }

    @Override
    protected IModel<String> getHeaderReportName() {
        return new ResourceModel("audit.report.title");
    }
}
