<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %> <!-- 건너오는 모든 데이터를 UTF-8로 받는다. -->
<jsp:useBean id="user" class="user.User" scope="page"></jsp:useBean>
<!-- id : JSP페이지에서 자바빈 객체에 접근 할 때 사용하는 이름이다. -->
<!-- class : 패키지 이름을 포함한 자바빈 클래스의 완전한 이름을 입력 -->
<!-- scope : 자바빈 객체가 저장될 영역을 지정. page, request, session, application 중 하나를 값으로 갖는다 default 값은 page -->
<jsp:setProperty name="user" property="userID" />
<!-- name : 프로퍼티 값을 변경할 자바빈 객체의 이름. jsp:useBean 액션 태그의 id속성에서 지정한 값을 사용 -->
<!-- property : 값을 지정할 프로퍼티의 이름 -->
<!-- value : 프로퍼티 값. 표현식 사용가능 -->
<jsp:setProperty name="user" property="userPassword" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		if(userID != null){ //이미 로그인 된사람들은 다시 로그인 페이지로 갈 수 없게
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인 되었습니다.')");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
		}
		
		UserDAO userDAO = new UserDAO();
		int result = userDAO.login(user.getUserID(), user.getUserPassword());
		if(result == 1){
			session.setAttribute("userID", user.getUserID());
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
		}
		else if(result == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('비밀번호가 틀립니다.')");
			script.println("history.back()"); // 이전 페이지로 돌려보냄
			script.println("</script>");
		}
		else if(result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('존재하지 않는 아이디입니다.')");
			script.println("history.back()"); // 이전 페이지로 돌려보냄
			script.println("</script>");
		}
		else if(result == -2){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생하였습니다.')");
			script.println("history.back()"); // 이전 페이지로 돌려보냄
			script.println("</script>");
		}
		
	%>


	

</body>
</html>