<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../../images/favicon.svg">
        <link rel="stylesheet" type="text/css" href="../../css/bootstrap-icons.css">
        <link rel="stylesheet" type="text/css" href="../../css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../../css/custom_styles.css">
        <title>OSBB HD4 | Customer info</title>
    </head>

    <body>
        <#include "../common/header.ftlh">

        <main>
            <div class="container d-flex flex-nowrap">
                <#include "../common/sidebar.ftlh">

                <div class="w-100">
                    <#if user_info??>
                        <h2 class="fs-5 mb-0 py-4 text-center">Customer info:</h2>

                        <table class="table table-bordered table-striped m-auto text-center" id="customers_table">
                            <thead>
                                <tr>
                                    <th scope="col">id</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Authority</th>
                                    <th scope="col">Enabled</th>
                                    <th scope="col">Apart. id</th>
                                    <th scope="col">Last name</th>
                                    <th scope="col">First name</th>
                                    <th scope="col">Patronymic</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="align-middle">${user_info.id}</td>
                                    <td class="align-middle">
                                        <a href="mailto:${user_info.email}" class="text-reset text-decoration-none">${user_info.email}</a>
                                    </td>
                                    <td class="align-middle">${user_info.authority}</td>
                                    <td class="align-middle">${user_info.enabled?c}</td>
                                    <td class="align-middle">${user_info.apartmentId!}</td>
                                    <td class="align-middle">${user_info.lastName!}</td>
                                    <td class="align-middle">${user_info.firstName!}</td>
                                    <td class="align-middle">${user_info.patronymic!}</td>
                                </tr>
                            </tbody>
                        </table>

                        <div class="d-flex mt-4 mb-4">
                            <div class="pe-2" style="width: 61%">
                                <h3 class="pb-2 fs-5 text-center">User accruals history:</h3>

                                <table class="table table-bordered table-striped tablemanager m-auto fs-0_8">
                                    <thead>
                                        <tr>
                                            <th scope="col" class="align-middle disableSort" style="padding: 0.5rem 0.3rem">Date of submit</th>
                                            <th scope="col" class="align-middle disableSort" style="padding: 0.5rem 0.3rem">Service name</th>
                                            <th scope="col" class="align-middle disableSort" style="padding: 0.5rem 0.3rem">Tariff, ₴/sq.m</th>
                                            <th scope="col" class="align-middle disableSort" style="padding: 0.5rem 0.3rem">Amount, ₴</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <#if accruals?has_content>
                                            <#list accruals as accrual>
                                                <tr>
                                                    <td style="padding: 0.5rem 0.3rem">${accrual.getFormattedDateOfSubmit()}</td>
                                                    <td style="padding: 0.5rem 0.3rem">${accrual.serviceName}</td>
                                                    <td style="padding: 0.5rem 0.3rem">${accrual.tariff?string('#,##0.00')}</td>
                                                    <td style="padding: 0.5rem 0.3rem">${accrual.sumOfAccrual?string('#,##0.00')}</td>
                                                </tr>
                                            </#list>
                                        <#else>
                                            <tr>
                                                <td colspan="100%" class="text-center">No accruals yet</td>
                                            </tr>
                                        </#if>
                                    </tbody>
                                </table>
                            </div>

                            <div style="width: 39%">
                                <h3 class="pb-2 fs-5 text-center">User payments history:</h3>

                                <table class="table table-bordered table-striped tablemanager2 m-auto fs-0_8">
                                    <thead>
                                        <tr>
                                            <th scope="col" class="disableSort">Date of submit</th>
                                            <th scope="col" class="disableSort">Amount, ₴</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <#if payments?has_content>
                                            <#list payments as payment>
                                                <tr>
                                                    <td>${payment.getFormattedDateOfSubmit()}</td>
                                                    <td>${payment.amount?string('#,##0.00')}</td>
                                                </tr>
                                            </#list>
                                        <#else>
                                            <tr>
                                                <td colspan="100%" class="text-center">No payments yet</td>
                                            </tr>
                                        </#if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    <#else>
                        <h2 class="fs-5 mb-0 py-4 text-center text-danger">Customer doesn't exist</h2>
                    </#if>
                </div>
            </div>
        </main>

        <#include "../common/footer.ftlh">

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings_with_pagination.js"></script>
        <script src="/js/tableManager2.js"></script>
        <script src="/js/tableManager_settings_with_pagination2.js"></script>
    </body>
</html>