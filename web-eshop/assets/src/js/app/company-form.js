document.addEventListener("DOMContentLoaded", function (event) {
    const formContainer = document.getElementById('new-address');
    if (!!formContainer) {
        const selectInputContainer = document.getElementById('reuse-address');
        const addressRadiosContainer = document.getElementById('address-chooser');

        formContainer.classList.add('d-none');
        selectInputContainer.classList.add('d-none');

        const select = selectInputContainer.querySelector('select');

        if (select.options.length === 0) {
            formContainer.classList.remove('d-none');
            addressRadiosContainer.classList.add('d-none');
        } else {
            const radios = addressRadiosContainer.querySelectorAll('input[type=radio]');

            radios.forEach(radio => {
                radio.addEventListener('change', function () {
                    console.log('Change');
                    initForm(this, formContainer, selectInputContainer);
                });
            });

            const radio = radios[0];
            initForm(radio, formContainer, selectInputContainer);
        }
    }
});

function initForm(radio, formContainer, selectInputContainer) {
    console.log(radio);

    if (radio.value === 'NEW') {
        formContainer.classList.remove('d-none');
        selectInputContainer.classList.add('d-none');
    } else {
        selectInputContainer.classList.remove('d-none');
        formContainer.classList.add('d-none');

    }
}
