$(function() {

    const $loginForm = $("#loginForm");

    if($loginForm.length) {

        $loginForm.validate({

            rules: {
                email: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                email: {
                    required: 'Lütfen mail adresinizi giriniz'
                },
                password: {
                    required: 'Lütfen şifrenizi giriniz'
                }
            },
            errorElement: 'em',
            errorPlacement: function(error, element) {
                error.addClass('help-block');
                error.insertAfter(element);
            }

        })
    }

})