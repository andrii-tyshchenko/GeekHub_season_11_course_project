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
                        <h2 class="pb-2 fs-5 text-center">Your payment history:</h2>

                        <table class="table table-bordered table-striped tablemanager w-75 m-auto">
                            <thead>
                                <tr>
                                    <th scope="col" class="disableSort">ID</th>
                                    <th scope="col" class="disableSort">Date of submit</th>
                                    <th scope="col" class="disableSort">Amount, ₴</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#if payments?has_content>
                                    <#list payments as payment>
                                        <tr>
                                            <td>${payment.id?c}</td>
                                            <td>${payment.getFormattedDateOfSubmit()}</td>
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

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings_with_pagination.js"></script>
    </body>
</html>