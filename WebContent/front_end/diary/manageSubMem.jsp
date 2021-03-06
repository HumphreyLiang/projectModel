<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.diary.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.submem.model.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    Member member = (Member)session.getAttribute("member");//此行先留著
%>

	
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Title Page</title>
    <link href="<%=request.getContextPath()%>/front_end/css/bootstrap.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/nav.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/colorplan.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<%=request.getContextPath()%>/front_end/css/modern-business.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/date.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="<%=request.getContextPath()%>/front_end/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="<%=request.getContextPath()%>/front_end/css/frontend.css" rel="stylesheet" type="text/css">
	<script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<%@ include file="navbar.file" %>
    <%@ include file="leftbar.file" %>
    <%@ include file="ad.file" %>
                <h5 class="page-header text-right">目前位置:日誌首頁</h5>
                
 
                <div class="row">
                    <div class="panel panel-default col-sm-8 col-sm-offset-2 top-margin-sm">
                        <div class="">
                            <div class="panel-heading">
                                 <c:forEach var="submem" items="${smSvc.getMemberAct(member.getMemNo())}">
	                                 <form action="<%=request.getContextPath()%>/front_end/diary/subMem.do" method=post >
	                            		<a href="<%=request.getContextPath()%>/front_end/diary/personalDiary.jsp?memNo=${submem.beSubMemNo}" > ${memSvc.getOneMember(submem.getBeSubMemNo()).getMemSname()}</a>
	                            		<input type="hidden" name="action" value="delete">
	                            		<input type="hidden" name="beSubMemNo" value="${submem.beSubMemNo }">
	                            		<input type="submit" value="點我取消追蹤"><br>
                            		</form>
                       			</c:forEach>
                            </div>
                            <div class="panel-body">
                               
                            </div>
                         </div>
                                                 
                    </div>
                </div>
                
               

                <%@ include file="bottom.file" %>
</body>
</html>