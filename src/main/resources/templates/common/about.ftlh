<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="../images/favicon.svg">
        <link rel="stylesheet" href="../css/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/custom_styles.css">
        <title>OSBB HD4 | About</title>
    </head>

    <body>
        <#include "header.ftlh">

        <main>
            <div class="container px-4">
                <h2 class="pb-2 text-center my-5 color-marker-blue">As OSBB management you can:</h2>
                <div class="row g-4 row-cols-1 row-cols-lg-3">
                    <div class="col d-flex align-items-start">
                        <div class="icon-square d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">
                            <p class="pb-2"><i class="bi bi-calculator color-marker-blue"></i></p>
                        </div>
                        <div>
                            <h3>Set a tariff</h3>
                            <p>Set a tariff for the maintenance of the building (₴/sq.m)</p>
                        </div>
                    </div>
                    <div class="col d-flex align-items-start">
                        <div class="icon-square d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">
                            <p class="pb-2"><i class="bi bi-cash-coin color-marker-blue"></i></p>
                        </div>
                        <div>
                            <h3>Accrue amount to be paid</h3>
                            <p>Make accruals for OSBB users</p>
                        </div>
                    </div>
                    <div class="col d-flex align-items-start">
                        <div class="icon-square d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">
                            <p class="pb-2"><i class="bi bi-receipt color-marker-blue"></i></p>
                        </div>
                        <div>
                            <h3>Get reports</h3>
                            <p>Get info about OSBB's balance, accruals and user payments</p>
                        </div>
                    </div>
                </div>

                <h2 class="pb-2 text-center my-5 color-marker-blue">As OSBB user you can:</h2>
                <div class="row g-4 row-cols-1 row-cols-lg-3">
                    <div class="col d-flex align-items-start">
                        <div class="icon-square d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">
                            <p class="pb-2"><i class="bi bi-info-square color-marker-blue"></i></p>
                        </div>
                        <div>
                            <h3>See your accruals</h3>
                            <p>Get info about current amount to be paid</p>
                        </div>
                    </div>
                    <div class="col d-flex align-items-start">
                        <div class="icon-square d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">
                            <p class="pb-2"><i class="bi bi-credit-card color-marker-blue"></i></p>
                        </div>
                        <div>
                            <h3>Pay for OSBB services</h3>
                            <p>Pay needed amount in your personal cabinet</p>
                        </div>
                    </div>
                    <div class="col d-flex align-items-start">
                        <div class="icon-square d-inline-flex align-items-center justify-content-center fs-4 flex-shrink-0 me-3">
                            <p class="pb-2"><i class="bi bi-receipt color-marker-blue"></i></p>
                        </div>
                        <div>
                            <h3>Get reports</h3>
                            <p>Get the history of your payments and accruals</p>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <#include "footer.ftlh">
    </body>
</html>