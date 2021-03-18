const d = document;
$adminButton = document.querySelector('#adminButton'),
    $studentButton = document.querySelector('#studentButton'),
    $loginForm = document.querySelector('.user'),
    $username = $loginForm.querySelector('.username'),
    $password = $loginForm.querySelector('.password');

let $inputs = d.querySelectorAll('.user [required]');

$adminButton.addEventListener('click', e => {
    $loginForm.classList.toggle('is-active');
    // admin attributes
    $username.setAttribute('placeholder', 'Ingrese su Usuario');
    $username.setAttribute('pattern', '^.{5,15}$'); //  5 to 15 chars
    $username.setAttribute('title', 'El nombre de usuario no es valido');

    $password.setAttribute('placeholder', 'Ingrese su Contraseña');
    $password.setAttribute('pattern', '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$');
    /*1 Uppercase,1 Lowercase, 1 Number,1 Symbol, symbol allowed --> !@#$%^&*_=+-
    Min 8 chars and Max 12 chars */
    $password.setAttribute('title', 'La contraseña ingresada no es valida.');
    $inputs = d.querySelectorAll('.user [required]');
    setAttributes($inputs);
});

$studentButton.addEventListener('click', e => {
    $loginForm.classList.toggle('is-active');
    // Student attributes
    $username.setAttribute('placeholder', 'Ingrese su DNI');
    $username.setAttribute('pattern', '^\\d{8}(?:[-\\s]\\d{4})?$');
    $username.setAttribute('title', 'El DNI ingresado no es valido');

    $password.setAttribute('pattern','^\\d+$') // Just numbers without 0
    $password.setAttribute('placeholder', 'Ingrese su Legajo');
    $password.setAttribute('title','El legajo no es valido');
    $inputs = d.querySelectorAll('.user [required]');
    setAttributes($inputs);
});

const setAttributes = ($inputs) => {
    $inputs.forEach(input => {
        const $span = d.createElement("span");
        $span.id = input.name;
        $span.textContent = input.title;
        $span.classList.add("user-error", "none");
        input.insertAdjacentElement("afterend", $span);
    });
};

d.addEventListener('keyup', e => {
    if (e.target.matches('.user [required]')) {
        let $input = e.target,
            pattern = $input.pattern || $input.dataset.pattern;

        if (pattern && $input.value !== "") {
            if (pattern) {
                let regex = new RegExp(pattern);
                if (!regex.exec($input.value)) {
                    d.getElementById($input.name).classList.add('is-active')
                    d.getElementById($input.name).classList.remove('none');
                } else {
                    d.getElementById($input.name).classList.add('none');
                    d.getElementById($input.name).classList.remove('is-active');
                }
            }
        }
        if (!pattern) {
            if ($input.value === "") {
                d.getElementById($input.name).classList.add('is-active');
                d.getElementById($input.name).classList.remove('none');
            } else {
                d.getElementById($input.name).classList.add('none');
                d.getElementById($input.name).classList.remove('is-active');
            }
        }
    }
});


