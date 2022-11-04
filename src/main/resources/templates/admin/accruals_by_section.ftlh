<tbody id="monthly_accruals_by_section">
    <#if sections_total?has_content>
        <#list sections_total as section_total>
            <tr>
                <td class="text-center">${section_total.section}</td>
                <td class="text-center">${section_total.amount?string('#,##0.00')}</td>
            </tr>
        </#list>
    <#else>
        <tr>
            <td colspan="100%" class="text-center">No accruals</td>
        </tr>
    </#if>
</tbody>