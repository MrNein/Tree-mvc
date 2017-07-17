package ru.splat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import ru.splat.model.Node;
import ru.splat.model.json.MoveSessionWrapper;
import ru.splat.model.json.RefreshNodeList;
import ru.splat.model.json.NodeToJSON;
import ru.splat.service.CommonService;
import ru.splat.temp.NodeUpdateWrapper;


/**
 * Created by Vadim on 06.07.2017.
 */
@Controller
@RequestMapping
public class MainController
{
    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/")
    public String getMainPage(Model model)
    {
        model.addAttribute("root", commonService.getRoot());
        model.addAttribute("sessionId", commonService.getSessionId());
        return "index";
    }


    @GetMapping(value = "/root")
    public @ResponseBody
    Node getRoot()
    {
        return commonService.getRoot();
    }


    @PostMapping(value = "/move_node")
    public @ResponseBody boolean moveNode(@RequestBody final MoveSessionWrapper node)
    {
        return commonService.moveNode(node);
    }


    @GetMapping("/node")
    public @ResponseBody
    List<Node> getChildNodes(@RequestParam("id") final long id)
    {
        return commonService.getChildNodes(id);
    }


    @PostMapping("/node")
    public @ResponseBody long addNode(@RequestBody final NodeToJSON node)
    {
        return commonService.addNode(node);
    }


    @PostMapping("/delete_node")
    public @ResponseBody boolean deleteNodes(@RequestBody final NodeToJSON node)
    {
        return commonService.deleteNodes(node);
    }


    @PostMapping("/rename_node")
    public @ResponseBody boolean renameNode(@RequestBody final NodeToJSON node)
    {
        return commonService.renameNode(node);
    }


    @PostMapping("/refresh")
    public  @ResponseBody List<NodeUpdateWrapper> getFreshTreeData(@RequestBody final RefreshNodeList list)
    {
        return commonService.returnUpdatedNodes(list);
    }
}
