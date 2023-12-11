<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page import="model.bean.Account" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<div class="h-[100vh] flex justify-center mt-[100px]">
  <div class="flex flex-col min-w-[50%]">
    <div class="flex flex-row justify-between items-center py-3 my-3 border-b-[2px] border-blue-400">
      <div class="text-2xl text-blue-600 uppercase font-bold">Thêm tài khoản</div>
    </div>
    <form method="post" name="accountInsert" action="account?action=insert">
        <div class="w-[100%] flex flex-col my-6">
            <label for="username" class="text-base font-medium mb-1">Tên tài khoản: </label>
            <input type="text" name="username" id="username" placeholder="Nhập tên tài khoản" class="rounded-md px-3 py-2 mb-1" onChange="handleInputChange('username')">
            <span class="text-red-500 font-medium text-xs" id="usernameError"></span>
        </div>
        <div class="w-[100%] flex flex-col my-6">
            <label for="password" class="text-base font-medium mb-1">Mật khẩu: </label>
            <input type="text" name="password" id="password" placeholder="Nhập mật khẩu" class="rounded-md px-3 py-2 mb-1" onChange="handleInputChange('password')">
            <span class="text-red-500 font-medium text-xs" id="passwordError"></span>
        </div>
        <div class="w-[100%] flex flex-col my-6">
            <label for="isAdmin" class="text-base font-medium mb-1">Phân quyền: </label>
            <select id="isAdmin" name="isAdmin" class="w-full rounded-md px-3 py-2 mb-1">
                <option value="false">Thủ thư</option>
                <option value="true">Quản trị viên</option>
            </select>
        </div>
        <div class="flex flex-row justify-center mt-10 mb-6">
            <input type="button" onClick="validateForm()" value="Thêm tài khoản" name="insert" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
            <a href="javascript:history.back()" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-stone-400 hover:bg-stone-500 hover:cursor-pointer rounded-md font-semibold">Quay lại</a>
        </div>
    </form>
  </div>
</div>
<script>
    function validateUsername() {
        const username = document.accountInsert.username.value;
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "account?mod=checkUsername&username=" + username, true);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                if (response) {
                    // ID chưa tồn tại, cho phép điền các trường khác
                    document.accountInsert.password.disabled = false;
                    document.accountInsert.isAdmin.disabled = false;
                    document.accountInsert.insert.disabled = false;
                    document.getElementById('usernameError').innerHTML = '';
                } else {
                    // ID đã tồn tại, vô hiệu hóa các trường khác
                    document.accountInsert.password.disabled = true;
                    document.accountInsert.isAdmin.disabled = true;
                    document.accountInsert.insert.disabled = true;
                    document.getElementById('usernameError').innerHTML = 'Tên tài khoản này đã tồn tại';
                }
            } else {
                // Xử lý lỗi khi gửi yêu cầu kiểm tra
                console.error("Error: " + xhr.status);
            }
            }
        }
        xhr.send();
    }
    function validateForm() {
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;
        console.log(username);
        console.log(password);
        if (username && password) {
            document.accountInsert.submit();
        }
        else {
            if (!username) {
                document.getElementById('usernameError').innerHTML = 'Vui lòng nhập trường này';
            }
            if (!password) {
                document.getElementById('passwordError').innerHTML = 'Vui lòng nhập trường này';
            }
        }
    }
    function handleInputChange(fieldName) {
        var input = document.getElementById(fieldName).value;
        console.log(input);
        if (input) {
            document.getElementById(fieldName + 'Error').innerHTML = '';
        }
        else {
            document.getElementById(fieldName + 'Error').innerHTML = 'Vui lòng nhập trường này';
        }
        if (fieldName === 'password' && input) {
            if (isValidPassword(input)) {
                document.getElementById(fieldName + 'Error').innerHTML = '';
            }
            else {
                document.getElementById(fieldName + 'Error').innerHTML = 'Mật khẩu cần có tối thiểu 8 ký tự, có ít nhất một ký tự thường, một ký tự in hoa, một ký tự số và một ký tự đặc biệt ';
            }
        }
        else if (fieldName === 'username' && input) {
            validateUsername();
        }
    }

    function isValidPassword(password) {
        var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,}$/;
        if (regex.test(password)) {
            return true;
        } else {
            return false;
        }
    }
</script>
</body>
</html>