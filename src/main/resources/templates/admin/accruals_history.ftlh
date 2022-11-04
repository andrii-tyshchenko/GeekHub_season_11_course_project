<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | Accruals history</title>
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
                                <h3 class="pb-2 fs-5 text-center">Monthly accruals of all sections:
                                    <#if selectValues?has_content>
                                        <select name="yearMonth_building" id="yearMonth_building_select">
                                            <#list selectValues as key, value>
                                                <option value="${key}">${value}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                    <a href="/admin/accruals_history/by_building/docx?yearMonth=" class="btn btn-outline-success" id="docx" download>Export to DOCX</a>
                                </h3>

                                <#if building_total??>
                                    <#include "../admin/accrual_of_building.ftlh">
                                <#else>
                                    <p class="text-center">No accruals</p>
                                </#if>
                            </div>

                            <div class="w-50">
                                <h3 class="pb-2 fs-5 text-center">Monthly accruals by section:
                                    <#if selectValues?has_content>
                                        <select name="yearMonth_sections" id="yearMonth_sections_select">
                                            <#list selectValues as key, value>
                                                <option value="${key}">${value}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                    <a href="/admin/accruals_history/by_section/xlsx?yearMonth=" class="btn btn-outline-success" id="xlsx" download>Export to XLSX</a>
                                </h3>

                                <table class="table table-bordered table-striped w-50 m-auto">
                                    <thead>
                                        <tr>
                                            <th scope="col">Section</th>
                                            <th scope="col">Total amount, ₴</th>
                                        </tr>
                                    </thead>
                                    <#include "../admin/accruals_by_section.ftlh">
                                </table>
                            </div>
                        </div>

                        <h2 class="pb-2 fs-5 text-center">Accruals history:</h2>

                        <table class="table table-bordered table-striped tablemanager m-auto fs-0_8">
                            <thead>
                                <tr>
                                    <th scope="col" class="disableSort">Date of submit</th>
                                    <th scope="col" class="disableSort">User id</th>
                                    <th scope="col" class="disableSort">User name</th>
                                    <th scope="col" class="disableSort">Service name</th>
                                    <th scope="col" class="disableSort">Tariff, ₴/sq.m</th>
                                    <th scope="col" class="disableSort">Sum of accrual, ₴</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#if accruals?has_content>
                                    <#list accruals as accrual>
                                        <tr>
                                            <td>${accrual.getFormattedDateOfSubmit()}</td>
                                            <td>${accrual.userId}</td>
                                            <td>
                                                <a href="/admin/customers/${accrual.userId}" class="text-reset text-decoration-none">
                                                    ${accrual.lastName!"n/a"} ${accrual.firstName!"n/a"} ${accrual.patronymic!"n/a"}
                                                </a>
                                            </td>
                                            <td>${accrual.serviceName}</td>
                                            <td>${accrual.tariff?string('#,##0.00')}</td>
                                            <td>${accrual.sumOfAccrual?string('#,##0.00')}</td>
                                        </tr>
                                    </#list>
                                </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>

        <#include "../common/footer.ftlh">

        <script src="/js/accruals_ajax.js"></script>
        <script src="/js/amount-by-building-to-docx.js"></script>
        <script src="/js/amount-by-section-to-xlsx.js"></script>

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings_with_pagination.js"></script>
    </body>
</html>