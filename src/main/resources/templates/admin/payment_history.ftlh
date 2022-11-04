<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | Payment history</title>
    </head>

    <body>
        <#include "../common/header.ftlh">

        <main>
            <div class="container d-flex flex-nowrap">
                <#include "../common/sidebar.ftlh">

                <div class="w-100 align-items-center">
                    <div class="w-100">
                        <div class="d-flex mb-4">
                            <div class="w-50">
                                <h3 class="pb-2 fs-5 text-center">Monthly payments of all sections:
                                    <#if selectValues?has_content>
                                        <select name="yearMonth_building" id="yearMonth_building_select">
                                            <#list selectValues as key, value>
                                                <option value="${key}">${value}</option>
                                            </#list>
                                        </select>
                                        <a href="/admin/payment_history/by_building/docx?yearMonth=" class="btn btn-outline-success" id="docx" download>Export to DOCX</a>
                                    </#if>
                                </h3>

                                <#if building_total??>
                                    <#include "../admin/payment_of_building.ftlh">
                                <#else>
                                    <p class="text-center">No payments</p>
                                </#if>
                            </div>

                            <div class="w-50">
                                <h3 class="pb-2 fs-5 text-center">Monthly payments by section:
                                    <#if selectValues?has_content>
                                        <select name="yearMonth_sections" id="yearMonth_sections_select">
                                            <#list selectValues as key, value>
                                                <option value="${key}">${value}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                    <a href="/admin/payment_history/by_section/xlsx?yearMonth=" class="btn btn-outline-success" id="xlsx" download>Export to XLSX</a>
                                </h3>

                                <table class="table table-bordered table-striped w-50 m-auto">
                                    <thead>
                                        <tr>
                                            <th scope="col">Section</th>
                                            <th scope="col">Total amount, ₴</th>
                                        </tr>
                                    </thead>
                                    <#include "../admin/payments_by_section.ftlh">
                                </table>
                            </div>
                        </div>

                        <h2 class="pb-2 fs-5 text-center">OSBB customers' payment history:</h2>

                        <table class="table table-bordered table-striped tablemanager w-75  m-auto">
                            <thead>
                                <tr>
                                    <th scope="col" class="disableSort">Date of submit</th>
                                    <th scope="col" class="disableSort">User id</th>
                                    <th scope="col" class="disableSort">User name</th>
                                    <th scope="col" class="disableSort">Amount, ₴</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#if payments?has_content>
                                    <#list payments as payment>
                                        <tr>
                                            <td>${payment.getFormattedDateOfSubmit()}</td>
                                            <td class="text-center">${payment.userId}</td>
                                            <td>
                                                <a href="/admin/customers/${payment.userId}" class="text-reset text-decoration-none">
                                                    ${payment.lastName!"n/a"} ${payment.firstName!"n/a"} ${payment.patronymic!"n/a"}
                                                </a>
                                            </td>
                                            <td>${payment.amount?string('#,##0.00')}</td>
                                        </tr>
                                    </#list>
                                <#else>
                                    <tr>
                                        <td colspan="100%" class="text-center">You don't have payments yet</td>
                                    </tr>
                                </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>

        <#include "../common/footer.ftlh">

        <script src="/js/payments_ajax.js"></script>
        <script src="/js/amount-by-building-to-docx.js"></script>
        <script src="/js/amount-by-section-to-xlsx.js"></script>

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings_with_pagination.js"></script>
    </body>
</html>