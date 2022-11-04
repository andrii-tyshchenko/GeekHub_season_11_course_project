<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../../images/favicon.svg">
        <link rel="stylesheet" type="text/css" href="../../css/bootstrap-icons.css">
        <link rel="stylesheet" type="text/css" href="../../css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../../css/custom_styles.css">
        <title>OSBB HD4 | Customers settings</title>
    </head>

    <body>
        <#include "../common/header.ftlh">

        <main>
            <div class="container d-flex flex-nowrap">
                <#include "../common/sidebar.ftlh">

                <div class="w-100">
                    <h3 class="fs-5 mb-0 py-4 text-center">Customers and admins settings:</h3>

                    <#if errors?has_content>
                        <div class="alert alert-danger" role="alert">
                            <ul>
                                <#list errors as error>
                                    <li><b>${error.getField()}</b>: ${error.getDefaultMessage()}</li>
                                </#list>
                            </ul>
                        </div>
                    </#if>

                    <#if successful_update_message??>
                        <div class="alert alert-success" role="alert">
                            ${successful_update_message}
                        </div>
                    </#if>

                    <#if customers??>
                        <table class="table table-bordered table-striped tablemanager m-auto text-center" id="customers_table">
                            <thead>
                            <tr>
                                <th scope="col" class="disableSort">id</th>
                                <th scope="col" class="disableSort disableFilterBy">Email</th>
                                <th scope="col" class="disableSort disableFilterBy">Authority</th>
                                <th scope="col" class="disableSort disableFilterBy">Enabled</th>
                                <th scope="col" class="disableSort disableFilterBy">Apart. id</th>
                                <th scope="col" class="disableSort disableFilterBy">Last name</th>
                                <th scope="col" class="disableSort disableFilterBy">First name</th>
                                <th scope="col" class="disableSort disableFilterBy">Patronymic</th>
                                <th scope="col" class="disableSort disableFilterBy"></th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list customers as customer>
                                    <form action="/admin/settings/customers" method="POST">
                                        <tr>
                                            <td class="align-middle">
                                                ${customer.getId()}
                                                <input type="hidden" name="id" value="${customer.id}">
                                            </td>
                                            <td class="align-middle">
                                                <input type="email" name="email" id="email-${customer.id}" value="${customer.getEmail()}" disabled />
                                            </td>
                                            <td class="align-middle">
                                                <select name="authority" id="auth-${customer.id}" disabled>
                                                    <option value="${customer.getAuthority()}" selected>${customer.getAuthority()[5..]}</option>
                                                    <option value="ROLE_USER">USER</option>
                                                    <option value="ROLE_ADMIN">ADMIN</option>
                                                </select>
                                            </td>
                                            <td class="align-middle">
                                                <select name="isEnabled" id="enbld-${customer.id}" disabled>
                                                    <option value="${customer.isEnabled()?c}" selected>${customer.isEnabled()?c}</option>
                                                    <option value="true">true</option>
                                                    <option value="false">false</option>
                                                </select>
                                            </td>
                                            <td class="align-middle">
                                                <input type="number" name="apartmentId" <#if customer.getAuthority() == 'ROLE_USER'>id="apt-${customer.id}"<#else>id="apt-adm-${customer.id}"</#if> value="${customer.apartmentId!}" required disabled />
                                            </td>
                                            <td class="align-middle">
                                                <input type="text" name="lastName" id="lnm-${customer.id}" value="${customer.lastName!}" disabled />
                                            </td>
                                            <td class="align-middle">
                                                <input type="text" name="firstName" id="fnm-${customer.id}" value="${customer.firstName!}" disabled />
                                            </td>
                                            <td class="align-middle">
                                                <input type="text" name="patronymic" id="ptr-${customer.id}" value="${customer.patronymic!}" disabled />
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-primary btn-sm align-top" id="chng-btn-${customer.id}">
                                                    <i class="bi bi-gear"></i>
                                                </button>
                                                <button type="submit" class="btn btn-primary btn-sm align-top" id="sbmt-btn-${customer.id}" disabled>
                                                    <i class="bi bi-check-circle"></i>
                                                </button>
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            </td>
                                        </tr>
                                    </form>
                                </#list>
                            </tbody>
                        </table>
                    </#if>
                </div>
            </div>
        </main>

        <#include "../common/footer.ftlh">

        <script src="/js/enable-submit-customers.js"></script>

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings_with_pagination.js"></script>
    </body>
</html>