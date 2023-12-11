<%@ page import="java.util.ArrayList" %>
<%@ page import="model.bean.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<div class="flex flex-row w-[100wh] h-[100vh] bg-blue-100">
    <div class="flex-1 overflow-x-auto sm:mx-0.5 lg:mx-0.5">
        <div class="py-2 inline-block min-w-full sm:px-6 lg:px-8">
            <div class="overflow-hidden">
                <div class="flex flex-row justify-between py-3 my-3 border-b-[2px] border-blue-400">
                    <div class="text-2xl text-blue-600 uppercase font-bold">Danh mục</div>
                    <a href="category?mod=insert"
                       class="py-2 px-3 text-white bg-blue-500 hover:bg-blue-600 rounded-md font-semibold">Thêm danh
                        mục</a>
                </div>
                <div class="flex flex-row justify-between my-4">
                    <div class="flex bg-white px-3 py-2 rounded-md w-[300px]">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="gray" class="w-6 h-6">
                            <path fill-rule="evenodd"
                                  d="M10.5 3.75a6.75 6.75 0 100 13.5 6.75 6.75 0 000-13.5zM2.25 10.5a8.25 8.25 0 1114.59 5.28l4.69 4.69a.75.75 0 11-1.06 1.06l-4.69-4.69A8.25 8.25 0 012.25 10.5z"
                                  clip-rule="evenodd"/>
                        </svg>
                        <input type="text" name="search" id="search" placeholder="Nhập tên danh mục"
                               class="flex-1 bg-white ml-2 outline-none">
                    </div>
                    <div class="flex items-center justify-center">
                        <span class="mr-2 font-normal text-base">Sắp xếp: </span>
                        <select class="min-w-[200px] rounded-md px-3 py-2" id="sorter">
                            <option value="asc">Tăng dần</option>
                            <option value="desc">Giảm dần</option>
                        </select>
                    </div>
                </div>
                <table class="min-w-full">
                    <thead class="bg-white border-b">
                    <tr>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            #
                        </th>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            Tên danh mục
                        </th>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            Số lượng sách
                        </th>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            Thao tác
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        ArrayList<Category> categoryList = (ArrayList<Category>) request.getAttribute("categoryList");
                        for (int i = 0; i < categoryList.size(); i++) {
                            Category category = categoryList.get(i);
                    %>
                    <% if (i % 2 == 0) { %>
                    <tr class="bg-blue-200 border-b">
                            <% } else { %>
                    <tr class="bg-white border-b">
                        <% } %>
                        <td class="px-6 py-4 whitespace-nowrap text-center text-sm font-normal text-gray-900"><%= i + 1 %>
                        </td>
                        <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                            <%= category.getName() %>
                        </td>
                        <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                            <%= category.getTotalBook() %>
                        </td>
                        <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                            <ul class="flex justify-center gap-3 text-base">
                                <li>
                                    <a href="book?mod=filter&category=<%= category.getId() %>">
                                        <i class="view-icon fa-regular fa-eye"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="?mod=update&id=<%= category.getId()%>">
                                        <i class="view-icon fa-regular fa-pen-to-square"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="" class="action-delete" onclick="deleteCategory(<%= category.getId() %>)">
                                        <i class="view-icon fa-regular fa-trash-can"></i>
                                    </a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    const handleSearchAndSorter = () => {
        const search = document.getElementById('search');
        const sorter = document.getElementById('sorter');

        search.addEventListener('keyup', () => {
            searchAndSortLogic(search, sorter);
        })
        sorter.addEventListener('change', () => {
            searchAndSortLogic(search, sorter);
        })
    }
    handleSearchAndSorter();

    function deleteCategory(categoryId) {
        if (confirm('Bạn có chắc chắn muốn xóa?')) {
            const xhr = new XMLHttpRequest();
            xhr.open('DELETE', '?action=delete&id=' + categoryId, true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    console.log(xhr.responseText);
                }
            };
            xhr.send();
        }
        window.reload();
    }

    function searchAndSortLogic(search, sorter) {
        const searchString = search.value.toLowerCase();
        const sorterString = sorter.value.toLowerCase();
        const table = document.getElementsByTagName('tbody')[0];
        const rows = table.getElementsByTagName('tr');
        let index = 1;

        Array.from(rows).forEach((row) => {
            Array.from(rows).sort((a, b) => {
                const tdNameA = a.getElementsByTagName('td')[1];
                const tdNameB = b.getElementsByTagName('td')[1];
                const textValueA = tdNameA.textContent || tdNameA.innerHTML;
                const textValueB = tdNameB.textContent || tdNameB.innerHTML;
                const cmp = textValueA.localeCompare(textValueB, 'en', {sensitivity: 'base'});
                return sorterString === 'asc' ? cmp : -cmp;
            }).forEach((row) => {
                table.appendChild(row);
            })
        })

        Array.from(rows).forEach((row) => {
            const tdIndex = row.getElementsByTagName('td')[0];
            const tdName = row.getElementsByTagName('td')[1];

            const textNameValue = tdName.textContent || tdName.innerHTML;

            if (textNameValue.toLowerCase().indexOf(searchString) > -1) {
                row.style.display = '';
                tdIndex.textContent = (index++).toString();
                if (index % 2 === 0) {
                    row.classList.remove('bg-white');
                    row.classList.add('bg-blue-200');
                } else {
                    row.classList.remove('bg-blue-200');
                    row.classList.add('bg-white');
                }
            } else {
                row.style.display = 'none';
            }
        })
    }
</script>
</body>
</html>