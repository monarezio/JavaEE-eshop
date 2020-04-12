document.addEventListener("DOMContentLoaded", function (event) {
    render()
});

function render() {
    const isForCompany = document.querySelector('[id$=\'company-invoice\'');
    if (!!isForCompany) {
        if(!isForCompany.checked) {
            for (let element of document.getElementsByClassName('i-company')) {
                element.style.display = 'none';
            }
        } else {
            for (let element of document.getElementsByClassName('i-company')) {
                element.style.display = 'block';
            }
        }

        const companySelect = document.querySelector('.i-company select');

        if(companySelect.options.length <= 1)
            companySelect.style.display = 'none';

        const companyContainer = document.getElementById('company');
        if(!!companySelect.options[companySelect.selectedIndex].value) {
            companyContainer.style.display = 'none';
        } else {
            companyContainer.style.display = 'block';
        }

        const addressSelect = document.querySelector('#address-selector select');
        const addressContainer = document.getElementById('address');
        if(addressSelect.options.length <= 1)
            addressSelect.style.display = 'none';

        if(!!addressSelect.options[addressSelect.selectedIndex].value)
            addressContainer.style.display = 'none';
        else
            addressContainer.style.display = 'block';

        isForCompany.addEventListener('change', (e) => render());
        companySelect.addEventListener('change', (e) => render());
        addressSelect.addEventListener('change', (e) => render());
    }
}
