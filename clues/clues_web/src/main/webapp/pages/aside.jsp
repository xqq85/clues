<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="${pageContext.request.contextPath}/img/user2-160x160.jpg"
					class="img-circle" alt="User Image">
			</div>
			<div class="pull-left info">
				<p>
					<security:authentication property="principal.username"></security:authentication>
				</p>
				<a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
			</div>
		</div>

		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header">菜单</li>
			<li id="admin-index"><a
				href="${pageContext.request.contextPath}/pages/main.jsp"><i
					class="fa fa-dashboard"></i> <span>首页</span></a></li>

			<security:authorize access="hasRole('ADMIN')">
			<li class="treeview"><a href="#"> <i class="fa fa-cogs"></i>
					<span>系统管理</span> <span class="pull-right-container"> <i
						class="fa fa-angle-left pull-right"></i>
				</span>
			</a>
				<ul class="treeview-menu">
					<li id="user-manager">
						<a
						href="${pageContext.request.contextPath}/user/findAll.do?page=1&size=4"> <i
							class="fa fa-circle-o"></i> 用户管理
					</a>
					</li>
					<li id="role-manager"><a
						href="${pageContext.request.contextPath}/role/findAll.do?page=1&size=4"> <i
							class="fa fa-circle-o"></i> 角色管理
					</a></li>
					<li id="permission-manager"><a
						href="${pageContext.request.contextPath}/permission/findAll.do?page=1&size=4">
							<i class="fa fa-circle-o"></i> 资源权限管理
					</a></li>
				</ul>

			</li>
			</security:authorize>
			<li class="treeview"><a href="#"> <i class="fa fa-user-plus"></i>
				<span>客户线索管理</span> <span class="pull-right-container"> <i
						class="fa fa-angle-left pull-right"></i>
				</span>
			</a>
				<ul class="treeview-menu">
					<li id="clue">
						<a href="${pageContext.request.contextPath}/clues/findAll.do?page=1&size=4"> <i
								class="fa fa-circle-o"></i> 线索
						</a>
					</li>
					<security:authorize access="hasRole('USER')">
					<li id="user"><a
							href="${pageContext.request.contextPath}/clients/findAll.do?page=1&size=4"> <i
							class="fa fa-circle-o"></i> 客户
					</a></li>
					</security:authorize>
					<li id="public"><a
							href="${pageContext.request.contextPath}/publicSea/findAll.do?page=1&size=4">
						<i class="fa fa-circle-o"></i> 公海
					</a></li>
				</ul>

			</li>

			<li class="treeview"><a href="#"> <i class="fa fa-cube"></i>
					<span>产品/订单管理</span> <span class="pull-right-container"> <i
						class="fa fa-angle-left pull-right"></i>
				</span>
			</a>
				<ul class="treeview-menu">

					<li id="project-manager"><a
						href="${pageContext.request.contextPath}/product/findAll.do?page=1&size=4">
							<i class="fa fa-circle-o"></i> 产品管理
					</a></li>
					<li id="goods-manager"><a
						href="${pageContext.request.contextPath}/orders/findAll.do?page=1&size=4"> <i
							class="fa fa-circle-o"></i> 订单管理
					</a></li>

				</ul></li>

		</ul>
	</section>
	<!-- /.sidebar -->
</aside>