<%--
Created by IntelliJ IDEA.
User: km786
Date: 2022-05-25
Time: 오후 4:27
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="kr">
<head>
</head>
<body>
<jsp:include page="../templates/header.jsp"></jsp:include>

<div class="container" id="main">
   <div class="col-md-6 col-md-offset-3">
      <div class="panel panel-default content-main">
          <form name="question" method="post" action="/user/login">
              <div class="form-group">
                  <label for="userId">사용자 아이디</label>
                  <input class="form-control" id="userId" name="userId" placeholder="User ID">
              </div>
              <div class="form-group">
                  <label for="password">비밀번호</label>
                  <input type="password" class="form-control" id="password" name="password" placeholder="Password">
              </div>
              <button type="submit" class="btn btn-success clearfix pull-right">로그인</button>
              <div class="clearfix" />
          </form>
        </div>
    </div>
</div>

<jsp:include page="../templates/footer.jsp"></jsp:include>
</body>
</html>