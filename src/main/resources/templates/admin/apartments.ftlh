<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | Apartments</title>
    </head>

    <body>
        <#include "../common/header.ftlh">

        <main>
            <div class="container d-flex flex-nowrap">
                <#include "../common/sidebar.ftlh">

                <div class="w-100">
                    <#if errors?has_content>
                        <div class="alert alert-danger" role="alert">
                            <ul>
                                <#list errors as error>
                                    <li><b>${error.getField()}:</b> ${error.getDefaultMessage()}</li>
                                </#list>
                            </ul>
                        </div>
                    </#if>

                    <#if successful_update_message??>
                        <div class="alert alert-success" role="alert">
                            ${successful_update_message}
                        </div>
                    </#if>

                    <h3 class="fs-5 mb-0 py-4 text-center">Apartments:</h3>

                    <#if apartments??>
                        <table class="table table-bordered table-striped tablemanager w-75 m-auto text-center">
                            <thead>
                                <tr>
                                    <th scope="col" class="disableSort">id</th>
                                    <th scope="col" class="disableSort">Section</th>
                                    <th scope="col" class="disableSort">Floor</th>
                                    <th scope="col" class="disableSort">Apartment</th>
                                    <th scope="col" class="disableSort disableFilterBy">Square</th>
                                    <th scope="col" class="disableSort">Acc. number</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list apartments as apartment>
                                    <form action="/admin/apartments" method="POST">
                                        <tr>
                                            <td class="align-middle">
                                                ${apartment.id}
                                                <input type="hidden" name="id" value="${apartment.id}">
                                            </td>
                                            <td class="align-middle">${apartment.section}</td>
                                            <td class="align-middle">${apartment.floor}</td>
                                            <td class="align-middle">${apartment.apartmentNumber}</td>
                                            <td class="align-middle">
                                                <input type="number" name="square" id="sq-${apartment.id}" value="${apartment.square?c}" step="0.1" disabled required>
                                                <button type="button" class="btn btn-primary btn-sm align-top" id="chng-btn-${apartment.id}">
                                                    <i class="bi bi-gear"></i>
                                                </button>
                                                <button type="submit" class="btn btn-primary btn-sm align-top" id="sbmt-btn-${apartment.id}" disabled>
                                                    <i class="bi bi-check-circle"></i>
                                                </button>
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            </td>
                                            <td class="align-middle">${apartment.accountNumber?c}</td>
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

        <script src="/js/enable-submit-apartments.js"></script>

        <script src="/js/tableManager.js"></script>
        <script src="/js/tableManager_settings_with_pagination.js"></script>
    </body>
</html>