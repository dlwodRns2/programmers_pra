$(document).ready(() => {
    setupAjax();
    checkAdmin();
});

// 관리자 권한 확인 - 성공하면 내용을 보여주고, 실패(403)하면 쫓아낸다
let checkAdmin = () => {
    $.ajax({
        type: 'GET',
        url: '/api/members/admin',
        dataType: 'json',
        success: (response) => {
            $('#admin-message').text(response.message);
            $('#admin-content').show();
        },
        error: () => {
            alert('관리자만 접근할 수 있습니다.');
            window.location.href = '/hello';
        }
    });
}
