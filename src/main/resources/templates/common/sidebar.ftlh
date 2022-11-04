<div class="d-flex flex-column flex-shrink-0 p-3 bg-light sidebar">
    <h2 class="fs-4 mb-0 text-center">Personal cabinet</h2>

    <hr>

    <div class="text-center text-muted">
        <#if user_sidebar_info??>
            <#assign sidebar_info = user_sidebar_info>
        </#if>

        <#assign authorities = Session.SPRING_SECURITY_CONTEXT.authentication.authorities>
        <#assign isAdmin = authorities?seq_contains("ROLE_ADMIN")>

        <p class="fw-bold mb-0">
            Account number:
        </p>
        <p>
            <#if isAdmin>
                ADMIN
            <#else>
                <#if sidebar_info.accountNumber??>
                    ${sidebar_info.accountNumber?c}
                <#else>
                    n/a
                </#if>
            </#if>
        </p>
        <p class="fw-bold mb-0">
            Name:
        </p>
        <p>
            ${sidebar_info.lastName!"n/a"}
            ${sidebar_info.firstName!""}
            ${sidebar_info.patronymic!""}
        </p>
        <p class="fw-bold mb-0">
            Address:
        </p>
        <p>
            <#if isAdmin>
                n/a
            <#else>
                ${sidebar_info.street!"n/a"} str.,
                building ${sidebar_info.building!"n/a"},
                section ${sidebar_info.section!"n/a"},
                apartment ${sidebar_info.apartmentNumber!"n/a"}
            </#if>
        </p>
    </div>

    <hr>

    <#assign role = "user">
    <#if isAdmin>
        <#assign role = "admin">
    <#else>

    </#if>
    <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
            <a href="/${role}/accruals_history" class="nav-link">
                <i class="bi bi-card-list"></i> Accruals history
            </a>
        </li>
        <li>
            <a href="/${role}/payment_history" class="nav-link">
                <i class="bi bi-cash-coin"></i> Payment history
            </a>
        </li>

        <br>

        <li>
            <a href="/${role}/settings" class="nav-link">
                <i class="bi bi-gear"></i> Account settings
            </a>
        </li>

        <br>

        <li>
            <form action="/exit" method="POST">
                <button type="submit" class="nav-link">
                    <i class="bi bi-power"></i> SIGN OUT
                </button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </li>
    </ul>
</div>