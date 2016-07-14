$ ->
	p = {}
	[p.all, p.user, p.admin] = ($(each) for each in $('#role-panel button'))
	role = if $.app.params.has 'role' then $.app.params.get('role') else 'all'
	p[role].addClass 'active'
	p.all.click -> $.app.params.remove('role').go()
	p.user.click -> $.app.params.set('role', 'user').go()
	p.admin.click -> $.app.params.set('role', 'admin').go()

	q = {}
	[q.id, q.name, q.createdAt] = ($(each) for each in $('#sort-panel button'))
	sort = if $.app.params.has 'sort' then $.app.params.get('sort') else 'id'
	q[sort].addClass 'active'
	q.id.click -> $.app.params.remove('sort').go()
	q.name.click -> $.app.params.set('sort', 'name').go()
	q.createdAt.click -> $.app.params.set('sort', 'createdAt').go()
