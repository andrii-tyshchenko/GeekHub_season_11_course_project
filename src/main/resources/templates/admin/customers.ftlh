<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" type="text/css" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../css/custom_styles.css">
        <title>OSBB HD4 | Customers</title>
    </head>

    <body>
        <#include "../common/header.ftlh">

        <main>
            <div class="container d-flex flex-nowrap">
                <#include "../common/sidebar.ftlh">

                <div class="w-100">
                    <h2 class="fs-5 mb-0 py-4 text-center">Customers and admins:</h2>

                    <#if customers??>
                        <table class="table table-bordered table-striped tablemanager m-auto text-center" id="customers_table">
                            <thead>
                                <tr>
                                    <th scope="col" class="disableSort">id</th>
                                    <th scope="col" class="disableSort">Email</th>
                                    <th scope="col" class="disableSort">Authority</th>
                                    <th scope="col" class="disableSort">Enabled</th>
                                    <th scope="col" class="disableSort">Apart. id</th>
                                    <th scope="col" class="disableSort">Last name</th>
                                    <th scope="col" class="disableSort">First name</th>
                                    <th scope="col" class="disableSort">Patronymic</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list customers as customer>
                                    <tr>
                                        <td class="align-middle">${customer.getId()}</td>
                                        <td class="align-middle">
                                            <a href="mailto:${customer.getEmail()}" class="text-reset text-decoration-none">${customer.getEmail()}</a>
                                        </td>
                                        <td class="align-middle">${customer.getAuthority()}</td>
                                        <td class="align-middle">${customer.isEnabled()?c}</td>
                                        <td class="align-middle">${customer.apartmentId!}</td>
                                        <td class="align-middle">${customer.lastName!}</td>
                                        <td class="align-middle">${customer.firstName!}</td>
                                        <td class="align-middle">${customer.patronymic!}</td>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                    </#if>
                </div>
            </div>
        </main>

        <#include "../common/footer.ftlh">

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings_with_pagination.js"></script>
    </body>
</html>