<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page import="model.bean.Student" %>
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
      <div class="text-2xl text-blue-600 uppercase font-bold">Cập nhật Sinh viên</div>
    </div>
    <%
        Student student = (Student) request.getAttribute("student");
    %>
    <form method="post" name="studentEdit" action="student?action=update&id=<%= student.getId() %>">
      <div class="w-[100%] flex flex-col my-6">
        <label for="id" class="text-base font-medium mb-1">Mã sinh viên: </label>
        <input type="text" name="id" id="id" value="<%= student.getId() %>" readonly class="rounded-md px-3 py-2 mb-1">
        <span class="text-red-500 font-medium text-xs hidden">Vui lòng nhập trường này</span>
    </div>
      <div class="w-[100%] flex flex-col my-6">
        <label for="name" class="text-base font-medium mb-1">Tên sinh viên: </label>
        <input type="text" name="name" id="name"  value="<%= student.getName() %>" class="rounded-md px-3 py-2 mb-1">
        <span class="text-red-500 font-medium text-xs hidden">Vui lòng nhập trường này</span>
      </div>
      <div class="w-[100%] flex flex-col my-6">
        <label for="classId" class="text-base font-medium mb-1">Lớp: </label>
        <select id="classId" name="classId" class="w-full rounded-md px-3 py-2 mb-1">
            <%
                ArrayList<ClassEntity> classCBB = (ArrayList<ClassEntity>) request.getAttribute("classCBB");
                for (int i = 0; i < classCBB.size(); i++)
                {
            %>
            <option value="<%= classCBB.get(i).getId() %>" <% if(classCBB.get(i).getId()==student.getClassId()){ %>selected<% } %>>
                <%= classCBB.get(i).getName() %>
            </option>
            <%
                }
            %>
        </select>
      </div>
      <div class="flex flex-row justify-center mt-10 mb-6">
        <input type="button" onClick="validateForm()" value="Cập nhật sinh viên" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
        <a href="javascript:history.back()" class="text-center m-auto py-2 px-4 min-w-[200px] uppercase text-white bg-stone-400 hover:bg-stone-500 hover:cursor-pointer rounded-md font-semibold">Quay lại</a>
      </div>
    </form>
  </div>
</div>
<script>
    function validateForm() {
        const listInput = document.querySelectorAll('input[type="text"]');
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
            document.studentEdit.submit();
        }
    }
</script>
</body>
</html>