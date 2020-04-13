import * as SimpleMDE from 'simplemde';

document.addEventListener('DOMContentLoaded', function (event) {
    const textArea = document.querySelector('[name*=\'description\']');

    if (!!textArea) {
        const simpleMDE = new SimpleMDE({element: textArea});
    }
});
