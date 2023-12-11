<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<div class="w-[100vw] h-[100vh] relative flex items-center justify-center" style="background-image: url(img/dut_bg.png);">
  <div class="bg-gray-200 bg-opacity-80 w-[550px] rounded-lg py-12 px-12 z-50">
    <form method="post" name="loginForm" onsubmit="handleSubmit(event)">
      <div class="text-4xl uppercase text-center w-full text-blue-500 font-bold">Đăng nhập</div>
      <div class="text-md text-red-500 text-center w-[100%] font-medium mt-2 min-h-[2rem] loginError"></div>
      <div class="w-[100%] flex flex-col my-3">
        <label for="username" class="text-lg font-medium mb-1">Tên tài khoản: </label>
        <input type="text" name="username" id="username" placeholder="Nhập tên tài khoản" class="text-lg rounded-md px-4 py-2 mb-1" onchange="handleInputChange('username')">
        <span class="text-red-500 font-medium text-sm min-h-[1.25rem]" id="usernameError"></span>
      </div>
      <div class="w-[100%] flex flex-col my-3">
        <label for="password" class="text-lg font-medium mb-1">Mật khẩu: </label>
        <input type="password" name="password" id="password" placeholder="Nhập mật khẩu" class="text-lg rounded-md px-4 py-2 mb-1">
        <span class="text-red-500 font-medium text-sm min-h-[3rem]" id="passwordError"></span>
      </div>
      <div class="flex flex-row w-[100%] mt-3">
        <input type="submit" value="Đăng nhập" class="text-center text-lg mr-2 py-2 px-4 w-[100%] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
      </div>
    </form>
  </div>
  <div class="absolute inset-0 backdrop-blur-xs backdrop-filter z-10"></div>
</div>
</body>
<script>
  function handleSubmit(event) {
    event.preventDefault();
    let isValidForm;
    let username = document.forms["loginForm"]["username"].value;
    let password = document.forms["loginForm"]["password"].value;
    if (username && password) {
      isValidForm = true;
    }
    else {
      if (!username) {
        document.getElementById('usernameError').innerHTML = 'Vui lòng nhập trường này';
      }
      if (!password) {
        document.getElementById('passwordError').innerHTML = 'Vui lòng nhập trường này';
      }
      isValidForm = false;
    }
    if (isValidForm) {
      login(username, password, function(response) {
        if (response === "false") {
          document.querySelector(".loginError").innerHTML = "Tài khoản hoặc mật khẩu không đúng";
        } else {
          document.querySelector(".loginError").innerHTML = "";
          window.location.reload();
        }
      });
    }
  }
  function handleInputChange(fieldName) {
    let input = document.getElementById(fieldName).value;
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
    let regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,}$/;
    return regex.test(password);
  }
  function login(username, password, callback) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'auth?action=login&username=' + username + '&password=' + password, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function () {
      if (xhr.readyState == 4) {
        if (xhr.status == 200) {
          callback(xhr.responseText);
        } else {
          console.error("Error: " + xhr.status);
        }
      }
    };
    xhr.send();
  }
</script>
</html>