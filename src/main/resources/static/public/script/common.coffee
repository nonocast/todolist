# usage:
# $.app.params.set('page', 4).build()
# $.app.params.get('page')

$.app = $.app or {}

class RequestParams
	constructor: ->
		@params = {}

		query = window.location.search.slice 1
		return if query is ''

		for each in query.split '&'
			q = each.split '='
			@params[q[0]] = q[1]

	has: (key) -> key of @params && @params[key].length > 0

	get: (key) -> decodeURIComponent @params[key]

	set: (key, value) ->
		@params[key] = value
		this

	remove: (keys...) ->
		delete @params[key] for key in keys
		this

	clear: ->
		@params = {}
		this

	build: ->
		$.param(@params)

	go: ->
		window.location.search = @build()

$.app.params = new RequestParams()
