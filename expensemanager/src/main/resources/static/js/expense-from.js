let validNumber = new RegExp(/^(?!0)\d*\.?\d{0,2}$/)
let lastValid = $("#amount").val();

const validateNumber = function(element) {
    if(validNumber.test(element.value)) {
        lastValid = element.value;
    }else {
        element.value = lastValid;
    }
}

$(function() {
    $('#createdAt').datepicker({
        dateFormat: "dd/mm/yy",
        changeMonth: true,
        changeYear: true,
        maxDate: new Date()
    });


    const $expenseForm = $("#expenseform");

    if($expenseForm.length) {
        $expenseForm.validate({

            rules: {
                name: {
                    required: true,
                    minlength: 3
                },
                amount: {
                    required: true
                },
                dateString: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: 'Lütfen gider adını giriniz',
                    minlength: 'Gider adı en az 3 karakter olmalı'
                },
                amount: {
                    required: 'Lütfen miktarı giriniz'
                },
                dateString: {
                    required: 'Lütfen tarihi giriniz'
                }
            }

        })
    }

})