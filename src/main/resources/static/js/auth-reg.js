"use strict";

document.forms[0].addEventListener("submit", function(event) {
    let username = event.target.elements.username.value;
    let password = event.target.elements.password.value;
    let email = event.target.elements.email.value;

    console.log(username);
    console.log(password);
    console.log(email);
});