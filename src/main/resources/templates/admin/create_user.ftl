<#import "/spring.ftl" as spring>
<#include "/layout/admin.ftl">

<@page title="user" sidebar="create_user">
<section class="subtitle">
	<h2>创建用户</h2>
</section>

<section class="data-table">
	<div class="container-fluid">
		<form style="margin:20px 10px;" action="/admin/create_user" method="post">
            <@spring.bind "form.email" />
            <div class="form-group ${spring.status.error?then("has-error", "has-success")}">
				<label for="email">邮箱地址</label>
				<input type="email" name="${spring.status.expression}" class="form-control" placeholder="Email" value="${spring.status.value?default("")}">
                <p class="error"><@spring.showErrors "" /></p>
			</div>

            <@spring.bind "form.name" />
            <div class="form-group ${spring.status.error?then("has-error", "has-success")}">
                <label for="name">名称</label>
                <input type="text" name="${spring.status.expression}" class="form-control" placeholder="Name" value="${spring.status.value?default("")}">
	            <p class="error"><@spring.showErrors "" /></p>
            </div>

            <@spring.bind "form.password" />
            <div class="form-group ${spring.status.error?then("has-error", "has-success")}">
				<label for="password">密码</label>
				<input type="password" name="${spring.status.expression}" class="form-control" placeholder="Password" value="${spring.status.value?default("")}">
                <p class="error"><@spring.showErrors "" /></p>
			</div>
			<div class="checkbox">
                <@spring.bind "form.admin" />
				<label>
                    <input type="checkbox" name="${spring.status.expression}" ${(spring.status.value=="true")?then("checked", "")}>管理员
				</label>
			</div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button type="submit" class="btn btn-default">创建用户</button>
		</form>
	</div>
</section>
</@page>
