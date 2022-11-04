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
                        <h2 class="pb-2 fs-5 text-center">Your accruals history:</h2>

                        <table class="table table-bordered table-striped tablemanager m-auto">
                            <thead>
                                <tr>
                                    <th scope="col" class="disableSort">Date of submit</th>
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

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings_with_pagination.js"></script>
    </body>
</html>