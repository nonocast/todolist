<#import "/spring.ftl" as spring>
<#include "/layout/admin.ftl">

<@page title="dba" sidebar="dba">
<section class="subtitle">
	<h2>数据库管理</h2>
</section>

<section class="data-table">
<div class="container-fluid">
	<form action="/admin/dba_rebuild" method="post">
		<button style="margin-left:10px;" class="btn btn-default" type="submit">Rebuild database</button>
	</form>
</div>
</section>
</@page>