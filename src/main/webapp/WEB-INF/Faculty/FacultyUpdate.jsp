<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page import="model.bean.Faculty" %>

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
      <div class="text-2xl text-blue-600 uppercase font-bold">Cập nhật khoa</div>
    </div>
    <%
        Faculty faculty = (Faculty) request.getAttribute("faculty");
    %>
    <form method="post" name="facultyEdit" action="faculty?action=update&id=<%= faculty.getId() %>">
      <div class="w-[100%] flex flex-col my-6">
        <label for="id" class="text-base font-medium mb-1">Mã khoa: </label>
        <input type="text" name="id" id="id" value="<%= faculty.getId() %>" readonly class="rounded-md px-3 py-2 mb-1 input-required">
        <span class="text-red-500 font-medium text-xs hidden">Vui lòng nhập trường này</span>
    </div>
      <div class="w-[100%] flex flex-col my-6">
        <label for="name" class="text-base font-medium mb-1">Tên khoa: </label>
        <input type="text" name="name" id="name"  value="<%= faculty.getName() %>" class="rounded-md px-3 py-2 mb-1 input-required">
        <span class="text-red-500 font-medium text-xs hidden">Vui lòng nhập trường này</span>
      </div>
      <div class="flex flex-row justify-center mt-10 mb-6">
        <input type="button" onClick="validateForm()" value="Cập nhật khoa" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
        <a href="javascript:history.back()" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-stone-400 hover:bg-stone-500 hover:cursor-pointer rounded-md font-semibold">Quay lại</a>
      </div>
    </form>
  </div>
</div>
<script>
    function validateForm() {
        const listInput = document.querySelectorAll('.input-required');
        var check = true;
        listInput.forEach(input => {
            if (input.value == "") {
                check = false;
                if (input.nextElementSibling.classList.contains("hidden")) {
                    input.nextElementSibling.classList.remove("hidden");
                }
            } else if (!input.nextElementSibling.classList.contains("hidden")) {
                input.nextElementSibling.classList.add("hidden");
            }
        });
        if (check) {
            document.facultyEdit.submit();
        }
    }
</script>
</body>
</html>