<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | Cabinet</title>
    </head>

    <body>
        <#include "../common/header.ftlh">

        <main>
            <div class="container d-flex flex-nowrap">
                <#include "../common/sidebar.ftlh">

                <div class="w-100 align-items-center">
                    <div class="w-100">
                        <table class="table table-borderless w-75 m-auto">
                            <tr>
                                <td class="fw-bold text-end">Total amount of apartments:</td>
                                <td class="border-end text-center">
                                    <#if apartments_amount??>
                                        <a href="/admin/apartments">${apartments_amount}</a>
                                    </#if>
                                </td>
                                <td class="fw-bold text-end">Last accrual date:</td>
                                <td class="text-center">
                                    <#if last_accrual_date??>
                                        ${last_accrual_date}
                                    </#if>
                                </td>
                            </tr>
                            <tr>
                                <td class="fw-bold text-end">Total amount of customers:</td>
                                <td class="border-end text-center">
                                    <#if customers_amount??>
                                        <a href="/admin/customers">${customers_amount}</a>
                                    </#if>
                                </td>
                                <td class="fw-bold text-end">Balance for today:</td>
                                <td class="text-center color-marker-blue">
                                    ${(balance?string('#,##0.00'))!'n/a'} ₴
                                </td>
                            </tr>
                        </table>
                    </div>

                    <hr>

                    <div class="w-100">
                        <form action="/admin/cabinet" method="POST">
                            <h3 class="fs-5 mb-0 py-4 text-center">Maybe it's time to make accrual?</h3>

                            <#if errors?has_content>
                                <div class="alert alert-danger" role="alert">
                                    <ul>
                                        <#list errors as error>
                                            <li><b>${error.getField()}</b>: ${error.getDefaultMessage()}</li>
                                        </#list>
                                    </ul>
                                </div>
                            </#if>

                            <#if successful_accrual_message??>
                                <div class="alert alert-success" role="alert">
                                    ${successful_accrual_message}
                                </div>
                            </#if>

                            <#if autoaccrual_status_update_message??>
                                <div class="alert alert-success" role="alert">
                                    ${autoaccrual_status_update_message}
                                </div>
                            </#if>

                            <table class="table m-auto">
                                <thead>
                                    <tr class="text-center">
                                        <th scope="col" class="w-75">Service name</th>
                                        <th scope="col" class="w-25">Tariff, ₴/sq.m</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <tr>
                                        <td><input type="text" class="form-control" name="serviceName" value="${default_service_name_value}" maxlength="64" required></td>
                                        <td><input type="number" class="form-control w-50 m-auto" name="tariff" value="<#if last_tariff_value??>${last_tariff_value?c}</#if>" step=".01" required></td>
                                    </tr>
                                </tbody>
                            </table>

                            <div class="d-flex justify-content-center">
                                <input type="submit" class="btn btn-primary mt-3 w-50 m-auto" id="submit" value="Make tariff-dependent accrual">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </div>
                        </form>

                        <#if current_autoaccrual_status?? && current_autoaccrual_status.enabled == true>
                            <form action="/admin/autoaccrual" method="POST">
                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-danger mt-3 w-50 m-auto">
                                        Disable monthly autoaccrual [now is ON]
                                    </button>
                                    <input type="hidden" name="isEnabled" value="false" />
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                </div>
                            </form>

                            <div class="alert alert-info mt-3" role="alert">
                                Monthly autoaccruals are now enable (every 1st of the month at 7:00 AM).
                                Current tariff: ${current_autoaccrual_status.tariff} ₴/sq.m.
                            </div>
                        <#else>
                            <div class="d-flex justify-content-center">
                                <button type="button" class="btn btn-success mt-3 w-50 m-auto" data-bs-toggle="modal" data-bs-target="#modalWindow">
                                    Enable monthly autoaccrual [now is OFF]
                                </button>

                                <!-- Modal -->
                                <div class="modal fade" id="modalWindow" tabindex="-1" aria-labelledby="modalWindowLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="modalWindowLabel">Enable monthly autoaccrual</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>

                                            <form action="/admin/autoaccrual" method="POST">
                                                <div class="modal-body text-center">
                                                    <table class="table m-auto">
                                                        <thead>
                                                        <tr class="text-center">
                                                            <th scope="col" class="w-75">Service name</th>
                                                            <th scope="col" class="w-25">Tariff, ₴/sq.m</th>
                                                        </tr>
                                                        </thead>

                                                        <tbody>
                                                        <tr>
                                                            <td><input type="text" class="form-control" name="serviceName" value="${default_service_name_value}" maxlength="64" required></td>
                                                            <td><input type="number" class="form-control w-50 m-auto" name="tariff" value="<#if last_tariff_value??>${last_tariff_value?c}</#if>" step=".01" required></td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>

                                                <div class="modal-footer">
                                                    <button type="submit" class="btn btn-primary">Enable</button>
                                                    <input type="hidden" name="isEnabled" value="true" />
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#if>
                    </div>

                    <hr>

                    <div class="w-100">
                        <h3 class="fs-5 mb-0 py-4 text-center">Top
                            <select name="quantity" id="quantity_select">
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="25">25</option>
                                <option value="50">50</option>
                                <option value="">All</option>
                            </select>
                             debtors:
                            <a href="/admin/cabinet/debtors/pdf?quantity=" class="btn btn-outline-success" id="pdf" download>Export to PDF</a>
                        </h3>
                        <table class="table table-bordered table-striped tablemanager m-auto text-center" id="debtors_table">
                            <thead>
                                <tr>
                                    <th scope="col" class="disableSort">Acc. number</th>
                                    <th scope="col" class="disableSort">Section</th>
                                    <th scope="col" class="disableSort">Apartment</th>
                                    <th scope="col" class="disableSort">Email</th>
                                    <th scope="col" class="disableSort">Name</th>
                                    <th scope="col" class="disableSort">Payments, ₴</th>
                                    <th scope="col" class="disableSort">Accruals, ₴</th>
                                    <th scope="col" class="disableSort">Debt, ₴</th>
                                </tr>
                            </thead>
                            <#include "../admin/debtors.ftlh">
                        </table>
                    </div>
                </div>
            </div>
        </main>

        <#include "../common/footer.ftlh">

        <script src="/js/bootstrap.bundle.min.js"></script>

        <script src="/js/get-debtors.js"></script>
        <script src="/js/get-debtors-pdf.js"></script>

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings.js"></script>
    </body>
</html>