<#include "/layout/admin.ftl">

<@view title="user" sidebar="tasks">
<section class="subtitle">
	<h2>任务列表</h2>
</section>
<section class="data-table">
	<div class="container-fluid">
		<table class="table table-striped">
			<thead>
			<tr>
				<th class="visible-sm visible-md visible-lg">编号</th>
                <th class="visible-lg">类型</th>
                <th class="visible-lg">状态</th>
				<th class="visible-xs visible-sm visible-md visible-lg">内容</th>
                <th class="visible-lg">创建人</th>
				<th class="visible-lg">创建时间</th>
				<th class="text-center td-btn">操作</th>
			</tr>
			</thead>
			<tbody>
          <#list page.content as task>
          <tr>
	          <td class="visible-sm visible-md visible-lg">${task.id}</td>
	          <td class="visible-lg">${task.category}</td>
	          <td class="visible-lg">${task.status}</td>
	          <td class="visible-xs visible-sm visible-md visible-lg">${task.content?html}</td>
	          <td class="visible-lg">${task.belongsTo.name}</td>
	          <td class="visible-lg">${task.createdAt}</td>
	          <td class="text-center td-btn"><a class="btn-table" href="/admin/tasks/${task.id}/edit" title="编辑"><i class="fa fa-cog" aria-hidden="true"></a></td>
          </tr>
          </#list>
			<tbody>
		</table>
	</div>
</section>
<@pagination.section page />
</div>
</@view>
