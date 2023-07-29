<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/Header.jsp" %>
	<div style="margin:0 auto; width:50%" class="notiEven_Table">
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col" style="width:5%">번호</th>
		      <th scope="col" style="width:24%">제목</th>
		      <th scope="col" style="width:9%">글쓴이</th>
		    </tr>

		  </thead>
		  <tbody>
		    <tr>
		      <th scope="row">${EventB.eventId}</th>
		      <td>${EventB.eventBoardTitle}</td>
		      <td>${EventB.eventBoardWriter}</td>
		    </tr>
		    <tr>
		    	<th scope="col" colspan="3">내용</th>
		    </tr>
		    <tr>
		    	<td colspan="3" class="noticeEven_Detail" style="white-space:pre;">${EventB.eventBoardContents}</td>
		    </tr>
		  </tbody>
		</table>
      <c:if test="${member != null}">
	      <c:if test="${member.adminCk == 1}">
			<div>
				<button class="button_jd btn btn-outline-warning" onclick="eventListBtn()">글목록(notice)</button>
				<button class="button_jd btn btn-outline-warning" onclick="eventUpdateBtn()">글수정(notice)</button>
				<button class="button_jd btn btn-outline-warning" onclick="eventDeleteBtn()">글삭제(notice)</button>
			</div>
			</c:if>
		</c:if>
		<hr>
		
			</div>		

		</div>
	

<%@include file="/WEB-INF/views/include/footer.jsp" %>












