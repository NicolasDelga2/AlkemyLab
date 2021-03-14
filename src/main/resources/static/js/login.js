const $adminButton = document.querySelector('#adminButton'),
    $studentButton = document.querySelector('#studentButton'),
    $loginForm = document.querySelector('.user');


$adminButton.addEventListener('click', e => {
    $loginForm.querySelector('#username').setAttribute('placeholder', 'Ingrese su Usuario');
    $loginForm.querySelector('#password').setAttribute('placeholder', 'Ingrese su Contraseña');
});
$studentButton.addEventListener('click', e => {
    $loginForm.querySelector('#username').setAttribute('placeholder', 'Ingrese su DNI');
    $loginForm.querySelector('#password').setAttribute('placeholder', 'Ingrese su Legajo');
});

