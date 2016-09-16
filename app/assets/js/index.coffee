$ ->
  $.get "/competitors", (competitors) ->
    $.each competitors, (index, competitor) ->
      name = $("<div>").addClass("name").text competitor.name
      $("#competitors").append $("<li>").append(name)