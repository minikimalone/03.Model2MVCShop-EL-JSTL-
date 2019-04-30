<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
 
function fncGetList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${param.menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				
				
				<tr>
					<td width="93%" class="ct_ttl01">
					
							${param.menu.equals("manage")?"��ǰ ����":"��ǰ ��� ��ȸ"}
					
					</td>
				</tr>
				
				
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>

		</td>
		
		<td align="right">
			<c:if test="${param.menu eq 'manage'}">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${! empty search.searchCondition&&search.searchCondition.equals('0') ? 'selected' : ''}>��ǰ��ȣ</option>
				<option value="1" ${! empty search.searchCondition&&search.searchCondition.equals('1') ? 'selected' : ''}>��ǰ��</option>
				<option value="2" ${! empty search.searchCondition&&search.searchCondition.equals('2') ? 'selected' : ''}>���� </option>
			</select>
			</c:if>
			<c:if test="${!(param.menu eq 'manage')}">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="1" ${! empty search.searchCondition&&search.searchCondition.equals('1') ? 'selected' : ''}>��ǰ��</option>
				<option value="2" ${! empty search.searchCondition&&search.searchCondition.equals('2') ? 'selected' : ''}>���� ></option>
			</select>
			</c:if>
			<input 	type="text" name="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : ''}"
			  class="ct_input_g" style="width:200px; height:20px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >��ü ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		
		<c:if test="${param.menu eq 'manage'}">
		
		<td class="ct_list_b" width="100">��ǰ��ȣ</td>
		</c:if>
		<c:if test="${!(param.menu eq 'manage')}">
		<td class="ct_line02"></td>
		</c:if>
		
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
	
		<c:choose>
		<c:when test="${param.menu eq 'manage'}">
		<td class="ct_list_b">�����</td>	
		</c:when>
		<c:when test="${param.menu eq 'search'}">
		<td class="ct_list_b">��ǰ ������</td>	
		</c:when>
		</c:choose>
		
		<td class="ct_list_b">�������</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
		
	<c:forEach var="i" items="${list}" varStatus="j" >
	<tr class="ct_list_pop">
		<td align="center">${j.index+1}</td>
		
		<c:if test="${param.menu eq 'manage'}">
		<td>${i.prodNo}</td>
		</c:if>
		<c:if test="${!(param.menu eq 'manage')}">
		<td></td>
		</c:if>
		
		<td align="left">
				
				
				<a href="/getProduct.do?prodNo=${i.prodNo}&menu=${param.menu}">${i.prodName}</a>
								
				
		</td>
		
		<td></td>
		<td align="left">${i.price}</td>
		<td></td>
		
		<c:choose>
		<c:when test="${param.menu eq 'manage'}">
		<td align="left">${i.regDate}</td>	
		</c:when>
		<c:when test="${param.menu eq 'search'}">
		<td align="center">${i.prodDetail}</td>	
		</c:when>
		</c:choose>		
		
		<td align="left">
		
		<c:if test="${!param.menu.equals('manage')}">
			
				�Ǹ���
			
			</c:if>
	
		<c:if test="${param.menu.equals('manage')}">			
		<c:choose>	
			<c:when test="${i.proTranCode.equals('0')}">
				�Ǹ���
			</c:when>
			<c:when test="${i.proTranCode.equals('1')}">
				���ſϷ� <a href= "/updateTranCodeByProd.do?prodNo=${i.prodNo}&tranCode=2">����ϱ�</a>
			</c:when>
			<c:when test="${i.proTranCode.equals('2')}">
				�����
			</c:when>
			<c:otherwise>
				��ۿϷ�
			</c:otherwise>
		</c:choose>
		</c:if>
		</td>	
	</tr>	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>

	</table>
	
<!--  ������ Navigator ���� -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value="1"/>
					
			<jsp:include page="../common/pageNavigator.jsp"/>	
		
    	</td>
 	</tr>
</table>
<!--  ������ Navigator �� -->


</form>
</div>

</body>
</html>
	