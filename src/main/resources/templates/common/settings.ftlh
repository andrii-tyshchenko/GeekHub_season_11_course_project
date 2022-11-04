<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | Settings</title>
    </head>

    <body>
        <#include "header.ftlh">

        <main>
            <div class="container d-flex flex-nowrap">
                <#include "sidebar.ftlh">

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

                    <#if successful_change_message??>
                        <div class="alert alert-success" role="alert">
                            ${successful_change_message}
                        </div>
                    </#if>

                    <div class="ms-auto me-auto settings">
                        <div class="d-flex">
                            <div class="col-6">
                                <h2 class="pb-2 fs-5">Account settings:</h2>
                            </div>
                            <#if isAdmin>
                                <div class="col-6">
                                    <h3 class="pb-2 fs-5 text-end"><a href="/admin/settings/customers" class="text-decoration-none text-muted">For customers settings press here</a></h3>
                                </div>
                            </#if>
                        </div>

                        <#if user_info??>
                            <#assign user = user_info>
                        </#if>

                        <form action="/${role}/settings" method="POST">
                            <div class="d-flex mb-3">
                                <label class="col-lg-4 col-form-label fw-bold text-end pe-3" for="email">Email:</label>
                                <div class="col-lg-7">
                                    <input class="form-control" type="email" name="email" id="email" value="${user.email!""}" required />
                                </div>
                            </div>

                            <div class="d-flex mb-3">
                                <label class="col-lg-4 col-form-label fw-bold text-end pe-3" for="lastName">Last name:</label>
                                <div class="col-lg-7">
                                    <input class="form-control" type="text" name="lastName" id="lastName" value="${user.lastName!""}" />
                                </div>
                            </div>

                            <div class="d-flex mb-3">
                                <label class="col-lg-4 col-form-label fw-bold text-end pe-3" for="firstName">First name:</label>
                                <div class="col-lg-7">
                                    <input class="form-control" type="text" name="firstName" id="firstName" value="${user.firstName!""}" />
                                </div>
                            </div>

                            <div class="d-flex mb-3">
                                <label class="col-lg-4 col-form-label fw-bold text-end pe-3" for="patronymic">Patronymic:</label>
                                <div class="col-lg-7">
                                    <input class="form-control" type="text" name="patronymic" id="patronymic" value="${user.patronymic!""}">
                                </div>
                            </div>

                            <div class="d-flex">
                                <label class="col-lg-4 col-form-label text-end pe-3"></label>
                                <div class="col-lg-7 text-center">
                                    <input type="submit" class="btn btn-primary ms-1 me-1" value="Save changes" />
                                    <input type="reset" class="btn btn-outline-secondary ms-1 me-1" value="Reset changes" />
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                </div>
                            </div>
                        </form>

                        <br>

                        <h3 class="pb-2 fs-5">Change password:</h3>

                        <form action="/${role}/password" method="POST">
                            <div class="d-flex mb-3">
                                <label class="col-lg-4 col-form-label fw-bold text-end pe-3" for="password">New password:</label>
                                <div class="col-lg-7">
                                    <input class="form-control" type="password" name="password" id="password" required />
                                </div>
                            </div>

                            <div class="d-flex mb-3">
                                <label class="col-lg-4 col-form-label fw-bold text-end pe-3" for="confirm_password">Confirm new password:</label>
                                <div class="col-lg-7">
                                    <input class="form-control" type="password" name="confirm_password" id="confirm_password" required />
                                </div>
                            </div>

                            <div class="d-flex">
                                <label class="col-lg-4 col-form-label text-end pe-3"></label>
                                <div class="col-lg-7 text-center">
                                    <input type="submit" class="btn btn-primary ms-1 me-1" value="Save changes" />
                                    <input type="reset" class="btn btn-outline-secondary ms-1 me-1" value="Reset changes" />
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>

        <#include "footer.ftlh">

        <script src="/js/confirm-password.js"></script>
    </body>
</html>