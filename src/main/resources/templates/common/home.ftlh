<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4</title>
    </head>

    <body>
        <#include "header.ftlh">

        <main>
            <div class="container px-4 py-5">
                <div class="row flex-lg-row-reverse align-items-center g-5 py-5">
                    <div class="col-lg-6">
                        <h2 class="display-5 fw-bold lh-1 mb-3">Personal cabinet of OSBB Heroiv Dnipra 4 user and admin</h2>
                        <p class="lead">Your reliable assistant in OSBB management.<br>
                            Regardless of whether you are a resident of the house or the head of the condominium,
                            it will help you control receipts and expenditures of OSBB.</p>
                        <div class="d-grid gap-2 d-md-flex justify-content-center">
                            <a class="btn btn-primary btn-lg px-4 me-md-2 w-50" href="/signin">Sign In</a>
                            <a class="btn btn-outline-secondary btn-lg px-4 w-50" href="/signup">Sign Up</a>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <img src="images/condominium.png" class="d-block mx-lg-auto img-fluid" alt="building" width="700" loading="lazy">
                    </div>
                </div>
            </div>
        </main>

        <#include "footer.ftlh">
    </body>
</html>