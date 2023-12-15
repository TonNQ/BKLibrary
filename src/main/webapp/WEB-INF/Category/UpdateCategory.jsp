<%@ page import="model.bean.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<%
    Category category = (Category) request.getAttribute("category");
%>
<div class="flex flex-col">
    <div class="sm:mx-0.5 lg:mx-0.5">
        <div class="py-2 inline-block min-w-full sm:px-6 lg:px-8">
            <div class="overflow-hidden">
                <div class="flex flex-row justify-between items-center py-3 my-3 border-b-[2px] border-blue-400">
                    <div class="text-2xl text-blue-600 uppercase font-bold">Cập nhật danh mục</div>
                </div>
                <form method="post" name="update" action="category?action=update&id=<%= category.getId() %>" onsubmit="return validateForm()">
                    <div class="flex flex-row w-[100%]">
                        <div class="w-[50%] flex flex-col ml-2 my-2">
                            <label for="name" class="text-base font-medium mb-1">Tên danh mục: </label>
                            <input type="text" name="name" id="name" placeholder="Nhập tên danh mục" class="rounded-md px-3 py-2 mb-1" value="<%= category.getName()%>" onkeyup="checkCategoryName()">
                            <span class="text-red-500 font-medium text-xs nameError"></span>
                        </div>
                    </div>
                    <div class="flex flex-row justify-end">
                        <input type="submit" value="Cập nhật" class="text-center mr-2 py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
                        <a href="javascript:history.back()" class="text-center ml-2 py-2 px-4 min-w-[200px] uppercase text-white bg-stone-400 hover:bg-stone-500 hover:cursor-pointer rounded-md font-semibold">Quay lại</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    let isValidForm = true;
    function validateForm() {
        const listInput = document.querySelectorAll('input[type="text"]');
        listInput.forEach(input => {
            if (input.value.trim() === "") {
                isValidForm = false;
                input.nextElementSibling.innerHTML = "Vui lòng nhập vào trường này";
            }
        });
        return isValidForm;
    }
    function checkCategoryName() {
        const categoryName = document.forms["update"]["name"].value;
        const xhr = new XMLHttpRequest();
        xhr.open('GET', 'category?mod=check&name=' + categoryName, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                if (xhr.responseText === "true") {
                    document.querySelector(".nameError").innerHTML = "Danh mục đã tồn tại";
                    isValidForm = false;
                } else {
                    document.querySelector(".nameError").innerHTML = "";
                    isValidForm = true;
                }
            }
        };
        xhr.send();
    }
</script>
</body>
</html>