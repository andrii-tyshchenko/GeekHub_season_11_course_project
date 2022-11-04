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
                        <#if error??>
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </#if>

                        <#if successful_payment_message??>
                            <div class="alert alert-success" role="alert">
                                ${successful_payment_message}
                            </div>
                        </#if>

                        <table class="table table-borderless w-75 m-auto" style="width: 65%;">
                            <tr>
                                <td class="fw-bold border-end" style="width: 40%">Last accrual date:</td>
                                <td class="fw-bold text-end" style="width: 40%">Last accrued total amount:</td>
                                <td class="text-center" style="width: 20%">
                                    ${(last_accrued_total_amount?string('#,##0.00'))!"n/a"} ₴
                                </td>
                            </tr>
                            <tr>
                                <td class="border-end">
                                    <#if last_accrual_details?has_content>
                                        ${last_accrual_details[0].getFormattedDateOfSubmit()}
                                    <#else>
                                        n/a
                                    </#if>
                                </td>
                                <td class="fw-bold text-end">Debt (-) / overpayment (+):</td>
                                <td class="text-center color-marker-blue">
                                    <#if user_debt??>
                                        ${user_debt?string('#,##0.00')} ₴
                                    </#if>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>
                                    <button type="button" class="btn btn-primary px-4 me-md-2 w-100" data-bs-toggle="modal" data-bs-target="#modalWindow">
                                        Pay
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="modalWindow" tabindex="-1" aria-labelledby="modalWindowLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modalWindowLabel">Make payment</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>

                                <form action="/user/cabinet" method="POST">
                                    <div class="modal-body text-center">
                                        <label for="payment">Enter amount to pay: </label>
                                        <input type="number" name="payment" id="payment" step="0.01" required />
                                    </div>

                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">Make payment</button>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <hr>

                    <div class="w-100">
                        <h3 class="fs-5 mb-0 py-4 text-center">Last accrual details:</h3>

                        <table class="table table-bordered table-striped m-auto --text-center">
                            <thead>
                                <tr>
                                    <th scope="col">Date of submit</th>
                                    <th scope="col">Service name</th>
                                    <th scope="col">Tariff, ₴/sq.m</th>
                                    <th scope="col">Sum of accrual, ₴</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#if last_accrual_details?has_content>
                                    <#list last_accrual_details as accrual_info>
                                        <tr>
                                            <td>${accrual_info.getFormattedDateOfSubmit()}</td>
                                            <td>${accrual_info.serviceName}</td>
                                            <td>${accrual_info.tariff?string('#,##0.00')}</td>
                                            <td>${accrual_info.sumOfAccrual?string('#,##0.00')}</td>
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

        <script src="/js/bootstrap.bundle.min.js"></script>
    </body>
</html>