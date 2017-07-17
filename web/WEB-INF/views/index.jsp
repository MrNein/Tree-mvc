<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script defer src="<c:url value="/resources/js/jquery-3.2.1.min.js"/>" type="text/javascript"></script>
<script defer src="<c:url value="/resources/js/bootstrap.js"/>" type="text/javascript"></script>
<script defer src="<c:url value="/resources/js/dragAndDrop.js"/>" type="text/javascript"></script>
<script defer src="<c:url value="/resources/js/app.js"/>" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-theme.css"/>">

<html>
<head>
  <title>$Title$</title>
</head>
<body>

<ul id="${sessionId}" class = "sessionId">
  <li id="${root.id}" class="root li-element">
    <span class="arrow glyphicon glyphicon-menu-right" aria-hidden="true"></span>
    <span class="glyphicon glyphicon-folder-close" aria-hidden="true" ondrop="drop(event, this)" ondragover="allowDrop(event)"></span>
    <span class="node-name"> ${root.name} </span>
    <div class = "dropdown switch" >
      <a href = "#" class = "dropdown-toggle-js" data-toggle = "dropdown" >
        Edit
        <b class = "caret"> </b>
      </a>
      <ul class = "dropdown-menu" node-id="${root.id}" node-name="${root.name}" node-parentId="${root.parentId}">
        <li class="add-node"> <a href = "#" class = "" data-toggle = "modal" data-target = "#basicModal"> Add </a> </li>
        <li class="rename-node"> <a href = "#" class = "" data-toggle = "modal" data-target ="#basicRenameModal"> Rename </a> </li>
      </ul>
    </div>
    <ul class="children">

    </ul>
  </li>
</ul>

<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        <h3>Add new folder</h3>
      </div>


      <form class="form-add">
        <input name="name" type="text" class="form-control" required/>
        <input name="id" type="hidden" value ="" />
        <input name="parentId" type="hidden" value=""/>

        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary" >Add node</button>
        </div>
      </form>
    </div>
  </div>
</div>


<div class="modal fade" id="basicRenameModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body text-center">
        <h3>Rename folder</h3>
      </div>


      <form class="form-rename">
        <input name="name" class="form-control" type="text" value="" required/>
        <input name="id" type="hidden" value ="" />

        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary"  >Rename node</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div id="myModalBox" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- Заголовок модального окна -->
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">Houston we have a problem</h4>
      </div>
      <!-- Основное содержимое модального окна -->
      <div class="modal-body">
        You session is too old please reload page
      </div>
    </div>
  </div>
</div>

</body>
</html>
