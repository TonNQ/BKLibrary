<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page import="model.bean.Faculty" %>
<%@ page import="model.bean.ClassEntity" %>

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
      <div class="text-2xl text-blue-600 uppercase font-bold">Cập nhật lớp</div>
    </div>
    <%
        ClassEntity classEntity = (ClassEntity) request.getAttribute("classEntity");
    %>
    <form method="post" name="classEdit" action="class?action=update&id=<%= classEntity.getId() %>">
      <div class="w-[100%] flex flex-col my-6">
        <label for="id" class="text-base font-medium mb-1">Mã lớp: </label>
        <input type="text" name="id" id="id" value="<%= classEntity.getId() %>" readonly class="rounded-md px-3 py-2 mb-1 input-required">
        <span class="text-red-500 font-medium text-xs hidden">Vui lòng nhập trường này</span>
    </div>
      <div class="w-[100%] flex flex-col my-6">
        <label for="name" class="text-base font-medium mb-1">Tên lớp: </label>
        <input type="text" name="name" id="name"  value="<%= classEntity.getName() %>" class="rounded-md px-3 py-2 mb-1 input-required">
        <span class="text-red-500 font-medium text-xs hidden">Vui lòng nhập trường này</span>
      </div>
      <div class="w-[100%] flex flex-col my-6">
        <label for="facultyId" class="text-base font-medium mb-1">Khoa: </label>
        <select id="facultyId" name="facultyId" class="w-full rounded-md px-3 py-2 mb-1">
            <%
                ArrayList<Faculty> facultyCBB = (ArrayList<Faculty>) request.getAttribute("facultyCBB");
                for (int i = 0; i < facultyCBB.size(); i++)
                {
            %>
            <option value="<%= facultyCBB.get(i).getId() %>" <% if(facultyCBB.get(i).getId()==classEntity.getFacultyId()){ %>selected<% } %>>
                <%= facultyCBB.get(i).getName() %>
            </option>
            <%
                }
            %>
        </select>
      </div>
      <div class="flex flex-row justify-center mt-10 mb-6">
        <input type="button" onClick="validateForm()" value="Cập nhật lớp" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
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
            document.classEdit.submit();
        }
    }
</script>
</body>
</html>