<%@ page import="java.util.ArrayList" %>
<%@ page import="model.bean.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/multi-select.css">
    <script src="js/multi-select.js"></script>
</head>
<body>
<div class="flex flex-col">
    <div class="inline-block min-w-full sm:px-6 lg:px-8">
        <div class="overflow-hidden h-[100vh]">
            <div class="flex flex-row justify-between items-center py-3 my-3 border-b-[2px] border-blue-400">
                <div class="text-2xl text-blue-600 uppercase font-bold">Mượn sách</div>
            </div>
            <form method="post" name="loanInsert" action="loan?action=insert" onsubmit="return handleSubmit(event)">
                <div class="flex flex-row w-[100%]">
                    <div class="w-[50%] mr-2 flex flex-col my-2">
                        <label for="studentId" class="text-base font-medium mb-1">Mã số sinh viên: </label>
                        <input type="text" name="studentId" id="studentId" placeholder="Nhập mã số sinh viên" class="rounded-md px-3 py-2 mb-1" onBlur="validateId()">
                        <span class="text-red-500 font-medium text-xs" id="studentIdError"></span>
                    </div>
                    <div class="w-[50%] mr-2 flex flex-col my-2">
                        <label for="dueDate" class="text-base font-medium mb-1">Hạn trả sách: </label>
                        <input type="date" name="dueDate" id="dueDate" class="rounded-md px-3 mb-1" style="height: 40px" onblur="validateDate()">
                        <span class="text-red-500 font-medium text-xs" id="dueError"></span>
                    </div>
                </div>
                <div class="flex flex-row w-[100%]">
                    <div class="w-[100%] flex flex-col my-2">
                        <label for="selection" class="text-base font-medium mb-2">Sách: </label>
                        <select id="selection" multiple>
                            <%
                                ArrayList<Book> bookList = (ArrayList<Book>) request.getAttribute("bookList");
                                if (bookList.size() > 0)
                                {
                                    for (int i = 0; i < bookList.size(); i++) {
                                        Book book = bookList.get(i);
                            %>
                            <option value="<%=book.getId()%>">[<%=book.getId()%>] <%=book.getName()%> - <%=book.getAuthor()%></option>
                            <%      }
                                }
                            %>
                        </select>
                        <span class="text-red-500 font-medium text-xs mt-1" id="bookIdsError"></span>
                    </div>
                </div>
                <input type="text" name="bookIds" id="bookIds" class="hidden" value="">
                <div class="flex flex-row justify-end mt-4">
                    <input type="submit" value="Thêm sách mượn" class="text-center mr-2 py-2 px-4 min-w-[200px] uppercase text-white bg-blue-500 hover:bg-blue-600 hover:cursor-pointer rounded-md font-semibold">
                    <a href="" class="text-center ml-2 py-2 px-4 min-w-[200px] uppercase text-white bg-stone-400 hover:bg-stone-500 hover:cursor-pointer rounded-md font-semibold">Quay lại</a>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    var selected = new MultiSelectTag('selection')
    let isValidId = false;
    function handleSubmit(event) {
        event.preventDefault();
        var selectedValues = selected.getValues();
        var isValidDate = validateDate();
        validateId();
        console.log(selectedValues, isValidId, isValidDate);
        if (selectedValues.length > 0 && isValidId && isValidDate) {
            document.getElementById('bookIdsError').innerHTML = '';
            var concatenatedValues = selectedValues.map(function(item) {
                return item.value;
            }).join(',');
            document.getElementById('bookIds').value = concatenatedValues;
            console.log(concatenatedValues)
            event.currentTarget.submit();
            return true;
        }
        else {
            if (selectedValues.length === 0) {
                document.getElementById('bookIdsError').innerHTML = 'Vui lòng chọn tối thiểu 1 sách';
            }
            return false;
        }
    }
    function validateId() {
        const id = document.getElementById("studentId").value;
        console.log(id)
        if (id === '') {
            document.querySelector('#studentIdError').innerHTML = 'Vui lòng nhập trường này';
            isValidId = false;
        }
        else {
            const xhr = new XMLHttpRequest();
            xhr.open("GET", "student?mod=checkId&id=" + id, true);

            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        const response = JSON.parse(xhr.responseText);
                        if (response) {
                            document.querySelector('#studentIdError').innerHTML = 'Mã số sinh viên không tồn tại';
                            isValidId = false;
                        } else {
                            document.querySelector('#studentIdError').innerHTML = '';
                            isValidId = true;
                        }
                    } else {
                        // Xử lý lỗi khi gửi yêu cầu kiểm tra
                        console.error("Error: " + xhr.status);
                        isValidId = false;
                    }
                }
            }
            xhr.send();
        }
    }
    function validateDate() {
        var dueDate = document.getElementById('dueDate').value;
        if (dueDate === '') {
            document.getElementById('dueError').innerHTML = 'Vui lòng chọn hạn trả sách';
            return false;
        }
        var selectedDate = new Date(dueDate);
        var today = new Date();
        if (selectedDate > today) {
            document.getElementById('dueError').innerHTML = '';
            return true;
        } else {
            document.getElementById('dueError').innerHTML = 'Vui lòng chọn hạn trả sách lớn hơn ngày hiện tại';
            return false;
        }
    }
</script>
</body>
</html>