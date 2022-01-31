<%@page import="java.util.Calendar"%>
<%@page import="com.capipyre.cis.dto.Product"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.capipyre.cis.util.DbConnector"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>검색 결과 - Customer Information Service</title>
<link rel="stylesheet" href="../stylesheet/main.css">
<style>
	.container {
		height: 80vh;
	}
	#backBtn {
		margin: 1rem;
		padding: 0.3rem;
	}
</style>
</head>
<body>
	<%
		String prodCode = request.getParameter("prodCode");

		String sql = new StringBuilder("")
				.append("select ")
					.append("pt.prod_code prod_code, ")
					.append("pt.manufacturer manufacturer, ")
					.append("to_char(pt.created_at, 'yyyy-mm-dd') created_at ")
				.append("from ")
					.append("product_tbl pt ")
				.append("where ")
					.append("pt.prod_code = ?")
				.toString();

		PreparedStatement pstmt = DbConnector.getInstance().createPreparedStmt(sql);

		pstmt.setString(1, prodCode);

		ResultSet rs = pstmt.executeQuery();

		Product product = null;

		if (rs.next()) {
			product = new Product(
						rs.getString("prod_code"),
						rs.getString("manufacturer"),
						rs.getString("created_at")
					);
		}
	%>
	<button id="backBtn">◀ 다시 검색하기</button>
	<div class="container">
		<div class="vertical-align">
			<div>
				<h1 class="banner">제품코드 조회 결과</h1>
				<table>
					<tbody>
						<tr>
							<td colspan="2">제조사 조회 결과</td>
						</tr>
						<tr>
							<td>제품코드</td>
							<td><%= prodCode %></td>
						</tr>
						<tr>
							<td>제조사명</td>
							<td>
								<%= 
									product != null 
										? product.getManufacturer()
										: "등록되지 않은 제품입니다!!"
								%>
							</td>
						</tr>
						<tr>
							<td>조회일시</td>
							<td><%= new Date().toString() %></td>
						</tr>
					</tbody>
				</table>
				<div>
					작성자: 한국인 비번: 1004 작성일:
					<% if (product != null) { %>
						<%= product.getCreatedAt() %>
					<% } else {
							Calendar cal = Calendar.getInstance();
							int yr = cal.get(Calendar.YEAR);
							int mth = cal.get(Calendar.MONTH) + 1;
							int dayOfMth = cal.get(Calendar.DAY_OF_MONTH);
							String padMth = mth >= 10 ? "" : "0";
							String padDayOfMth = dayOfMth >= 10 ? "" : "0";
							String dateStr = String.format(
									"%d-%s%d-%s%d", yr, padMth, mth, padDayOfMth, dayOfMth);
					%>
						<%= dateStr %>
					<% } %> 
				</div>
			</div>
		</div>
	</div>
	<% pstmt.close(); %>
	<script>
		var backBtn = document.getElementById('backBtn');
		backBtn.onclick = function () {
			window.location.href = "home.html";
		};
	</script>
</body>
</html>