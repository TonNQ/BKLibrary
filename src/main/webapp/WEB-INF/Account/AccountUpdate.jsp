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
      <div class="text-2xl text-blue-600 uppercase font-bold">Cập nhật tài khoản</div>
    </div>
    <%
        Account account = (Account) request.getAttribute("account");
    %>
    <form method="post" name="accountUpdate" action="account?action=update&id=<%= account.getId() %>">
        <div class="w-[100%] flex flex-col my-6">
            <label for="username" class="text-base font-medium mb-1">Tên tài khoản: </label>
            <input type="text" name="username" value="<%= account.getUsername() %>" readonly  id="username" placeholder="Nhập tên tài khoản" class="rounded-md px-3 py-2 mb-1">
            <span class="text-red-500 font-medium text-xs" id="errorUsername"></span>
        </div>
        <div class="w-[100%] flex flex-col my-6">
            <label for="password" class="text-base font-medium mb-1">Mật khẩu: </label>
            <input type="text" name="password" id="password" value="<%= account.getPassword() %>" placeholder="Nhập mật khẩu" class="rounded-md px-3 py-2 mb-1" onChange="handleInputChange('username')">
            <span class="text-red-500 font-medium text-xs" id="passwordError"></span>
        </div>
        <div class="w-[100%] flex flex-col my-6">
            <label for="isAdmin" class="text-base font-medium mb-1">Phân quyền: </label>
            <select id="isAdmin" name="isAdmin" class="w-full rounded-md px-3 py-2 mb-1">
                <option value="false" <% if(!account.isAdmin()){ %>selected<% } %>>Thủ thư</option>
                <option value="true" <% if(account.isAdmin()){ %>selected<% } %>>Quản trị viên</option>
            </select>
        </div>
        <div class="flex flex-row justify-center mt-10 mb-6">
            <input type="button" onClick="validateForm()" value="Cập nhật tài khoản" name="update" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
            <a href="javascript:history.back()" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-stone-400 hover:bg-stone-500 hover:cursor-pointer rounded-md font-semibold">Quay lại</a>
        </div>
    </form>
  </div>
</div>
<script>
    function validateForm() {
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;
        if (username && password) {
            document.accountUpdate.submit();
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