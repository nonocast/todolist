<#include "/layout/admin.ftl">

<@page title="user" sidebar="users">
<section class="subtitle">
	<h2>用户列表</h2>
</section>
<section class="data-table">
	<div class="container-fluid">
		<table class="table table-striped">
			<thead>
			<tr>
				<th class="visible-sm visible-md visible-lg">编号</th>
				<th class="visible-xs visible-sm visible-md visible-lg">邮箱地址</th>
				<th class="visible-lg">名称</th>
                <th class="visible-lg">手机</th>
                <th class="visible-lg">区域</th>
                <th class="visible-lg">微信</th>
                <th class="visible-lg">权限</th>
                <th class="visible-lg">创建时间</th>
				<th class="text-center td-btn">操作</th>
			</tr>
			</thead>
			<tbody>
          <#list users as user>
					<tr>
						<td class="visible-sm visible-md visible-lg">${user.id}</td>
						<td class="visible-xs visible-sm visible-md visible-lg">${user.email}</td>
						<td class="visible-lg">${user.name}</td>
                        <td class="visible-lg">${user.mobile}</td>
                        <td class="visible-lg">${user.location}</td>
                        <td class="visible-lg">${user.wechatid}</td>
                        <td class="visible-lg">${user.admin?then("管理员", "普通用户")}</td>
                        <td class="visible-lg">${user.createdAt}</td>
						<td class="text-center td-btn"><a class="btn-table" href="project-info.html" title="编辑"><i class="fa fa-cog" aria-hidden="true"></a></td>
					</tr>
          </#list>
			<tbody>
		</table>
	</div>
</section>
<!--
<section class="paginations">
	<div class="container-fluid">
		<small class="pull-left pagination-text visible-sm visible-md visible-lg">现在是第1页，共1页，总共15条数据</small>
		<ul class="pagination pull-right">
			<li>
				<a href="#" aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>
			<li class="active"><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li>
				<a href="#" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</ul>
	</div>
</section>
-->
</div>
</@page>
