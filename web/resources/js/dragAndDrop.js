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

    var self = $(folder).parent();
    var selfId = self.attr("id");
    if ($movedObj.find('#' + selfId).length || selfId == movedObjId) {
        alert('Так нельзя :)');
        return;
    }

    $.ajax({
        type: "POST",
        url: "/move_node",
        data: {id: movedObjId,parentId: selfId},
        success: function (data) {
            if (data == true) {
                   if ($("#"+selfId).children(".glyphicon-menu-down").length > 0) {
                       $(self).children(".children").append($movedObj);
                   }
                   else {
                       $movedObj.remove();
                   }
            }
        }
    });
}
