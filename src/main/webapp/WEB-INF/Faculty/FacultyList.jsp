<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page import="model.bean.Faculty" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="flex flex-col">
    <div class="flex-1 overflow-x-auto sm:mx-0.5 lg:mx-0.5">
        <div class="py-2 inline-block min-w-full sm:px-6 lg:px-8">
            <div class="overflow-hidden">
                <div class="flex flex-row justify-between items-center py-3 my-3 border-b-[2px] border-blue-400">
                    <div class="text-2xl text-blue-600 uppercase font-bold">Danh sách Khoa</div>
                    <a href="faculty?mod=insert" class="py-2 px-3 text-white bg-blue-500 hover:bg-blue-600 rounded-md font-semibold">Thêm khoa</a>
                </div>
                <%
                    String searchKeyFaculty = (String) request.getParameter("searchKeyFaculty");
                    if (searchKeyFaculty == null) {
                        searchKeyFaculty = "";
                    }
                    else {
                        searchKeyFaculty = searchKeyFaculty.trim();
                    }
                %>
                <form class="flex flex-row justify-between my-4" method="get"  action="faculty?mod=search">
                    <div class="flex bg-white px-3 py-2 rounded-md w-[300px]">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="gray" class="w-6 h-6">
                            <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 100 13.5 6.75 6.75 0 000-13.5zM2.25 10.5a8.25 8.25 0 1114.59 5.28l4.69 4.69a.75.75 0 11-1.06 1.06l-4.69-4.69A8.25 8.25 0 012.25 10.5z" clip-rule="evenodd" />
                        </svg>
                        <input type="text" name="searchKeyFaculty" id="search" placeholder="Nhập Mã khoa hoặc Tên khoa" class="flex-1 bg-white ml-2 outline-none" value="<%=searchKeyFaculty%>">
                    </div>
                </form>

                <%
                    ArrayList<Faculty> facultyList = (ArrayList<Faculty>) request.getAttribute("facultyList");
                    if (facultyList.size() > 0)
                    {
                %>

                <table class="min-w-full">
                    <thead class="bg-white border-b">
                    <tr>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            STT
                        </th>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            Mã khoa
                        </th>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            Tên Khoa
                        </th>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            Số lượng lớp
                        </th>
                        <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                            Chi tiết
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for (int i = 0; i < facultyList.size(); i++)
                        {
                            if(i % 2 == 0) {
                    %>
                    <tr class="bg-blue-200 border-b">
                            <% } else { %>
                    <tr class="bg-white border-b">
                            <% } %>

                        <td class="px-6 py-4 whitespace-nowrap text-center text-sm font-normal text-gray-900">
                            <%= (i+1) %>
                        </td>
                        <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                            <%= facultyList.get(i).getId() %>
                        </td>
                        <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                            <%= facultyList.get(i).getName() %>
                        </td>
                        <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                            <%= facultyList.get(i).getTotalClass() %>
                        </td>
                        <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                            <ul class="flex justify-evenly text-base">
                                <li>
                                    <a href="class?searchFacultyId=<%= facultyList.get(i).getId() %>">
                                        <i class="view-icon fa-regular fa-eye"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="?mod=update&id=<%= facultyList.get(i).getId() %>">
                                    <i class="view-icon fa-regular fa-pen-to-square"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="?mod=delete&id=<%= facultyList.get(i).getId() %>" class="action-delete" data-count-class="<%= facultyList.get(i).getTotalClass() %>">
                                    <i class="view-icon fa-regular fa-trash-can"></i>
                                    </a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>

                <%
                }
                else
                {
                %>
                <div class="text-xl text-blue-400 font-bold">Danh sách trống</div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
<script>
    const actionDelete = document.querySelectorAll('.action-delete');
    actionDelete.forEach((item) => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            const countClass = item.getAttribute('data-count-class');
            if (countClass > 0) {
                alert('Không thể xóa khoa này vì khoa này đang có lớp');
                return;
            }
            const url = item.getAttribute('href');
            const confirmDelete = confirm('Bạn có chắc chắn muốn xóa?');
            if (confirmDelete) {
                window.location.href = url;
            }
        })
    })
</script>
</body>
</html>