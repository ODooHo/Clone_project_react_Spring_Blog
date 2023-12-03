package com.dooho.board.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController //RestController 해당 클래스를 controller 레이어로 인식하도록 함 Rest한 형태(@Controller + @ResponseBody)
@RequestMapping("/") //RequestMapping request의 url의 패턴을 보고 해당하는 패턴이 왔을때 해당 클래스를 실행
class MainController {
    @GetMapping("")
    fun hello(): String {
        return "Connection Successful"
    }


}