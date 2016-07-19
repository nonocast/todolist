$ ->
	q = {}
	[q.id, q.createdAt] = ($(each) for each in $('#sort-panel button'))
	sort = if $.app.params.has 'sort' then $.app.params.get('sort') else 'id'
	q[sort.replace(/,.*$/, "")].addClass 'active'
	q.id.click -> $.app.params.remove('page').remove('sort').go()
	q.createdAt.click -> $.app.params.remove('page').set('sort', 'createdAt,desc').go()

	$('#search-panel input').on 'input propertychange', ->
		$("#search-panel .form-control-clear").toggleClass 'hidden', $('#search-panel input').val().length is 0


	$('#search-panel .form-control-clear').click ->
		$('#search-panel input').val('')
		$.app.params.clear().go()


	if $.app.params.has('q')
		$("#search-panel input").val $.app.params.get('q').replace(/\+/g, ' ')
		$("#search-panel .form-control-clear").removeClass 'hidden'

	$("#search-panel button").click ->
		text = $("#search-panel input").get(0).value
		$.app.params.clear().set('q', text).go()
