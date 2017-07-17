function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev, folder) {
    ev.preventDefault();
    ev.stopPropagation();

    var movedObjId = ev.dataTransfer.getData("text");
    var $movedObj = $('#' + movedObjId);
    var oldParent = $('#'+ movedObjId).parent();
    var oldParentId = oldParent.parent().attr("id");

    var self = $(folder).parent();
    var selfId = self.attr("id");
    if ($movedObj.find('#' + selfId).length || selfId == movedObjId) {
        alert('Так нельзя :)');
        return;
    }

    var moveNode ={};
    moveNode.id = movedObjId;
    moveNode.parentId = selfId;
    moveNode.oldParentId = oldParentId;

    var object = form_to_object(moveNode);
    var dataToSend = {};
    dataToSend.sessionId = sessionId;
    dataToSend.moveNode = moveNode;

    $.ajax({
        cache: false,
        type: "POST",
        url: "/move_node",
        data: JSON.stringify(dataToSend),
        contentType: "application/json; charset=utf-8",
        //data: {id: movedObjId, parentId: selfId, oldParentId: oldParentId},
        success: function (data) {
            if (data == true) {
                if ($("#"+selfId).children(".glyphicon-menu-down").length > 0) {
                    $(self).children(".children").append($movedObj);
                }
                else {
                    $movedObj.remove();
                }
            }
            else
            {
                alert('One node is block, please repeat later');
            }
        }
    });
}
