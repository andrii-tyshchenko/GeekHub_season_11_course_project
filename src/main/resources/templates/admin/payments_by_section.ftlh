<tbody id="monthly_payments_by_section">
    <#if sections_total?has_content>
        <#list sections_total as section_total>
            <tr>
                <td class="text-center">${section_total.section}</td>
                <td class="text-center">${section_total.amount?string('#,##0.00')}</td>
            </tr>
        </#list>
    <#else>
        <tr>
            <td colspan="100%" class="text-center">No payments</td>
        </tr>
    </#if>
</tbody>