<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | Sign In</title>
    </head>

    <body>
        <#include "header.ftlh">

        <main>
            <div class="container px-4">
                <div class="d-flex justify-content-center">
                    <#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
                        <div class="alert alert-danger w-75" role="alert">
                            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
                        </div>
                    </#if>

                    <#if successful_signup_message??>
                        <div class="alert alert-success w-75" role="alert">
                            <i class="bi bi-check-circle"></i> ${successful_signup_message}
                        </div>
                    </#if>

                    <#if successful_reset_message??>
                        <div class="alert alert-success w-75" role="alert">
                            <i class="bi bi-check-circle"></i> ${successful_reset_message}
                        </div>
                    </#if>
                </div>

                <div class="d-flex justify-content-center">
                    <form class="signin_form" method="POST">
                        <div class="my-4 text-center">
                            <h1>Please sign in</h1>
                        </div>
                        <div class="mb-4 form-floating">
                            <input type="email" id="email" class="form-control" name="username" placeholder="Email address" required />
                            <label class="form-label" for="email"><i class="bi bi-at"></i> Email address</label>
                        </div>

                        <div class="mb-4 form-floating">
                            <input type="password" id="password" class="form-control" name="password" placeholder="Password" required />
                            <label class="form-label" for="password"><i class="bi bi-lock"></i> Password</label>
                        </div>

                        <div class="row mb-4">
                            <div class="col d-flex justify-content-center">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkbox" name="remember-me" />
                                    <label class="form-check-label" for="checkbox"> Remember me </label>
                                </div>
                            </div>

                            <div class="col text-end">
                                <a href="/reset_password" class="text-decoration-none">Forgot password?</a>
                            </div>
                        </div>

                        <input type="submit" value="SIGN IN" class="btn btn-primary mb-4 w-100"/>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                        <div class="text-center">
                            <p>Not a member? <a href="/signup" class="text-decoration-none">Register</a></p>
                        </div>
                    </form>
                </div>
            </div>
        </main>

        <#include "footer.ftlh">
    </body>
</html>