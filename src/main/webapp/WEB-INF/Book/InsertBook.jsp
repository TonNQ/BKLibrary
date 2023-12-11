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
<div class="flex flex-col">
    <div class="sm:mx-0.5 lg:mx-0.5">
        <div class="py-2 inline-block min-w-full sm:px-6 lg:px-8">
            <div class="overflow-hidden">
                <div class="flex flex-row justify-between items-center py-3 my-3 border-b-[2px] border-blue-400">
                    <div class="text-2xl text-blue-600 uppercase font-bold">Thêm sách</div>
                    <a href="" class="text-center mr-2 py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">Thêm file</a>
                </div>
                <form method="post" name="add" action="?action=insert" onsubmit="return validateForm()">
                    <div class="flex flex-row w-[100%]">
                        <div class="w-[50%] flex flex-col mr-2 my-2">
                            <label for="bookId" class="text-base font-medium mb-1">Mã sách: </label>
                            <input type="text" name="bookId" id="bookId" placeholder="Nhập mã sách" class="rounded-md px-3 py-2 mb-1" onblur="checkBookId()">
                            <span class="text-red-500 font-medium text-xs bookIdError"></span>
                        </div>
                        <div class="w-[50%] flex flex-col ml-2 my-2">
                            <label for="name" class="text-base font-medium mb-1">Tên sách: </label>
                            <input type="text" name="name" id="name" placeholder="Nhập tên sách" class="rounded-md px-3 py-2 mb-1">
                            <span class="text-red-500 font-medium text-xs nameError"></span>
                        </div>
                    </div>
                    <div class="flex flex-row w-[100%]">
                        <div class="w-[50%] flex flex-col mr-2 my-2">
                            <label for="author" class="text-base font-medium mb-1">Tác giả: </label>
                            <input type="text" name="author" id="author" placeholder="Nhập tên tác giả" class="rounded-md px-3 py-2 mb-1">
                            <span class="text-red-500 font-medium text-xs authorError"></span>
                        </div>
                        <div class="w-[50%] flex flex-col ml-2 my-2">
                            <label for="author" class="text-base font-medium mb-1">Số lượng: </label>
                            <input type="text" name="quantity" id="quantity" placeholder="Nhập số lượng" class="rounded-md px-3 py-2 mb-1">
                            <span class="text-red-500 font-medium text-xs quantityError"></span>
                        </div>
                    </div>
                    <div class="flex flex-row w-[100%]">
                        <div class="w-[50%] flex flex-col mr-2 my-2">
                            <label for="category" class="text-base font-medium mb-1">Danh mục: </label>
                            <select id="category" name= "category" class="w-full rounded-md px-3 py-2 mb-1">
                                <%
                                    ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categoryList");
                                    for (int i = 0; i < categories.size(); i++) {
                                        Category category = categories.get(i);
                                %>
                                <option value="<%= category.getId() %>"><%= category.getName() %></option>
                                <% } %>
                            </select>
                            <span class="text-red-500 font-medium text-xs categoryError"></span>
                        </div>
                    </div>
                    <div class="flex flex-row justify-end">
                        <input type="submit" value="Thêm sách" class="text-center mr-2 py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
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
        const quantity = document.forms["add"]["quantity"].value;
        if (isNaN(quantity)) {
            const quantityError = document.querySelector(".quantityError");
            quantityError.innerHTML = "Vui lòng nhập vào kiểu số";
            isValidForm = false;
        }
        return isValidForm;
    }
    function checkBookId() {
        const bookId = document.forms["add"]["bookId"].value;
        const xhr = new XMLHttpRequest();
        xhr.open('GET', '?mod=check&id=' + bookId, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                if (xhr.responseText === "true") {
                    document.querySelector(".bookIdError").innerHTML = "Mã sách đã tồn tại";
                    isValidForm = false;
                } else {
                    document.querySelector(".bookIdError").innerHTML = "";
                    isValidForm = true;
                }
            }
        };
        xhr.send();
    }
</script>
</body>
</html>