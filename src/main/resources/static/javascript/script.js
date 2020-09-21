const backLink = document.querySelector(".govuk-back-link");

backLink.onclick = function(e) {
    history.back();
};