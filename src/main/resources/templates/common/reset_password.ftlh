<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | Reset password</title>
    </head>

    <body>
        <#include "header.ftlh">

        <main>
            <div class="container px-4">
                <div class="d-flex justify-content-center">
                    <#if error??>
                        <div class="alert alert-danger w-75" role="alert">
                            ${error}
                        </div>
                    </#if>
                </div>

                <div class="d-flex justify-content-center">
                    <form class="reset_password_form" action="/reset_password" method="POST">
                        <div class="my-4 text-center">
                            <p><i class="bi bi-question-circle fs-1"></i></p>
                            <h1>Forgot password?</h1>
                            <p class="text-muted">You can reset your password here.</p>
                            <p class="text-muted">We'll send you new one.</p>
                        </div>
                        <div class="mb-4 form-floating">
                            <input type="email" id="email" class="form-control" name="email" placeholder="Enter your email address" required />
                            <label class="form-label" for="email"><i class="bi bi-at"></i> Enter your email address</label>
                        </div>

                        <input type="submit" value="Reset password" class="btn btn-primary mb-4 w-100"/>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </div>
            </div>
        </main>

        <#include "footer.ftlh">
    </body>
</html>