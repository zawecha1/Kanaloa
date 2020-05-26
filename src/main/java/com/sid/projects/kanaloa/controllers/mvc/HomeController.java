package com.sid.projects.kanaloa.controllers.mvc;

import com.sid.projects.kanaloa.domain.repositories.DiscordBotGuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    final DiscordBotGuildRepository discordBotGuildRepository;

    @Autowired
    public HomeController(DiscordBotGuildRepository discordBotGuildRepository)
    {
        this.discordBotGuildRepository = discordBotGuildRepository;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String showHomePage(@AuthenticationPrincipal OAuth2User userDetails,
                               HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse,
                               Model model) {
        return "login";
    }
}
