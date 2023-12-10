<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page import="model.bean.Loan" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<div class="flex flex-col">
    <div class="overflow-x-auto sm:mx-0.5 lg:mx-0.5">
        <div class="py-2 inline-block min-w-full sm:px-6 lg:px-8">
            <div class="overflow-hidden">
                <div class="flex flex-row justify-between items-center py-3 my-3 border-b-[2px] border-blue-400">
                    <div class="text-2xl text-blue-600 uppercase font-bold">Lịch sử mượn trả sách</div>
                    <a href="" class="py-2 px-3 text-white bg-blue-500 hover:bg-blue-600 rounded-md font-semibold">Thêm sách mượn</a>
                </div>
                <%
                    String searchStudentId = (String) request.getParameter("searchStudentId");
                    if (searchStudentId == null) {
                        searchStudentId = "";
                    }
                    else {
                        searchStudentId = searchStudentId.trim();
                    }

                    int loanStatus = 1;
                    if (request.getParameter("loanStatus") != null) {
                        loanStatus = Integer.parseInt(request.getParameter("loanStatus").trim());
                    }
                %>
                <form class="flex flex-row justify-between my-4" action="loan?mod=search">
                    <div class="flex bg-white px-3 py-2 rounded-md w-[300px]">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="gray" class="w-6 h-6">
                            <path fill-rule="evenodd" d="M10.5 3.75a6.75 6.75 0 100 13.5 6.75 6.75 0 000-13.5zM2.25 10.5a8.25 8.25 0 1114.59 5.28l4.69 4.69a.75.75 0 11-1.06 1.06l-4.69-4.69A8.25 8.25 0 012.25 10.5z" clip-rule="evenodd" />
                        </svg>
                        <input type="text" name="searchStudentId" id="search" placeholder="Nhập MSSV" class="flex-1 bg-white ml-2 outline-none" value="<%=searchStudentId%>" >
                    </div>
                    <div class="flex items-center justify-center">
                        <span class="mr-2 font-normal text-base">Tình trạng: </span>
                        <select class="min-w-[200px] rounded-md px-3 py-2" name="loanStatus">
                            <option value="0" selected>Tất cả</option>
                            <option value="1" <% if(1==loanStatus){ %>selected<% } %>>Sách chưa trả</option>
                            <option value="2" <% if(2==loanStatus){ %>selected<% } %>>Sách trả quá hạn</option>
                            <option value="3" <% if(3==loanStatus){ %>selected<% } %>>Sách trả đúng hạn</option>
                        </select>
                        <button type="submit" class="px-3 py-2 ml-5 font-medium bg-blue-500 text-white rounded-lg hover:cursor-pointer hover:bg-blue-600">Lọc</button>
                    </div>
                </form>
                <%
                    ArrayList<Loan> loanList = (ArrayList<Loan>) request.getAttribute("loanList");
                    if (loanList.size() > 0)
                    {
                %>
                <table class="min-w-full">
                    <thead class="bg-white border-b">
                        <tr>
                            <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                                STT
                            </th>
                            <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                                Mã sách
                            </th>
                            <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                                MSSV
                            </th>
                            <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                                Ngày mượn
                            </th>
                            <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                                Hạn
                            </th>
                            <th scope="col" class="text-sm font-medium text-gray-900 px-6 py-4 text-center">
                                Ngày trả
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (int i = 0; i < loanList.size(); i++)
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
                                <%= loanList.get(i).getBookId() %>
                            </td>
                            <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                                <%= loanList.get(i).getStudentId() %>
                            </td>
                            <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                                <%= loanList.get(i).getBorrowDate() %>
                            </td>
                            <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                                <%= loanList.get(i).getDueDate() %>
                            </td>
                            <td class="text-sm text-gray-900 font-normal px-6 py-4 whitespace-nowrap text-center">
                                <% if (loanList.get(i).getReturnDate() == null) { %>
                                    <a href="loan?mod=return&id=<%= loanList.get(i).getId() %>" class="px-2 py-1 text-sm font-medium bg-blue-400 text-white rounded-lg hover:cursor-pointer hover:bg-blue-500">Trả sách</a>
                                <% } else { %>
                                    <%= loanList.get(i).getReturnDate() %>
                                <% } %>
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
</body>
</html>