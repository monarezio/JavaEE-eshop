import * as SimpleMDE from 'simplemde';

document.addEventListener('DOMContentLoaded', function (event) {
    const textArea = document.querySelector('[name*=\'description\']');

    console.log(textArea);

    if (!!textArea) {
        const simpleMDE = new SimpleMDE({element: textArea});
    }
});
