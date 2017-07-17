var myMap = new Map();

var sessionId ;

var countError = 0;

var MAXCOUNTERROR = 7;

var TIMETOREFRESH = 2000;

function createType(){
    this.ADD = 'ADD';
    this.RENAME = "RENAME";
    this.DELETE = 'DELETE';
    this.MOVE = 'MOVE';
}

var typeOperations = new createType();

$(document).ready(function() {


    var switcher = {};
    switcher.blue = $('.root').attr("id");
    switchHandle(switcher);
    sessionId = $('.sessionId').attr('id');




    refresh();
    setInterval('refresh()', TIMETOREFRESH);

    $(".dropdown-toggle-js").dropdown();


    $(document).on("submit", ".form-add", function (e) {

        $('#basicModal').modal('hide');
        var object = form_to_object($(this));
        var dataToSend = {};
        dataToSend.sessionId = sessionId;
        dataToSend.node = object;
        e.preventDefault();
        $.ajax({
            cache: false,
            type: "POST",
            url: "/node",
            dataType: 'json',
            data: JSON.stringify(dataToSend),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data > 0) {
                    var parentNode = $("#" + object.id);

                    if (parentNode.children(".glyphicon-menu-down").length > 0) {
                        parentNode.children(".children").append(createLiElement(data, object.name, object.parentId));
                        switchHandle(switcher);
                    }
                }
                else {
                    alert('Node is block, please wait a moment');
                }
            },
            error: function(e){
                if(countError > MAXCOUNTERROR)
                {
                    $("#myModalBox").modal('show');
                }
                else
                {
                    countError++;
                    alert('We have a problem ');
                }
            }
        });


    });

    $(document).on("click", ".dropdown-menu", function (e) {
        $('input[name=id]').val($(e.currentTarget).attr("node-id"));
        $('input[name=parentId]').val($(e.currentTarget).attr("node-parentId"));
    });

    $(document).on("click", ".arrow", function (e) {
        var param = $(e.currentTarget).parent().attr("id");
        var selectedObject = $("#" + param);
        selectedObject.children(".arrow").removeClass('arrow');
        addSpiner(param);
        $.ajax({
            cache: false,
            type: "GET",
            url: "/node",
            data: {id: param},
            success: function (data) {

                $("#" + switcher.blue).children('.node-name').removeClass('blue');
                switcher.blue = param;
                selectedObject.children('.node-name').addClass('blue');
                selectedObject.children(".glyphicon-menu-right").addClass('unvisible');

                setTimeout(function () {
                    selectedObject.children(".glyphicon-menu-right").addClass('glyphicon-menu-down');
                    selectedObject.children(".glyphicon-menu-right").removeClass('glyphicon-menu-right');
                    selectedObject.children(".glyphicon-folder-close").addClass('glyphicon-folder-open');
                    selectedObject.children(".glyphicon-folder-close").removeClass('glyphicon-folder-close');

                    data.forEach(function (item, i, arr) {
                        selectedObject.children(".children").append(createLiElement(item.id, item.name, item.parentId));
                    });

                    switchHandle(switcher);
                    deleteSpinner(param);
                    selectedObject.children(".glyphicon-menu-down").removeClass('unvisible');

                }, 2000)
            },
            error: function () {
                selectedObject.children(".glyphicon-menu-right").addClass('arrow');
            }
        });

    });


    $(document).on("click", ".glyphicon-menu-down", function (e) {
        var param = $(e.currentTarget).parent().attr("id");
        var selectedObject = $("#" + param);

        $("#" + switcher.blue).children('.node-name').removeClass('blue');
        switcher.blue = param;
        selectedObject.children('.node-name').addClass('blue');

        selectedObject.children(".glyphicon-menu-down").addClass('glyphicon-menu-right');
        selectedObject.children(".glyphicon-menu-down").removeClass('glyphicon-menu-down');
        selectedObject.children(".glyphicon-folder-open").addClass('glyphicon-folder-close');
        selectedObject.children(".glyphicon-folder-open").removeClass('glyphicon-folder-open');
        selectedObject.children(".children").children().remove();
        selectedObject.children(".glyphicon-menu-right").addClass('arrow');
    });


    $(document).on("click", ".rename-node", function (e) {
        $('input[name=id]').val($(e.currentTarget).parent().attr("node-id"));
        $('input[name=name]').val($(e.currentTarget).parent().attr("node-name"));
    });

    $(document).on("click", ".delete-node", function (e) {
        var param = $(e.currentTarget).parent().attr("node-id");
        var object = deleteNode_to_object($(e.currentTarget).parent());

        var dataToSend = {};
        dataToSend.sessionId = sessionId;
        dataToSend.node = object;

        $.ajax({
            cache: false,
            type: "POST",
            url: "/delete_node",
            data: JSON.stringify(dataToSend),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data == true) {
                    $("#" + param).remove();
                }
                else {
                    alert('Error for process delete, pleease repeat')
                }
            },
            error: function(e){
                if(countError > MAXCOUNTERROR)
                {
                    $("#myModalBox").modal('show');
                }
                else
                {
                    countError++;
                    alert('We have a problem ');
                }

            }
        });

    });

    $(document).on("submit", ".form-rename", function (e) {
        $('#basicRenameModal').modal('hide');
        var object = form_to_object($(this));

        var dataToSend = {};
        dataToSend.sessionId = sessionId;
        dataToSend.node = object;

        e.preventDefault();
        $.ajax({
            cache: false,
            type: "POST",
            url: "/rename_node",
            dataType: 'json',
            data: JSON.stringify(dataToSend),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data == true) {
                    $("#" + +object.id).children(".node-name").text(object.name);
                } else {
                    alert('Node is block, please wait a moment');
                }
            },
            error: function(e){
                if(countError > MAXCOUNTERROR)
                {
                    $("#myModalBox").modal('show');
                }
                else
                {
                    countError++;
                    alert('We have a problem ');
                }

            }
        });

    });

});


function form_to_object(selector) {
    var ary = $(selector).serializeArray();
    var obj = {};
    for (var a = 0; a < ary.length; a++) obj[ary[a].name] = ary[a].value;
    return obj;
}


function deleteNode_to_object(selector) {
    var ary = $(selector).serializeArray();
    var obj = {};
    //for (var a = 0; a < ary.length; a++) obj[ary[a].name] = ary[a].value;
    obj["id"] = selector.attr("node-id");
    obj["name"] = selector.attr("node-name");
    obj["parentId"] = selector.attr("node-parentid");
    return obj;
}

function createLiElement(id, name, parentId) {
    var result =
        '<li id=' + id + ' class="li-element" draggable="true" ondragstart="drag(event)">' +
        '<span class="arrow glyphicon glyphicon-menu-right" aria-hidden="true" ></span>' +
        '<span class="glyphicon glyphicon-folder-close" aria-hidden="true" ondrop="drop(event, this)" ondragover="allowDrop(event)"></span>' +
        '<span class="node-name">' + name  + '</span>' +
        '<div class = "dropdown switch" >' +
        '<a href = "#" class = "dropdown-toggle-js switch" data-toggle = "dropdown" >' +
        'Edit' +
        '<b class = "caret" > </b>' +
        '</a>' +
        '<ul class = "dropdown-menu" ' + 'node-id=' + '"' + id + '"' + ' node-name=' + '"' + name + '"' + ' node-parentId = ' + '"' + parentId + '"' + '>' +
        '<li> <a href = "#" data-toggle = "modal" data-target = "#basicModal" data-id> Add </a> </li>' +
        '<li class="rename-node"> <a href = "#" data-toggle = "modal" data-target ="#basicRenameModal"> Rename </a> </li>' +
        '<li class="delete-node"> <a href = "#"> Delete </a> </li>' +
        '</ul>' +
        '</div>' +
        '<ul class="children" >' +
        '</ul>' +
        '</li>';

    return result;
}

function addSpiner(id){
    var spiner = '<div class="page-preloader"><span class="spinner"></span></div>';
    $("#"+id).prepend(spiner);
}

function deleteSpinner(id){
    $("#"+id).children('.page-preloader').remove();
}

function switchHandle(switcher) {
    $(".switch a").click(function (e) {
        $("#" + switcher.blue).children('.node-name').removeClass('blue');
        switcher.blue = $(e.currentTarget).parent().children('ul').attr("node-id");
        $("#" + switcher.blue).children('.node-name').addClass('blue');
    });
}

function refresh(){
    var object = $('.li-element');
    var arr = [];
    for(var i=0; i<object.length; ++i){arr[i]=$(object[i]).attr('id')}

    var dataToSend = {};
    dataToSend.sessionId = sessionId;
    dataToSend.list = arr;

    if(countError < MAXCOUNTERROR) {
        $.ajax({
            cache: false,
            type: "POST",
            url: "/refresh",
            dataType: 'json',
            data: JSON.stringify(dataToSend),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.length > 0) {

                    data.forEach(function (item, i, data) {
                        if (myMap.get(item.node.id) != item.token) {
                            if (item.type == typeOperations.RENAME) {
                                reName(item.node);
                                myMap.set(item.node.id, item.token);
                            }
                            else if (item.type == typeOperations.DELETE) {
                                deleteNode(item.node);
                                myMap.set(item.node.id, item.token);
                            }
                            else if (item.type == typeOperations.ADD) {
                                if (myMap.get(item.node.parentId) != item.token) {
                                    addNode(item.node);
                                    myMap.set(item.node.parentId, item.token);
                                }
                            }
                            else if (item.type == typeOperations.MOVE) {
                                moveNode(item.node);
                                myMap.set(item.node.id, item.token);
                            }

                        }
                    });
                }
            },
            error: function (e) {

                if (countError > MAXCOUNTERROR) {
                    $("#myModalBox").modal('show');
                }
                else {
                    countError++;
                }
            }
        });
    }
    else
    {
        $("#myModalBox").modal('show');
    }
}


function reName(node){
    var selectedObject = $("#"+ node.id);

    selectedObject.children(".node-name").text(node.name);
}

function deleteNode(node){
    var selectedObject = $("#" + node.id);
    selectedObject.remove();
}

function addNode(node) {
    var parentNode = $("#" + node.parentId);
    var child = $('#' + node.id);
    if (child.length < 1) {
        if (parentNode.children(".glyphicon-menu-down").length > 0) {
            parentNode.children(".children").append(createLiElement(node.id, node.name, node.parentId));
        }
    }
}

function moveNode(node){
    var $movedObj = $('#' + node.id);
    var newParent = $('#' + node.parentId);

    if(newParent.children('#'+node.id).length < 1) {
        if (newParent.children(".glyphicon-menu-down").length > 0) {
            $(newParent).children(".children").append($movedObj);
        }
        else {
            $movedObj.remove();
        }
    }
}


