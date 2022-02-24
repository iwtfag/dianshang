$(function () {
    $('#login').click(function (event) {
        event.preventDefault();
        let userName = $('#username').val();
        let password = $('#password').val();
        $.get('login', {
            name: userName,
            password: password
        }, data => {
            alert('Login Message: ' + data);
            window.history.back();
        });
    });
});