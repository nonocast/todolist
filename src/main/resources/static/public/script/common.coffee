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

	has: (key) -> key of @params

	get: (key) -> @params[key]

	set: (key, value) ->
		@params[key] = value
		this

	remove: (key) ->
		delete @params[key]
		this

	build: ->
		$.param(@params)

	go: ->
		window.location.search = @build()

$.app.params = new RequestParams()
