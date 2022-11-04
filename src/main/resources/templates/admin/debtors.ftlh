<tbody id="debtors">
    <#if debtors_info?has_content>
        <#list debtors_info as debtor_info>
            <tr>
                <td>${debtor_info.accountNumber?c}</td>
                <td>${debtor_info.section}</td>
                <td>${debtor_info.apartmentNumber}</td>
                <td>
                    <a href="mailto:${debtor_info.email}" class="text-reset text-decoration-none">${debtor_info.email}</a>
                </td>
                <td>
                    <a href="/admin/customers/${debtor_info.userId}" class="text-reset text-decoration-none">
                        ${debtor_info.lastName!"n/a"} ${debtor_info.firstName!"n/a"} ${debtor_info.patronymic!"n/a"}
                    </a>
                </td>
                <td>${debtor_info.userPayments?string('#,##0.00')}</td>
                <td>${debtor_info.userAccruals?string('#,##0.00')}</td>
                <td class="fw-bold text-danger">${debtor_info.debt?string('#,##0.00')}</td>
            </tr>
        </#list>
    <#else>
        <tr>
            <td colspan="100%" class="text-center">You don't have debtors</td>
        </tr>
    </#if>
</tbody>