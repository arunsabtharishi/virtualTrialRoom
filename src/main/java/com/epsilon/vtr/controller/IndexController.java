package com.epsilon.vtr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
        public String getHomePage() {
            return "home";
        }

      @RequestMapping(value={"/profiles"}, method = RequestMethod.GET)
        public String getProfilePage() {
            return "profileManagement";
        }

      @RequestMapping(value={"/trailRoom"},method = RequestMethod.GET)
      public String getNotificationPage() {
          return "trailRoom";
      }


      @RequestMapping(value={"/inventory"},method = RequestMethod.GET)
      public String getItemsPage() {
          return "items";
      }

}
