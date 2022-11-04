<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | Sign Up</title>
    </head>

    <body>
        <#include "header.ftlh">

        <main>
            <div class="container px-4">
                <#if errors?has_content>
                    <div class="d-flex justify-content-center">
                        <div class="alert alert-danger w-75" role="alert">
                            <ul>
                                <#list errors as error>
                                    <li><b>${error.getField()}:</b> ${error.getDefaultMessage()}</li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </#if>

                <div class="d-flex justify-content-center">
                    <form class="signup-form" action="/signup" method="POST">
                        <div class="my-4 text-center">
                            <h1>Please sign up</h1>
                        </div>

                        <div class="mb-3 form-floating">
                            <input type="number" id="account_number" class="form-control" name="accountNumber" placeholder="Account number" max="999999999" required />
                            <label class="form-label" for="account_number"><i class="bi bi-card-heading"></i> Account number</label>
                        </div>
                        <button type="button" class="btn btn-primary mb-4 w-100" id="account_number_btn">
                            Check account number
                        </button>

                        <h3 class="pt-2 pb-2 fs-5 text-center">Required fields:</h3>

                        <div class="mb-3 form-floating">
                            <input type="text" id="email" class="form-control" name="email" placeholder="Email address" required disabled />
                            <label class="form-label" for="email"><i class="bi bi-at"></i> Email address</label>
                        </div>

                        <div class="mb-3 form-floating">
                            <input type="password" id="password" class="form-control" name="password" placeholder="Password" required disabled />
                            <label class="form-label" for="password"><i class="bi bi-lock-fill"></i> Password</label>
                        </div>

                        <div class="mb-3 form-floating">
                            <input type="password" id="confirm_password" class="form-control" name="confirm_password" placeholder="Confirm password" required disabled />
                            <label class="form-label" for="confirm_password"><i class="bi bi-lock"></i> Confirm password</label>
                        </div>

                        <h3 class="pt-2 pb-2 fs-5 text-center">Optional fields:</h3>

                        <div class="mb-3 form-floating">
                            <input type="text" id="lastName" class="form-control" name="lastName" placeholder="Last name" disabled />
                            <label class="form-label" for="lastName"><i class="bi bi-person-fill"></i> Last name</label>
                        </div>

                        <div class="mb-3 form-floating">
                            <input type="text" id="firstName" class="form-control" name="firstName" placeholder="First name" disabled />
                            <label class="form-label" for="firstName"><i class="bi bi-person"></i> First name</label>
                        </div>

                        <div class="mb-3 form-floating">
                            <input type="text" id="patronymic" class="form-control" name="patronymic" placeholder="First name" disabled />
                            <label class="form-label" for="patronymic"><i class="bi bi-person-circle"></i> Patronymic</label>
                        </div>

                        <input type="submit" id="submit" value="SIGN UP" class="btn btn-primary mb-4 w-100" disabled />

                        <input type="hidden" id="apartment_id" name="apartmentId" value="" />
                        <input type="hidden" name="authority" value="ROLE_USER" />
                        <input type="hidden" name="isEnabled" value="true" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                        <div class="text-center">
                            <p>Already a member? <a href="/signin" class="text-decoration-none">Login</a></p>
                        </div>
                    </form>
                </div>
            </div>
        </main>

        <#include "footer.ftlh">

        <script src="/js/check-account-number.js"></script>
        <script src="/js/confirm-password.js"></script>
    </body>
</html>