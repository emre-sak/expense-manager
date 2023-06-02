$(function(){

    $.validator.addMethod('customEmail', function(value, element){

        return this.optional(element) || /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value);
        
    });

    const $registerForm = $('#registerForm');

    if($registerForm.length) {

        $registerForm.validate({

            rules: {
                name: {
                    required: true,
                    minlength: 3
                },
                email: {
                    required: true,
                    customEmail: true
                },
                password: {
                    required: true,
                    minlength: 3,
                    maxlength: 15
                },
                confirmPassword: {
                    required: true,
                    equalTo: '#password'
                }
            },
            messages: {
                name: {
                    required: 'Lütfen isminizi giriniz',
                    minlength: 'İsminiz en az 3 karakter uzunluğunda olmalı',
                },
                email: {
                    required: 'Lütfen email adresinizi giriniz',
                    customEmail: 'Lütfen geçerli bir email adresi giriniz'
                },
                password: {
                    required: 'Lütfen şifrenizi giriniz',
                    minlength: 'Şifreniz en az 3 karakter uzunluğunda olmalı',
                    maxlength: 'Şifreniz en fazla 15 karakter uzunluğunda olmalı'
                },
                confirmPassword: {
                    required: 'Lütfen şifrenizi tekrar giriniz',
                    equalTo: 'Şifreler aynı olmalıdır'
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