<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
          <form name="question" method="post" action="/user/update">
              <div class="form-group">
                  <label for="userId">아이디</label>
                  <input class="form-control" type="hidden" id="userId" name="userId" value=${user.userId} placeholder="User ID">
              </div>
              <div class="form-group">
                  <label for="password">비밀번호</label>
                  <input type="password" class="form-control" id="password" name="password" placeholder="Password">
              </div>
              <div class="form-group">
                  <label for="name">이름</label>
                  <input class="form-control" id="name" name="name" value=${user.name} placeholder="Name">
              </div>
              <div class="form-group">
                  <label for="email">이메일</label>
                  <input type="email" class="form-control" id="email" name="email" value=${user.email} placeholder="Email">
              </div>
              <button type="submit" class="btn btn-success clearfix pull-right">개인정보 수정</button>
              <div class="clearfix" />
          </form>
        </div>
    </div>
</div>

<jsp:include page="../templates/footer.jsp"></jsp:include>
</body>
</html>