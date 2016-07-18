<#include "/layout/admin.ftl">

<#assign header>
<script type="text/coffeescript" src="/private/script/users.coffee"></script>
</#assign>

<@view title="user" sidebar="users" header=header>
<section class="subtitle">
	<h2>用户列表</h2>
</section>

<section style="margin:0 14px 0 16px;">
	<div class="row">
		<div class="col-md-8">
			<div id="sort-panel" class="btn-group" role="group" aria-label="...">
				<button type="button" class="btn btn-default btn-sm">编号</button>
				<button type="button" class="btn btn-default btn-sm">名称</button>
				<button type="button" class="btn btn-default btn-sm">创建时间</button>
			</div>
			<div id="role-panel" style="margin-left:5px;" class="btn-group" role="group" aria-label="...">
				<button type="button" class="btn btn-default btn-sm">所有类型</button>
				<button type="button" class="btn btn-default btn-sm">普通用户</button>
                <button type="button" class="btn btn-default btn-sm">管理员</button>
			</div>
		</div>
			<div class="col-md-4">
                <div id="search-panel" class="input-group">
                    <div class="form-group has-feedback has-clear">
                        <input type="text" class="form-control" id="exampleInput1" placeholder="">
                        <span style="color:#bec0c2;" class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden"></span>
                    </div>
                    <span class="input-group-btn">
                        <button type="button" class="btn btn-default" id="exampleButton1">搜索</button>
                    </span>
                </div>
			</div>
	</div>
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
	              <td class="visible-xs visible-sm visible-md visible-lg">${user.email?html}</td>
	              <td class="visible-lg">${user.name?html}</td>
	              <td class="visible-lg">${user.mobile?html}</td>
	              <td class="visible-lg">${user.location?html}</td>
	              <td class="visible-lg">${shorten(user.wechatid)}</td>
	              <td class="visible-lg">${user.role}</td>
	              <td class="visible-lg">${user.createdAt}</td>
	              <td class="text-center td-btn"><a class="btn-table" href="users/${user.id}/edit" title="编辑"><i class="fa fa-cog" aria-hidden="true"></a></td>
              </tr>
              </#list>
			<tbody>
		</table>
	</div>
</section>
    <@pagination.section page />
</div>
</@view>
