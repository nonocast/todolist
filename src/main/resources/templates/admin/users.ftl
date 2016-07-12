<#include "/layout/admin.ftl">

<@view title="user" sidebar="users">
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
          <#list page.content as user>
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
<@pagination.section page />
</div>
</@view>
