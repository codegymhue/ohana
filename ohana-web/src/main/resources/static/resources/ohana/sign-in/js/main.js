(function ($) {
    "use strict";
    $('.validate-input .input100').each(function () {
        $(this).on('blur', function () {
            if (validate(this) == false) {
                showValidate(this);
            } else {
                $(this).parent().addClass('true-validate');
            }
        })
    })

    var input = $('.validate-input .input100');

    $('.validate-form').on('submit', function () {
        var check = true;
        $("#phoneOrEmail").removeClass('error');
        let regexEmail = /\w+\w*@\w+(\.\w+)$/;
        let regexPhone = /(84|0[1-9])+([0-9]{8})\b/;
        let regexPassword = /(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-`!~({})|.,*_@#$%^&+=/])(?=\S+$).{6,}$/;
        let phoneOrEmail = $("#phoneOrEmail").val();
        if (!regexEmail.test(phoneOrEmail) && !regexPhone.test(phoneOrEmail)) {
            // alert($("#phoneOrEmail").val());
            $("#checkPhoneOrEmail").html("");
            let str = `<label id="title-error" class="error" for="title">Vui lòng nhập đúng định dạng Email</label>`;
            $("#checkPhoneOrEmail").append(str);
            check = false;
        }
        console.log("check")

        for (var i = 0; i < input.length; i++) {
            if (validate(input[i]) == false) {
                showValidate(input[i]);
                check = false;
            }
        }
        let aapassword = $("#aapassword").val();
        console.log("aapassword: " + aapassword)
        if (!regexPassword.test(aapassword)) {
            if ($("#phoneOrEmail").val() != null) {

            }
            $("#checkPassword").html("");
            let str = `<label id="title-error" class="error" for="title">Vui lòng nhập mật khẩu mạnh hơn</label>`;
            $("#checkPassword").append(str);
            check = false;
        }
        let confirmPassword = $("#confirmPassword").val();
        console.log("confirmPassword:" + confirmPassword)
        if (aapassword != confirmPassword) {
            check = false;
            $("#checkConfirmPassword").html("");
            let str = `<label id="title-error" class="error" for="title">Vui lòng nhập lại đúng mật khẩu</label>`;
            $("#checkConfirmPassword").append(str);
            $("#confirmPassword").val("");
            console.log("check1")

        }
        if (!$('#termsCheckbox').is(':checked')) {
            check = false
            $("#checkD").html("");
            let str = `<label id="title-error" class="error" for="title">Bạn cần đồng ý điều khoản và chính sách bên chúng tôi</label>`;
            $("#checkD").append(str);
            // alert("Bạn cần đồng ý điều khoản và chính sách bên chúng tôi")
        }

        return check;
    });

    // $('.validate-form .input100').each(function () {
    //     $(this).focus(function () {
    //         hideValidate(this);
    //         $(this).parent().removeClass('true-validate');
    //     });
    // });

    function validate(input) {
        if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        } else {
            if ($(input).val().trim() == '') {
                return false;
            }
        }
    }

    // function showValidate(input) {
    //     var thisAlert = $(input).parent();
    //
    //     $(thisAlert).addClass('alert-validate');
    //
    //     $(thisAlert).append('<span class="btn-hide-validate">&#xf135;</span>')
    //     $('.btn-hide-validate').each(function () {
    //         $(this).on('click', function () {
    //             hideValidate(this);
    //         });
    //     });
    // }
    //
    // function hideValidate(input) {
    //     var thisAlert = $(input).parent();
    //     $(thisAlert).removeClass('alert-validate');
    //     $(thisAlert).find('.btn-hide-validate').remove();
    // }
    $('.validate-formChangePass').on('submit', function () {
        var check = true;
        let regexPassword = /(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-`!~({})|.,*_@#$%^&+=/])(?=\S+$).{6,}$/;
        console.log("check")

        let aapassword = $("#aapassword").val();
        console.log("aapassword: " + aapassword)
        if (!regexPassword.test(aapassword)) {
            $("#checkPassword").html("");
            let str = `<label id="title-error" class="error" for="title">Vui lòng nhập mật khẩu mạnh hơn</label>`;
            $("#checkPassword").append(str);
            check = false;
        }

        let confirmPassword = $("#confirmPassword").val();
        console.log("confirmPassword:" + confirmPassword)

        if (aapassword != confirmPassword) {
            check = false;
            $("#checkConfirmPassword").html("");
            let str = `<label id="title-error" class="error" for="title">Vui lòng nhập lại đúng mật khẩu</label>`;
            $("#checkConfirmPassword").append(str);
            $("#confirmPassword").val("");
            console.log("check1")

        }
        return check;
    });

    //
    // $('.validate-form').on('submit', function () {
    //
    // }


})(jQuery);