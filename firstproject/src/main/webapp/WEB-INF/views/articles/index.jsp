<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
--%>
<!-- header section -->
<%@ include file="/WEB-INF/views/layouts/header.jsp" %>

<!-- body section -->
<table class="table">
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Title</th>
        <th scope="col">Content</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="article" items="${articleList}">
            <tr>
                <th scope="row"><c:out value="${article.id}" /></th>
                <td><a href='/articles/<c:out value="${article.id}" />'><c:out value="${article.title}" /></a></td>
                <td><c:out value="${article.content}" /></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<a href="/articles/new">New Article</a>
<!--
<br>
<img src="/images/cat1.jpg" />
-->


<!-- footer section -->
<%@ include file="/WEB-INF/views/layouts/footer.jsp" %>