menu = ->
	$("#menu-toggle").click (e) ->
		e.preventDefault()
		$('#wrapper').toggleClass('toggled')
		$('#menu-toggle').toggleClass('folded')
		if $("#menu-toggle").hasClass('folded')
			$("#menu-toggle>i").removeClass('fa-angle-double-left')
			$("#menu-toggle>i").addClass('fa-angle-double-right')
		else
			$("#menu-toggle>i").addClass('fa-angle-double-left')
			$("#menu-toggle>i").removeClass('fa-angle-double-right')

sidebar = ->
	p = $(".sidebar-nav").find("li a")

	items = {}
	['users', 'create_user', 'tasks'].forEach (x, i) -> items[x] = p[i]
	console.log $.page
	console.log items[$.page]
	$(items[$.app.page]).addClass('act')

window.page = (index) ->
	p = window.location.href
	[base, paramString] = p.split '?'
	paramString = "" unless paramString?
	params = paramString.split '&'
	params = params.filter (x) -> x.match(/^page=\d+/) == null
	params.push "page=#{index}"
	result = "#{base}?#{params.join('&').replace(/^&/, "")}"
	document.location.href = result

$ ->
	menu()
	sidebar()
